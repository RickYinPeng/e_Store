package yp.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.pojo.EasyUIDataGridResult;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.pojo.TbItem;
import yp.e3mall.service.ItemService;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/rest/item/query/item/desc/{itemId}")
    @ResponseBody
    public E3Result getItemById(@PathVariable Long itemId){
        System.out.println("*************************************");
        System.out.println("itemId:"+itemId);
        E3Result e3Result = itemService.getItemById(itemId);

        return e3Result;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        //调用服务
        EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
        return itemList;
    }

    /**
     * 商品添加功能
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem tbItem,String desc){
        E3Result e3Result = itemService.addItem(tbItem, desc);
        return e3Result;
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result deleteItem(String ids){
        System.out.println("进入删除方法");
        System.out.println("ids:"+ids);
        E3Result e3Result = itemService.deleteItem(ids);
        return e3Result;
    }
}
