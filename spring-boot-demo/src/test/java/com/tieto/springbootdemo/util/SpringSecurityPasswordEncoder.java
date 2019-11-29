package com.tieto.springbootdemo.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SpringSecurityPasswordEncoder {

    private static Logger logger = LoggerFactory.getLogger(SpringSecurityPasswordEncoder.class);

    @Test
    public void encodePassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        logger.info("加密后的密码:" + encode);
        logger.info("bcrypt密码对比:" + passwordEncoder.matches("123456", encode));

        String md5Password = "{MD5}88e2d8cd1e92fd5544c8621508cd706b";
        logger.info("MD5密码对比:" + passwordEncoder.matches("123456", encode));
    }

}
