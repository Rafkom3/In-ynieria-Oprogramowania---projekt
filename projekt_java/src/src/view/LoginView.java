package view;
import java.util.Scanner;

public class LoginView {
    private final Scanner scanner;

    public LoginView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptUsername() {
        System.out.print("Login (lub wpisz 'exit' aby zakończyć): ");
        return scanner.nextLine().trim();
    }

    public String promptPassword() {
        System.out.print("Hasło: ");
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println("Błąd: " + message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}