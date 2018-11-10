package yp.e3mall.content.service;

import yp.e3mall.common.pojo.EasyUITreeNode;
import yp.e3mall.common.utils.E3Result;

import java.util.List;

public interface ContentCategoryService {

    /**
     * 查询内容树列表
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentCatList(long parentId);

    /**
     * 添加内容
     * @param parentId
     * @param name
     * @return
     */
    E3Result addContentCategory(long parentId,String name);

    /**
     * 根据ID删除内容
     * @param id
     */
    void deleteContentCategoryById(long id);
}
