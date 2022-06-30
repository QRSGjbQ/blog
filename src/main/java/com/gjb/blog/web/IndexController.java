package com.gjb.blog.web;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public  String blog(Model model, @PageableDefault(size = 2, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable){
        //分页的数据
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tag",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }
    @PostMapping("/search")
    public String search(Model model, @PageableDefault(size = 2, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query){
        //搜索
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        //返回搜索的字符串
        model.addAttribute("query",query);
        return "search";
    }
    //前端展示经过转换过后的格式
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id , Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return  "blog";
    }
}
