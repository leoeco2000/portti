package com.lemon.portti.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.portti.persistence.entity.User;
import com.lemon.portti.persistence.mapper.UserMapper;
import com.lemon.portti.persistence.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends
    ServiceImpl<UserMapper, User> implements UserService {

  @Override
  public User selectByUserNamePassword(String userName, String password) {
    User user = this.baseMapper.selectByUserNamePassword(userName, password);
    return user;
  }
}
