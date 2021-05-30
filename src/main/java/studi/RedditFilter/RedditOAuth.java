package studi.RedditFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;


// Reddit's OAUTH docs are here:
// https://github.com/reddit/reddit/wiki/OAuth2
// http://www.reddit.com/r/redditdev/comments/218wd7/oauth_20_you_asked_i_listened_updated_and_more/ 


// Docs for Google HTTP lib, and GAE urlfetch:
// https://code.google.com/p/google-http-java-client/
// https://developers.google.com/appengine/docs/java/urlfetch/

// Apache License 
// copyright 2014 /u/redlist-admin

// You will need to comment out or replace my custom Log statements.

public class RedditOAuth {

    // This line is GAE specific!  for detecting when running on local admin
    // public static final boolean production = true;
    // (SystemProperty.Environment.Value.Production == SystemProperty.environment.value());

    public static final String OAUTH_API_DOMAIN = "https://oauth.reddit.com";

    // Step 1. Send user to auth URL
    public static final String OAUTH_AUTH_URL = "https://ssl.reddit.com/api/v1/authorize";
    // https://ssl.reddit.com/api/v1/authorize?client_id=CLIENT_ID&response_type=TYPE&state=RANDOM_STRING&redirect_uri=URI&duration=DURATION&scope=SCOPE_STRING

    // Step 2. Reddit sends user to REDIRECT_URI
    // private static final String REDIRECT_URI = production ? "http://127.0.0.1:8888" + "/auth"
    //         : "http://127.0.0.1:8888/auth";

    // Step 3. Get token
    public static final String OAUTH_TOKEN_URL = "https://ssl.reddit.com/api/v1/access_token";

    // I think it is easier to create 2 reddit apps (one with 127.0.0.1 redirect URI)
    public static final String MY_APP_ID = "NHQGnCG793ryFQ";
    public static final String MY_APP_SECRET = "905JHnVZgHzp2FHc6CQLp6y2fcseEA";

    public static final String SCOPE_ID = "identity";

    // Field name in responses
    public static final String ACCESS_TOKEN_NAME = "access_token";
    public static final String REFRESH_TOKEN_NAME = "refresh_token";

    public static final String USERNAME = "notkillbob8";
    public static final String PASSWORD = "AsDf1234";

    public static boolean permanentAccess = true;

/*
    public static String getUserAuthUrl(String state) {
        String duration = permanentAccess ? "permanent" : "temporary";
        String url = OAUTH_AUTH_URL + "?client_id=" + MY_APP_ID + "&response_type=code&state=" + state
                + "&redirect_uri=" + REDIRECT_URI + "&duration=" + duration;

        // scopes: modposts, identity, edit, flair, history, modconfig, modflair, modlog, modposts, modwiki,
        // mysubreddits, privatemessages, read, report, save, submit, subscribe, vote, wikiedit, wikiread, etc.
        return url;
    }
*/

    // The Google Java HTTP library has a 'pluggable' architecture - the following line selects the URLFetch transport,
    // which is the native HTTP api for GAE.  'NetHttpTransport()' would be a more generic alternative.
    //public static final HttpTransport HTTP_TRANSPORT = new UrlFetchTransport();
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    
    // public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    public static JSONObject getToken() throws IOException {
        // Log.v( "getToken for code=" + code );
        GenericUrl url = new GenericUrl(OAUTH_TOKEN_URL);
        Map<String, String> params = new HashMap<String, String>(3);
        // params.put("grant_type", "authorization_code");
        // params.put("code", code);
        // params.put("redirect_uri", REDIRECT_URI);
        params.put("grant_type", "password");
        params.put("username", USERNAME);
        params.put("password", PASSWORD);
        HttpContent hc = new UrlEncodedContent(params);

