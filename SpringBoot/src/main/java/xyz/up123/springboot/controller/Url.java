package xyz.up123.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.up123.springboot.server.UserServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.springboot.controller
 * @ClassName: Url
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/13 13:53
 * @Version: 1.0
 */

@Controller()
@RequestMapping("/url")
public class Url {

    @Autowired
    private UserServer userServer;

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

    @GetMapping("/demo3")
    public String demo3(@RequestHeader(name = "myHeader") String myHeader,
                      @CookieValue(name = "myCookie") String myCookie) {
        //System.out.println("myHeader:" + myHeader);
        //System.out.println("myCookie:" + myCookie);
        //userServer.sayHello();
        return "/index";
    }
}
