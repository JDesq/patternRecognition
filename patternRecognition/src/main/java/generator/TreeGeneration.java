package generator;

import ASTMCore.ASTMSource.CompilationUnit;
import com.google.gson.Gson;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import gastmappers.Language;
import gastmappers.Mapper;
import gastmappers.MapperFactory;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TreeGeneration {
    private String inputPath;
    private String[] classPath;
    private Mapper mapper;
    private Language language;
    private ArrayList<String> analyzedFactsList;
    private ArrayList<ASTMCore.ASTMSource.CompilationUnit> compilationUnitsList;

    public void InitGenerator(String inputFolder, String[] classPath, Language language) throws UnsupportedLanguageException {
        MapperFactory factory = new MapperFactory();
        this.inputPath = inputFolder;
        this.classPath = classPath;
        this.language = language;
        this.mapper = factory.createMapper(language);
        this.analyzedFactsList = new ArrayList<String>();
        this.compilationUnitsList = new ArrayList<CompilationUnit>();
    }

    public void ReleaseGenerator() {
        this.inputPath = null;
        this.language = null;
        this.mapper = null;
        this.analyzedFactsList.clear();
        this.analyzedFactsList = null;
        this.compilationUnitsList.clear();
        this.compilationUnitsList = null;
    }

    public void AnalyzeFacts() throws UnsupportedLanguageException, IOException {
        AnalyzeInputFolder(this.inputPath);
        //Object gast = Configuration.defaultConfiguration().jsonProvider().parse(AnalyzedFacts());
        //List<String> methods = JsonPath.read(gast, "..declOrDefn[?(@.tag == 'method')].['signature', 'className', 'packageName', 'method']");
    }

    private void AnalyzeInputFolder(String inputPath) throws IOException, UnsupportedLanguageException {
        File folders = new File(inputPath);
        String folderPath = folders.getCanonicalPath() + File.separator;
        File root = new File(folderPath);
        File[] sourceFiles = root.listFiles();
        String sourcePath;
        if (sourceFiles != null){
            for (File s: sourceFiles) {
                sourcePath = s.getAbsolutePath();
                if (s.isFile()) {
                    if (sourcePath.contains(Language.getFileExtension(this.language)))
                        AnalyzeSourceFile(sourcePath, s.getName());
                    continue;
                }
                AnalyzeInputFolder(sourcePath);
            }
        }else{
            System.out.println("Error al leer el directorio: "+inputPath);
        }
    }

    public void AnalyzeSourceFile(String sourcePath, String filename) throws IOException {
        Gson json = new Gson();
        Genson genson = new GensonBuilder()
                .useClassMetadata(true)
                .useRuntimeType(true)
                .useConstructorWithArguments(true)
                .create();
        String[] sources = { this.inputPath };
        ArrayList<ASTMCore.ASTMSource.CompilationUnit> units = mapper.getGastCompilationUnit(sourcePath);
        for (CompilationUnit unit : units) {
            compilationUnitsList.add(unit);
            //analyzedFactsList.add(json.toJson(unit).replaceAll("null,", "")); //***
            analyzedFactsList.add(genson.serialize(unit));
        }
    }

    public String AnalyzedFacts() {
        return analyzedFactsList.toString();
    }

    public ArrayList<String> getAnalyzedFactsList() {
        return analyzedFactsList;
    }

    public ArrayList<CompilationUnit> getCompilationUnitsList() {
        return compilationUnitsList;
    }

}