        HttpRequestFactory requestFactory = HTTP_TRANSPORT
                .createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        // request.setParser( new JsonObjectParser(JSON_FACTORY) );
                        request.getHeaders().setBasicAuthentication(MY_APP_ID, MY_APP_SECRET).setUserAgent("MyBot/0.0.1");
                    }
                });

        HttpRequest request = requestFactory.buildPostRequest(url, hc);
        HttpResponse response = request.execute();

        JSONObject jo = null;
        try {
            if (response.isSuccessStatusCode()) {

                String json = response.parseAsString();
                // Log.i("Success with " + json);

                // Parse with org.json
                JSONTokener tokener = null;
                tokener = new JSONTokener( json );
                jo = new JSONObject(tokener);

                // Sample response:
                // {"access_token": "cdkVPfKww5R0D1v-MJAD89Y19QM",
                // "token_type": "bearer",
                // "expires_in": 3600,
                // "refresh_token": "vzZ0PP0A4k-twzSuVyvRN7uH2JY",
                // "scope": "identity"}
            } else
            System.out.println("hat nicht geklappt" + response.getStatusCode());
                // Log.w("Request failed with " + response.getStatusCode());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            response.disconnect();
        }

        return jo;
    }

    public static JSONObject refreshToken(String reftoke) throws IOException {
        // Log.v( "refreshToken with reftoke=" + reftoke );
        GenericUrl url = new GenericUrl(OAUTH_TOKEN_URL);
        Map<String, String> params = new HashMap<String, String>(3);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", reftoke);
        HttpContent hc = new UrlEncodedContent(params);

        HttpRequestFactory requestFactory = HTTP_TRANSPORT
                .createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.getHeaders().setBasicAuthentication(MY_APP_ID, MY_APP_SECRET);
                    }
                });

        HttpRequest request = requestFactory.buildPostRequest(url, hc);
        HttpResponse response = request.execute();

        JSONObject jo = null;
        try {
            if (response.isSuccessStatusCode()) {

                String json = response.parseAsString();
                // Log.i("Success with " + json);

                JSONTokener tokener = null;
                tokener = new JSONTokener( json );
                jo = new JSONObject(tokener);

                // Sample response:
                // { "access_token": Your access token,
                //    "token_type": "bearer",
                //    "expires_in": Unix Epoch Seconds,
                //    "scope": A scope string, }                
            } else
            System.out.println("hat nicht geklappt" + response.getStatusCode());
                // Log.w("Request failed with " + response.getStatusCode());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            response.disconnect();
        }

        return jo;
    }


    // http://www.reddit.com/dev/api
    // https://oauth.reddit.com/api/v1/me
    public static final String ENDPOINT_ID = OAUTH_API_DOMAIN + "/api/v1/me";
    public static final String ENDPOINT_SUBS = OAUTH_API_DOMAIN + "/subreddits/mine/";  // a list endpoint
        // Followed by 'subscriber', 'contributor', or 'moderator'


    public static JSONObject getInfo( final String token ) throws IOException {
        // Log.v( "getInfo with token=" + token );

        GenericUrl url = new GenericUrl( ENDPOINT_ID );

        HttpRequestFactory requestFactory = HTTP_TRANSPORT
                .createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.getHeaders().setAuthorization( "bearer " + token );                     
                    }
                });

        HttpRequest request = requestFactory.buildGetRequest( url );
        HttpResponse response = request.execute();

        JSONObject jo = null;

        try {
            if (response.isSuccessStatusCode()) {

                String json = response.parseAsString();

                //Log.i("Success with " + json);
                // Sample response:
                // {"name": "myName", "created": 1346020929.0, "created_utc": 1346017329.0, "link_karma": 1308, 
                // "comment_karma": 32602, "over_18": true, "is_gold": true, "is_mod": false, "has_verified_email": true, "id": "76gyp"}

                // Parse with org.json
                JSONTokener tokener = null;
                tokener = new JSONTokener( json );
                jo = new JSONObject(tokener);

            } else
            System.out.println("hat nicht geklappt" + response.getStatusCode());
                // Log.w("Request failed with " + response.getStatusCode());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            response.disconnect();
        }

        return jo;
    }

    public static JSONObject getSubreddit( final String baseUrl, final String subredditName, final SortEnum sortEnum, final String token) throws IOException {
        return getSubreddit(baseUrl, subredditName, sortEnum, token, 100);
    }

    // A generic get fn to build the rest of my API calls around
    public static JSONObject getSubreddit( final String baseUrl, final String subredditName, final SortEnum sortEnum, final String token, final int limit) throws IOException {
        // Log.v( "get for URL=" + surl );

        GenericUrl url = new SubredditUrl( baseUrl + subredditName.replaceAll("/", "") + "/" + sortEnum.getName() ).setLimit(limit);

        HttpRequestFactory requestFactory = HTTP_TRANSPORT
                .createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        // request.setParser( new JsonObjectParser(JSON_FACTORY) );
                        request.getHeaders().setAuthorization( "bearer " + token );                     
                    }
                });

        HttpRequest request = requestFactory.buildGetRequest( url );
        HttpResponse response = request.execute();

        JSONObject jo = null;

        // Note the recommended use of finally { disconnect() }
        try {
            if (response.isSuccessStatusCode()) {

                String json = response.parseAsString();
                // Log.i("Success with " + json);

                // Parse with org.json
                JSONTokener tokener = null;
                tokener = new JSONTokener( json );
                jo = new JSONObject(tokener);

                // Or Parse directly into Java objects using Jackson

            } else
            System.out.println("hat nicht geklappt" + response.getStatusCode());
                // Log.w("Request failed with " + response.getStatusCode());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            response.disconnect();
        }

        return jo;
    }
}
