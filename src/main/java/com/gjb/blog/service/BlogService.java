package com.gjb.blog.service;

import com.gjb.blog.po.Blog;
import com.gjb.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BlogService {
    //根据id值来得到一条信息
    Blog  getBlog(Long id);
    //分页查询，同时是以博客条数条件
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    //分页查询
    Page<Blog> listBlog(Pageable pageabl);
    //判定query值，分页查询
    Page<Blog> listBlog(String query,Pageable pageable);
    //保存方法
    Blog savaBlog(Blog  blog);
    //根据id值进行更新保存操作
    Blog updateBlog(Long id,Blog blog);
    //删除操作
    void deleteBlog(Long id);
    //根据一个值来得到推荐的博客列表
    List<Blog> listRecommendBlogTop(Integer size);
    //用于前端展示
    Blog getAndConvert(Long id);

}
