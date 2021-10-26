package com.bs.epc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 进行跳转的路由
 */
@Controller
@RequestMapping("epc")
public class MySystemController {
    @RequestMapping("hello")
    public String hello(){
        return "epc/out/outbreakNetwork";
    }
}
