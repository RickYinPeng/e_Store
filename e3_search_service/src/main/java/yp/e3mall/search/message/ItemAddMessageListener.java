package yp.e3mall.search.message;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import yp.e3mall.common.pojo.SearchItem;
import yp.e3mall.search.mapper.ItemMapper;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author RickYinPeng
 * @ClassName ItemAddMessageListener
 * @Description 监听商品添加事件，接受消息后，将对应的商品信息同步到索引库
 * @date 2018/11/30/12:14
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapperp;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("接受到消息，并同步索引库！！！");
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;

            String text = textMessage.getText();
            long id = Long.valueOf(text);
            //等待事务提交
            Thread.sleep(1000);

            //根据商品id查询商品信息
            SearchItem searchItem = itemMapperp.getItemById(id);

            //创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            //向文档对象中添加域
            document.addField("id", searchItem.getId());
            System.out.println(searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            System.out.println(searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            System.out.println(searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            System.out.println(searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            System.out.println(searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());

            //把文档对象写入索引库
            solrServer.add(document);

            //提交
            solrServer.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
