package yp.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yp.e3mall.common.pojo.SearchResult;
import yp.e3mall.search.dao.SearchDao;
import yp.e3mall.search.service.SearchService;

/**
 * @author RickYinPeng
 * @ClassName SearchServiceImpl
 * @Description 商品搜索Service
 * @date 2018/11/23/0:52
 */
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) throws SolrServerException {
        //创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery(keyword);

        //设置分页条件
        if(page <= 0){
            page = 1;
        }
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);

        //设置默认搜索域
        solrQuery.set("df","item_title");

        //开启高亮显示
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");

        //调用Dao
        SearchResult search = searchDao.search(solrQuery);

        //计算总页数
        long recordCount = search.getRecordCount();
        int totalPage = (int) (recordCount/rows);
        if(recordCount%rows>0){
            totalPage++;
        }
        search.setTotalPages(totalPage);
        //返回结果

        return search;
    }
}
