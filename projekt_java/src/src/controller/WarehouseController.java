package controller;

import view.WarehouseView;

import java.util.Scanner;

public class WarehouseController {
    private final WarehouseView view;

    public WarehouseController(Scanner scanner) {
        view = new WarehouseView(scanner);
    }

    public void run(String username) {
        int choice;
        do {
            choice = view.showMenuAndPrompt();
            switch (choice) {
                case 1:
                    view.showPlaceholder("Sprawdź magazyn");
                    break;
                case 2:
                    view.showPlaceholder("Wybierz część");
                    break;
                case 3:
                    view.showPlaceholder("Przyjęcie wizyty");
                    break;
                case 4:
                    System.out.println("[WAREHOUSE] Wylogowywanie...\n");
                    break;
                default:
                    System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
            }
        } while (choice != 4);
    }
}
