package yp.e3mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yp.e3mall.common.pojo.EasyUITreeNode;
import yp.e3mall.mapper.TbItemCatMapper;
import yp.e3mall.pojo.TbItemCat;
import yp.e3mall.pojo.TbItemCatExample;
import yp.e3mall.service.ItemCatService;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类管理
 */
@Service
public class ItemCatServiceImpl  implements ItemCatService{

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据parentID查询子结果
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        //设置查询条件
        //所有字段都会有方法(别怕)
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCatList = tbItemCatMapper.selectByExample(tbItemCatExample);
        //把列表转换成EasyUITreeNode列表
        List<EasyUITreeNode> list = new ArrayList<EasyUITreeNode>();
        for (TbItemCat tbItemCat: tbItemCatList) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbItemCat.getId());
            easyUITreeNode.setText(tbItemCat.getName());
            easyUITreeNode.setState(tbItemCat.getIsParent()==true?"closed":"open");
            list.add(easyUITreeNode);
        }
        //返回结果
        return list;
    }
}
