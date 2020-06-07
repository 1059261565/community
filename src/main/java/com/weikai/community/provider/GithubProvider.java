package com.weikai.community.provider;


import com.alibaba.fastjson.JSON;
import com.weikai.community.dto.AccessTokenDTO;
import com.weikai.community.dto.GithubUserDto;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 2020年6月5日17:27:33
 * weikai
 * 模拟POST请求 请求Github https://github.com/login/oauth/access_token 获取access_token
 */
@Component
public class GithubProvider {

        public String getAccessToken(AccessTokenDTO accessTokenDTO) throws Exception{

            String url = "https://github.com/login/oauth/access_token";


            MediaType mediaType = MediaType.get("application/json");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
                return token;

        }

        public GithubUserDto githubUserDto(String accessToken) throws Exception {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token=" + accessToken)
                    .build();
            Response c = client.newCall(request).execute();
            String string = c.body().string();
            GithubUserDto githubUserDto = JSON.parseObject(string, GithubUserDto.class);
            return githubUserDto;
        }
}
