package yp.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.search.service.SearchItemService;

/**
 * @author RickYinPeng
 * @ClassName SearchItemController
 * @Description 导入商品数据到索引库
 * @date 2018/11/22/1:07
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importItemList(){
        E3Result e3Result = searchItemService.importAllItems();
        return e3Result;
    }
}
