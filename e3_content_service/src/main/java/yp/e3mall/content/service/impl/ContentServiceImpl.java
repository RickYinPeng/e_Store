package yp.e3mall.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yp.e3mall.common.pojo.EasyUIDataGridResult;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.content.service.ContentService;
import yp.e3mall.mapper.TbContentMapper;
import yp.e3mall.pojo.TbContent;
import yp.e3mall.pojo.TbContentExample;

import java.util.Date;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName ContentServiceImpl
 * @Description
 * @date 2018/10/13/21:09
 */
@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public EasyUIDataGridResult getItemList(long id, int page, int rows) {
        //设置分页信息
        //page:页码
        //rows:每页显示数量
        PageHelper.startPage(page,rows);
        //执行查询
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(id);
        List<TbContent> itemList = tbContentMapper.selectByExample(tbContentExample);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(itemList);
        //取分页结果
        PageInfo pageInfo = new PageInfo(itemList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public E3Result addContent(TbContent tbContent) {
        //补全内容，因为数据库中有update和creatDate
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //将内容插入内容表
        tbContentMapper.insert(tbContent);

        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
        return tbContents;
    }
}
