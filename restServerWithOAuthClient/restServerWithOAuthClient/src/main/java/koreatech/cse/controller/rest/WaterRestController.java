package koreatech.cse.controller.rest;

import koreatech.cse.domain.rest.Water;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.List;

@Controller
public class WaterRestController {
    @Value("${water_service_key}")
    String water_service_key;

    @RequestMapping("/water")
    public void getWater(){
        System.out.println("Testing GET METHOD -----/water ");

        String strUrl = "http://api.data.go.kr/openapi/appn-mnrlsp-info-std";
        strUrl += "?serviceKey=" + water_service_key;
        strUrl += "&type=json";
        strUrl += "&s_page=0";
        strUrl += "&s_list=1";

        System.out.println(strUrl);

        RestTemplate restTemplate = new RestTemplate();

        try{
            System.out.println("try");
            ResponseEntity<Water> waterResponseEntity
                    = restTemplate.getForEntity(strUrl, Water.class);
            Water water = waterResponseEntity.getBody();
            System.out.println(water);
            System.out.println("/try");
        }catch (HttpClientErrorException e){
            System.out.println("catch");
            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
            System.out.println("/catch");
        }
    }

    @RequestMapping(value="/water/{instt_name}", method= RequestMethod.GET, produces = "application/json")
    public String springRequest(List list, @PathVariable String instt_name) throws IOException {
        System.out.println(instt_name);

        //textblank.getInstituteName(instt_name);
        String strUrl = "http://api.data.go.kr/openapi/appn-mnrlsp-info-std";
        strUrl += "?serviceKey=" + water_service_key;
        strUrl += "&type=json";
        strUrl += "&s_page=0";
        strUrl += "&s_list=10";
        strUrl += "&instt_nm=" + URLEncoder.encode(instt_name, "UTF-8");

        /*MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List> waterlistResponseEntity = restTemplate.exchange(
                strUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );*/

        System.out.println(strUrl);

        HttpURLConnection connection = null;
        try {
            URL url = new URL(strUrl);
            URLConnection urlConnection = url.openConnection();
            connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode() + e.getStatusText());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "MS949"));
        String urlString = "";
        String current;
        while ((current = in.readLine()) != null) {
            urlString += current + "\n";
        }

       //list = waterlistResponseEntity.getBody();
        System.out.println(urlString);
        //System.out.println(list);
        return "findSpring";
    }
}