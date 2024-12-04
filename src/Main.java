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
                    3. Все юзеры.
                    4. Выход.
                    """);

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    bank.bankInterface.signup();
                    break;
                case "2":
                    if (bank.bankInterface.signin()) {
                        boolean isLoggedIn = true;
                        while (isLoggedIn) {
                            System.out.print("""
                                    Добро пожаловать в личный кабинет!
                                    1. Добавить карту.
                                    2. Удалить карту.
                                    3. Показать карты.
                                    4. Выйти.
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
                                    isLoggedIn = false;
                                    break;
                                default:
                                    System.out.println("Такой команды не существует!");
                            }
                        }
                    } else {
                        System.out.println("Неверный логин или пароль.");
                    }
                    break;
                case "3":
                    bank.bankInterface.showUsers();
                    break;
                case "4":
                    System.out.println("Спасибо, что используете банк. До свидания!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Такой команды не существует! Попробуйте снова.");
            }
        }
    }
}
