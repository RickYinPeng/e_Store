package yp.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author RickYinPeng
 * @ClassName TestSolrJ
 * @Description
 * @date 2018/11/21/15:58
 */
public class TestSolrJ {

    /**
     * 添加文档
     * @throws Exception
     */
    @Test
    public void addDocument() throws Exception{
        //创建一个SolrServer对象，创建一个连接。参数：solr服务的url(地址)
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");

        //创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();

        //向文档对象中。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义
        document.addField("id","doc01");
        document.addField("item_title","测试商品01");
        document.addField("item_price",1000);

        //把文档写入索引库
        solrServer.add(document);

        //提交
        solrServer.commit();
    }

    /**
     * 删除文档
     * @throws Exception
     */
    @Test
    public void deleteDocument() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");

        //删除文档
        //solrServer.deleteById("doc01");
        solrServer.deleteByQuery("id:doc01");

        //提交
        solrServer.commit();
    }


    @Test
    public void queryIndex() throws Exception{
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");

        //创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();

        //设置查询条件
        //solrQuery.setQuery("*:*");
        solrQuery.set("q","*:*");

        //执行查询，QueryResponse对象
        QueryResponse queryResponse = solrServer.query(solrQuery);

        //取文档列表，取查询结果的总记录数
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("查询结果总记录数："+results.getNumFound());

        //遍历文档列表，从文档取域的内容
        for(SolrDocument solrDocument: results){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println("--------------------------------------------------");
        }
    }


    @Test
    public void queryIndexFuza() throws Exception{
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");

        //创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();

        //设置查询条件
        solrQuery.setQuery("手机");
        solrQuery.setStart(0);
        solrQuery.setRows(20);
        solrQuery.set("df","item_title");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");

        //执行查询，QueryResponse对象
        QueryResponse queryResponse = solrServer.query(solrQuery);

        //取文档列表，取查询结果的总记录数
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("查询结果总记录数："+results.getNumFound());

        //取高亮
        Map<String, Map<String, List<String>>> highlighting =
                queryResponse.getHighlighting();

        //遍历文档列表，从文档取域的内容
        for(SolrDocument solrDocument: results){
            System.out.println(solrDocument.get("id"));

            List<String> strings = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if(strings!=null && strings.size()>0){
                title = strings.get(0);
            }else{
                title = (String) solrDocument.get("item_title");
            }
            System.out.println(title);
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println("--------------------------------------------------");
        }
    }
}
