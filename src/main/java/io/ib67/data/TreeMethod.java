package io.ib67.data;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class TreeMethod implements TreeObject{
    @Builder.Default
    private Comment comment=Comment.EMPTY_COMMENT;
    private final String name;
    private final String returnType;
    @Builder.Default
    private final List<MethodArgument> arguments = new ArrayList<>();
}
