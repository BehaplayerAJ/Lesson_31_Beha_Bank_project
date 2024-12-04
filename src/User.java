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

        public String[] createCard() {
            System.out.println("Введите имя ващей карты (оставьте пустым для значения по умолчанию):");
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

            return new String[]{cardName, cardNumber.toString(), cardType, String.valueOf(cardBalance), UUID.randomUUID().toString()};
        }
    };
}
