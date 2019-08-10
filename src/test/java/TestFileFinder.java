import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TestFileFinder {
    @Test
    public void testFileFinderPropTypes(){
        Set<String> result = new FileFinderManager("src/test/resources/prop-types").getLocalFiles();
        Set<String> expect = new HashSet<>();
        expect.add("./factoryWithTypeCheckers");
        expect.add("./lib/ReactPropTypesSecret");
        expect.add("./checkPropTypes");
        expect.add("./factoryWithThrowingShims");
        Assert.assertTrue(result.equals(expect));
    }


    @Test
    public void testFileFinderWithGivenExample(){
        Set<String> result = new FileFinderManager("src/test/resources/exampleModule").getLocalFiles();
        Set<String> expect = new HashSet<>();
        expect.add("./a");
        expect.add("./b");
        Assert.assertTrue(result.equals(expect));
    }
}
