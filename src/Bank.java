import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Bank {
    BankInterface bankInterface = new BankInterface() {
        private final ArrayList<String[]> users = new ArrayList<>();
        private final Scanner sc = new Scanner(System.in);

        @Override
        public void addAdmin() {
            String[] admin = {"Бехруз", "Муталов", "Админ", "beh123mut1195", "Админ", String.valueOf(UUID.randomUUID())};
            users.add(admin);
        }

        @Override
        public void signup() {
            String[] user = createUser();
            users.add(user);
            System.out.println("Пользователь был успешно зарегистрирован!");
        }

        @Override
        public boolean signin() {
            System.out.println("Введите ваш никнейм: ");
            String nickname = sc.nextLine().trim();

            System.out.println("Введите ваш пароль: ");
            String password = sc.nextLine().trim();

            String role = getRole(nickname, password);
            if (role != null) {
                System.out.println("Успешный вход! Ваша роль: " + role);
                return true;
            } else {
                System.out.println("Неверный никнейм или пароль.");
                return false;
            }
        }

        @Override
        public void showUsers() {
            if (!users.isEmpty()) {
                for (int i = 0; i < users.size(); i++) {
                    System.out.println(
                            (i + 1) + ". Имя: " + users.get(i)[0] +
                                    ", Фамилия: " + users.get(i)[1] +
                                    ", Никнейм: " + users.get(i)[2] +
                                    ", Пароль: " + users.get(i)[3] +
                                    ", Роль: " + users.get(i)[4] +
                                    ", Айди: " + users.get(i)[5]
                    );
                }
            } else {
                System.out.println("Нет зарегистрированных пользователей.");
            }
        }

        // Вспомогательный метод для определения роли
        private String getRole(String nickname, String password) {
            for (String[] user : users) {
                if (Objects.equals(user[2], nickname) && Objects.equals(user[3], password)) {
                    return user[5];
                }
            }
            return null;
        }

        private String[] createUser() {
            System.out.println("Введите ваше имя (оставьте пустым для значения по умолчанию):");
            String userName = sc.nextLine().trim();
            if (userName.isEmpty()) userName = "Без имени";

            System.out.println("Введите вашу фамилию (оставьте пустым для значения по умолчанию):");
            String userSurname = sc.nextLine().trim();
            if (userSurname.isEmpty()) userSurname = "Без фамилии";

            System.out.println("Введите ваш никнейм (оставьте пустым для значения по умолчанию):");
            String userNickname = sc.nextLine().trim();
            if (userNickname.isEmpty()) userNickname = "Юзер " + users.size();

            System.out.println("Введите ваш пароль (оставьте пустым для значения по умолчанию):");
            String userPassword = sc.nextLine().trim();
            if (userPassword.isEmpty()) userPassword = String.valueOf(UUID.randomUUID());

            return new String[]{userName, userSurname, userNickname, userPassword, "Пользователь", String.valueOf(UUID.randomUUID())};
        }
    };
}
