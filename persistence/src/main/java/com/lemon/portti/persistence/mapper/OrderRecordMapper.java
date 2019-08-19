package com.lemon.portti.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.portti.persistence.entity.OrderRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OrderRecordMapper extends BaseMapper<OrderRecord> {
    int deleteByPrimaryKey(Integer id);

//    int insert(OrderRecord record);

    int insertSelective(OrderRecord record);

    OrderRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRecord record);

    int updateByPrimaryKey(OrderRecord record);

    List<OrderRecord> selectAll();
}