package yp.e3mall.service;

import yp.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

    /**
     * 商品类目初始化
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getItemCatList(long parentId);

}
