package db.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Courier {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "courier")
    private Set<Order> orders;

    public Courier() {
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        if (order.getCourier() == null) {
            order.setCourier(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
