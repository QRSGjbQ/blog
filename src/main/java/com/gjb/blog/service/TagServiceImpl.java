package com.gjb.blog.service;

import com.gjb.blog.dao.TagRepository;
import com.gjb.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagRepository.findById(id).get();
        if(t==null){
            System.out.println("不存在");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Tag getByName(String name) {
        return tagRepository.findByName(name);
    }
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(converTolist(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort= Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    private List<Long> converTolist(String ids){
        //首先获取list
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            //截取
            String[] idarray=ids.split(",");
            //添加到list中
            for(int i=0;i<idarray.length;i++){
                list.add(new Long (idarray[i]));
            }
        }
        return list;
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }
}
