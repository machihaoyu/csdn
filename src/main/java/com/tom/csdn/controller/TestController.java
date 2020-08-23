package com.tom.csdn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Administrator on 2020/8/23.
 */
@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/info")
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response, BufferedReader br)
            throws ServletException, IOException {
        if ("POST".equals(request.getMethod())){
        }else if ("GET".equals(request.getMethod())){
            Enumeration<String> paraNames=request.getParameterNames();
            for(Enumeration<String> e=paraNames;e.hasMoreElements();){
                logger.info("get请求信息：");
                String thisName=e.nextElement()+"";  //name名
                logger.info("参数名："+thisName);
                String thisValue=request.getParameter(thisName);   //name名对应的值
                logger.info("参数值："+thisValue);
            }
        }

        //Header部分
        logger.info("header信息："+ request.getHeaderNames());
        Enumeration<?> enum1 = request.getHeaderNames();
        while (enum1.hasMoreElements()) {
            logger.info("header信息：");
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            logger.info(key + "\t" + value);
        }

        Cookie[] cookies = request.getCookies();
        if (null != cookies){
            for(Cookie cookie : cookies){
                logger.info("cookie信息：" + cookie.getName());
            }
        }

        //body部分
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            logger.info("IOException: " + e);
        }
        logger.info("body的参数:" + str);
    }
}
