package yp.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author RickYinPeng
 * @ClassName TestSolrCloud
 * @Description 测试SolCloud
 * @date 2018/11/25/22:51
 */
public class TestSolrCloud {

    @Test
    public void testAddDocument() throws Exception{
        //创建一个集群的连接，应该使用CloudSolrServer创建
        //zkHost:zookeeper的地址列表
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");

        //设置一个defaultCollection属性
        cloudSolrServer.setDefaultCollection("collection2");

        //创建一个文档对象
        SolrInputDocument document= new SolrInputDocument();

        //向文档中添加域
        document.setField("id","solrcloud01");
        document.setField("item_title","测试商品01");
        document.setField("item_price",123);

        //把文件写入索引库
        cloudSolrServer.add(document);

        //提交
        cloudSolrServer.commit();
    }

    @Test
    public void testQueryDocument() throws Exception{
        //创建一个集群的连接，应该使用CloudSolrServer创建
        //zkHost:zookeeper的地址列表
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");

        //设置一个defaultCollection属性
        cloudSolrServer.setDefaultCollection("collection2");

        //创建一个查询条件
        SolrQuery solrQuery = new SolrQuery();

        //设置查询条件
        solrQuery.setQuery("*:*");

        //执行查询
        QueryResponse query = cloudSolrServer.query(solrQuery);

        //取查询结果
        SolrDocumentList results = query.getResults();
        System.out.println("总记录数："+results.getNumFound());

        //打印
        for(SolrDocument solrDocument:results){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("title"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_price"));
        }
    }
}
