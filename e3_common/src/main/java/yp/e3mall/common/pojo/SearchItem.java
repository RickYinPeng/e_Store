package yp.e3mall.common.pojo;

import java.io.Serializable;

/**
 * @author RickYinPeng
 * @ClassName SearchItem
 * @Description 搜索相关的item
 * @date 2018/11/19/18:43
 *
 * SELECT
 *   a.id,
 *   a.title,
 *   a.sell_point,
 *   a.price,
 *   a.image,
 *   b.name          category_name
 * FROM tb_item a
 *  LEFT JOIN tb_item_cat b
 *   ON a.cid = b.id
 * WHERE a.status = 1;
 */
public class SearchItem implements Serializable{
    private String id;
    private String title;
    private String sell_point;
    private long price;
    private String image;
    private String category_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String[] getImages(){
        if(image!=null && !"".equals(image)){
            String[] split = image.split(",");
            return split;
        }
        return null;
    }


    @Override
    public String toString() {
        return "SearchItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", sell_point='" + sell_point + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", category_name='" + category_name + '\'' +
                '}';
    }
}
