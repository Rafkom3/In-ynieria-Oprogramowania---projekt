package controller;

import view.ClientView;

import java.util.Scanner;

import model.Cart;
import model.Part;
import controller.PartStore;
import view.ClientView;

import java.util.List;
import java.util.Scanner;
import model.Visit;
import controller.VisitStore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClientController {
    private final ClientView view;
    private final Scanner scanner;

    public ClientController(Scanner scanner) {
        this.scanner = scanner;
        this.view = new ClientView(scanner);
    }

    public void run(String username) {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        PartStore partStore;
        try {
            partStore = new PartStore("parts.xml");
        } catch (Exception e) {
            System.out.println("Błąd wczytywania parts.xml: " + e.getMessage());
            return;
        }
        List<Part> availableParts = partStore.getAllParts();

        Cart cart = new Cart();

        boolean logout = false;
        while (!logout) {
            int choice = view.showMenuAndPrompt();
            switch (choice) {
                case 1:
                    orderParts(availableParts, cart);
                    break;
                case 2:
                    System.out.println("\n--- ZAWARTOŚĆ KOSZYKA ---");
                    System.out.println(cart.toString());
                    break;
                case 3:
                    System.out.println("[CLIENT] Opcja 'Zapisz wizytę'");
                    registerVisit(username);
                    break;
                case 4:
                    System.out.println("[CLIENT] Wylogowywanie...\n");
                    logout = true;
                    break;
                default:
                    System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
            }
        }
    }

    private void orderParts(List<Part> availableParts, Cart cart) {
        System.out.println("\n--- DOSTĘPNE CZĘŚCI ---");
        for (Part p : availableParts) {
            System.out.println(p.toString());
        }

        System.out.print("Podaj ID części do dodania do koszyka (lub 0, by wrócić): ");
        String line = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawne ID. Wracam do menu klienta.");
            return;
        }
        if (id == 0) {
            return;
        }

        Part found = null;
        for (Part p : availableParts) {
            if (p.getId() == id) {
                found = p;
                break;
            }
        }
        if (found != null) {
            cart.addPart(found);
            System.out.println("Dodano do koszyka: " + found.getName() +
                    " (cena: " + found.getPrice() + " zł)");
        } else {
            System.out.println("Nie znaleziono części o ID = " + id);
        }
    }

    private void registerVisit(String username) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- REJESTRACJA WIZYTY ---");
        String[] services = {"Przegląd", "Zakup części", "Diagnostyka", "Inne"};
        for (int i = 0; i < services.length; i++) {
            System.out.printf("%d. %s%n", i+1, services[i]);
        }
        System.out.print("Wybierz usługę (1-" + services.length + "): ");
        int svcChoice;
        try {
            svcChoice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny wybór. Anuluję rezerwację.");
            return;
        }
        String serviceType;
        if (svcChoice >= 1 && svcChoice <= services.length) {
            serviceType = services[svcChoice-1];
            if (serviceType.equals("Inne")) {
                System.out.print("Opisz usługę: ");
                serviceType = sc.nextLine().trim();
            }
        } else {
            System.out.println("Niepoprawna opcja. Anuluję.");
            return;
        }

        System.out.print("Podaj datę i godzinę (format RRRR-MM-DD HH:MM): ");
        String dtLine = sc.nextLine().trim();
        LocalDateTime dateTime;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            dateTime = LocalDateTime.parse(dtLine, fmt);
        } catch (DateTimeParseException e) {
            System.out.println("Niepoprawny format daty. Anuluję.");
            return;
        }

        System.out.print("Dane kontaktowe (telefon lub e-mail): ");
        String contact = sc.nextLine().trim();

        System.out.print("Dodatkowe uwagi (opcjonalnie): ");
        String notes = sc.nextLine().trim();

        Visit visit = new Visit(username, serviceType, dateTime, contact, notes, "oczekująca");
        try {
            VisitStore vs = new VisitStore("visits.xml");
            vs.saveVisit(visit);
            System.out.println("Wizyta zapisana pomyślnie.\nPotwierdzenie:");
            System.out.println(visit);
        } catch (Exception e) {
            System.out.println("Błąd zapisu wizyty: " + e.getMessage());
        }
    }

}