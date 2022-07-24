package ua.marketplace.app;

import ua.marketplace.app.controller.Controller;

public class Application {
    public static void main(String[] args) {
        Controller appController = new Controller();
        appController.run();
    }
}
