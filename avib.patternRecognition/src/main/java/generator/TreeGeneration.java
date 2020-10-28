package generator;

import ASTMCore.ASTMSource.CompilationUnit;
import com.google.gson.Gson;
import gastmappers.Language;
import gastmappers.Mapper;
import gastmappers.MapperFactory;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TreeGeneration {
    private String inputPath;
    private String classPath;
    private Mapper mapper;
    private Language language;
    private ArrayList<String> analyzedFactsList;
    private ArrayList<ASTMCore.ASTMSource.CompilationUnit> compilationUnitsList;

    public void InitGenerator(String inputFolder, String classPath, Language language) throws UnsupportedLanguageException {
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
    }

    private void AnalyzeInputFolder(String inputPath) throws IOException, UnsupportedLanguageException {
        File folders = new File(inputPath);
        String folderPath = folders.getCanonicalPath() + File.separator;
        File root = new File(folderPath);
        File[] sourceFiles = root.listFiles();
        String sourcePath = null;
        for (File s: sourceFiles) {
            sourcePath = s.getAbsolutePath();
            if (s.isFile()) {
                if (sourcePath.contains(Language.getFileExtension(this.language)))
                    AnalyzeSourceFile(sourcePath, s.getName());
                continue;
            }
            AnalyzeInputFolder(sourcePath);
        }
    }

    public void AnalyzeSourceFile(String sourcePath, String filename) throws IOException {
        Gson json = new Gson();
        String[] sources = { this.inputPath };
        String[] classpath = { this.classPath };
        ArrayList<ASTMCore.ASTMSource.CompilationUnit> units = mapper.getGastCompilationUnit(sourcePath);
        for (CompilationUnit unit : units) {
            compilationUnitsList.add(unit);
            analyzedFactsList.add(json.toJson(unit).replaceAll("null,", "")); // IS THIS RIGHT?
        }
    }

    public String AnalyzedFacts() {
        return analyzedFactsList.toString();
    }

}
