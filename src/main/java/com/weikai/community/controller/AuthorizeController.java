package com.weikai.community.controller;

import com.weikai.community.dto.AccessTokenDTO;
import com.weikai.community.dto.GithubUserDto;
import com.weikai.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Value("${github.client.id}")
    private String cilenId;
    @Value("${github.client.secret}")
    private String cilenSecret;
    @Value("${github.redirect.uri}")
    private String redirectUrl;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state) throws Exception{
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(cilenId);
        accessTokenDTO.setClient_secret(cilenSecret);
        accessTokenDTO.setCode(code);
        System.out.println(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        System.out.println(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDto user = githubProvider.githubUserDto(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
