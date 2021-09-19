package io.ib67.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class TreeClass implements TreeObject {
    @Builder.Default
    private List<TreeMethod> methods = new ArrayList<>();
    @Builder.Default
    private List<TreeField> fields = new ArrayList<>();
    private String packageName;
    private String name;
    private ClassType type;
    @Setter
    private Comment comment;

    @Override
    public String toString() {
        return packageName.length() == 0 ? name : packageName + "." + name;
    }

    public TreeField ofField(String name, String typeFQDN) {
        return fields.stream().filter(e -> e.getName().equals(name) && e.getType().equals(typeFQDN)).findFirst().orElseGet(() -> {
            var f = TreeField.builder().name(name).type(typeFQDN).build();
            fields.add(f);
            return f;
        });
    }

    public TreeMethod ofMethod(String method, ArrayList<MethodArgument> arguments) { // use arraylist to force that the list is able to use hashCode().
        return methods.stream().filter(e -> e.getArguments().hashCode() == arguments.hashCode()).findFirst().orElseGet(() -> {
            var meth = TreeMethod.builder().arguments(arguments).name(method).build();
            methods.add(meth);
            return meth;
        });
    }
}
