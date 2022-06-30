package com.gjb.blog.service;

import com.gjb.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    //添加
    Tag saveTag(Tag tag);
    //删除
    void deleteTag(Long id);
    //更新
    Tag updateTag(Long id,Tag tag);
    //按照id进行查询
    Tag getTag(Long id);
    //按照名称进行查询
    Tag getByName(String name);
    //分页查询
    Page<Tag> listTag(Pageable pageable);
    //所有的Tag列表
    List<Tag> listTag();
    //获取一个id集合
    List<Tag> listTag(String   ids);

    List<Tag> listTagTop(Integer size);
}
