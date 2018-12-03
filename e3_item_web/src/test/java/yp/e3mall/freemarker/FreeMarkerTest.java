package yp.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RickYinPeng
 * @ClassName FreeMarkerTest
 * @Description
 * @date 2018/12/2/17:55
 */
public class FreeMarkerTest {

    @Test
    public void testFreeMarkerTest() throws Exception{
        //1、创建一个模板文件
        //2、创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3、设置模板文件保存的目录
        configuration.setDirectoryForTemplateLoading(new File("E:\\IdeaWorpace\\e_Store\\e3_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4、模板文件的编码格式，一般就是utf-8
        configuration.setDefaultEncoding("utf-8");
        //5、加载一个模板文件，创建一个模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //6、创建一个数据集。可以是pojo也可以是map。推荐使用map
        Map data = new HashMap<>();
        data.put("hello","hello freemark");
        //7、创建一个Writer对象，指定输出文件的路径及文件名
        Writer out = new FileWriter(new File("G:\\freemark\\hello.txt"));
        //8、生成静态页面
        template.process(data,out);
        //9、关闭流
        out.close();
    }
}
