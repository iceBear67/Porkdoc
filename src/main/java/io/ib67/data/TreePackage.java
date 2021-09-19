package io.ib67.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Builder
@Getter
public class TreePackage implements TreeObject {
    @Builder.Default
    private Map<String, TreePackage> subPackages = new ConcurrentHashMap<>();
    @Builder.Default
    private Map<String, TreeClass> classMap = new ConcurrentHashMap<>();
    private String stage;
    @Builder.Default
    @Setter
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
