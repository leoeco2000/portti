package com.lemon.portti.persistence;

import com.lemon.portti.PorttiApplication;
import com.lemon.portti.persistence.entity.Student;
import com.lemon.portti.persistence.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//方案1：
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {SqlApplication.class, DataSourceAutoConfiguration.class})
@SpringBootTest(classes = {PorttiApplication.class})
//方案2：
//@RunWith(SpringRunner.class)
//@MybatisTest
//@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
// @Transactional(propagation = Propagation.NOT_SUPPORTED)
public class StudentMapperTest {

  @Autowired
//  private StudentMapper studentMapper;
  private StudentService studentService;

  @Test
  public void testInsert() {
    Student stu = new Student()
        .setName("a")
        .setSex(1)
        .setAddr("x");
    studentService.save(stu);

    int count = studentService.count();
    Assertions.assertThat(count).isEqualTo(2);
  }
}