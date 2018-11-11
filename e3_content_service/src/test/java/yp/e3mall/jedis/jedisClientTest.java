package yp.e3mall.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import yp.e3mall.common.jedis.JedisClient;

/**
 * @author RickYinPeng
 * @ClassName jedisClientTest
 * @Description
 * @date 2018/11/11/12:58
 */
public class jedisClientTest {

    @Test
    public void testJedisClient(){
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");

        //从容器中获得JedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("mytest","jedisClient");
        String mytest = jedisClient.get("mytest");
        System.out.println(mytest);
    }

}
