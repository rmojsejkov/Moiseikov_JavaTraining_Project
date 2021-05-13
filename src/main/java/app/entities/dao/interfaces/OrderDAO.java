package app.entities.dao.interfaces;

import app.entities.Order;

import java.util.Collection;

public interface OrderDAO {
    boolean add(Order order);
    boolean delete (int orderId);
    boolean update(Order order, int orderId);
    Order getOrder(int orderId);
    Collection<Order> getAllOrders();
}
