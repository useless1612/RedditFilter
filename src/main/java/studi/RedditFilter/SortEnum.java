package studi.RedditFilter;

public enum SortEnum {
    TOP("top"),
    CONTROVERSIAL("controversial"),
    HOT("hot"),
    NEW("new"),
    RISING("rising");
    

    private String name;
    
    SortEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}