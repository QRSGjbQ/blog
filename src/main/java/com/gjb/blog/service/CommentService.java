package com.gjb.blog.service;

import com.gjb.blog.po.Comment;

import java.util.List;

public interface CommentService {
    //
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
