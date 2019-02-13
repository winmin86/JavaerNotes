package xyz.up123.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.springboot.controller
 * @ClassName: Url
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/13 13:53
 * @Version: 1.0
 */

@Controller()
@RequestMapping("/url")
public class Url {

    @GetMapping("/forward")
    public String urlFun1(HttpServletRequest request, HttpServletResponse response) {
        return "forward:/url/index";
    }

    @GetMapping("/redirect")
    public String urlFun2(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/url/index";
    }

    @GetMapping("/forwardm")
    public ModelAndView urlFun3(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "esx");
        mv.setViewName("forward:/url/index");
        return mv;
    }

    @GetMapping("/redirectm")
    public ModelAndView urlFun4(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "esx");
        mv.setViewName("redirect:/url/index");
        return mv;
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "/index";
    }
}
