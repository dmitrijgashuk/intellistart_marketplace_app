package ua.marketplace.app.consoleui;

import java.util.Scanner;

public class ConsoleDialogue {
    private static final String GREETING_MESSAGE = "-** Welcome **-";
    private static final String GET_NEW_ATTEMPT = " try again or entre '0' to exit";
    private static final String TRY_AGAIN = " try again";
    public static final String BASE_MESSAGE_CHOSE_OPERATION = "Choose next option to continue:";
    public static final String REQUEST_CUSTOMER = "Enter customer id :";
    public static final String REQUEST_PRODUCT = "Enter product id :";

    private Scanner in;

    public ConsoleDialogue (){
        in = new Scanner(System.in);
    }

    public void greeting() {
        System.out.println(GREETING_MESSAGE);
    }

    public void showMainMenu() {
        System.out.println(BASE_MESSAGE_CHOSE_OPERATION);
        for(Command command : Command.values()){
            String message = String.format("%d - %s", command.getNumber(), command.getDescription());
            System.out.println(message);
        }
    }

    public Command getCommand() {
        while (true) {
            try {
                int commandIndex = readIntByConsole();
                if(commandIndex == 0){
                    break;
                }
                return Command.getByNumber(commandIndex);
            } catch (IllegalArgumentException ex ) {
                System.err.println(ex.getMessage() + GET_NEW_ATTEMPT );
            } catch (InvalidInputException ex){
                System.err.println(ex.getMessage() + GET_NEW_ATTEMPT);
            }
        }
        return Command.EXIT;
    }

    public String requestCustomerMenu() {
        System.out.println(REQUEST_CUSTOMER);
        return readStringByConsole();
    }

    public String requestProductMenu() {
        System.out.println(REQUEST_PRODUCT);
        return readStringByConsole();
    }

    public String[] buyProductMenu() {
        String[] parameters = new String[2];
        System.out.println("Enter customer's id who want to by a product :");
        String customerID = readStringByConsole();
        parameters[0] = customerID;

        System.out.println(REQUEST_PRODUCT);
        String productID = readStringByConsole();
        parameters[1] = productID;

        return parameters;
    }

    public String[] addProductMenu(){
        String[] parameters = new String[2];

        System.out.println("Enter product name:");
        String firstName = readStringByConsole();
        parameters[0] = firstName;

        System.out.println("Enter how much money does it cost: (for example - '4.0', can not will be '0')");

        boolean isInterrupt = false;
        while (!isInterrupt) {
            try {
                Double inFloat = readFloatInConsole();
                if (inFloat != 0) {
                    isInterrupt = true;
                    parameters[1] = inFloat.toString();
                }else{
                    System.err.println("can not will be '0" + TRY_AGAIN);
                }
            } catch (InvalidInputException ex){
                System.err.println(ex.getMessage() + TRY_AGAIN);
            }
        }
        return parameters;
    }

    public String[] addCustomerMenu() {
        String[] parameters = new String[3];

        System.out.println("Enter customer first name:");
        String firstName = readStringByConsole();
        parameters[0] = firstName;

        System.out.println("Enter customer last name:");
        String lastName = readStringByConsole();
        parameters[1] = lastName;

        System.out.println("Enter how much money does customer have: (for example - '34.0')");

        boolean isInterrupt = false;
        while (!isInterrupt) {
            try {
                Double inDouble = readFloatInConsole();
                if (inDouble != 0) {
                    isInterrupt = true;
                    parameters[2] = inDouble.toString();
                }else{
                    System.err.println("can not will be '0" + TRY_AGAIN);
                }
                parameters[2] = inDouble.toString();
            } catch (InvalidInputException ex){
                System.err.println(ex.getMessage() + TRY_AGAIN);
            }
        }
        return parameters;
    }

    private int readIntByConsole() {
        String inputString = in.nextLine();
        try {
            return Integer.parseInt(inputString);
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("It is not a int number", exception);
        }
    }

    private Double readFloatInConsole() {
        String inputString = in.nextLine();
        try {
            return Double.parseDouble(inputString);
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("It is not a number", exception);
        }
    }

    private String readStringByConsole() {
        String inputString = in.nextLine();
        if(inputString.isEmpty() && inputString.isBlank()){
            throw new InvalidInputException("can not be empty");
        }
        return inputString;
    }
}
