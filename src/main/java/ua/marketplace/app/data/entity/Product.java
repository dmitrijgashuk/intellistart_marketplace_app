package ua.marketplace.app.data.entity;

import java.util.Objects;
import java.util.Random;

public class Product {
    private int id;
    private String productName;
    private double productPrice;

    public Product(String productName, double productPrice) {
        id = new Random().nextInt(10000);
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.productPrice, productPrice) == 0 && productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productPrice);
    }
}
