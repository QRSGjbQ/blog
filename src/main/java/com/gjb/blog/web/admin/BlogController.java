package com.gjb.blog.web.admin;

import com.gjb.blog.po.Blog;
import com.gjb.blog.po.User;
import com.gjb.blog.service.BlogService;
import com.gjb.blog.service.TagService;
import com.gjb.blog.service.TypeService;
import com.gjb.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static  final  String INPUT="admin/blogs-input";
    private static  final  String LIST="admin/blogs";
    private static  final  String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    //查询显示
    @GetMapping("/blogs")
    public String blogs(Model model, @PageableDefault(size = 2, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(Model model, @PageableDefault(size = 2, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs::blogList";
    }
    //新增页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        //初始化分类
        model.addAttribute("types", typeService.listType());
        //初始化标签
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
    //传送给前端的操作：更新和添加进行同一个操作
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes redirectAttributes){
        //当前登录用户的对象
        blog.setUser((User) session.getAttribute("user"));
        //获取所需要的种类值
        blog.setType(typeService.getType(blog.getType().getId()));
        //获取所需要的标签值
        blog.setTags(tagService.listTag(blog.getTagIds()));

        //先进行初始化，在封装对象
        Blog b=blogService.savaBlog(blog);
        if(b==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }
    //更新操作
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable Long id, Model model){
        //初始化分类
        model.addAttribute("types", typeService.listType());
        //初始化标签
        model.addAttribute("tags",tagService.listTag());
        Blog blog=blogService.getBlog(id);
        //初始化，处理成字符串
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }
    //删除操作
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除操作成功");
        return REDIRECT_LIST;
    }
}
