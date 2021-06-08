package yildiz.edu.tr.onur.proje;

import java.util.Date;

public class Item {
    private int id;
    private String kind, color, pattern, imgName, buyingDate;
    private int price;

    public Item(int id, String kind, String color, String pattern, String imgName, String buyingDate, int price) {
        this.id = id;
        this.kind = kind;
        this.color = color;
        this.pattern = pattern;
        this.imgName = imgName;
        this.buyingDate = buyingDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", kind='" + kind + '\'' +
                ", color='" + color + '\'' +
                ", pattern='" + pattern + '\'' +
                ", imgName='" + imgName + '\'' +
                ", buyingDate='" + buyingDate + '\'' +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(String  buyingDate) {
        this.buyingDate = buyingDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
