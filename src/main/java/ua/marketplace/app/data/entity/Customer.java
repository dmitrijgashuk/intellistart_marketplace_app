package ua.marketplace.app.data.entity;

import java.util.Objects;
import java.util.Random;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private double amountOfMoney;

    public Customer(String firstName, String lastName, double amountOfMoney) {
        id = new Random().nextInt(10000);// I use bound for convenience, in this situation
        this.firstName = firstName;
        this.lastName = lastName;
        this.amountOfMoney = amountOfMoney;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amount){
        this.amountOfMoney = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Double.compare(customer.amountOfMoney, amountOfMoney) == 0 && firstName.equals(customer.firstName) && lastName.equals(customer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, amountOfMoney);
    }
}
