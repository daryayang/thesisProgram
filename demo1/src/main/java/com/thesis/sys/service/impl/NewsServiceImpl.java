package com.thesis.sys.service.impl;

import com.thesis.sys.entity.News;
import com.thesis.sys.mapper.NewsMapper;
import com.thesis.sys.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangyang
 * @since 2024-01-14
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
