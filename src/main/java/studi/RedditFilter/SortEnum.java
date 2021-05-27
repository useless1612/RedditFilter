package studi.RedditFilter;

public enum SortEnum {
    TOP("top"),
    CONTROVERSIAL("cont"),
    HOT("hot"),
    POPULAR("popular"),
    NEW("new"),
    RANDOM("random"),
    RISING("rising");
    

    private String name;
    
    SortEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}