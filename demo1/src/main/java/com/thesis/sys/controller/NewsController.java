package com.thesis.sys.controller;

import com.thesis.common.vo.Result;
import com.thesis.sys.entity.News;
import com.thesis.sys.entity.User;
import com.thesis.sys.mapper.NewsMapper;
import com.thesis.sys.service.INewsService;
import com.thesis.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangyang
 * @since 2024-01-14
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private INewsService iNewsService;
    @GetMapping("/all")
    public Result<List<News>> getALlNews() {
        List<News> list = iNewsService.list();

        for (News news : list) {
            System.out.println("标题：" + news.getTitle());
            System.out.println("摘要：" + news.getSummary());
            System.out.println("发布时间：" + (news.getPublishTime() != null ? news.getPublishTime() : "N/A"));
            System.out.println("来源：" + (news.getSource() != null ? news.getSource() : "N/A"));
            System.out.println("链接：" + (news.getLink() != null ? news.getLink() : "N/A"));
            System.out.println("------------------------");
        };
        return Result.success(list);
    }

}
