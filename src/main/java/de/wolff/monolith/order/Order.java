package de.wolff.monolith.order;

import de.wolff.monolith.catalog.Item;
import de.wolff.monolith.customer.Customer;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERTABLE")
public class Order implements Serializable{

    private static final long serialVersionUID = 5667015424651024817L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Customer customer;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderLine> orderLines;

    public Order(){
        super();
        id = -1L;
        orderLines = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addLine(int count, Item item){
        OrderLine line = new OrderLine(count, item);
        this.orderLines.add(line);
        line.setOrder(this);
    }

    public int getNumberOfLines(){
        return orderLines.size();
    }

    public double totalPrice(){
        return orderLines.stream()
                .map((ol) -> ol.getCount() * ol.getItem().getPrice())
                .reduce(0.0, (d1, d2) -> d1 + d2);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
