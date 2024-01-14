package com.thesis.sys.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.thesis.config.XuanYuanConfig;
import com.thesis.sys.service.AIChatService;
import com.thesis.sys.utils.SSEUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/xuanyuan")
public class XuanYuanTextController2 {

    @Resource
    private SSEUtils sseUtils;
    @Resource
    private AIChatService aiChatService;
    @Resource
    private XuanYuanConfig xuanYuanConfig;

    /**
     * 流式回答 输出到控制台中
     * @return 返回JSON字符串，只包含回答的内容
     */
    @PostMapping("/chat-stream")
    public String streamOutputToTerminal(String question){
        return aiChatService.streamOutputToTerminal(question);
    }

    /**
     * 与前端建立SSE连接
     * @param clientId
     * @return
     */
    @RequestMapping(value = "/sse/connect", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/event-stream;charset=UTF-8")
    public SseEmitter sseConnect(Long clientId){
        return sseUtils.sseConnect(clientId);
    }

    /**
     * SSE方式向前端发送消息
     * @param clientId
     * @param question
     */
    @PostMapping(value = "/sse/chat")
    public void streamOutputToPage(Long clientId,String question){
        aiChatService.sendMessageToPageBySSE(clientId,question);
    }
}
