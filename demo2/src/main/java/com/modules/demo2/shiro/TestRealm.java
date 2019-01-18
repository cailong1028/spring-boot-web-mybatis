package com.modules.demo2.shiro;

import com.modules.demo2.entity.TUserEntity;
import com.modules.demo2.service.TUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class TestRealm extends AuthorizingRealm {

    @Autowired
    private TUserService tUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

//        String password = (String) authenticationToken.getCredentials();
        String username = ((UsernamePasswordToken) authenticationToken).getUsername();
        String password = new String(((UsernamePasswordToken) authenticationToken).getPassword());
        TUserEntity tUserEntity = tUserService.getByUsername(username);

        if(!tUserEntity.getPassword().equals(password)){
            return null;
        }
        return new SimpleAuthenticationInfo(tUserEntity, password, getName());
    }
}
