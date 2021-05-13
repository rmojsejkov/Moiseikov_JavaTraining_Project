package app.entities;

import java.util.Objects;

public class Order {

    private int id;
    private int rowId;
    private int loginId;
    private int dateId;
    private String category;
    private float price;

    public Order() {

    }

    public Order(int id, int rowId, int loginId, int dateId, String category, float price) {
        this.id = id;
        this.rowId = rowId;
        this.loginId = loginId;
        this.dateId = dateId;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                rowId == order.rowId &&
                loginId == order.loginId &&
                dateId == order.dateId &&
                Objects.equals(category, order.category) &&
                Objects.equals(price, order.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rowId, loginId, dateId, category, price);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", rowId=" + rowId +
                ", loginId=" + loginId +
                ", dateId=" + dateId +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
