package io.ib67.walker;

import io.ib67.iterators.IterableSources;
import io.ib67.iterators.MultiThreadedFileIterator;
import io.ib67.util.FakeSpinLock;

import java.net.URL;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public interface ISourceWalker {
    FakeSpinLock walk(BiConsumer<URL, String> consumer, Runnable whenDone);

    static ISourceWalker of(Path path) {
        return of(new MultiThreadedFileIterator(path));
    }
    static ISourceWalker of(IterableSources source){
        return new SourceWalkerImpl(source);
    }
}
