package studi.RedditFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Collections;

import retrofit2.Response;

public class Login {
    
String clientId = "NHQGnCG793ryFQ";
String secretKey = "905JHnVZgHzp2FHc6CQLp6y2fcseEA";
String username = "notkillbob8";
String password = "m;ke5(hl";


private void getAuthToken(){
//     RestTemplate restTemplate = new RestTemplate();
//     HttpHeaders headers = new HttpHeaders();
//     //Different login details as I had to re-create the app
//     headers.setBasicAuth(clientId, secretKey);
//     headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//     headers.put("User-Agent",
//             Collections.singletonList("tomcat:com.e4developer.e4reddit-test:v1.0 (by /u/bartoszjd)"));
//     String body = "grant_type=password&username=bartoszjd&password=thisissecret";
//     HttpEntity<String> request
//             = new HttpEntity<>(body, headers);
//     String authUrl = "https://www.reddit.com/api/v1/access_token";
//     Response<String> response = restTemplate.postForEntity(
//             authUrl, request, String.class);
//     ObjectMapper mapper = new ObjectMapper();
//     Map<String, Object> map = new HashMap<>();
//     try {
//         map.putAll(mapper
//                 .readValue(response.getBody(), new TypeReference<Map<String,Object>>(){}));
//     } catch (IOException e) {
//         e.printStackTrace();
//     }
//     System.out.println(response.getBody());
//     return String.valueOf(map.get("access_token"));

 }

}