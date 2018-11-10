package yp.e3mall.content.service;

import yp.e3mall.common.pojo.EasyUIDataGridResult;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.pojo.TbContent;

import java.util.List;

public interface ContentService {

    /**
     * 内容分页列表
     * @param id
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getItemList(long id, int page, int rows);

    /**
     * 添加内容
     * @param tbContent
     * @return
     */
    E3Result addContent(TbContent tbContent);

    /**
     * 根据内容分类id查询内容，就展示轮播图那块
     * @param cid
     * @return
     */
    List<TbContent> getContentListByCid(long cid);

}
