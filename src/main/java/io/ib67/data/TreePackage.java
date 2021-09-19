package io.ib67.data;

import com.sun.source.tree.Tree;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Builder
@Getter
public class TreePackage implements TreeObject {
    @Builder.Default
    private Map<String, TreePackage> subPackages = new HashMap<>();
    @Builder.Default
    private Map<String,TreeClass> classMap = new HashMap<>();
    private String stage;
    @Builder.Default
    private Comment comment = Comment.EMPTY_COMMENT;
    private TreePackage parent;
    public Collection<TreePackage> subPackages(){
        return subPackages.values();
    }
    public TreePackage ofSubPackage(String packageLoc) {
        if(packageLoc.equals(stage)){
            return this;
        }
        if (!packageLoc.contains(".")) {
            return subPackages.computeIfAbsent(packageLoc, e -> TreePackage.builder().parent(this).stage(packageLoc).build());
        }
        TreePackage pointer = this;
        for (String s : packageLoc.split("\\.")) {
            pointer = pointer.ofSubPackage(s);
        }
        return pointer;
    }
    public TreeClass ofClass(String name){
        var a = this;
        return classMap.computeIfAbsent(name,n -> TreeClass.builder().packageName(a.toString()).name(n).build());
    }
    @Override
    public String toString() {
        if(stage == null){ // default package.
            return "";
        }
        if(parent==null){
            return stage;
        }
        return parent+"."+stage;
    }
}
