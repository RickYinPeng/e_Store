package yp.e3mall.cart.service;

import yp.e3mall.common.utils.E3Result;
import yp.e3mall.pojo.TbItem;

import java.util.List;

public interface CartService {

    /**
     * 往redis中添加购物车
     * @param userId 用户id
     * @param itemId 商品id
     * @return
     */
    E3Result addCart(Long userId,Long itemId,Integer num);

    /**
     * 合并服务端购物车列表和Cookie中购物车列表信息
     * @param userId 用户id
     * @param itemList Cookie中购物车信息
     * @return
     */
    E3Result mergeCart(Long userId, List<TbItem> itemList);

    /**
     * 取redis中购物车列表
     * @param userId 用户id
     * @return
     */
    List<TbItem> getCartList(Long userId);

    /**
     * 更新redis中购物车中商品数量
     * @param userId 用户id
     * @param itemId 商品id
     * @param num    商品数量
     * @return
     */
    E3Result updateCartNum(Long userId,Long itemId,int num);

    /**
     *
     * @param userId 用户id
     * @param itemId 商品id
     * @return
     */
    E3Result deleteCartItem(Long userId,Long itemId);
}
