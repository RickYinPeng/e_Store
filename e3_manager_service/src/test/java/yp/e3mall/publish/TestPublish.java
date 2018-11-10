package yp.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author RickYinPeng
 * @ClassName TestPublish
 * @Description 测试不使用Tomcat来发布dubbo服务
 * @date 2018/10/8/22:00
 */
public class TestPublish {

    @Test
    public void publishService() throws Exception{
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        /*本来这句话执行完就没了，但是我们需要让它卡在这不动*/
/*        while (true){
            *//*一秒钟执行一次，这样我们的程序就卡在这了*//*
            Thread.sleep(1000);
        }*/

        System.out.println("服务已经启动。。。。。");
        /*等待键盘输入*/
        System.in.read();
        System.out.println("服务已经关闭。。。。。");

    }
}
