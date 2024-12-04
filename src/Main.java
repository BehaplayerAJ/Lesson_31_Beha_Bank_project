import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        User user = new User();
        Scanner sc = new Scanner(System.in);

        bank.bankInterface.addAdmin();
        user.userInterface.addDemoCard();

        while (true) {
            System.out.print("""
                    Привет, добро пожаловать в банк!
                    1. Зарегистрироваться.
                    2. Войти.
                    3. Выход.
                    """);

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    bank.bankInterface.signup();
                    break;
                case "2":
                    if (bank.bankInterface.signin() && Objects.equals(bank.bankInterface.getRole(bank.nickname, bank.password), "Пользователь")) {
                        boolean isLoggedIn = true;
                        while (isLoggedIn) {
                            System.out.print("""
                                    Добро пожаловать в личный кабинет!
                                    1. Добавить карту.
                                    2. Удалить карту.
                                    3. Показать карты.
                                    4. Внести деньги.
                                    5. Перевести деньги.
                                    6. Выйти.
                                    """);

                            String cardChoice = sc.nextLine();

                            switch (cardChoice) {
                                case "1":
                                    user.userInterface.addCard();
                                    break;
                                case "2":
                                    user.userInterface.deleteCard();
                                    break;
                                case "3":
                                    user.userInterface.showCards();
                                    break;
                                case "4":
                                    user.userInterface.deposit();
                                    break;
                                case "5":
                                    user.userInterface.withdraw();
                                    break;
                                case "6":
                                    isLoggedIn = false;
                                    break;
                                default:
                                    System.out.println("Такой команды не существует!");
                            }
                        }
                    } else if (Objects.equals(bank.bankInterface.getRole(bank.nickname, bank.password), "Админ")) {
                        boolean isLoggedIn = true;
                        while (isLoggedIn) {
                            System.out.print("""
                                    Добро пожаловать в админ панель!
                                    1. Добавить пользователей.
                                    2. Удалить пользователя.
                                    3. Показать пользователей.
                                    4. Добавить карту.
                                    5. Удалить карту.
                                    6. Показать карты.
                                    7. Внести деньги.
                                    8. Перевести деньги.
                                    9. Выйти.
                                    """);

                            String cardChoice = sc.nextLine();

                            switch (cardChoice) {
                                case "1":
                                    bank.bankInterface.signup();
                                    break;
                                case "2":
                                    bank.bankInterface.removeUser();
                                    break;
                                case "3":
                                    bank.bankInterface.showUsers();
                                    break;
                                case "4":
                                    user.userInterface.addCard();
                                    break;
                                case "5":
                                    user.userInterface.deleteCard();
                                    break;
                                case "6":
                                    user.userInterface.showCards();
                                    break;
                                case "7":
                                    user.userInterface.deposit();
                                    break;
                                case "8":
                                    user.userInterface.withdraw();
                                    break;
                                case "9":
                                    isLoggedIn = false;
                                    break;
                                default:
                                    System.out.println("Такой команды не существует!");
                            }
                        }
                    }
                    break;
                case "3":
                    System.out.println("Спасибо, что используете банк. До свидания!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Такой команды не существует! Попробуйте снова.");
            }
        }
    }
}
