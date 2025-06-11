// src/src/model/Order.java
package model;

import java.time.LocalDateTime;

public class Order {
    private final String orderId;
    private final String partName;
    private final int quantity;
    private final LocalDateTime orderDate;
    private final String supplier;
    private String status;

    public Order(String orderId, String partName, int quantity,
                 LocalDateTime orderDate, String supplier, String status) {
        this.orderId   = orderId;
        this.partName  = partName;
        this.quantity  = quantity;
        this.orderDate = orderDate;
        this.supplier  = supplier;
        this.status    = status;
    }

    public String getOrderId()     { return orderId; }
    public String getPartName()    { return partName; }
    public int    getQuantity()    { return quantity; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getSupplier()    { return supplier; }
    public String getStatus()      { return status; }
    public void   setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format(
                "ID: %s%nCzęść: %s%nIlość: %d%nData: %s%nDostawca: %s%nStatus: %s",
                orderId, partName, quantity, orderDate, supplier, status
        );
    }
}
