package com.gjb.blog.dao;

import com.gjb.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepsitory extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    //得到推荐的博客列表信息
    @Query("select  b from t_blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);
    //得到模糊查询的博客信息
    @Query("select  b from t_blog  b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);
}
