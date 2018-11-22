package yp.e3mall.search.service;

import yp.e3mall.common.utils.E3Result;

/**
 * @author RickYinPeng
 * @ClassName SearchItemService
 * @Description 导入商品信息到索引库
 * @date 2018/11/21/16:28
 */
public interface SearchItemService {

    E3Result importAllItems();

}
