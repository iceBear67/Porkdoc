package io.ib67.data;

import lombok.Builder;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class MethodArgument {
    private final String type;
    private final String name;
    @Setter
    private String comment;

    public static ArrayList<MethodArgument> of(MethodArgument... arguments) {
        var a = new ArrayList<>(List.of(arguments));
        return a;
    }

    public static MethodArgument by(String typeFQDN, String name) {
        return MethodArgument.builder().type(typeFQDN).name(name).build();
    }

    public static MethodArgument by(String typeFQDN, String name, String comment) {
        return MethodArgument.builder().type(typeFQDN).name(name).comment(comment).build();
    }

    @Override
    public int hashCode() {
        int i = 1;
        i = i * 31 + type.hashCode();
        i = i * 31 + name.hashCode();
        return i;
    }
}
