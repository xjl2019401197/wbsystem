//package com.example.wbsystem_ssm.controller;
//
///**
// * @author: 小e
// * @since: 2023/3/14 20:58
// * @description:
// */
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
//import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @desc： 自定义处理404异常
// * @author： admin
// * @date： 2022/5/26 22:34
// * @version： JDK 1.8
// */
//@Slf4j
//@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
//class CustomErrorController extends BasicErrorController {
//
//
//    public CustomErrorController(ServerProperties serverProperties) {
//        super(new DefaultErrorAttributes(), serverProperties.getError());
//    }
//
//    /**
//     * 覆盖默认的JSON响应
//     */
//    @Override
//    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//
//        HttpStatus status = getStatus(request);
//        Map<String, Object> map = new HashMap<String, Object>(16);
//        map.put("rtnCode", "9999");
//        map.put("rtnMsg", "404");
//        log.info("覆盖默认的JSON响应");
//        return new ResponseEntity<Map<String, Object>>(map, status);
//    }
//
//    /**
//     * 覆盖默认的HTML响应
//     */
//    @Override
//    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
//        //请求的状态
//        response.setStatus(getStatus(request).value());
//        //指定自定义的视图
//        log.info("覆盖默认的HTML响应:{}","https://vue.ruoyi.vip/404");
//        return new ModelAndView("err.html");
//    }
//}
