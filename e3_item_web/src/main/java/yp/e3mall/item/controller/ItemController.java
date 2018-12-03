package yp.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import yp.e3mall.item.pojo.Item;
import yp.e3mall.pojo.TbItem;
import yp.e3mall.pojo.TbItemDesc;
import yp.e3mall.service.ItemService;

/**
 * @author RickYinPeng
 * @ClassName ItemController
 * @Description 商品详情页面展示Controller
 * @date 2018/12/2/14:58
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable long itemId, Model model){
        //调用服务取商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);

        //去商品描述信息
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);

        //把信息传递给页面
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);

        //返回逻辑视图
        return "item";
    }
}
