package yp.e3mall.item.pojo;

import yp.e3mall.pojo.TbItem;

/**
 * @author RickYinPeng
 * @ClassName Item
 * @Description
 * @date 2018/12/2/14:08
 */
public class Item extends TbItem{

    //因为父类不能强制转换为子类，所以我们得写一个构造方法，将值复制过来
    //因为我们查出来的是一个tbItem对象，这个对象不能直接转换为子类
    //所以我们在构造子类的时候将这个父类对象传入，然后将其属性复制到该子类中即可
    public Item(TbItem tbItem){
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }


    public String [] getImages(){
        String image = this.getImage();
        if(image!=null && !"".equals(image)){
            String[] split = image.split(",");
            return split;
        }
        return null;
    }

}
