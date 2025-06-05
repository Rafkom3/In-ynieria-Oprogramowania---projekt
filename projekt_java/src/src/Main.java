import controller.LoginController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        String xmlPath = "users.xml";
        LoginController loginController = new LoginController(xmlPath, scanner);
        loginController.run();
        scanner.close();
    }
}