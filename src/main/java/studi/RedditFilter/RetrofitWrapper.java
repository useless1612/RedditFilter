package studi.RedditFilter;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitWrapper {

	Retrofit retrofit = new Retrofit.Builder()
		    .baseUrl("https://api.example.com")
		    .addConverterFactory(GsonConverterFactory.create())
		    .build();
		
}
