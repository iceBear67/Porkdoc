package io.ib67;


import io.ib67.data.ProjectTree;
import io.ib67.walker.ISourceWalker;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Javadoc {
    private static Map<URL, SourceAnalyzer> sourceAnalyzers = new ConcurrentHashMap<>(128);
    protected static volatile long time;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        ISourceWalker.of(Paths.get("/home/icybear/IdeaProjects/New Zombie")).walk(SourceAnalyzer.defaultImpl(), () -> {
            //System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(ProjectTree.DEFAULT));
            System.out.println("Done! Took " + (System.currentTimeMillis() - time) + "ms");
        }).waitFor();
        ProjectTree.DEFAULT.iterateClasses(System.out::println);
    }
}
