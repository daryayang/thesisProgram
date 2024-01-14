package com.thesis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Date;

@Configuration
@Slf4j
public class XuanYuanConfig {

    @Value("${xuanyuan.apiKey}")
    public String API_KEY;
    @Value("${xuanyuan.secretKey}")
    public String SECRET_KEY;
    @Value("${xuanyuan.accessTokenUrl}")
    public String ACCESS_TOKEN_URL;
    @Value("${xuanyuan.4bit.0URL}")
    public String XUANYUAN_4bit_URL;

    // 过期时间为30天
    public String ACCESS_TOKEN = null;
    public String REFRESH_TOKEN = null;

    public Date CREATE_TIME = null; // accessToken创建时间

    public Date EXPIRATION_TIME = null; // accessToken到期时间

    /**
     * 获取accessToken
     *
     * @return true表示成功 false表示失败
     */
    public synchronized String flushAccessToken() {
        // 判断当前AccessToken是否为空且判断是否过期
        if (ACCESS_TOKEN != null && EXPIRATION_TIME.getTime() > CREATE_TIME.getTime()) return ACCESS_TOKEN;

        // 构造请求体 包含请求参数和请求头等信息
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "");
        Request request = new Request.Builder()
                .url(ACCESS_TOKEN_URL + "?client_id=" + API_KEY + "&client_secret=" + SECRET_KEY + "&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
        String response = null;
        try {
            // 请求
            response = HTTP_CLIENT.newCall(request).execute().body().string();
        } catch (IOException e) {
            log.error("ACCESS_TOKEN获取失败");
            return null;
        }

        // 刷新令牌以及更新令牌创建时间和过期时间
        JSONObject jsonObject = JSON.parseObject(response);
        ACCESS_TOKEN = jsonObject.getString("access_token");
        REFRESH_TOKEN = jsonObject.getString("refresh_token");
        CREATE_TIME = new Date();
        EXPIRATION_TIME = new Date(Long.parseLong(jsonObject.getString("expires_in")) + CREATE_TIME.getTime());

        return ACCESS_TOKEN;
    }
}
