import java.util.*;

public class User {
    UserInterface userInterface = new UserInterface() {
        private final UserEnum[] cardTypes = UserEnum.values();
        private final ArrayList<String[]> cards = new ArrayList<>();
        private final Scanner sc = new Scanner(System.in);
        private final Random rand = new Random();

        @Override
        public void addDemoCard() {
            StringBuilder demoCardNumber = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                demoCardNumber.append(rand.nextInt(10));
            }

            int demoCardBalance = rand.nextInt(1001);

            String[] demoCard = {"Демо", String.valueOf(demoCardNumber), UserEnum.UZCARD.name(), String.valueOf(demoCardBalance), UUID.randomUUID().toString()};
            cards.add(demoCard);
        }

        @Override
        public void addCard() {
            String[] card = createCard();
            cards.add(card);
            System.out.println("Карта была успешно добавлена!");
        }

        @Override
        public void deleteCard() {
            if (!cards.isEmpty()) {
                System.out.println("Для удаления карты введите её ID: ");
                String userId = sc.nextLine();

                if (removeCardById(userId)) {
                    System.out.println("Карта был удалена!");
                } else {
                    System.out.println("Карта не найдена!");
                }
            } else {
                System.out.println("Нет карт для удаления!");
            }
        }

        @Override
        public void showCards() {
            if (!cards.isEmpty()) {
                for (int i = 0; i < cards.size(); i++) {
                    System.out.println(i + 1 + ". Имя карты: " + cards.get(i)[0] + ". Номер карты: " + cards.get(i)[1] + ". Тип карты: " + cards.get(i)[2] + ". Баланс карты: " + cards.get(i)[3] + ". Айди карты: " + cards.get(i)[4]);
                }
            } else {
                System.out.println("В датабазе нет карт!");
            }
        }

        @Override
        public void deposit() {
            System.out.println("Для депозита введите id вашей карточки (по умолчанию - первая карточка):");
            String userId = sc.nextLine();
            if (userId.isBlank()) userId = cards.getFirst()[4];

            if (!depositCard(userId)) {
                System.out.println("Карта не найдена!");
            } else {
                System.out.println("Деньги успешно внесены на вашу карточку!");
            }
        }

        @Override
        public void withdraw() {
            System.out.println("Для вывода введите id вашей карточки (по умолчанию - первая карточка):");
            String userId = sc.nextLine().trim();
            if (userId.isBlank()) userId = cards.getFirst()[4];

            System.out.println("Введите id карты отправителя (по умолчанию - вторая карточка, если она есть)");
            String secondCardId = sc.nextLine().trim();

            if (!withdrawCard(userId, secondCardId)) {
                System.out.println("Карта не найдена!");
            } else {
                System.out.println("Деньги успешно выведены из вашей карточку!");
            }
        }

        public boolean depositCard(String cardId) {
            System.out.println("Введите количевство денег которое вы хотите внести (по умолчанию - 5)");
            String depositAmount = sc.nextLine();
            if (depositAmount.isBlank()) depositAmount = "5";
            int depositBalance;

            try {
                depositBalance = Integer.parseInt(depositAmount);
                if (depositBalance <= 0) {
                    System.out.println("Сумма должна быть положительным числом!");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод суммы. Попробуйте снова.");
                return false;
            }

            for (String[] card : cards) {
                if (Objects.equals(card[4], cardId)) {
                    int balance = Integer.parseInt(card[3]);
                    card[3] = String.valueOf(balance + depositBalance);
                    return true;
                }
            }
            return false;
        }

        public boolean withdrawCard(String cardId, String secondCardId) {
            System.out.println("Введите количество денег, которое вы хотите вывести (по умолчанию - 5):");
            String withdrawAmount = sc.nextLine().trim();
            if (withdrawAmount.isBlank()) withdrawAmount = "5";

            int withdrawBalance;
            try {
                withdrawBalance = Integer.parseInt(withdrawAmount);
                if (withdrawBalance <= 0) {
                    System.out.println("Сумма должна быть положительным числом!");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод суммы. Попробуйте снова.");
                return false;
            }

            for (String[] card : cards) {
                if (Objects.equals(card[4], cardId)) {
                    int balance = Integer.parseInt(card[3]);
                    if (balance < withdrawBalance) {
                        System.out.println("Недостаточно средств на карте!");
                        return false;
                    }

                    card[3] = String.valueOf(balance - withdrawBalance);

                    if (!secondCardId.isBlank()) {
                        for (String[] secondCard : cards) {
                            if (Objects.equals(secondCard[4], secondCardId)) {
                                int secondCardBalance = Integer.parseInt(secondCard[3]);
                                secondCard[3] = String.valueOf(secondCardBalance + withdrawBalance);
                                System.out.println("Успешный перевод " + withdrawBalance + " единиц на карту " + secondCard[1]);
                                return true;
                            }
                        }
                        System.out.println("Карта-получатель с указанным ID не найдена!");
                        return false;
                    }

                    System.out.println("Снятие " + withdrawBalance + " единиц успешно выполнено с карты " + card[1]);
                    return true;
                }
            }

            System.out.println("Карта с указанным ID не найдена.");
            return false;
        }


        public String[] createCard() {
            System.out.println("Введите имя вашей карты (оставьте пустым для значения по умолчанию):");
            String cardName = sc.nextLine();
            if (cardName.isBlank()) cardName = "Карта " + (cards.size() + 1);

            String cardType;

            System.out.println("Введите тип карты " + Arrays.toString(cardTypes) + " (оставьте пустым для значения по умолчанию):");
            while (true) {
                cardType = sc.nextLine().toUpperCase();
                if (cardType.isBlank()) cardType = UserEnum.UZCARD.name();

                try {
                    UserEnum.valueOf(cardType);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Некорректный приоритет. Пожалуйста, введите один из " + Arrays.toString(cardTypes) + ":");
                }
            }

            StringBuilder cardNumber = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                cardNumber.append(rand.nextInt(10));
            }

            int cardBalance = rand.nextInt(1001);

            return new String[]{cardName, String.valueOf(cardNumber), cardType, String.valueOf(cardBalance), UUID.randomUUID().toString()};
        }

        private boolean removeCardById(String id) {
            return cards.removeIf(card -> Objects.equals(card[4], id));
        }
    };
}
