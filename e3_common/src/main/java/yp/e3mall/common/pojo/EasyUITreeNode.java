package yp.e3mall.common.pojo;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable{

    //数据库中主键
    private long id;
    //数据库中的分类名字name
    private String text;
    //分类是否为父节点
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUITreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
