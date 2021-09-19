package io.ib67.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TreeField implements TreeObject{
    @Builder.Default
    private Comment comment=Comment.EMPTY_COMMENT;
    private String name;
    private String type;
    @Override
    public Comment getComment() {
        return comment;
    }
}
