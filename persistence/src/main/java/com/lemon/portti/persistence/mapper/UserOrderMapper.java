package com.lemon.portti.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.portti.persistence.entity.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder> {
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UserOrder record);

    int insertSelective(UserOrder record);

    UserOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOrder record);

    int updateByPrimaryKey(UserOrder record);

    UserOrder selectByPkAndStatus(@Param("id") Integer id, @Param("status") Integer status);
}