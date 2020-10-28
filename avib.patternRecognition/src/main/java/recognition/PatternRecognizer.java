package recognition;

import ASTMCore.ASTMSource.CompilationUnit;
import com.google.gson.Gson;
import gastmappers.Language;
import gastmappers.Mapper;
import gastmappers.MapperFactory;
import gastmappers.exceptions.UnsupportedLanguageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import generator.TreeGeneration;

public class PatternRecognizer {

    public static void main(String[] args) throws UnsupportedLanguageException, IOException {
        String inputFolder = "/media/files/ProyectoDiseno/Analyzer/avib.analyzer.analyzer/src/main/java/";
        String classPath = "/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre/lib/rt.jar";
        TreeGeneration astGenerate = new TreeGeneration();
        astGenerate.InitGenerator(inputFolder, classPath, Language.JAVA);
        astGenerate.AnalyzeFacts();
        System.out.println(astGenerate.AnalyzedFacts());
        astGenerate.ReleaseGenerator();
    }

}
