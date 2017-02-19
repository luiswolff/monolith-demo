package de.wolff.monolith.order;

import de.wolff.monolith.catalog.Item;
import de.wolff.monolith.customer.Customer;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@ViewScoped
@Named
public class OrderController implements Serializable{

    private static final long serialVersionUID = 7855994749894340242L;

    @Inject
    @Customer.CustomerList
    private List<Customer> customerList;

    @Inject
    @Item.ItemList
    private List<Item> itemList;

    @Inject
    private OrderBean orderBean;

    @Model
    @Produces
    private Order order = new Order();

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void loadOrder(){
        if (orderId == null){
            return;
        }
        order = orderBean.findOne(orderId);
    }

    public List<Order> getOrderList(){
        return Collections.unmodifiableList(orderBean.findAll());
    }

    public Iterable<Customer> getCustomerList() {
        return customerList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public String delete(Long id){
        orderBean.delete(id);
        return "/success";
    }

    public void addLine(){
        order.addLine(1, itemList.get(0));
    }

    public String doOrder(){
        orderBean.save(order);
        return "/success";
    }
}
