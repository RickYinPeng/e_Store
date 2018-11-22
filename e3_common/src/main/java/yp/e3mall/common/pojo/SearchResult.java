package yp.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName SearchResult
 * @Description
 * @date 2018/11/23/0:25
 */
public class SearchResult implements Serializable{
    private long recordCount;
    private int totalPages;
    private List<SearchItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "recordCount=" + recordCount +
                ", totalPages=" + totalPages +
                ", itemList=" + itemList +
                '}';
    }
}
