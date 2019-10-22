package xyz.up123.springboot.common;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.springboot.common
 * @ClassName: ToHtml
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 14:44
 * @Version: 1.0
 */
public class ToHtml {
    public static String getPath(String path) {
        if (path == null) {
            return "";
        }
        return Encrypt.md5(path);
    }

    public static String getPath(HttpServletRequest request) {
        /*String url = "";
        url = request.getScheme() +"://" + request.getServerName()
                + ":" +request.getServerPort()
                + request.getServletPath();

        if (request.getQueryString() != null){
            url += "?" + request.getQueryString();
        }*/
        if (request == null || request.getServletPath() == null) {
            return "";
        }
        String path = Encrypt.md5(request.getServletPath());
        return path;
    }


    public static void toHtml(String realFileName, String template, Map<String, Object> variables) {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");//模板所在目录，相对于当前classloader的classpath。
        resolver.setSuffix(".html");//模板文件后缀
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        //构造上下文(Model)
        Context context = new Context();
        for (Map.Entry entry: variables.entrySet()) {
            context.setVariable(entry.getKey().toString(), entry.getValue());
        }

        //渲染模板
        FileWriter write = null;
        try {
            write = new FileWriter(getPath(realFileName) + ".html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        templateEngine.process(template, context, write);

    }

    public static void toHtml(HttpServletRequest request, String template, ModelAndView mv) {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");//模板所在目录，相对于当前classloader的classpath。
        resolver.setSuffix(".html");//模板文件后缀
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        Map<String, Object> variables = mv.getModel();
        //构造上下文(Model)
        Context context = new Context();
        for (Map.Entry entry: variables.entrySet()) {
            context.setVariable(entry.getKey().toString(), entry.getValue());
        }

        //渲染模板
        FileWriter write = null;
        try {

            String realFileName = "static" + request.getServletPath() + ".html";
            FileUtil.createFile(realFileName);
            write = new FileWriter(realFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        templateEngine.process(template, context, write);

    }

    public static ModelAndView getHtml(HttpServletRequest request, HttpServletResponse response) {

        //String path = getPath(request) + ".html";
        String path = "static" + request.getServletPath() + ".html";
        File file = new File(path);
        ModelAndView mv = new ModelAndView();
        System.out.println(file.lastModified());

        if (file.exists()) {
            mv.setViewName("static" + request.getServletPath());
            return mv;
        }
        return null;
    }

    public static boolean htmlExist(HttpServletRequest request, HttpServletResponse response) {
        String path = getPath(request) + ".html";
        File file = new File(path);
        return file.exists();
    }
}
