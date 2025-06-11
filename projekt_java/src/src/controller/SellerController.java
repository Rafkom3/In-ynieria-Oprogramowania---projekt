package controller;

import view.SellerView;

import java.util.List;
import java.util.Scanner;
import model.Order;
import controller.OrderStore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
                    placeOrder();
                    break;
                case 2:
                    view.showPlaceholder("Sprawdź zamówienia");
                    viewOrders();
                    break;
                case 3:
                    System.out.println("[SELLER] Wylogowywanie...\n");
                    break;
                default:
                    System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
            }
        } while (choice != 3);
    }

    private void placeOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- ZŁOŻENIE ZAMÓWIENIA DO MAGAZYNU ---");

        System.out.print("Nazwa części: ");
        String part = sc.nextLine().trim();

        System.out.print("Ilość: ");
        int qty;
        try {
            qty = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowa ilość. Anuluję.");
            return;
        }

        System.out.print("Dostawca: ");
        String supplier = sc.nextLine().trim();

        LocalDateTime now = LocalDateTime.now();
        String id = OrderStore.generateId();
        Order order = new Order(id, part, qty, now, supplier, "złożone");

        try {
            OrderStore os = new OrderStore("orders.xml");
            os.saveOrder(order);
            System.out.println("Zamówienie zapisane. Szczegóły:");
            System.out.println(order);
        } catch (Exception ex) {
            System.out.println("Błąd przy zapisie: " + ex.getMessage());
        }
    }

    private void viewOrders() {
        System.out.println("\n--- LISTA ZAMÓWIEŃ ---");
        try {
            OrderStore os = new OrderStore("orders.xml");
            List<Order> all = os.getAllOrders();
            if (all.isEmpty()) {
                System.out.println("Brak zamówień w magazynie.");
            } else {
                all.forEach(o -> {
                    System.out.println(o);
                    System.out.println("------------------------");
                });
            }
        } catch (Exception ex) {
            System.out.println("Błąd wczytywania zamówień: " + ex.getMessage());
        }
    }

}