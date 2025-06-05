package controller;

import model.User;
import controller.UserStore;
import view.LoginView;

import java.util.Scanner;

/**
 * LoginController: odpowiada za cały proces logowania.
 *  - wczytuje UserStore z XML
 *  - wyświetla LoginView
 *  - po poprawnym logowaniu oddelegowuje do odpowiedniego kontrolera roli
 */
public class LoginController {
    private  UserStore userStore;
    private  LoginView loginView;
    private final Scanner scanner;

    public LoginController(String xmlPath, Scanner scanner) {
        this.scanner = scanner;
        UserStore store;
        try {
            store = new UserStore(xmlPath);
        } catch (Exception e) {
            System.out.println("Błąd przy wczytywaniu users.xml: " + e.getMessage());
            System.exit(1);
            return;
        }
        userStore = store;
        // 2. Ustaw LoginView
        loginView = new LoginView(scanner);
    }

    public void run() {
        while (true) {
            String login = loginView.promptUsername();
            if ("exit".equalsIgnoreCase(login)) {
                System.out.println("Koniec programu. Do widzenia!");
                break;
            }
            String pass = loginView.promptPassword();
            User user = userStore.getUserByUsername(login);
            if (user == null || !user.getPassword().equals(pass)) {
                loginView.showError("Niepoprawny login lub hasło. Spróbuj ponownie.\n");
                continue;
            }
            // poprawne dane → idź do menu roli
            System.out.println("\nZalogowano pomyślnie jako: " + user.getUsername() +
                    " (rola: " + user.getRole() + ")");
            openRoleController(user);
            // po wylogowaniu wrócimy tu i możemy zalogować się ponownie
        }
    }

    private void openRoleController(User user) {
        String role = user.getRole();
        switch (role) {
            case "CLIENT":
                new ClientController(scanner).run(user.getUsername());
                break;
            case "SELLER":
                new SellerController(scanner).run(user.getUsername());
                break;
            case "WAREHOUSE":
                new WarehouseController(scanner).run(user.getUsername());
                break;
            default:
                System.out.println("Nieznana rola: " + role + ". Wracam do logowania.");
        }
    }
}
