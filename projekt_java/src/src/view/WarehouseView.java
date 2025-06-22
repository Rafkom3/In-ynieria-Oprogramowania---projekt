package view;

import java.util.Scanner;

public class WarehouseView {
    private final Scanner scanner;

    public WarehouseView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMenuAndPrompt() {
        System.out.println("\n--- MENU MAGAZYNIERA ---");
        System.out.println("1. Sprawdź magazyn");
        System.out.println("2. Przyjęcie wizyty");
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
        System.out.println("[WAREHOUSE] Wybrałeś: " + actionName + ". (Opcja w budowie)");
    }
}
