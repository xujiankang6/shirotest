package com.bdqn.jiankang.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

//验证密码 查找到了1该用户 自定义密码验证器
public class MyMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String pwd = new String(usernamePasswordToken.getPassword());
        String mysqlpwd = (String) info.getCredentials();
        return this.equals(pwd, mysqlpwd);

    }


}
