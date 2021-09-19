package io.ib67.data;

import lombok.Builder;

@Builder
public class MethodArgument {
    private final String type;
    private final String name;
    private final String comment;
}
