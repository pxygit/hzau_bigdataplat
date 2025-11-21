package com.ruoyi.framework.web.service;

import com.ruoyi.system.service.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;

import org.keycloak.representations.AccessToken;

import java.util.Date;

/**
 * 用户验证处理
 *
 * @author pxy
 */
@Service
public class UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysPermissionService permissionService;

    public UserDetails loadUserByUsername(String username,AccessToken kcToken) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.由系统创建相对应的用户", username);

            // 使用keycloak账户登录后，ry数据库不存在该用户需要创建相应用户，注释错误抛出，改为创建账户
            // throw new ServiceException(MessageUtils.message("user.not.exists"));

            // 在此处实现用户创建逻辑
            SysUser tmpUser = new SysUser();
            tmpUser.setUserName(username);
            tmpUser.setNickName(kcToken.getNickName() != null ? kcToken.getNickName() : kcToken.getFamilyName()+kcToken.getGivenName());
            tmpUser.setEmail(kcToken.getEmail());
            tmpUser.setPhonenumber(kcToken.getPhoneNumber());
            tmpUser.setSex("0");
            tmpUser.setPassword("123456");
            tmpUser.setStatus("0");
            tmpUser.setDelFlag("0");
            tmpUser.setCreateBy("keycloak");
            tmpUser.setCreateTime(new Date());
            Long[] roleIds = {2L};
            tmpUser.setRoleIds(roleIds);
            userService.insertUser(tmpUser);
            user = tmpUser;
            log.info("keycloak用户创建成功：{}",username);
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

//        passwordService.validate(user); 注释掉，避免keycloak与本地密码不一致导致校验失败

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
