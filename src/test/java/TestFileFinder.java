import org.junit.Assert;
import org.junit.Test;
import org.mozilla.javascript.ast.AstRoot;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TestFileFinder {
    @Test
    public void testFileFinderPropTypes(){
        AstRoot root = null;
        try {
            root = new ParsingUtility("src/test/resources/prop-types/index.js").parse();
        } catch (IOException e) {
            Assert.assertFalse(true);
        }
        RequireNodeVisitor fileVisitor = new RequireNodeVisitor("src/test/resources/prop-types/index.js");
        root.visit(fileVisitor);
        Set result = fileVisitor.getLocalFiles();
        Set<String> expect = new HashSet<>();
        expect.add("./factoryWithTypeCheckers");
        expect.add("./lib/ReactPropTypesSecret");
        expect.add("./checkPropTypes");
        expect.add("./factoryWithThrowingShims");
        Assert.assertTrue(result.equals(expect));
    }


    @Test
    public void testFileFinderWithGivenExample(){
        AstRoot root = null;
        try {
            root = new ParsingUtility("src/test/resources/exampleModule/index.js").parse();
        } catch (IOException e) {
            Assert.assertFalse(true);
        }
        RequireNodeVisitor fileVisitor = new RequireNodeVisitor("src/test/resources/exampleModule/index.js");
        root.visit(fileVisitor);
        Set result = fileVisitor.getLocalFiles();
        Set<String> expect = new HashSet<>();
        expect.add("./a");
        expect.add("./b");
        Assert.assertTrue(result.equals(expect));
    }
}
