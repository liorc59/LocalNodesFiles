

import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.NodeVisitor;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) {
        System.out.println("Enter an absolute path to your Node.js module folder: ");
        Scanner scanner = new Scanner(System.in);
        String rootDirectory = scanner.next();


        try {
            AstRoot root = new ParsingUtility(rootDirectory +"/index.js").parse();
            RequireNodeVisitor fileVisitor = new RequireNodeVisitor(rootDirectory +"/index.js");
            root.visit(fileVisitor);
            System.out.println(fileVisitor.getLocalFiles());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
