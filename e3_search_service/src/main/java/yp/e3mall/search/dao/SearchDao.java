package yp.e3mall.search.dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import yp.e3mall.common.pojo.SearchItem;
import yp.e3mall.common.pojo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author RickYinPeng
 * @ClassName SearchDao
 * @Description 商品搜索Dao
 * @date 2018/11/23/0:31
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    /**
     * 根据查询条件查询索引
     * @param solrQuery
     * @return
     */
    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {
        //根据query查询索引库
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //取查询结果。
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //取查询结果总记录数
        long numFound = solrDocumentList.getNumFound();

        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);

        //取商品列表，需要取高亮显示
        Map<String, Map<String, List<String>>> highlighting =
                queryResponse.getHighlighting();



        List<SearchItem> searchItems = new ArrayList<>();
        for (SolrDocument solrDocument:solrDocumentList){
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) solrDocument.get("id"));
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));

            //取高亮显示
            List<String> strings = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if(strings!=null && strings.size()>0){
                title = strings.get(0);
            }else{
                title = (String) solrDocument.get("item_title");
            }

            searchItem.setTitle(title);
            //添加到商品列表
            searchItems.add(searchItem);
        }
        searchResult.setItemList(searchItems);

        //返回结果
        return searchResult;
    }

}
