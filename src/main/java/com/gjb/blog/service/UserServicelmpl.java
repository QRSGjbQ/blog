package com.gjb.blog.service;

import com.gjb.blog.dao.UserRepository;
import com.gjb.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

@Service
public class UserServicelmpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findAllByUsernameAndPassword(username,password);
        return user;
    }
}
