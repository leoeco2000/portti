package com.lemon.portti.persistence.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.portti.persistence.entity.ProductRobbingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ProductRobbingRecordMapper extends BaseMapper<ProductRobbingRecord> {
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(ProductRobbingRecord record);

    int insertSelective(ProductRobbingRecord record);

    ProductRobbingRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductRobbingRecord record);

    int updateByPrimaryKey(ProductRobbingRecord record);
}