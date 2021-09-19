package io.ib67;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.resolution.SymbolResolver;
import io.ib67.data.ProjectTree;
import io.ib67.util.CommentParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor
public class SourceAnalyzer implements BiConsumer<URL, String> {
    private final ProjectTree tree;

    @Override
    public void accept(URL file, String s) {
        try {
            var parsed = StaticJavaParser.parse(s);
            new JavaParser(new ParserConfiguration().setSymbolResolver(SymbolResolver)).
            for (TypeDeclaration<?> type : parsed.getTypes()) {

                if (type instanceof ClassOrInterfaceDeclaration) {
                    var d = (ClassOrInterfaceDeclaration) type;
                    var clazz = tree.ofClass(d.getFullyQualifiedName().get());
                    for (AnnotationExpr annotation : type.getAnnotations()) {
                        clazz.addAnnotation(annotation.resolve().getClassName());
                    }
                    for (ClassOrInterfaceType implementedType : d.getImplementedTypes()) {
                        clazz.addInterface(implementedType.resolve().getTypeDeclaration().get().getClassName());
                    }
                    for (ClassOrInterfaceType extendedType : d.getExtendedTypes()) {
                        clazz.setParentClass(extendedType.resolve().getTypeDeclaration().get().getClassName());
                    }
                    type.getComment().ifPresent(e -> {
                        clazz.setComment(CommentParser.parseComment(e.getContent()));
                    });
                }

            }
        } catch (ParseProblemException exception) {
            log.error("Can't parse {}, it will be ignored.", file.getPath());
        }
    }
}
