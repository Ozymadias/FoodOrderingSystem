import order.*;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ZERO;

public class Restaurant {
    private static LinkedHashMap<OrderPosition, Integer> order = new LinkedHashMap<>();
    private static Menu menu = new Menu();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printGreetings();
        mainLoop();
        provideBill();
    }

    private static void printGreetings() {
        System.out.println("Welcome to our restaurant!");
        System.out.println("You can order lunches - each consisting of main course and dessert of your choice. You can also order drinks.");
        System.out.println("Here is our menu:");
        printMenu();
    }

    private static void printMenu() {
        for (Cuisine cuisine : menu.getCuisines()) {
            System.out.println(cuisine.getName() + " cuisine:");
            listAll(cuisine.getMainCourses());
        }
        System.out.println("desserts:");
        listAll(menu.getDesserts());
        System.out.println("drinks:");
        listAll(menu.getDrinks());
        System.out.println("additionally:");
        System.out.println("Ice");
        System.out.println("Lemon");
    }

    private static void mainLoop() {
        String input = "";
        while (isTheClientStillOrdering(input)) {
            presentOrderingOptions();
            input = scanner.next();
            if ("L".equals(input))
                handleLunchOrder();
            if ("D".equals(input))
                handleDrinkOrder();
        }
    }

    private static boolean isTheClientStillOrdering(String input) {
        return !"B".equals(input);
    }

    private static void presentOrderingOptions() {
        System.out.println("Please choose one of options:");
        System.out.println("L - to order lunch; D - to order drinks; B - to receive a bill");
    }

    private static void handleLunchOrder() {
        int cuisine = getCuisine();
        getMainCourse(cuisine);
        getDessert();
    }

    private static int getCuisine() {
        int clientChoice;
        List<Drink> drinks = menu.getDrinks();
        do {
            System.out.println("Please choose one of our cuisines:");
            listAllCuisines(menu.getCuisines());
            clientChoice = drinks.size();
            if (scanner.hasNextInt())
                clientChoice = scanner.nextInt();
            else
                scanner.next();
        } while (clientChoice > drinks.size() - 1 || clientChoice <= 0);
        return clientChoice;
    }

    private static void listAllCuisines(List<Cuisine> list) {
        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + " - " + list.get(i).getName());
    }

    private static <T extends OrderPosition> void listAll(List<T> list) {
        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + " - " + list.get(i).getName());
    }

    private static void getMainCourse(int cuisineNumber) {
        int clientChoice;
        Cuisine cuisine = menu.getCuisine(cuisineNumber);
        List<MainCourse> mainCourses = cuisine.getMainCourses();
        do {
            System.out.println("Please choose one of our main courses:");
            cuisine.listMainCourses();
            clientChoice = mainCourses.size() + 1;
            if (scanner.hasNextInt())
                clientChoice = scanner.nextInt();
            else
                scanner.next();
        } while (clientChoice > mainCourses.size() || clientChoice <= 0);
        addToOrder(cuisine.getCourse(clientChoice-1));
    }

    private static void getDessert() {
        int clientChoice;
        List<Dessert> desserts = menu.getDesserts();
        do {
            System.out.println("Please choose one of our desserts:");
            listAll(menu.getDesserts());
            clientChoice = desserts.size() + 1;
            if (scanner.hasNextInt())
                clientChoice = scanner.nextInt();
            else
                scanner.next();
        } while (clientChoice > desserts.size() || clientChoice <= 0);
        addToOrder(menu.getDessert(clientChoice-1));
    }

    private static void handleDrinkOrder() {
        getDrink();
        handleIce();
        handleLemon();
    }

    private static void getDrink() {
        int clientChoice;
        List<Drink> drinks = menu.getDrinks();
        do {
            System.out.println("Please choose one of our drinks:");
            listAll(drinks);
            clientChoice = drinks.size() + 1;
            if (scanner.hasNextInt())
                clientChoice = scanner.nextInt();
            else
                scanner.next();
        } while (clientChoice > drinks.size()  || clientChoice <= 0);
        addToOrder(menu.getDrink(clientChoice-1));
    }

    private static void addToOrder(OrderPosition clientChoice) {
        Integer numberOfOccurrence = order.getOrDefault(clientChoice, 0);
        order.put(clientChoice, numberOfOccurrence + 1);
    }

    private static void handleIce() {
        String clientChoice;
        do {
            System.out.println("Would you like to have some ice in your drink?");
            System.out.println("Y - if yes; N - if no");
            clientChoice = scanner.next();
            if ("N".equals(clientChoice))
                return;
            if ("Y".equals(clientChoice))
                addToOrder(new Ice());
        } while (!"Y".equals(clientChoice) && !"N".equals(clientChoice));
    }

    private static void handleLemon() {
        String clientChoice;
        do {
            System.out.println("Would you like to have some lemon in your drink?");
            System.out.println("Y - if yes; N - if no");
            clientChoice = scanner.next();
            if ("N".equals(clientChoice))
                return;
            if ("Y".equals(clientChoice))
                addToOrder(new Lemon());
        } while (!"Y".equals(clientChoice) && !"N".equals(clientChoice));
    }

    private static void provideBill() {
        BigDecimal sum = ZERO;
        System.out.println("You ordered:");
        for (Map.Entry<OrderPosition, Integer> entry : order.entrySet()) {
            OrderPosition orderPosition = entry.getKey();
            Integer amount = entry.getValue();
            System.out.println(amount + "x " + orderPosition.getName() + ": " + (orderPosition.getPrice().multiply(new BigDecimal(amount))));
            sum = sum.add(orderPosition.getPrice().multiply(new BigDecimal(amount)));
        }
        System.out.println("Which total cost is: " + sum);
    }
}
