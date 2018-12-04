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
public class KakaoOauth2Controller {
    //제일 중요한 5가지 정보
    @Value("${kakao_client_id}")
    String kakao_client_id;
    @Value("${kakao_client_secret}")
    String kakao_client_secret;
    @Value("${kakao_rest_api_app_key}")
    String kakao_rest_api_app_key;


    //endpoint 3가지
    private static String kakao_authorization_endpoint = "https://kauth.kakao.com/oauth/authorize";
    private static String kakao_token_endpoint = "https://kauth.kakao.com/oauth/token"; //토큰을 발급 받기위한 토큰엔드포인트
    private static String kakao_redirect_uri = "http://localhost:8080/oauth/kakao_callback";

    //static 내용 수정은 재시작 해야함

    @Inject
    private UserService userService;

//    private OAuth2Template kakaoOauthTemplate = new OAuth2Template(
//            kakao_client_id,
//            kakao_client_secret,
//            kakao_authorization_endpoint,
//            kakao_token_endpoint
//    );

    @RequestMapping("/kakao_authorization_code")
    public String kakao_authorization_code() {
        OAuth2Template kakaoOauthTemplate = new OAuth2Template(
                kakao_client_id,
                kakao_client_secret,
                kakao_authorization_endpoint,
                kakao_token_endpoint
        );
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(kakao_redirect_uri);

        String authorizeUrl = kakaoOauthTemplate.buildAuthorizeUrl(
        GrantType.AUTHORIZATION_CODE, parameters);      //authorizeUrl 만드는 것
        System.out.println("authorizeUrl: " + authorizeUrl);
        return "redirect:" + authorizeUrl;
    }


    @RequestMapping("/kakao_callback")
    public String kakao_callback(Model model, HttpServletRequest request, @RequestParam String code) throws Exception {
        OAuth2Template kakaoOauthTemplate = new OAuth2Template(
                kakao_client_id,
                kakao_client_secret,
                kakao_authorization_endpoint,
                kakao_token_endpoint
        );

        System.out.println("Kakao Callback is called!!");
        System.out.println(code);
        //이 code가 매우 중요함. 카카오톡에서 당신은 토큰을 발급받을 자격이 있습니다(OAuth2인증이 되었다.)라고 주는 것.



        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setOutputStreaming(false);
        kakaoOauthTemplate.setRequestFactory(simpleClientHttpRequestFactory);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("client_id", kakao_client_id);
        multiValueMap.add("client_secret", kakao_client_secret);


        //이 부분이 제일 중요한 코드(3, 4번째 순서, 토큰을 발급받는 코드)
        AccessGrant accessGrant = kakaoOauthTemplate.exchangeForAccess(
                code,
                kakao_redirect_uri,
                multiValueMap
        );

        System.out.println("------ AccessGrant ------");
        System.out.println(accessGrant.getAccessToken());
        System.out.println(accessGrant.getRefreshToken());
        System.out.println(accessGrant.getExpireTime());
        System.out.println(accessGrant.getScope());
        System.out.println();

        return userService.kakaoLogin(request, accessGrant);
    }
}
