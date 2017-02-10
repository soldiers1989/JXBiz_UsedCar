package com.etong.android.jxappfours.models_contrast.javabean;

/**
 * 选择品牌
 * Created by Administrator on 2016/8/15.
 */
public class Models_Contrast_SelectBrand {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    private Integer id;         //品牌ID
    private String image;	     //品牌logo
    private String title;       //品牌名称
    private String letter;      //品牌首字母

    public Models_Contrast_SelectBrand() {

    }
    public Models_Contrast_SelectBrand(int type, String charTv) {
        this.type = type;
        this.charTv = charTv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }


}
