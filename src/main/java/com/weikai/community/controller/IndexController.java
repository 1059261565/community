package com.weikai.community.controller;

import com.weikai.community.mapper.UserMapper;
import com.weikai.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String greeting(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findBytoken(token);
                if(user != null){
                    request.getSession().setAttribute("user" , user);
                }
                break;
            }
        }
        }
        return "index";
    }
}
