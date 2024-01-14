package com.thesis.sys.mapper;

import com.thesis.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangyang
 * @since 2024-01-12
 */
public interface UserMapper extends BaseMapper<User> {

    public List<String> getNotiByUserId( Integer userId);


}
