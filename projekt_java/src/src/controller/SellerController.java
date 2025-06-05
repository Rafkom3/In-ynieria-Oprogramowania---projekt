package controller;

import view.SellerView;

import java.util.Scanner;

/**
 * SellerController: pętla menu dla roli SELLER.
 *  - pokazuje SellerView
 *  - reaguje na wybór (placeholdery)
 *  - "Wyloguj" kończy i wraca do LoginController
 */
public class SellerController {
    private final SellerView view;

    public SellerController(Scanner scanner) {
        view = new SellerView(scanner);
    }

    public void run(String username) {
        int choice;
        do {
            choice = view.showMenuAndPrompt();
            switch (choice) {
                case 1:
                    view.showPlaceholder("Zamówienie do magazynu");
                    break;
                case 2:
                    view.showPlaceholder("Sprawdź zamówienia");
                    break;
                case 3:
                    System.out.println("[SELLER] Wylogowywanie...\n");
                    break;
                default:
                    System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
            }
        } while (choice != 3);
    }
}