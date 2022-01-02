package com.group.exam.service.impl;

import com.group.exam.dao.UserMapper;
import com.group.exam.model.entity.SecurityUser;
import com.group.exam.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userMapper.findByID(id);
        if(user==null){
            throw new UsernameNotFoundException("用户名错误！");
        }
        return new SecurityUser(user);
    }
}
