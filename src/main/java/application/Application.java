package application;

import controllers.RootController;

public class Application {
    public static void main(String[] args) {
        RootController controller = new RootController();
        controller.runApplication();
    }
}
