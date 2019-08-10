import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FileFinderManager {
    private String rootDirectoryPath;

    public FileFinderManager(String rootDirectoryPath){
        this.rootDirectoryPath = rootDirectoryPath;
    }


    public Set<String> getLocalFiles(){
        return getLocalFiles(rootDirectoryPath+"/index.js");
    }

    private Set<String> getLocalFiles(String path){
        String fileData = readFile(path);
        Set<String> requires = new HashSet<>();
        while(fileData.indexOf("require(") != -1){

            //get require name
            fileData = fileData.substring(fileData.indexOf("require(")+9);
            String require = fileData.substring(0,fileData.indexOf("\'"));
            //chack if its a local dependency
            if (require.charAt(0) == '.') {
                requires.add(require);
                requires.addAll(getLocalFiles(buildPathToFile(path,require+".js")));
            }

            //remove the current dependency
            fileData = fileData.substring(fileData.indexOf("\'"));
        }
        return requires;
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


    private String readFile(String path){
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(path));
            return new String(fileBytes);
        } catch (IOException e) {
            return "";
        }
    }

}
