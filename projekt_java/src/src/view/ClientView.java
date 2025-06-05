package view;

import java.util.Scanner;

public class ClientView {
    private final Scanner scanner;

    public ClientView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMenuAndPrompt() {
        System.out.println("\n--- MENU KLIENTA ---");
        System.out.println("1. Zamów części");
        System.out.println("2. Zobacz koszyk");
        System.out.println("3. Zapisz wizytę");
        System.out.println("4. Wyloguj");
        System.out.print("Wybierz opcję (1-4): ");
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void showPlaceholder(String actionName) {
        System.out.println("[CLIENT] Wybrałeś: " + actionName + ". (Opcja w budowie)");
    }
}
