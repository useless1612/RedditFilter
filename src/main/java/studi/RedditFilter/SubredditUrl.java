package studi.RedditFilter;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class SubredditUrl extends GenericUrl {
    public SubredditUrl(String encodedUrl) {
        super(encodedUrl);
    }

    @Key("limit")
    private Integer limit;

    public SubredditUrl setLimit(Integer limit)  {
        this.limit = limit;
        return this;
    }
}