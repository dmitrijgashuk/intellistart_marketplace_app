package ua.marketplace.app.data;

import ua.marketplace.app.data.entity.Product;
import ua.marketplace.app.data.entity.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseExample {
    private Map<Integer,Customer> customers;
    private Map<Integer,Product> products;
    private Map<Integer,List<Product>> orders;


    public DataBaseExample(){
        customers = new HashMap<>();
        products = new HashMap<>();
        orders = new HashMap<>();
    }

    public Product getProduct(int productId){
        if(products.containsKey(productId)){
            return products.get(productId);
        }else {
            throw new DataException("this product not found");
        }
    }

    public void updateCustomer(Customer customer){// todo check ?? why is it here?
        customers.put(customer.getId(),customer);
    }

    public Customer getCustomer(int customerId){
        if(customers.containsKey(customerId)){
            return customers.get(customerId);
        }else{
            throw new DataException("this customer not found");
        }
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> tempList = new ArrayList<>();
        for(var entry: customers.entrySet()){
            tempList.add(entry.getValue());
        }
        return tempList;
    }

    public List<Product> getAllProducts() {
        List<Product> tempList = new ArrayList<>();
        for(var entry:products.entrySet()){
            tempList.add(entry.getValue());
        }
        return tempList;
    }

    public void addCustomer(Customer customer) {
        if(customers.containsValue(customer)){
            throw new DataException("this customer is already exists");
        }
        customers.put(customer.getId(), customer);
    }

    public void addProduct(Product product) {
        if(products.containsValue(product)) {
            throw new DataException("this product is already exists");
        }
        products.put(product.getId(),product);
    }

    public void removeCustomer(int customerId) {
        customers.remove(customerId);
        orders.remove(customerId);
    }

    public void removeProduct(int productId) {
        Product product = products.get(productId);
        for (var listEntry : orders.entrySet()) {
            listEntry.getValue().remove(product);
        }
        products.remove(productId);
    }

    public List<Customer> getCustomersByProduct(int productId) {
        List<Customer> customersByProductList =  new ArrayList<>();
        Product product = products.get(productId);
        for (var listEntry : orders.entrySet()) {
            boolean contains = listEntry.getValue().contains(product);
            if(contains){
                Integer key = listEntry.getKey();
                Customer customer = customers.get(key);
                customersByProductList.add(customer);
            }
        }
        return customersByProductList;
    }

    public List<Product> getProductsByCustomer(int customerId) {
        return orders.get(customerId);
    }

    public void addOrder(int  productId, int customerId) {
        Product product = products.get(productId);
        if(orders.containsKey(customerId)){
            var productList = orders.get(customerId);
            productList.add(product);
        }else{
            ArrayList<Product> temp = new ArrayList<>();
            temp.add(product);
            orders.put(customerId,temp);
        }
    }
}
