package yp.e3mall.search.mapper;

import yp.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * 自己写的mapper，用于使用自己写的sql语句来查询商品信息和商品分类U
 */
public interface ItemMapper {
    /**
     *
     * 用于将商品信息查出放入solr中
     * @return
     */
     List<SearchItem> getItemList();

    /**
     * 根据id查询单个SearchItem
     * @param itemId
     * @return
     */
     SearchItem getItemById(long itemId);


}
