package controller;

import model.Part;
import controller.PartStore;
import model.Visit;
import controller.VisitStore;
import view.WarehouseView;

import java.util.List;
import java.util.Scanner;

/**
 * WarehouseController obsługuje menu magazyniera:
 * 1. Sprawdź magazyn
 * 2. Wybierz część (placeholder)
 * 3. Przyjęcie wizyty – wybór wizyty z listy i przypisywanie części
 * 4. Wyloguj
 */
public class WarehouseController {
    private final WarehouseView view;
    private final Scanner scanner;
    private final PartStore partStore;
    private final VisitStore visitStore;

    public WarehouseController(Scanner scanner) {
        this.scanner = scanner;
        this.view = new WarehouseView(scanner);
        try {
            // Wczytanie dostępnych części oraz wizyt z XML
            this.partStore = new PartStore("parts.xml");
            this.visitStore = new VisitStore("visits.xml");
        } catch (Exception e) {
            throw new RuntimeException("Błąd inicjalizacji magazyniera: " + e.getMessage(), e);
        }
    }

    /**
     * Główna pętla menu magazyniera.
     */
    public void run(String username) {
        int choice;
        do {
            choice = view.showMenuAndPrompt();
            switch (choice) {
                case 1:
                    displayInventory();
                    break;
                case 2:
                    handleVisit();
                    break;
                case 3:
                    System.out.println("[WAREHOUSE] Wylogowywanie...\n");
                    break;
                default:
                    System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
            }
        } while (choice != 4);
    }

    /**
     * Wyświetla wszystkie części dostępne w magazynie.
     */
    private void displayInventory() {
        System.out.println("\n--- ZAWARTOŚĆ MAGAZYNU ---");
        for (Part p : partStore.getAllParts()) {
            System.out.println(p);
        }
    }

    /**
     * Obsługa przyjęcia wizyty:
     * - wyświetla listę wizyt
     * - pozwala wybrać wizytę
     * - pozwala wybrać część i przypisuje ją (potwierdzenie w konsoli)
     */
    private void handleVisit() {
        List<Visit> visits;
        try {
            visits = visitStore.getAllVisits();
        } catch (Exception e) {
            System.out.println("Błąd wczytywania wizyt: " + e.getMessage());
            return;
        }

        if (visits.isEmpty()) {
            System.out.println("Brak wizyt w systemie.");
            return;
        }

        System.out.println("\n--- LISTA WIZYT ---");
        for (int i = 0; i < visits.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, visits.get(i).toString().replaceAll("\\r?\\n", " | "));
        }
        System.out.print("Wybierz numer wizyty (0 aby wrócić): ");
        int vnum = parseInt();
        if (vnum <= 0 || vnum > visits.size()) {
            return;
        }
        Visit selectedVisit = visits.get(vnum - 1);

        // Teraz pozwalamy przypisać część do tej wizyty
        System.out.println("\n--- PRZYJĘCIE WIZYTY ---");
        System.out.println("Wybrana wizyta: ");
        System.out.println(selectedVisit.toString());
        System.out.println("\nDostępne części:");

        for (Part p : partStore.getAllParts()) {
            System.out.println(p);
        }
        System.out.print("Podaj ID części do przypisania (0 aby anulować): ");
        int pid = parseInt();
        if (pid <= 0) {
            System.out.println("Anulowano przypisanie części.");
            return;
        }

        Part found = partStore.getAllParts()
                .stream()
                .filter(p -> p.getId() == pid)
                .findFirst()
                .orElse(null);
        if (found != null) {
            // tylko wypisujemy potwierdzenie – nie zmieniamy Visit ani XML
            System.out.printf("Przypisano część \"%s\" do wizyty klienta %s.%n",
                    found.toString(), selectedVisit.getUsername());
        } else {
            System.out.println("Nie znaleziono części o ID = " + pid);
        }
    }

    /**
     * Pomocnicza metoda do bezpiecznego parsowania int.
     */
    private int parseInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }
}