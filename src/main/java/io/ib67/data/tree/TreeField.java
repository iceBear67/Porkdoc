package io.ib67.data.tree;

import io.ib67.data.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class TreeField implements TreeObject{
    @Builder.Default
    @Setter
    private Comment comment=Comment.EMPTY_COMMENT;
    private String name;
    private String type;
    @Override
    public Comment getComment() {
        return comment;
    }
}
