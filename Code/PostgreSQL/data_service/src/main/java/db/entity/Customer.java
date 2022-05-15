package db.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Customer {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Long address_id;
    private String phone;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    public Customer() {
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        if (order.getCustomer() == null) {
            order.setCustomer(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Order> getOrders() {
        return orders;
    }
}
