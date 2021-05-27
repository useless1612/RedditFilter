package studi.RedditFilter;

import java.io.IOException;
// import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String BASE_URL = "https://oauth.reddit.com/r/";

    // private Posts posts;
    // private Posts filteredPosts;
    // private KeepOrRemoveList keepList;
    // private List<String> removeList;
    // private String subredditName;
    // private SortEnum sortEnum;
    // private JSONObject jo;

    public static void main( String[] args ) throws IOException, JSONException
    {
        String subredditName = "python";
        SortEnum sortEnum = SortEnum.NEW;
        System.out.println( "Hello World!" );
        //String test = RedditOAuth.getUserAuthUrl("test").toString();
        JSONObject jo = RedditOAuth.getToken();
        System.out.println(jo);
        JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString("access_token"));

//  Filter Keep Weg
//  Subreddit ändert sich

        Posts posts = Posts.createFromJsonObject(response);
        KeepOrRemoveList keepList = new KeepOrRemoveList();
        keepList.add("python", true);
        KeepOrRemoveList removeList = new KeepOrRemoveList();
        removeList.add("fiddle", true);
        Posts filteredPosts = posts.filter(keepList, removeList);

        System.out.println(response);
        for (Post p : filteredPosts) {
            System.out.printf("Filtered POST Name:\n%s\n\n", p.getTitle());
        }

        for (Post p : posts) {
            System.out.printf("POST Name:\n%s\n\n", p.getTitle());
        }

        // SortEnum[] sortEnums = SortEnum.values();
        // mock data should be passed from user input

    }
}
