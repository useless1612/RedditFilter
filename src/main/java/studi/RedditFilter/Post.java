package studi.RedditFilter;

import org.json.JSONException;
import org.json.JSONObject;

public class Post {

private JSONObject object;
    public Post(JSONObject object) {
        this.object = object;
    }

    public String getTitle() throws JSONException {
        return this.object.getJSONObject("data").getString("title");
    }

    public String getPermalink() throws JSONException {
        return this.object.getJSONObject("data").getString("permalink");
    }

}