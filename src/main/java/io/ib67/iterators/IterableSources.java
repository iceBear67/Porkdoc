package io.ib67.iterators;

import java.net.URI;
import java.net.URL;
import java.util.function.BiConsumer;

public interface IterableSources {
    void run(BiConsumer<URL,String> consumer);
}
