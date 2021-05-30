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

//     private static Posts posts;
//     private Posts filteredPosts;
//     private KeepOrRemoveList keepList;
//     private List<String> removeList;
//     private String subredditName;
//     private SortEnum sortEnum;
//     private JSONObject jo;

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
    
    
    
    public static Posts useFilter(String subredditName, SortEnum sortEnum, String keep, String remove) throws IOException, JSONException {

        KeepOrRemoveList keepList = new KeepOrRemoveList();
        keepList.add(keep, true);
        KeepOrRemoveList removeList = new KeepOrRemoveList();
        removeList.add(remove, true);
        

        if (subredditName.equals("")) {
    		subredditName = "python";
		}
    	
        JSONObject jo = RedditOAuth.getToken();

        JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString("access_token"));
    	Posts posts = Posts.createFromJsonObject(response);

        
       Posts filteredPosts = posts.filter(keepList, removeList);

       return filteredPosts;
        
        







//         String subredditName = "python";
//         SortEnum sortEnum = SortEnum.NEW;
//         //String test = RedditOAuth.getUserAuthUrl("test").toString();
//         JSONObject jo = RedditOAuth.getToken();
//         System.out.println(jo);
//         JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString("access_token"));

// //  Filter Keep Weg
// //  Subreddit ändert sich

//         Posts posts = Posts.createFromJsonObject(response);
//         Posts filteredPosts = posts.filter(keepList, removeList);
        
//         String[] guipostfilt = new String[100];
//         String[] guipost = new String[100];
//         int i = 0;

//         System.out.println(response);
//         for (Post p : filteredPosts) {
//             System.out.printf("Filtered POST Name:\n%s\n\n", p.getTitle());
//             guipostfilt[i] = p.getTitle();
//             i++;
//         }

//         for (Post p : posts) {
//             System.out.printf("POST Name:\n%s\n\n", p.getTitle());
//             guipost[i] = p.getTitle();
//             i++;
//         }
        
//         Object [] row = {guipost};
        
        
        
        
         
        
        
    }
    
    
    
    public static Posts getonlyPostsforGui(String subredditName, SortEnum sortEnum) throws IOException, JSONException {
   	if (subredditName.equals("")) {
    		subredditName = "python";
		}
    	
//        SortEnum sortEnum = SortEnum.NEW;
        JSONObject jo = RedditOAuth.getToken();
//        System.out.println(jo);
        JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString("access_token"));
    	Posts posts = Posts.createFromJsonObject(response);
  

//        String[] guipost = new String[100];
//        String[] guipermlink = new String[100];
//        int i = 0;
//
//        for (Post p : posts) {
//            System.out.printf("POST Name:\n%s\n\n", p.getTitle());
//            guipost[i] = p.getTitle();
//            guipermlink[i] = p.getPermalink();
//            i++;
//        }
//
//        Object [] row = {guipost[1], guipermlink[2]};
        
        return posts;
    		 
    	 }
	    	
    
    
    
    public static Object[] getPostsforGui(String subredditName) throws IOException, JSONException {
   	if (subredditName.equals("")) {
    		subredditName = "python";
		}
    	
        SortEnum sortEnum = SortEnum.HOT;
        JSONObject jo = RedditOAuth.getToken();
//        System.out.println(jo);
        JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString("access_token"));
    	Posts posts = Posts.createFromJsonObject(response);
  

        String[] guipost = new String[100];
        String[] guipermlink = new String[100];
        int i = 0;

        for (Post p : posts) {
            System.out.printf("POST Name:\n%s\n\n", p.getTitle());
            guipost[i] = p.getTitle();
            guipermlink[i] = p.getPermalink();
            i++;
        }

        Object [] row = {guipost[1], guipermlink[2]};
        
        return row;
    		 
    	 }
	    	
    
    
    
    
    
    
//
//	public static Object getPost() throws IOException, JSONException {
//		Posts posts = getPostsforGui("");
//		
//        for (Post p : posts) {
//            System.out.printf("POST Name:\n%s\n\n", p.getTitle());
////            System.out.printf("POST Link:\n%s\n\n", "https://reddit.com/r/" + p.getPermalink());
//        }
//        
//        
//		return null;
//	}
//
//	public static Object getLink() throws IOException, JSONException {
//		Posts posts = getPostsforGui("");
//		
//        for (Post p : posts) {
////            System.out.printf("POST Name:\n%s\n\n", p.getTitle());
//            System.out.printf("POST Link:\n%s\n\n", "https://reddit.com/r/" + p.getPermalink());
//        }
//        return null;
//	}
//    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public void setLimit(int limit) {
//    	SubredditUrl.setLimit(limit);
//    	
//    }
//
//	public static String getPost() throws IOException, JSONException {
//		String postgui = null;
//        String subredditName = "python";
//        SortEnum sortEnum = SortEnum.NEW;
//        JSONObject jo = RedditOAuth.getToken();
//		JSONObject response = RedditOAuth.getSubreddit(BASE_URL, subredditName, sortEnum, jo.getString("access_token"));
//		Posts posts = Posts.createFromJsonObject(response);
//        for (Post p : posts) {
//        	 postgui = p.getTitle();   
//        	 
//        	}
//        return postgui;
//	}
}
