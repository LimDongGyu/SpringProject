package koreatech.cse.controller.oauth2;

import koreatech.cse.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/oauth")
public class FacebookOauth2Controller {

    //이런식으로 config.properties 파일로 빼는 이유는, 인증 키나 이런 건 민감한 보안성을 필요로 하기 때문에 git 같은
    //곳에 올리지 말라고 하는 것이다.
    @Value("${facebook_client_id}")
    private String facebook_client_id;      //본인 것으로 추가하기

    @Value("${facebook_client_secret}")
    private String facebook_client_secret;  //본인 것으로 추가하기


    private static String facebook_authorization_endpoint = "https://www.facebook.com/dialog/oauth";
    private static String facebook_token_endpoint = "https://graph.facebook.com/v3.2/oauth/access_token";
    private static String facebook_redirect_uri = "http://localhost:8080/oauth/facebook_callback";

    @Inject
    private UserService userService;

//    private OAuth2Template facebookOAuth2Template = new OAuth2Template(
//            facebook_client_id,
//            facebook_client_secret,
//            facebook_authorization_endpoint,
//            facebook_token_endpoint
//    );

    @RequestMapping("/facebook_authorization_code")
    public String facebook_authorization_code() {
        OAuth2Template facebookOAuth2Template = new OAuth2Template(
                facebook_client_id,
                facebook_client_secret,
                facebook_authorization_endpoint,
                facebook_token_endpoint
        );

        System.out.println(facebook_client_id);
        System.out.println(facebook_client_secret);

        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(facebook_redirect_uri);
        parameters.setScope("public_profile,email,user_friends,user_likes");
        //방금전에 friends 를 추가한게 Scope임.

        String authorizeUrl = facebookOAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
        System.out.println("authorizeUrl: " + authorizeUrl);

        return "redirect:" + authorizeUrl;
    }

    @RequestMapping("/facebook_callback")
    public String facebook_callback(Model model, HttpServletRequest request, @RequestParam(required=true) String code) throws Exception {
        System.out.println("Facebook Callback is called!!");
        System.out.println(code);

        OAuth2Template facebookOAuth2Template = new OAuth2Template(
                facebook_client_id,
                facebook_client_secret,
                facebook_authorization_endpoint,
                facebook_token_endpoint
        );

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setOutputStreaming(false);
        facebookOAuth2Template.setRequestFactory(simpleClientHttpRequestFactory);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("client_id", facebook_client_id);
        multiValueMap.add("client_secret", facebook_client_secret);


        //실제로 중요한 코드
        AccessGrant accessGrant = facebookOAuth2Template.exchangeForAccess(
                code,
                facebook_redirect_uri,
                multiValueMap
        );

        System.out.println("------ AccessGrant ------");
        System.out.println(accessGrant.getAccessToken());
        System.out.println(accessGrant.getRefreshToken());
        System.out.println(accessGrant.getExpireTime());
        System.out.println(accessGrant.getScope());
        System.out.println();


        //페이스북에 남겨놓은 정보를 가져오기 전 로그인
        return userService.facebookLogin(request, accessGrant);

    }
}