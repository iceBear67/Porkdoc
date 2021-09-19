package io.ib67.walker;

import io.ib67.iterators.IterableSources;

import java.io.File;
import java.net.URL;
import java.util.function.BiConsumer;

public class SourceWalkerImpl implements ISourceWalker{
    private IterableSources source;
    public SourceWalkerImpl(IterableSources source){
        this.source=source;
    }

    @Override
    public void walk(BiConsumer<URL, String> consumer) {
        source.run(consumer);
    }
}
