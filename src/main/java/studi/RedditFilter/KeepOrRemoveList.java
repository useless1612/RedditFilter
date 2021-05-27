package studi.RedditFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeepOrRemoveList {

    private Map<String, Boolean> map;
    private int checked;

    public KeepOrRemoveList() {
        this.map = new HashMap<String, Boolean>();
        checked = 0;
    }

    public KeepOrRemoveList(Map<String, Boolean> map) {
        this.map = map;
        checked = this.map.values().stream().map((x) -> x ? 1 : 0 ).reduce(0, (total, element) -> total + element);
    }

    public void uncheck(String key) {
        if (true == this.map.put(key, false)) {
            checked--;
        };
    }

    public void check(String key) {
        if (false == this.map.put(key, true)) {
            checked++;
        };
    }

    public void add(String key, Boolean check) {
        if (this.map.containsKey(key)) {
            return;
        }
        if (null != this.map.put(key, check)) {
            throw new RuntimeException("Did overwrite entry with add method, should not be possible");
        };

        // Only add if check == true
        if (check) {
            checked++;
        }
    }

    public void remove(String key) {
        if (!this.map.containsKey(key)) {
            return;
        }

        Boolean returnValue = this.map.remove(key);
        if (null == returnValue) {
            throw new RuntimeException("Tried to delete non existing key, should not be possible");
        }

        // Only remove if returnValue == true
        if (returnValue) { 
            checked--;
        }
    }

    public int getChecked() {
        return this.checked;
    }
    
    public List<String> getListOfCheckedKeywords() {
        return this.map.entrySet().stream().filter((entry) -> entry.getValue()).map((entry) -> entry.getKey()).collect(Collectors.toList());
    }
}