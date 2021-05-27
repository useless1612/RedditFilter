package studi.RedditFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;



import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class KeepOrRemoveListTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void simpleConstructorTest()
    {
        KeepOrRemoveList list = new KeepOrRemoveList();
        assertEquals(0, list.getChecked());
    }

    @Test
    public void mapConstructor()
    {
        Map<String, Boolean> map = new HashMap<>();
        map.put("python", true);
        map.put("spider", false);
        map.put("nsfw", true);
        map.put("trump", true);

        KeepOrRemoveList list = new KeepOrRemoveList(map);
        assertEquals(3, list.getChecked());
    }

    @Test
    public void mapConstructorWithEmptyMap()
    {
        Map<String, Boolean> map = new HashMap<>();

        KeepOrRemoveList list = new KeepOrRemoveList(map);
        assertEquals(0, list.getChecked());
    }

    @Test
    public void uncheckTest()
    {
        Map<String, Boolean> map = new HashMap<>();
        map.put("python", true);
        map.put("spider", false);
        map.put("nsfw", true);
        map.put("trump", true);

        KeepOrRemoveList list = new KeepOrRemoveList(map);

        list.uncheck("python");
        assertEquals(2, list.getChecked());
        list.uncheck("python");
        assertEquals(2, list.getChecked());
        list.uncheck("nsfw");
        assertEquals(1, list.getChecked());
        list.uncheck("trump");
        assertEquals(0, list.getChecked());
        list.uncheck("spider");
        assertEquals(0, list.getChecked());
    }

    @Test
    public void checkTest()
    {
        Map<String, Boolean> map = new HashMap<>();
        map.put("python", true);
        map.put("spider", false);
        map.put("nsfw", true);
        map.put("trump", true);

        KeepOrRemoveList list = new KeepOrRemoveList(map);

        list.check("python");
        assertEquals(3, list.getChecked());
        list.check("nsfw");
        assertEquals(3, list.getChecked());
        list.check("trump");
        assertEquals(3, list.getChecked());
        list.check("spider");
        assertEquals(4, list.getChecked());
        list.check("spider");
        assertEquals(4, list.getChecked());
    }

    @Test
    public void addTest()
    {
        Map<String, Boolean> map = new HashMap<>();

        KeepOrRemoveList list = new KeepOrRemoveList(map);
        assertEquals(0, list.getChecked());
        list.add("python", true);
        assertEquals(1, list.getChecked());
        list.add("nsfw", false);
        assertEquals(1, list.getChecked());
        list.add("whatcouldcowrong", true);
        assertEquals(2, list.getChecked());
        list.add("trump", true);
        assertEquals(3, list.getChecked());
    }

    @Test
    public void removeTest()
    {
        Map<String, Boolean> map = new HashMap<>();
        map.put("python", true);
        map.put("spider", false);
        map.put("nsfw", true);
        map.put("trump", true);

        KeepOrRemoveList list = new KeepOrRemoveList(map);

        assertEquals(3, list.getChecked());
        list.remove("python");
        assertEquals(2, list.getChecked());
        list.remove("spider");
        assertEquals(2, list.getChecked());
        list.remove("nsfw");
        assertEquals(1, list.getChecked());
        list.remove("trump");
        assertEquals(0, list.getChecked());
        list.remove("trump");
        assertEquals(0, list.getChecked());
    }

    @Test
    public void getListOfCheckedKeywordsTest()
    {
        Map<String, Boolean> map = new HashMap<>();
        map.put("python", true);
        map.put("spider", false);
        map.put("nsfw", true);
        map.put("trump", true);

        KeepOrRemoveList list = new KeepOrRemoveList(map);
        List<String> returnedKeywords = list.getListOfCheckedKeywords();
        List<String> expectedKeywords = Arrays.asList("python", "nsfw", "trump");
        assertTrue(expectedKeywords.containsAll(returnedKeywords));
    }
}