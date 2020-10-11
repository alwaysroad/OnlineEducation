package com.cornelius.aclservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cornelius.aclservice.entity.UserRole;
import com.cornelius.aclservice.mapper.UserRoleMapper;
import com.cornelius.aclservice.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
