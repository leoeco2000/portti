package com.lemon.portti.persistence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.portti.persistence.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserService extends IService<User> {

  User selectByUserNamePassword(@Param("userName") String userName,
      @Param("password") String password);
}
