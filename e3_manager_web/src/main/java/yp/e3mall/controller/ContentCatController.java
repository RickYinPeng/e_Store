package yp.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.pojo.EasyUITreeNode;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.content.service.ContentCategoryService;

import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName ContentCatController
 * @Description 内容分类管理Controller
 * @date 2018/10/10/18:59
 */
@Controller
public class ContentCatController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 显示内容管理分类
     * @param parentId
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> contentCatList = contentCategoryService.getContentCatList(parentId);
        return contentCatList;
    }

    @RequestMapping(value="/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public E3Result creatContentCategory(long parentId,String name){
        E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
        return e3Result;
    }

    @RequestMapping("/content/category/delete/")
    public void deleteContentCategoryById(long id){
        contentCategoryService.deleteContentCategoryById(id);
    }

}
