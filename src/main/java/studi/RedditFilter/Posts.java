package studi.RedditFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Posts implements Iterable<Post> {

    public static Posts createFromJsonObject(JSONObject parentObject) throws JSONException {

        List<Post> list = new ArrayList<>();
        try {
            JSONArray arr = parentObject.getJSONObject("data").getJSONArray("children");
            for (int i = 0; i < arr.length(); i++) {
                list.add(new Post(arr.getJSONObject(i)));
            }
        } catch (ClassCastException e) {
            System.err.println("Failed to create post list from json array");
        }
        return new Posts(list);
    }

    private List<Post> postList;

    public Posts(List<Post> postList) {
        this.postList = postList;
    }

    public void forEachPost(Consumer<Post> action) {
        this.postList.forEach(action);
    }

    public Iterator<Post> iterator() {
        return this.postList.iterator();
    }

    public Spliterator<Post> spliterator() {
        return this.postList.spliterator();
    }

    public int size() {
        return this.postList.size();
    }

    public Posts filter(KeepOrRemoveList keepList, KeepOrRemoveList removeList) throws JSONException {
        List<Post> filteredPosts = this.postList;
        if (keepList.getChecked() > 0) {
            filteredPosts = filteredPosts.stream().filter((x) -> {
                String title = "";
                try {
                    title = x.getTitle();
                } catch (JSONException e) {
                    System.err.println("Could not find the title of a post: " + e.toString());
                }
                for (String keyword : keepList.getListOfCheckedKeywords()) {
                    if (title.toLowerCase().contains(keyword.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
        }
        filteredPosts = filteredPosts.stream().filter((x) -> {
            String title = "";
            try {
                title = x.getTitle();
            } catch (JSONException e) {
                System.err.println("Could not find the title of a post: " + e.toString());
            }
            for (String keyword : removeList.getListOfCheckedKeywords()) {
                if (title.toLowerCase().contains(keyword.toLowerCase())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
        return new Posts(filteredPosts);
    }

    public String[][] getPostDataForTable() {
        List<String[]> list = this.postList.stream().map((Post x) -> { 
            try {
                return new String[] { x.getTitle(), "https://reddit.com" + x.getPermalink() };
            } catch (JSONException e) {
                System.err.println("Found JSONException while converting Posts data for JTable: " + e.toString());
                return new String[] { "", "" };
            }
        }).collect(Collectors.toList());
        return (String[][]) list.toArray(new String[0][0]);
    }
}