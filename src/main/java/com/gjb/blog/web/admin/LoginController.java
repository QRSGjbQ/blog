package com.gjb.blog.web.admin;

import com.gjb.blog.po.User;
import com.gjb.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    //进行登录操作
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    @PostMapping("/login")
    public String loginPage(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession httpSession,
                        RedirectAttributes redirectAttributes,
                        Model model){
        User user=userService.checkUser(username, password);
        if(user!=null){
            user.setPassword(null);
            httpSession.setAttribute("user",user);
            return "admin/index";
        }else {
            redirectAttributes.addFlashAttribute("message","用户名或者密码错误");
            return "redirect:/admin";
        }
    }

    //登出
    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("user");
        return "redirect:/admin";
    }
}
