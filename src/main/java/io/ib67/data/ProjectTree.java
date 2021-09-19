package io.ib67.data;

import io.ib67.data.tree.TreeClass;
import io.ib67.data.tree.TreePackage;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ProjectTree {
    private static final Map<String, ProjectTree> trees = new ConcurrentHashMap<>();
    public static final ProjectTree DEFAULT = ProjectTree.of("DEFAULT");
    @Getter
    private String name;
    private Map<String, TreePackage> packages = new ConcurrentHashMap<>();

    private ProjectTree(String name) {
        this.name = name;
    }

    public static ProjectTree of(String name) {
        return trees.computeIfAbsent(name, ProjectTree::new);
    }

    public TreePackage ofPackage(String packageLoc) {
        if (!packageLoc.contains(".")) {
            return packages.computeIfAbsent(packageLoc, e -> TreePackage.builder().stage(packageLoc).build());
        }
        return packages.computeIfAbsent(packageLoc.split("\\.")[0], e -> TreePackage.builder().stage(e).build()).ofSubPackage(packageLoc);
    }

    public TreeClass ofClass(String classFQDN) {
        var a = classFQDN.split("\\.");
        if (a.length == 0) {
            ofPackage("").ofClass(classFQDN);
        }
        var joiner = new StringJoiner(".");
        String clazzName = null;
        for (int i = 0; i < a.length; i++) {
            if (i == a.length - 1) {
                clazzName = a[i];
                break;
            }
            joiner.add(a[i]);
        }
        return ofPackage(joiner.toString()).ofClass(clazzName);
    }

    public void iterateClasses(Consumer<TreeClass> classConsumer) {
        rootPackages().parallelStream().map(this::flat).flatMap(Collection::stream).flatMap(e -> e.classes().stream()).forEach(classConsumer);
    }

    private List<TreePackage> flat(TreePackage treePackage) {
        List<TreePackage> a = new ArrayList<>();
        treePackage.subPackages().stream().filter(a::add).map(this::flat).flatMap(Collection::stream).forEach(a::add);
        return a;
    }

    public Collection<TreePackage> rootPackages() {
        return packages.values();
    }
}
