package yp.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RickYinPeng
 * @ClassName HtmlGenController
 * @Description 生成静态页面测试
 * @date 2018/12/6/20:59
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        //加载模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //创建一个数据集
        Map data = new HashMap<>();
        data.put("hello","你好世界！！！");
        //指定文件输出的路径及文件名
        Writer writer = new FileWriter("G:\\freemark\\hello2.html");
        //输出文件
        template.process(data,writer);
        //关闭流
        writer.close();
        return "OK";
    }
}
