package io.ib67;


import io.ib67.data.ProjectTree;
import io.ib67.walker.ISourceWalker;

import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Javadoc {
    private static Map<URL,SourceAnalyzer> sourceAnalyzers = new ConcurrentHashMap<>(128);
    protected static volatile long time;
    public static void main(String[] args){

        /*ISourceWalker.of(Paths.get("/home/icybear/IdeaProjects/Oyster")).walk((f, s)->{
            sourceAnalyzers.computeIfAbsent(f,z->new SourceAnalyzer()).accept(f,s);
        })*/
        System.out.println(ProjectTree.of("test").ofClass("A"));
    }
}
