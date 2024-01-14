package com.thesis.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thesis.sys.entity.User;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangyang
 * @since 2024-01-09
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);


    Map<String, Object> getUserInfo(String token);
}
