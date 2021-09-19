package io.ib67.iterators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;

public class MultiThreadedFileIterator implements IterableSources{
    private Path path;
    private Executor readPool = ForkJoinPool.commonPool();
    public MultiThreadedFileIterator(Path path){
        this.path=path;
    }
    @Override
    public void run(BiConsumer<URL, String> consumer) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) {
                    File file = path.toFile();
                    if (!file.getName().endsWith(".java")) {
                        return FileVisitResult.CONTINUE;
                    }
                    readPool.execute(()->{
                        try (var is = new FileInputStream(file)) {
                            String list = new String(is.readAllBytes());
                            consumer.accept(file.toURI().toURL(),list);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
