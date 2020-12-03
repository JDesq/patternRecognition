package generator;

import ASTMCore.ASTMSource.CompilationUnit;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class TreeManager {

    private String DBpath;

    public TreeManager(){
        this.DBpath = "/media/files/ProyectoDiseno/patternRecognition/patternRecognition/src/main/java/Patterns/";
    }

    public void saveAnalyzedCode(String ASTjson, String filename) throws  IOException{
        String dir = this.DBpath+filename+".json";
        FileWriter fw = new FileWriter(dir);
        fw.write(ASTjson);
        fw.close();
        System.out.println("**** Save AST pattern: "+filename+ ", save succesfull. ****");
    }

    private String readASTfile(String path, String filename) throws IOException{
        String dir = path+filename+".json";
        File f = new File(dir);
        Scanner myReader = new Scanner(f);
        return myReader.nextLine();
    }

    public ArrayList<CompilationUnit> getCompilationUnitsListFromFile(String path, String filename) throws  IOException{
        String AST = readASTfile(path, filename);
        Genson genson = new GensonBuilder()
                .useClassMetadata(true)
                .useRuntimeType(true)
                .useConstructorWithArguments(true)
                .create();
        return genson.deserialize(AST, new GenericType<ArrayList<CompilationUnit>>() {});
    }

    public  ArrayList<CompilationUnit> getFileFromDB(String pattern) throws  IOException{
        String AST = readASTfile(this.DBpath, pattern);
        Genson genson = new GensonBuilder()
                .useClassMetadata(true)
                .useRuntimeType(true)
                .useConstructorWithArguments(true)
                .create();
        return genson.deserialize(AST, new GenericType<ArrayList<CompilationUnit>>() {});
    }

    public String getPatternFromDB (String pattern) throws IOException {
        String AST = readASTfile(this.DBpath, pattern);
        return AST;
    }

    public ArrayList<String> GetDBview() throws IOException {
        File folders = new File(this.DBpath);
        String folderPath = folders.getCanonicalPath() + File.separator;
        File root = new File(folderPath);
        File[] sourceFiles = root.listFiles();
        ArrayList<String> patterns = new ArrayList<>();
        assert sourceFiles != null;
        for (File s: sourceFiles) {
            if(s.isFile()){
                String name = FilenameUtils.removeExtension(s.getName());
                patterns.add(name);
            }
        }
        return patterns;
    }

    public void setDataBasePath(String DBpath) {
        this.DBpath = DBpath;
    }





}
