package yp.e3mall.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import yp.e3mall.content.service.ContentService;
import yp.e3mall.pojo.TbContent;

import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName IndexController
 * @Description 首頁展示
 * @date 2018/10/7/13:34
 */
@Controller
public class IndexController {

    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_LUNBO_ID;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        //查询内容列表1
        List<TbContent> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
        //把结果传递给页面
        model.addAttribute("ad1List",ad1List);

        return "index";
    }
}
