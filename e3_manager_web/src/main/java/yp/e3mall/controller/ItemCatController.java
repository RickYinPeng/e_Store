package yp.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.pojo.EasyUITreeNode;
import yp.e3mall.service.ItemCatService;

import java.util.List;

/**
 * 商品分类管理Controller
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode>
        getItemCatList(@RequestParam(name = "id",defaultValue = "0") Long parentId){
        //调用服务查询结点列表
        List<EasyUITreeNode> itemCatList = itemCatService.getItemCatList(parentId);
        return itemCatList;
    }
}
