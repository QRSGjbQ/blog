package com.gjb.blog.service;

import com.gjb.blog.dao.BlogRepsitory;
import com.gjb.blog.po.Blog;
import com.gjb.blog.po.Type;
import com.gjb.blog.util.MarkdownUtils;
import com.gjb.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepsitory blogRepsitory;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepsitory.findById(id).orElse(null);
    }

    //动态查询的判定过程条件
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //判断标题是否符合
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                //根据id值来判断类型
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                //根据是否推荐来进行判断
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    //分页查询
    @Override
    public Page<Blog> listBlog(Pageable pageabl) {
        return blogRepsitory.findAll(pageabl);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepsitory.findByQuery(query, pageable);
    }


    @Transactional
    @Override
    public Blog savaBlog(Blog blog) {
        if (blog.getId() == null) {
            //进行日期的设置
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            //新增为0
            blog.setViews(0);
        } else {
            //这里是更新
            blog.setUpdateTime(new Date());
        }
        return blogRepsitory.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepsitory.findById(id).get();
        if (b == null) {
            System.out.println("空值,不存在");
        }
        BeanUtils.copyProperties(blog, b);
        return blogRepsitory.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepsitory.deleteById(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepsitory.findTop(pageable);
    }

    //用于前端显示
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepsitory.findById(id).get();
        if (blog == null) {
            System.out.println("没找到");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }


}
