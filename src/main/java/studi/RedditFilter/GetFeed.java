package studi.RedditFilter;

import java.util.List;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;


public interface GetFeed {
	
	@GET("/r/{subreddit}/hot")
	Call<List<Posts>> postsinsubreddit(@Path("subreddit") String subreddit);



}
