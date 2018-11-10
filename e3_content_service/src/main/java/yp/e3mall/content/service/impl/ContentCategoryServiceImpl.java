package yp.e3mall.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yp.e3mall.common.pojo.EasyUITreeNode;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.content.service.ContentCategoryService;
import yp.e3mall.mapper.TbContentCategoryMapper;
import yp.e3mall.pojo.TbContentCategory;
import yp.e3mall.pojo.TbContentCategoryExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName ContentCategoryServiceImpl
 * @Description 内容分类管理
 * @date 2018/10/10/17:39
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 显示分类树
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> catList= tbContentCategoryMapper.selectByExample(tbContentCategoryExample);

        List<EasyUITreeNode>  EasyUITreeNode_List = new ArrayList<>();
        for (int i = 0;i<catList.size();i++){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(catList.get(i).getId());
            node.setText(catList.get(i).getName());
            node.setState(catList.get(i).getIsParent()==true?"closed":"open");
            EasyUITreeNode_List.add(node);
        }

        return EasyUITreeNode_List;
    }

    /**
     * 添加内容分类
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public E3Result addContentCategory(long parentId, String name) {
        //创建一个tb_content_category表对应的pojo
        TbContentCategory tbContentCategory = new TbContentCategory();

        //设置pojo的属性
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        //1：正常 2：删除
        tbContentCategory.setStatus(1);
        //默认排序是1
        tbContentCategory.setSortOrder(1);
        //新添加的节点一定是叶子节点，所以是false
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());

        //插入到数据库,插入完了之后，
        //这个对象tbContentCategory就已经包含了id属性，因为我们设置了在mapper.xml中
        tbContentCategoryMapper.insert(tbContentCategory);

        //判断父节点的isParent属性。如果不是true改为true
        //根据parentid查询父节点
        TbContentCategory Parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if(!Parent.getIsParent()){
            Parent.setIsParent(true);
            //更新到数据库中
            tbContentCategoryMapper.updateByPrimaryKey(Parent);
        }

        //返回结果,返回e3Result，其中Dat属性是上面的pojo，id已经有了
        return E3Result.ok(tbContentCategory);
    }

    @Override
    public void deleteContentCategoryById(long id) {


        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        System.out.println(tbContentCategory);
        TbContentCategory tbContentCategory_Parent = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        //这是删除，所以原来的父节点需要将节点状态改一下
        tbContentCategoryMapper.deleteByPrimaryKey(id);
        if(tbContentCategory_Parent.getIsParent()){
            TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
            criteria.andParentIdEqualTo(tbContentCategory_Parent.getId());

            Integer i = tbContentCategoryMapper.countByExample(tbContentCategoryExample);
            System.out.println("i:"+i);

            if(i==0){
                tbContentCategory_Parent.setIsParent(false);
                tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory_Parent);
            }
        }

    }
}
