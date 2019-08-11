import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.NodeVisitor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RequireNodeVisitor implements NodeVisitor {

    private Set<String> localFiles;
    private String path;

    public RequireNodeVisitor(String path){
        this.localFiles = new HashSet<>();
        this.path = path;
    }

    @Override
    public boolean visit(AstNode node) {
        getLocalFiles(node.toSource());
        return true; //process children
    }


    private void getLocalFiles(String data){
        while(data.indexOf("require(") != -1){

            //get require name
            data = data.substring(data.indexOf("require(")+9);
            String require = data.substring(0,data.indexOf("\'"));
            //chack if its a local dependency
            if (require.charAt(0) == '.') {
                localFiles.add(require);

                try {
                    AstRoot root = new ParsingUtility(buildPathToFile(path,require+".js")).parse();
                    RequireNodeVisitor newFileVisitor = new RequireNodeVisitor(buildPathToFile(path,require+".js"));
                    root.visit(newFileVisitor);
                    localFiles.addAll(newFileVisitor.getLocalFiles());
                } catch (IOException e) {
                }
            }

            //remove the current dependency
            data = data.substring(data.indexOf("\'"));
        }
    }


    public Set<String> getLocalFiles(){
        return localFiles;
    }

    //return the path to the nextFile
    private String buildPathToFile(String currentFile, String newFileRequire){
        String pathToDirectory = currentFile.substring(0,currentFile.lastIndexOf('/'));
        if(newFileRequire.indexOf("../") == -1) return pathToDirectory+newFileRequire.substring(1);
        while(newFileRequire.indexOf("../") != -1){
            pathToDirectory = pathToDirectory.substring(0, pathToDirectory.lastIndexOf('/'));
            newFileRequire = newFileRequire.substring(2);
        }
        return pathToDirectory + "/" + newFileRequire;

    }


}
