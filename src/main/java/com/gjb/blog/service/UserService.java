package com.gjb.blog.service;

import com.gjb.blog.po.User;

public interface UserService {
    //检查用户名与密码
    User checkUser(String username, String password);
}
