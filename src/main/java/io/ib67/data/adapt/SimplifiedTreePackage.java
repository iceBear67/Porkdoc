package io.ib67.data.adapt;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class SimplifiedTreePackage {
    @Builder.Default
    private Set<String> subPackages = new HashSet<>();
    @Builder.Default
    private Set<String> classes = new HashSet<>();
    private String stage; // io.ib67.XX
}
