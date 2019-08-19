package com.lemon.portti.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.portti.persistence.entity.OrderTradeRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 订单交易记录 Mapper 接口
 * </p>
 *
 * @author lemon
 * @since 2019-08-18
 */

@Component
@Mapper
public interface OrderTradeRecordMapper extends BaseMapper<OrderTradeRecord> {
  int deleteByPrimaryKey(Integer id);

//    int insert(OrderTradeRecord record);

  int insertSelective(OrderTradeRecord record);

  OrderTradeRecord selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(OrderTradeRecord record);

  int updateByPrimaryKey(OrderTradeRecord record);

  List<OrderTradeRecord> selectAll();
}
