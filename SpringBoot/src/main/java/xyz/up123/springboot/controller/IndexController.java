package xyz.up123.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import xyz.up123.springboot.common.ToHtml;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.springboot.controller
 * @ClassName: IndexController
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 13:39
 * @Version: 1.0
 */
@Controller
public class IndexController {

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/index/{id}")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Integer id) {


        /*ModelAndView html = ToHtml.getHtml(request, response);
        if ( html!= null) {
            return html;
        }
        System.out.println(ToHtml.getPath(request));*/

        List<String> list = new ArrayList<>();
        list.add("需求");
        list.add("成果000");
        list.add("人力111");
        list.add("进度222");

        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "列表数据");
        mv.addObject("array", list);
        mv.setViewName("/index");

        ToHtml.toHtml(request, "/index", mv);
        return mv;
    }

    @GetMapping("/index/log")
    @ResponseBody
    public Object log() {
        logger.trace("【IndexController.class】trace level log input");
        logger.debug("【IndexController.class】debug level log input");
        logger.info("【IndexController.class】info level log input");
        logger.warn("【IndexController.class】warn level log input");
        logger.error("【IndexController.class】error level log input");
        return "hello world";
    }
}
