package io.ib67.data;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class TreeClass implements TreeObject{
    private List<TreeMethod> methods;
    private String packageName;
    private String name;
    private Comment comment;

    @Override
    public String toString() {
        return packageName.length()==0?name:packageName+"."+name;
    }
}
