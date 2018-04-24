package controller;

/**
 * Created by Aggrey on 10/30/2017.
 */
public class OrderList {
    private  Integer itemid;
    private  String price;
    private String quantity;
    private String itemname;
    private String total;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getItemid() {
        return itemid;
    }

    public String getPrice() {
        return price;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public OrderList(Integer itemid,String itemname, String quantity, String price, String sum ) {
        this.itemid = itemid;
        this.price = price;
        this.total = sum;
        this.quantity = quantity;
        this.itemname=itemname;
    }
}
