package yp.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author RickYinPeng
 * @ClassName MyMessageListener
 * @Description
 * @date 2018/11/29/21:46
 */
public class MyMessageListener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        //取消息内容
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = ((TextMessage) message).getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
