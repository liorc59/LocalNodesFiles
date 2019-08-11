import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.ast.AstRoot;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParsingUtility {
    private String pathFile;

    public ParsingUtility(String path){
        this.pathFile =  path;
    }


    public AstRoot parse() throws IOException {
        return parse(readFile(pathFile));
    }

    public AstRoot parse(String src) throws IOException {

        CompilerEnvirons env = new CompilerEnvirons();
        env.setRecoverFromErrors(true);
        env.setGenerateDebugInfo(true);
        env.setRecordingComments(true);

        StringReader strReader = new StringReader(src);

        IRFactory factory = new IRFactory(env);
        return factory.parse(strReader, null, 0);

    }

    private String readFile(String path){
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(path));
            return new String(fileBytes);
        } catch (IOException e) {
            return "";
        }
    }
}
