package view;

import java.util.Scanner;

public class SellerView {
    private final Scanner scanner;

    public SellerView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMenuAndPrompt() {
        System.out.println("\n--- MENU SPRZEDAWCY ---");
        System.out.println("1. Zamówienie do magazynu");
        System.out.println("2. Sprawdź zamówienia");
        System.out.println("3. Wyloguj");
        System.out.print("Wybierz opcję (1-3): ");
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void showPlaceholder(String actionName) {
        System.out.println("[SELLER] Wybrałeś: " + actionName + ". (Opcja w budowie)");
    }
}
