package studi.RedditFilter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity {

   

    public static void test(){
    


    Retrofit.Builder builder = new Retrofit.Builder()
    .baseUrl("https://www.reddit.com/")
    .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    GetFeed posts = retrofit.create(GetFeed.class);
    Call<List<Posts>> call = posts.postsinsubreddit("funny");

    call.enqueue(new Callback<List<Posts>>(){

        @Override
        public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
            // TODO Auto-generated method stub
            List<Posts> posts = response.body();
            System.out.println(posts.toString());

            
        }

        @Override
        public void onFailure(Call<List<Posts>> call, Throwable t) {
            // TODO Auto-generated method stub
            System.out.println("something went wrong");
        }
        
    });

    
    }

    
}
