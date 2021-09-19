package io.ib67.walker;

import io.ib67.iterators.IterableSources;
import io.ib67.util.FakeSpinLock;

import java.net.URL;
import java.util.function.BiConsumer;

public class SourceWalkerImpl implements ISourceWalker {
    private IterableSources source;
    private volatile boolean done = false;
    private FakeSpinLock lock = new FakeSpinLock();

    public SourceWalkerImpl(IterableSources source) {
        this.source = source;
        lock.lock();
    }

    @Override
    public FakeSpinLock walk(BiConsumer<URL, String> consumer, Runnable whenDone) {
        source.run(consumer, () -> {
            whenDone.run();
            lock.unlock();
        });
        return lock;
    }
}
