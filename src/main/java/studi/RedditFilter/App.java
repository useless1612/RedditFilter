package studi.RedditFilter;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String BASE_URL = "https://oauth.reddit.com/r/";
    private static final String accToken = "access_token";


    public static void main( String[] args ) throws IOException, JSONException
    {
        String subredditName = "python";
        SortEnum sortEnum = SortEnum.NEW;
        System.out.println( "Hello World!" );
        JSONObject jo = RedditOAuth.getToken();
        System.out.println(jo);
        JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString(accToken));

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
        


  

    }
    
    
    
    public static Posts useFilter(String subredditName, SortEnum sortEnum, String keep, String remove) throws IOException, JSONException {

        KeepOrRemoveList keepList = new KeepOrRemoveList();
        keepList.add(keep, true);
        KeepOrRemoveList removeList = new KeepOrRemoveList();
        removeList.add(remove, true);
        

        if (subredditName.equals("")) {
    		subredditName = "python";
		}
    	
        JSONObject jo = RedditOAuth.getToken();


		JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString(accToken));
    	Posts posts = Posts.createFromJsonObject(response);

        
       Posts filteredPosts = posts.filter(keepList, removeList);

       return filteredPosts;
        
        
        
           
         
        
        
    }
    
    
    
    public static Posts getonlyPostsforGui(String subredditName, SortEnum sortEnum) throws IOException, JSONException {
   	if (subredditName.equals("")) {
    		subredditName = "python";
		}
    	

        JSONObject jo = RedditOAuth.getToken();
        JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString(accToken));
    	Posts posts = Posts.createFromJsonObject(response);
  
        
        return posts;
    		 
    	 }
	    	
    
    

    

}
