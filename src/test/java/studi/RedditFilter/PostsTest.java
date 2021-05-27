package studi.RedditFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.ClassLoader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

/**
 * Unit test for simple App.
 */
class PostsTest {
    static private JSONObject jsonObject;

    @BeforeAll
    static void loadMockData() throws URISyntaxException, IOException, JSONException {
        Path mockPath = Paths.get(ClassLoader.getSystemClassLoader().getResource("dataForMockObjects.json").toURI());
        String content = Files.readString(mockPath);
        JSONTokener tokener = null;
        tokener = new JSONTokener(content);
        jsonObject = new JSONObject(tokener);
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void convertToPostListTestWithMockedReturnData() throws JSONException {
        {
            Posts posts = Posts.createFromJsonObject(jsonObject);
            assertEquals(10, posts.size());

            // Test getter for Post Class
            for (Post post : posts) {
                post.getTitle();
                post.getPermalink();
            }
        }
    }

    @Test
    public void filterTest() throws JSONException {
        Posts posts = Posts.createFromJsonObject(jsonObject);
        KeepOrRemoveList keepList = new KeepOrRemoveList();
        keepList.add("python", true);
        KeepOrRemoveList removeList = new KeepOrRemoveList();
        removeList.add("fiddle", true);
        Posts filteredPosts = posts.filter(keepList, removeList);
        for (Post post : filteredPosts) {
            assertTrue(post.getTitle().toLowerCase().contains("python"));
            assertTrue(!post.getTitle().toLowerCase().contains("fiddle"));
        }
    }
}