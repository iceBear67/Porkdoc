package io.ib67.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Getter
public class TreeMethod implements TreeObject{
    @Builder.Default
    private final ArrayList<MethodArgument> arguments = new ArrayList<>();
    private final String name;
    private final String returnType;
    @Builder.Default
    @Setter
    private Comment comment = Comment.EMPTY_COMMENT;
}
