package com.gjb.blog.dao;

import com.gjb.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepsitory extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
