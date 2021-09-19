package io.ib67;


import io.ib67.data.MethodArgument;
import io.ib67.data.ProjectTree;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Javadoc {
    private static Map<URL,SourceAnalyzer> sourceAnalyzers = new ConcurrentHashMap<>(128);
    protected static volatile long time;
    public static void main(String[] args) {

        /*ISourceWalker.of(Paths.get("/home/icybear/IdeaProjects/Oyster")).walk((f, s)->{
            sourceAnalyzers.computeIfAbsent(f,z->new SourceAnalyzer()).accept(f,s);
        })*/
        ProjectTree.of("test").ofClass("A").ofMethod("method", MethodArgument.of(MethodArgument.builder().name("aa").type("String").build()));
        ProjectTree.of("test").ofClass("A").ofMethod("method", MethodArgument.of(MethodArgument.builder().name("aa").type("String").build()));
        ProjectTree.of("test").ofClass("A").ofMethod("method", MethodArgument.of(MethodArgument.builder().name("aa").type("String").build()));
        ProjectTree.of("test").ofClass("A").ofMethod("method", MethodArgument.of(MethodArgument.builder().name("aa").type("String").build()));
        var a = ProjectTree.of("test").ofClass("A");
        System.out.println(1);
    }
}
