package io.ib67.data;

import io.ib67.util.Pair;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@EqualsAndHashCode
@Builder
@Getter
public class Comment {
    public static final Comment EMPTY_COMMENT = Comment.builder().build();
    @Builder.Default
    private String data=null;
    @Nullable
    @Builder.Default
    private String deprecated = null;
    @Builder.Default
    @Nullable
    private List<Pair<String, String>> params = null;
    @Builder.Default
    @Nullable
    private String see = null;
    @Builder.Default
    @Nullable
    private String author=null;
    @Builder.Default
    @Nullable
    private String since=null;
}
