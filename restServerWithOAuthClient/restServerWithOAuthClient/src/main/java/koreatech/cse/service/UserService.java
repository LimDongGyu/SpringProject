package koreatech.cse.service;

import koreatech.cse.domain.Authority;
import koreatech.cse.domain.User;
import koreatech.cse.domain.oauth2.kakao.KakaoProfile;
import koreatech.cse.domain.oauth2.facebook.FacebookProfile;
import koreatech.cse.repository.AuthorityMapper;
import koreatech.cse.repository.UserMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Inject
    private UserMapper userMapper;
    @Inject
    private AuthorityMapper authorityMapper;
    @Inject
    private PasswordEncoder passwordEncoder;

    private HashMap<Integer, AccessGrant> kakaoAccessTokenMap = new HashMap();
    private HashMap<Long, AccessGrant> facebookAccessTokenMap = new HashMap();

    public Boolean signup(User user) {
        if(user.getEmail() == null || user.getPassword() ==  null)
            return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        Authority authority = new Authority();
        authority.setUserId(user.getId());
        authority.setRole("ROLE_USER");                     //사용자 권한
        authorityMapper.insert(authority);

        if(user.getEmail().contains("admin")) {
            Authority adminAuthority = new Authority();
            adminAuthority.setUserId(user.getId());
            adminAuthority.setRole("ROLE_ADMIN");
            authorityMapper.insert(adminAuthority);
        }

        System.out.println("user created :" + new Date());
        return true;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password.");
        }
        List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        System.out.println("user = " + user);
        return user;
    }

    public String kakaoLogin(HttpServletRequest request, AccessGrant accessGrant) {
        KakaoProfile kakaoProfile = getKakaoProfile(accessGrant);

        //데이터베이스가 유저가 등록되어 있는지 확인함.
        User user = userMapper.findByEmail(kakaoProfile.getId().toString());
        if (user == null) {                                             //최초 가입시
            user = new User();                                          //사용자 객체 생성
            user.setEmail(kakaoProfile.getId().toString());             //카카오 아이디
            user.setName(kakaoProfile.getProperties().getNickname());   //카카오 닉네임
            user.setAge(-1);
            user.setPassword("0000");
            signup(user);   //회원가입 시켜버림

            /*
            private HashMap<Integer, AccessGrant> kakaoAccessTokenMap = new HashMap();
            private HashMap<Long, AccessGrant> facebookAccessTokenMap = new HashMap();
             */
            //다른 요청을 할 때 AccessToken이 필요할지 모르니 보관하고 있기 위해서 위 코드가 필요하다.
            kakaoAccessTokenMap.put(kakaoProfile.getId(), accessGrant);
            System.out.println(kakaoAccessTokenMap + "*****");
        }

        List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "0000", user.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        return "redirect:/";
    }

    public String facebookLogin(HttpServletRequest request, AccessGrant accessGrant) throws Exception{
        FacebookProfile facebookProfile = getFacebookProfile(accessGrant);

        User user = userMapper.findByEmail(facebookProfile.getId().toString());
        if (user == null) { //사용자 정보가 없으면 생성
            user = new User();
            user.setEmail(facebookProfile.getId().toString());
            user.setName(facebookProfile.getName());
            user.setAge(-2);
            user.setPassword("0000");
            signup(user);

            facebookAccessTokenMap.put(facebookProfile.getId(), accessGrant);
            System.out.println(facebookAccessTokenMap + "*****");
        }

        List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "0000", user.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        return "redirect:/";        //HomeController . hello로
    }

    public HashMap<Integer, AccessGrant> getKakaoAccessTokenMap() {
        return kakaoAccessTokenMap;
    }

    public HashMap<Long, AccessGrant> getFacebookAccessTokenMap() {
        return facebookAccessTokenMap;
    }

    public KakaoProfile getKakaoProfile(AccessGrant accessGrant) {
        RestTemplate restTemplate = new RestTemplate();
        KakaoProfile kakaoProfile = null;
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", "Bearer " + accessGrant.getAccessToken());

            String kakao_profile_url = "https://kapi.kakao.com/v2/user/me";

            ResponseEntity<KakaoProfile> kakaoProfileResponseEntity = restTemplate.exchange(
                    kakao_profile_url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KakaoProfile.class
            );

            kakaoProfile = kakaoProfileResponseEntity.getBody();
            System.out.println("!!!! " + kakaoProfile);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
        }
        return kakaoProfile;
    }

    public FacebookProfile getFacebookProfile(AccessGrant accessGrant) {
        RestTemplate restTemplate = new RestTemplate();
        FacebookProfile facebookProfile = null;
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", "Bearer " + accessGrant.getAccessToken());

            String facebook_profile_url = "https://graph.facebook.com/v3.2/me?fields=email,name,id,friends";

            ResponseEntity<FacebookProfile> facebookProfileResponseEntity = restTemplate.exchange(
                    facebook_profile_url,
                    HttpMethod.GET,
                    new HttpEntity<Object>(headers),
                    FacebookProfile.class       //deserialization
            );

            facebookProfile = facebookProfileResponseEntity.getBody();
            System.out.println("!!! " + facebookProfile);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
        }
        return facebookProfile;
    }
}
