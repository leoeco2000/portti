package com.lemon.portti.web.controller.dbtest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lemon.portti.persistence.entity.Student;
import com.lemon.portti.persistence.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbtest")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @GetMapping("/insert")
  public String testInsert() {

    studentService.remove(Wrappers.<Student>emptyWrapper());

    Student stu = new Student()
        .setName("a")
        .setSex(1)
        .setAddr("x");
    studentService.save(stu);

    int count = studentService.count();

    return String.valueOf(count);
  }

}
