package com.aop.controller;


import com.aop.annotation.OperationLogDetail;
import com.aop.enums.OperationType;
import com.aop.enums.OperationUnit;
import com.aop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <h3>AspectDemo</h3>
 * <p>测试接口请求</p>
 * @author : 刘芳
 * @date : 2021-10-25
 **/

@Controller
@RequestMapping("user")
public class UserController {


        @Autowired
        private UserService userService;

        /**
         * 访问路径 http://localhost:1111/aop/user/findUserNameByTel?tel=1234567
         * @param tel 手机号
         * @return userName
         */
        @ResponseBody
        @RequestMapping("/findUserNameByTel")
        @OperationLogDetail(detail = "通过手机号获取用户名",level = 3,operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
        public String findUserNameByTel(@RequestParam("tel") String tel){
            return userService.findUserName(tel);
        }

}
