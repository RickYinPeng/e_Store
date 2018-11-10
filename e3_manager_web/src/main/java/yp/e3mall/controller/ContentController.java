package yp.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.pojo.EasyUIDataGridResult;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.content.service.ContentService;
import yp.e3mall.pojo.TbContent;

/**
 * @author RickYinPeng
 * @ClassName ContentController
 * @Description
 * @date 2018/10/13/21:14
 */
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(@RequestParam("categoryId") long id, Integer page, Integer rows){
        EasyUIDataGridResult itemList = contentService.getItemList(id, page, rows);
        return itemList;
    }

    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent tbContent){
        E3Result e3Result = contentService.addContent(tbContent);
        return e3Result;
    }
}
