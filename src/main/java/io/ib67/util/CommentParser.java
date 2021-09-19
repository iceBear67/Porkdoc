package io.ib67.util;

import io.ib67.data.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class CommentParser {
    private CommentParser() {

    }

    public static Comment parseComment(String str) {
        StringBuilder content = new StringBuilder();
        List<Pair<String, String>> params = new ArrayList<>();
        String deprecation = null;
        String since = null;
        String author = null;
        String see = null;
        for (String line : str.split("\n")) {
            if (line.startsWith("/**")) {
                if (line.length() == 3) {
                    continue;
                } else {
                    content.append(line.replaceFirst("/\\*\\*", "")).append('\n');
                }
            }
            if (line.endsWith("*/")) {
                break;
            }
            var text = trimHead(line.replaceFirst("\\*", ""));
            if (text.startsWith("@")) {
                var s = text.split(" ");
                String key;
                if (s.length != 0) {
                    key = s[0];
                } else {
                    key = text;
                }
                switch (key) {
                    case "@param":
                        String type;
                        String desc;
                        if (s.length >= 3) {
                            type = s[1];
                            StringJoiner joiner = new StringJoiner(" ");
                            for (String s1 : Arrays.copyOfRange(s, 2, s.length - 1)) {
                                joiner.add(s1);
                            }
                            desc = joiner.toString();
                        } else if (s.length == 2) {
                            type = s[1];
                            desc = "Missing description.";
                        } else {
                            break;
                        }
                        params.add(new Pair<>(type, desc));
                        break;
                    case "@deprecated":
                        if (s.length == 0) {
                            deprecation = "Just Deprecated";
                        }
                        deprecation = s[1];
                        break;
                    case "@since":
                        if (s.length == 0) {
                            break;
                        }
                        since = s[1];
                        break;
                    case "@author":
                        if (s.length == 0) {
                            break;
                        }
                        author = s[1];
                        break;
                    case "@see":
                        if (s.length == 0) {
                            break;
                        }
                        see = s[1];
                        break;
                    default:
                        break;
                }
            } else {
                content.append(text).append('\n');
            }
        }
        Comment comment = Comment.builder()
                .data(content.toString())
                .params(params)
                .deprecated(deprecation)
                .since(since)
                .author(author)
                .since(since)
                .see(see)
                .build();
        return comment;
    }

    private static String trimHead(String str) {
        var a = str.endsWith("  ");
        return a ? str.trim() + "  " : str.trim();
    }
}
