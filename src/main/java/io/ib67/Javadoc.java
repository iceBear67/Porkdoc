package io.ib67;


import com.google.gson.GsonBuilder;
import io.ib67.data.ProjectTree;
import io.ib67.walker.ISourceWalker;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Javadoc {
    private static Map<URL,SourceAnalyzer> sourceAnalyzers = new ConcurrentHashMap<>(128);
    protected static volatile long time;

    public static void main(String[] args) throws InterruptedException {

        ISourceWalker.of(Paths.get("/home/icybear/IdeaProjects/Oyster")).walk((f, s) -> {
            sourceAnalyzers.computeIfAbsent(f, z -> new SourceAnalyzer(ProjectTree.DEFAULT)).accept(f, s);
        });
        Thread.sleep(1000L);
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(ProjectTree.DEFAULT));
    }
}
