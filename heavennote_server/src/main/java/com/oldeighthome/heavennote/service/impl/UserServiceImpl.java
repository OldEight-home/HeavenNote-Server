package com.oldeighthome.heavennote.service.impl;

import com.oldeighthome.heavennote.entity.User;
import com.oldeighthome.heavennote.mapper.UserMapper;
import com.oldeighthome.heavennote.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
