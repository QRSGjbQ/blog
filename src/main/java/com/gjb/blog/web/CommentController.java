package com.gjb.blog.web;

import com.gjb.blog.po.Comment;
import com.gjb.blog.po.User;
import com.gjb.blog.service.BlogService;
import com.gjb.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    //静态用户头像
    @Value("${comment.avatar}")
    private String avatar;


    //递归遍历树节点展示
    @GetMapping("/comments/{blogId}")
    public String  comments(@PathVariable Long blogId , Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog::commentList";
    }
    @PostMapping("/comments")
    public  String post(Comment comment, HttpSession httpSession){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user=(User) httpSession.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
