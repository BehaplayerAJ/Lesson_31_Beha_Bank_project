import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Bank {
    String nickname;
    String password;
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

            for (String[] existingUser : users) {
                if (Objects.equals(existingUser[2], user[2])) {
                    System.out.println("Никнейм уже существует! Попробуйте другой.");
                    return;
                }
            }

            users.add(user);
            System.out.println("Пользователь был успешно зарегистрирован!");
            String[] lastUser = users.getLast();
            System.out.println("Имя: " + lastUser[0] +
                    ", Фамилия: " + lastUser[1] +
                    ", Никнейм: " + lastUser[2] +
                    ", Пароль: " + lastUser[3] +
                    ", Роль: " + lastUser[4] +
                    ", Айди: " + lastUser[5]
            );
        }

        @Override
        public boolean signin() {
            System.out.println("Введите ваш никнейм: ");
            nickname = sc.nextLine().trim();

            System.out.println("Введите ваш пароль: ");
            password = sc.nextLine().trim();

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
        public void removeUser() {
            if (!users.isEmpty()) {
                System.out.println("Для удаления пользователя введите его ID: ");
                String userId = sc.nextLine();

                for (String[] user : users) {
                    if (Objects.equals(user[5], userId) && Objects.equals(user[4], "Админ")) {
                        System.out.println("Ты не можешь удалить админа!");
                        return;
                    }
                }

                if (removeUserById(userId)) {
                    System.out.println("Пользователь был удален!");
                } else {
                    System.out.println("Пользователь не найден!");
                }
            } else {
                System.out.println("Нет пользователей для удаления!");
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

        @Override
        public String getRole(String userNickname, String userPassword) {
            for (String[] user : users) {
                if (Objects.equals(user[2], userNickname) && Objects.equals(user[3], userPassword)) {
                    return user[4];
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
            if (userNickname.isEmpty()) userNickname = "Пользователь " + users.size();
            nickname = userNickname;

            System.out.println("Введите ваш пароль (оставьте пустым для значения по умолчанию):");
            String userPassword = sc.nextLine().trim();
            if (userPassword.isEmpty()) userPassword = String.valueOf(UUID.randomUUID());
            password = userPassword;

            return new String[]{userName, userSurname, userNickname, userPassword, "Пользователь", String.valueOf(UUID.randomUUID())};
        }

        private boolean removeUserById(String id) {
            return users.removeIf(user -> Objects.equals(user[5], id));
        }
    };
}
