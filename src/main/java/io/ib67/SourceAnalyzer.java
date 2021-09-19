package io.ib67;

import java.io.File;
import java.net.URL;
import java.util.function.BiConsumer;

public class SourceAnalyzer implements BiConsumer<URL, String> {
    private int commentDepth = 0;
    private int codeDepth = 0;
    private Status status = Status.CLASS;

    @Override
    public void accept(URL file, String s) {
        System.out.println(file+" --> "+Thread.currentThread().getName());
    }
}
