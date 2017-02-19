package de.wolff.monolith.order;

import de.wolff.monolith.catalog.Item;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OrderLine implements Serializable{

    private static final long serialVersionUID = 4605485362930899306L;

    @Id
    @ManyToOne
    private Order order;

    @Id
    @ManyToOne
    private Item item;

    @Column(name = "F_COUNT")
    private int count;

    public OrderLine() {
    }

    public OrderLine(int count, Item item) {
        this.count = count;
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
