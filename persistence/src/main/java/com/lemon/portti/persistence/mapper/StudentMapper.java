package com.lemon.portti.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.portti.persistence.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
//  @Insert("INSERT INTO student (name, sex, addr) VALUES (#{name}, #{sex}, #{addr})")
//  int insert(Student stu);
}