package recognition;

import ASTMCore.ASTMSource.CompilationUnit;
import com.google.gson.Gson;
import generator.TreeGeneration;
import generator.TreeManager;
import gastmappers.Language;
import gastmappers.Mapper;
import gastmappers.MapperFactory;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.IOException;
import java.util.ArrayList;


public class PatternRecognizer {

    public static void main(String[] args) throws UnsupportedLanguageException, IOException {
        String[] patterns;
        String inputFolder = "/media/files/ProyectoDiseno/patterns/src/";
        String[] classPath = {"/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre/lib/rt.jar"};
        String patternsPath = "/media/files/ProyectoDiseno/patternRecognition/patternRecognition/src/main/java/Patterns/";

        TreeGeneration astGenerate = new TreeGeneration();
        TreeManager astManager = new TreeManager();

        ArrayList<String> dbView = astManager.GetDBview();

        astGenerate.InitGenerator(inputFolder+dbView.get(0), classPath, Language.JAVA);

        System.out.println(astManager.getPatternFromDB(dbView.get(0)));
        //System.out.println(astGenerate.AnalyzedFacts());

        //astGenerate.saveAnalyzedCode(patternsPath,"singleton");

        //ArrayList<CompilationUnit> example, example2 = new ArrayList<>();
        //example = astManager.getFileFromDB(dbView.get(0));
        //example2 = astGenerate.getCompilationUnitsList();

        astGenerate.ReleaseGenerator();
    }

}
