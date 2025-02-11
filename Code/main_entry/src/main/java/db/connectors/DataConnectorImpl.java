package db.connectors;

import db.entities.*;
import db.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class DataConnectorImpl implements DataConnector {

    @Value("${data-service.url}")
    private String dataServiceUrl;

    private final String RESTAURANT_SERVICE_URL = "http://host.docker.internal:8066/";
    private final String COURIER_SERVICE_URL = "http://host.docker.internal:8081/";
    private final String LOGGING_SERVICE_URL = "http://host.docker.internal:8042/";

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Restaurant getRestaurantById(int id) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(dataServiceUrl + "restaurant?id=" + id);
        ResponseEntity<Restaurant> result = restTemplate.getForEntity(uri, Restaurant.class);
        return result.getBody();
    }

    @Override
    public List<Restaurant> getAllRestaurantsById(Set<Integer> ids) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestParameter = "?ids=";
        for (Integer i : ids) {
            requestParameter += i + ",";
        }
        URI uri = new URI(dataServiceUrl + "restaurant/list" + requestParameter);

        ResponseEntity<Restaurant[]> response = restTemplate.getForEntity(uri, Restaurant[].class);
        if (response != null) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            throw new EntityNotFoundException("No restaurants has been found");
        }
    }

    @Override
    public Order postNewOrder(OrderRequest orderRequest) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(dataServiceUrl + "order");
        HttpEntity<OrderRequest> request =
                new HttpEntity<>(orderRequest, headers);
        Order result = restTemplate.postForObject(uri, request, Order.class);
        return result;
    }

    @Override
    public Order sendOrderWithCourier(OrderRequest orderRequest) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(dataServiceUrl + "order/deliver");
        HttpEntity<OrderRequest> request =
                new HttpEntity(orderRequest, headers);
        Order result = restTemplate.postForObject(uri, request, Order.class);

        return result;
    }

    @Override
    public List<Order> getOrdersForCourier(int id) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(dataServiceUrl + "order/courier?id="+id);

        ResponseEntity<Order[]> response = restTemplate.getForEntity(uri, Order[].class);
        if (response != null) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            throw new EntityNotFoundException("No orders has been found");
        }
    }

    @Override
    public List<Order> getOrdersForCustomer(int id) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(dataServiceUrl + "order/customer?id="+id);

        ResponseEntity<Order[]> response = restTemplate.getForEntity(uri, Order[].class);
        if (response != null) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            throw new EntityNotFoundException("No orders has been found");
        }
    }

    @Override
    public List<Order> getOrdersForRestaurant(int id) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(dataServiceUrl + "order/restaurant?id="+id);

        ResponseEntity<Order[]> response = restTemplate.getForEntity(uri, Order[].class);
        if (response != null) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            throw new EntityNotFoundException("No orders has been found");
        }
    }

    @Override
    public int getNearestCourierId(double x, double y) throws URISyntaxException {
        //todo call MongoDB


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(COURIER_SERVICE_URL + "couriers/nearest");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        if (response != null) {
            return Integer.parseInt(response.getBody());
        } else {
            throw new EntityNotFoundException("No couriers has been found");
        }

    }

    @Override
    public User login(UserRequest userRequest) throws URISyntaxException {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            URI uri = new URI(dataServiceUrl + "customer/login");
            HttpEntity<UserRequest> request =
                    new HttpEntity<>(userRequest, headers);
            User result = restTemplate.postForObject(uri, request, User.class);
            return result;
    }

    @Override
    public Customer createCustomer(CustomerRequest customerRequest) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = new URI(dataServiceUrl + "customer");
        HttpEntity<CustomerRequest> request =
                new HttpEntity<>(customerRequest, headers);
        Customer result = restTemplate.postForObject(uri, request, Customer.class);
        return result;
    }

}