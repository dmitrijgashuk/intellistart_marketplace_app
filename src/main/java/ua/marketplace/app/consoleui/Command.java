package ua.marketplace.app.consoleui;

public enum Command {
    EXIT (0,"exit"),
    ADD_CUSTOMER (1,"add new customer"),
    ADD_PRODUCT (2, "add new product"),
    BUY (3, "buy some product"),
    SHOW_CUSTOMERS (4, "show all customers"),
    SHOW_PRODUCTS (5, "show all products"),
    REMOVE_CUSTOMER (6,"remove customer"),
    REMOVE_PRODUCT (7,"remove product"),
    SHOW_CUSTOMERS_BY_PRODUCT_ID (8,"show all customers by product"),
    SHOW_PRODUCTS_BY_CUSTOMER_ID (9,"show all products buy current customer");

    private int number;
    private String description;

    Command(int operationNumber, String description) {
        this.number = operationNumber;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public static Command getByNumber(int number) {
        for(Command command : values()) {
            if(command.getNumber() == number) {
                return command;
            }
        }
        throw new IllegalArgumentException("Wrong number for command ");
    }
}
