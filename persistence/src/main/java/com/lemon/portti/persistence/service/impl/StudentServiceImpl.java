package com.lemon.portti.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.portti.persistence.entity.Student;
import com.lemon.portti.persistence.mapper.StudentMapper;
import com.lemon.portti.persistence.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends
    ServiceImpl<StudentMapper, Student> implements StudentService {

}
