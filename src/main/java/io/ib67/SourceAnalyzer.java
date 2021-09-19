package io.ib67;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import io.ib67.data.MethodArgument;
import io.ib67.data.ProjectTree;
import io.ib67.util.CommentParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor
public class SourceAnalyzer implements BiConsumer<URL, String> {
    private final ProjectTree tree;
    @Override
    public void accept(URL file, String s) {
        try {
            var parsed = StaticJavaParser.parse(s);
            for (TypeDeclaration<?> type : parsed.getTypes()) {

                if (type instanceof ClassOrInterfaceDeclaration) {
                    var d = (ClassOrInterfaceDeclaration) type;
                    var clazz = tree.ofClass(d.getFullyQualifiedName().get());
                    //System.out.println(clazz.getName());
                    for (AnnotationExpr annotation : type.getAnnotations()) {
                        clazz.addAnnotation(solveType(parsed.getImports(), annotation.getNameAsString()));
                    }
                    for (ClassOrInterfaceType implementedType : d.getImplementedTypes()) {
                        clazz.addInterface(solveType(parsed.getImports(), implementedType.getNameAsString()));
                    }
                    for (ClassOrInterfaceType extendedType : d.getExtendedTypes()) {
                        clazz.setParentClass(solveType(parsed.getImports(), extendedType.getNameAsString()));
                    }
                    type.getComment().ifPresent(e -> {
                        clazz.setComment(CommentParser.parseComment(e.getContent()));
                    });

                    // Methods.
                    for (MethodDeclaration method : type.getMethods()) {
                        if (!method.isPublic()) {
                            continue;
                        }
                        ArrayList<MethodArgument> arguments = new ArrayList<>();
                        for (Parameter parameter : method.getParameters()) {
                            arguments.add(MethodArgument.builder().type(solveType(parsed.getImports(), parameter.getTypeAsString())).name(parameter.getNameAsString()).build());
                        }
                        var treeMethods = clazz.ofMethod(method.getNameAsString(), arguments);
                        treeMethods.setReturnType(solveType(parsed.getImports(), method.getType().asString()));
                        method.getComment().ifPresent(e -> {
                            treeMethods.setComment(CommentParser.parseComment(e.getContent()));
                        });
                    }

                    // Fields.
                    for (FieldDeclaration field : type.getFields()) {
                        if (!field.isPublic()) {
                            continue;
                        }
                        for (VariableDeclarator variable : field.getVariables()) {
                            clazz.ofField(variable.getNameAsString(), solveType(parsed.getImports(), variable.getTypeAsString()));
                        }
                    }
                }

            }
        } catch (ParseProblemException exception) {
            log.error("Can't parse {}, it will be ignored.", file.getPath());
        }
    }

    private String solveType(NodeList<ImportDeclaration> declarations, String name) {
        String typeName = null;
        String realName = null;
        if (!name.contains(".")) {
            typeName = name;
        } else {
            var s = name.split("\\.");
            typeName = s[0];
            realName = s[1];

        }
        for (ImportDeclaration declaration : declarations) {
            if (declaration.getName().asString().endsWith(typeName)) {
                if (realName != null) {
                    return declaration.getNameAsString() + "." + realName;
                }
                return declaration.getNameAsString();
            }
        }
        return name;
    }
}
