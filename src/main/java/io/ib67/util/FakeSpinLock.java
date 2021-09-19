package io.ib67.util;

import lombok.SneakyThrows;

public class FakeSpinLock {
    volatile boolean locked = false;

    @SneakyThrows
    public void waitFor() {
        while (locked) {
            Thread.sleep(100L);
        }
    }

    public void unlock() {
        locked = false;
    }

    public void lock() {
        locked = true;
    }
}
