package com.lemon.portti.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.portti.persistence.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserLogMapper extends BaseMapper<UserLog> {
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UserLog record);

    int insertSelective(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);
}