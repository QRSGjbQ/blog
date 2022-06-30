package com.gjb.blog.service;

import com.gjb.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TypeService {
    //新增
    Type savaType(Type type);
    //根据id获得
    Type getType(Long id);
    //根据名称查询
    Type getTypeByName(String name);
    //分页查询
    Page<Type> listType(Pageable pageable);
    //更新
    Type updateType(Long id,Type type);
    //删除
    void deleteType(Long id);
    //type列表
    List<Type> listType();

    //
    List<Type> listTypeTop(Integer size);

}
