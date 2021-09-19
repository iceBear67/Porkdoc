package io.ib67.walker;

import io.ib67.iterators.IterableSources;
import io.ib67.iterators.MultiThreadedFileIterator;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public interface ISourceWalker {
    void walk(BiConsumer<URL,String> consumer);
    static ISourceWalker of(Path path){
        return of(new MultiThreadedFileIterator(path));
    }
    static ISourceWalker of(IterableSources source){
        return new SourceWalkerImpl(source);
    }
}
