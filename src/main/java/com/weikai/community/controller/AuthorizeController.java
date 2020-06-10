package com.weikai.community.controller;

import com.weikai.community.dto.AccessTokenDTO;
import com.weikai.community.dto.GithubUserDto;
import com.weikai.community.mapper.UserMapper;
import com.weikai.community.model.User;
import com.weikai.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/*
 * 2020年6月5日17:17:11
 * weikai
 * 登录后回调callback
 */
@Controller
@PropertySource({"classpath:application.properties"})
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String cilenId;
    @Value("${github.client.secret}")
    private String cilenSecret;
    @Value("${github.redirect.uri}")
    private String redirectUrl;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request) throws Exception{
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(cilenId);
        accessTokenDTO.setClient_secret(cilenSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        //调用getAccessToken接口 获取Github返回token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //调用githubUserDto接口 获取用户信息
        GithubUserDto githubUserDtouser = githubProvider.githubUserDto(accessToken);
        if(githubUserDtouser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUserDtouser.getName());
            user.setAccountId(String.valueOf(githubUserDtouser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
            //登陆成功 写cookie和session
            request.getSession().setAttribute("user",githubUserDtouser);
            return  "redirect:/";
        }else {
            //登陆失败  请重新登陆
            return  "redirect:/";
        }
    }
}
