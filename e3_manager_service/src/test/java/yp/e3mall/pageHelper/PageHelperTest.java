package yp.e3mall.pageHelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import yp.e3mall.mapper.TbItemMapper;
import yp.e3mall.pojo.TbItem;
import yp.e3mall.pojo.TbItemExample;

import java.util.List;

public class PageHelperTest {


    @Test
    public void testPageHelp() throws Exception{
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        //从容器中获得Mapper代理对象
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        //执行sql语句之前设置分页信息使用PageHelper的startPage方法
        PageHelper.startPage(1,10);
        //执行查询
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        System.out.println("tbItems.size:"+tbItems.size());
        //取分页信息，PageInfo。
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);
        // 1.可以拿到总记录数
        System.out.println("总记录数"+pageInfo.getTotal());
        // 2.总页数
        System.out.println("总页数"+pageInfo.getPages());
        // 3.当前页显示数量
        System.out.println("当前页显示数量"+tbItems.size());
    }

}
