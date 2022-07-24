package ua.marketplace.app.controller;

import ua.marketplace.app.consoleui.ConsoleDialogue;
import ua.marketplace.app.consoleui.Command;
import ua.marketplace.app.consoleui.InvalidInputException;
import ua.marketplace.app.data.DataBaseExample;
import ua.marketplace.app.data.DataException;
import ua.marketplace.app.data.entity.Customer;
import ua.marketplace.app.data.entity.Product;

import java.util.List;

public class Controller {

    private ConsoleDialogue consoleDialogue = new ConsoleDialogue();
    private DataBaseExample dataBaseExample = new DataBaseExample();

    public void run() {
        consoleDialogue.greeting();
        consoleDialogue.showMainMenu();
        boolean isAppAlive = true;
        while (isAppAlive){
            try {
                Command command = consoleDialogue.getCommand();
                if(command == Command.EXIT){
                    isAppAlive = false;
                }
                commandProcessing(command);
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            } catch (DataException ex){
                System.err.println(ex.getMessage());
            }
        }
    }

    private void commandProcessing(Command command) {
        switch (command) {
            case EXIT:
                System.exit(-1);// check code ?
                break;
            case ADD_CUSTOMER: addCustomer();
                break;
            case ADD_PRODUCT: addProduct();
                break;
            case BUY: buyProduct();
                break;
            case SHOW_CUSTOMERS: showCustomers();
                break;
            case SHOW_PRODUCTS: showProducts();
                break;
            case REMOVE_CUSTOMER: removeCustomer();
                break;
            case REMOVE_PRODUCT: removeProduct();
                break;
            case SHOW_CUSTOMERS_BY_PRODUCT_ID: requestByProduct();
                break;
            case SHOW_PRODUCTS_BY_CUSTOMER_ID: requestByCustomer();
                break;
        }
    }

    private void requestByCustomer() {
        String input = consoleDialogue.requestCustomerMenu();
        int id = Integer.parseInt(input);
        Customer customer = dataBaseExample.getCustomer(id);
        List<Product> productsByCustomer = dataBaseExample.getProductsByCustomer(id);
        System.out.println(customer.getFirstName() + " " + customer.getLastName() + " has been bought:" );
        System.out.println("------------------------------------");
        for (Product product: productsByCustomer){
            String format = String.format(" %d | %s | %s ",product.getId(),
                    product.getProductName(),product.getProductPrice());
            System.out.println(format);
        }
        System.out.println("------------------------------------");

    }

    private void requestByProduct() {
        String input = consoleDialogue.requestProductMenu();
        int id = Integer.parseInt(input);
        Product product = dataBaseExample.getProduct(id);
        List<Customer> customersByProduct = dataBaseExample.getCustomersByProduct(id);
        System.out.println("All customers who bought - " + product.getProductName() );
        System.out.println("------------------------------------");
        for (Customer customer: customersByProduct){
            String format = String.format(" %d | %s | %s | %s ",customer.getId(),
                    customer.getFirstName(),customer.getLastName(), customer.getAmountOfMoney());
            System.out.println(format);
        }
        System.out.println("------------------------------------");

    }

    private void removeProduct() {
        String input = consoleDialogue.requestProductMenu();
        int id = Integer.parseInt(input);
        dataBaseExample.removeProduct(id);
        System.out.println("Product has been removed");
    }

    private void removeCustomer() {
        String input = consoleDialogue.requestCustomerMenu();
        int id = Integer.parseInt(input);
        dataBaseExample.removeCustomer(id);
        System.out.println("Customer has been removed");

    }

    private void showProducts() {
        List<Product> allProducts = dataBaseExample.getAllProducts();
        System.out.println("------------------------------------");
        for (Product product: allProducts){
            String input = String.format(" %d | %s | %s ",product.getId(),
                    product.getProductName(),product.getProductPrice());
            System.out.println(input);
        }
        System.out.println("------------------------------------");
    }

    private void showCustomers() {
        List<Customer> allCustomers = dataBaseExample.getAllCustomers();
        System.out.println("------------------------------------");
        for (Customer customer: allCustomers){
            String input = String.format(" %d | %s | %s | %s ",customer.getId(),
                    customer.getFirstName(),customer.getLastName(), customer.getAmountOfMoney());
            System.out.println(input);
        }
        System.out.println("------------------------------------");
    }

    private void buyProduct() {
        String[] buyInfo = consoleDialogue.buyProductMenu();
        int customerID = Integer.parseInt(buyInfo[0]);
        int productID = Integer.parseInt(buyInfo[1]);

        Product product = dataBaseExample.getProduct(productID);
        Customer customer = dataBaseExample.getCustomer(customerID);
        if(customer.getAmountOfMoney()<product.getProductPrice()){
            throw new InvalidInputException("customer has not enough money");
        }else {
            double amount = customer.getAmountOfMoney() - product.getProductPrice();
            customer.setAmountOfMoney(amount);
        }
        dataBaseExample.addOrder(productID,customerID);
        dataBaseExample.updateCustomer(customer);
        System.out.println("Operation successful");
    }

    private void addProduct() {
        String[] dataForCustomer = consoleDialogue.addProductMenu();
        String productName = dataForCustomer[0];
        double cost = Double.parseDouble(dataForCustomer[1]);
        Product product = new Product(productName,cost);

        dataBaseExample.addProduct(product);
        System.out.println("The product has been added");
    }

    private void addCustomer() {
        String[] dataForCustomer = consoleDialogue.addCustomerMenu();
        String firstName = dataForCustomer[0];
        String lastName = dataForCustomer[1];
        double money = Double.parseDouble(dataForCustomer[2]);
        Customer customer = new Customer(firstName,lastName, money);
        dataBaseExample.addCustomer(customer);
        System.out.println("Costumer has been added");
    }
}
