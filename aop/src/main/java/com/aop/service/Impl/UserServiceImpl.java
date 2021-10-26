package com.aop.service.Impl;

import com.aop.aspect.LogAspect;
import com.aop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <h3>AspectDemo</h3>
 * <p>service实现</p>
 * @author : 刘芳
 * @date : 2021-10-25
 **/
@Service
public class UserServiceImpl implements UserService {

        private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

        @Override
        public String findUserName(String tel) {
            LOG.info("tel:" + tel);
            return "zhangsan";
        }
    }
