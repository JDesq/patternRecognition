package recognition;

import ASTMCore.ASTMSource.CompilationUnit;
import com.google.gson.Gson;
import generator.TreeGeneration;
import gastmappers.Language;
import gastmappers.Mapper;
import gastmappers.MapperFactory;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.IOException;
import java.util.ArrayList;


public class PatternRecognizer {

    public static void main(String[] args) throws UnsupportedLanguageException, IOException {
        String[] patterns;
        String inputFolder = "/media/files/ProyectoDiseno/patterns/src/singleton/";
        String[] classPath = {"/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre/lib/rt.jar"};
        patterns = new String[]{"bridge", "chainOfResponsibility", "command", "composite", "decorator", "factory", "observer",
                                "prototype", "proxy", "singleton", "state", "strategy", "template", "visitor"};
        TreeGeneration astGenerate = new TreeGeneration();
        //for (String pattern : patterns){
            //CompilationUnit unit = astGenerate.getCompilationUnitsListFromFile("/media/files/ProyectoDiseno/patternRecognition/patternRecognition/src/main/java/Patterns/", pattern).get(0);
            //System.out.println(unit.getClass());
        //}
        astGenerate.InitGenerator(inputFolder, classPath, Language.JAVA);
        astGenerate.AnalyzeFacts();
        System.out.println(astGenerate.AnalyzedFacts());
        astGenerate.saveAnalyzedCode("/media/files/ProyectoDiseno/patternRecognition/patternRecognition/src/main/java/Patterns/","singleton");
        astGenerate.ReleaseGenerator();

    }

}
