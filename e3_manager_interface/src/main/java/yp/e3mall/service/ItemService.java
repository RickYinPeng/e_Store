package yp.e3mall.service;

import yp.e3mall.common.pojo.EasyUIDataGridResult;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.pojo.TbItem;

public interface ItemService {

	/**
	 * 根据ID查询商品
	 * @param itemId
	 * @return
	 */
    E3Result getItemById(long itemId);

	/**
	 * 分页功能
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getItemList(int page,int rows);

    /**
     * 新增商品
     */
    E3Result addItem(TbItem tbItem,String desc);

    /**
     * 根据商品ID删除商品
     * @param ids
     * @return
     */
    E3Result deleteItem(String ids);


}
