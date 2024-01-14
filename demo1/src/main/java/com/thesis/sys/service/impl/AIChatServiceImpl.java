package com.thesis.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thesis.config.XuanYuanConfig;

import com.thesis.sys.service.AIChatService;
import com.thesis.sys.utils.SSEUtils;
import com.thesis.sys.utils.XuanYuanChatUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hty
 * @date 2023-11-23 9:14
 * @email 1156388927@qq.com
 * @description
 */

@Slf4j
@Service
public class AIChatServiceImpl implements AIChatService {

    @Resource
    private XuanYuanConfig xuanYuanConfig;
    @Resource
    private XuanYuanChatUtils xuanYuanChatUtils;
    @Resource
    private SSEUtils sseUtils;

    //历史对话，需要按照user,assistant 使用队列方便控制上下文长度
    LinkedList<Map<String,String>> messages = new LinkedList<>();
    /**
     * 用来异步发送消息
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public String unStreamChat(String question) {
        if(question == null || question.equals("")){
            return "请输入问题";
        }
        String responseJson = null;
        //先获取令牌然后才能访问api
        if (xuanYuanConfig.flushAccessToken() != null) {
            xuanYuanChatUtils.recordChatHistory(messages,"user",question);
            String requestJson = xuanYuanChatUtils.constructRequestJson(1,0.95,1.0,false,messages);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestJson);

            Request request = new Request.Builder()
       
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            try {
                responseJson = client.newCall(request).execute().body().string();
                //将回复的内容转为一个JSONObject
                JSONObject responseObject = JSON.parseObject(responseJson);
                //统计Token的消耗
                xuanYuanChatUtils.countToken(responseObject);
                //将回复的内容添加到消息中
                xuanYuanChatUtils.recordChatHistory(messages,"assistant",responseObject.getString("result"));
            } catch (IOException e) {
                log.error("网络有问题");
                return "网络有问题，请稍后重试";
            }
        }
        return responseJson;
    }

    @Override
    public String streamOutputToTerminal(String question) {
        //将问题放在历史对话中
        xuanYuanChatUtils.recordChatHistory(messages,"user",question);
        StringBuilder answer = new StringBuilder();
        // 发起异步请求
        Response response = xuanYuanChatUtils.getERNIEBot40ChatStream(1,messages,true);
        ResponseBody responseBody = null;
        try {
            //TODO:抽取这部分的逻辑，减少重复代码
            responseBody = response.body();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody.byteStream()));
            String str;
            while ((str = reader.readLine()) != null) {
                // 在控制台输出每个数据块
                System.out.println(str);
                //将结果汇总起来  这里new出来的字符串中是包含id、token消耗数量等信息的JSON字符串，需要做信息统计的话可以在这里进行拓展
                answer.append(str);
                reader.readLine();
            }
        } catch (IOException e) {
            //如果出现了异常就应该将问题也从对话历史中删除
            xuanYuanChatUtils.removeMessage(messages);
            log.error("InputStream流式读取出错 => {}",e.getMessage());
            throw new RuntimeException(e);
        }finally {
            xuanYuanChatUtils.closeStream(response,responseBody,null);
        }

        //将回复的内容添加到消息中
        StringBuilder assistantAnswer = new StringBuilder();
        String[] answerArray = answer.toString().split("data: ");
        for (int i=1;i<answerArray.length;++i) {
            assistantAnswer.append(JSON.parseObject(answerArray[i]).get("result"));
        }
        xuanYuanChatUtils.recordChatHistory(messages,"assistant",assistantAnswer.toString());
        return assistantAnswer.toString();
    }

    @Override
    public void sendMessageToPageBySSE(Long clientId, String question) {
        // 异步发送消息
        executorService.execute(() -> {
            xuanYuanChatUtils.recordChatHistory(messages, "user", question);

            try (Response response = xuanYuanChatUtils.getERNIEBot40ChatStream(1, messages, true)) {
                if (response == null || response.body() == null) {
                    log.error("流获取失败");
                    return;
                }

                try (InputStream inputStream = response.body().byteStream()) {
                    // 以流的方式处理响应内容，输出到控制台
                    byte[] buffer = new byte[4096]; // 增加缓冲区大小以提高性能
                    int bytesRead;
                    StringBuilder answer = new StringBuilder();

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        String str = new String(buffer, 0, bytesRead);
                        // 从6开始，因为 data: 这个前缀占了6个字符，所以 0 + 6 = 6，结尾还需要截取2个字符，因为是以\n\n结尾
                        JSONObject jsonObject = JSON.parseObject(str.substring(6, str.length() - 2));
                        if (jsonObject != null && jsonObject.getString("result") != null) {
                            xuanYuanChatUtils.countToken(jsonObject);
                            answer.append(jsonObject.getString("result"));
                        }
                    }

                    // 向客户端发送消息
                    if (!sseUtils.sendMessage(clientId, answer.toString())) {
                        log.error("消息发送失败了，结束消息发送");
                        return;
                    }

                    // 记录聊天历史
                    xuanYuanChatUtils.recordChatHistory(messages, "assistant", answer.toString());
                } catch (IOException e) {
                    log.error("流式请求出错,断开与{}的连接 => {}", clientId, e.getMessage());
                    // 移除当前连接
                    sseUtils.removeConnect(clientId);
                    // 移除当次的问题，因为向前端发送消息失败，需要重新发送消息
                    xuanYuanChatUtils.removeMessage(messages);
                }
            }
        });
    }

}
