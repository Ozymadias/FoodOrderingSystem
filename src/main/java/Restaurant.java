import order.*;

import java.util.*;

public class Restaurant {
    private static Order order = new Order();
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
        List<Cuisine> cuisines = menu.getCuisines();
        System.out.println("Please choose one of our cuisines:");
        listAllCuisines(cuisines);
        return getClientChoice(cuisines.size());
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
        Cuisine cuisine = menu.getCuisine(cuisineNumber);
        List<MainCourse> mainCourses = cuisine.getMainCourses();
        System.out.println("Please choose one of our main courses:");
        cuisine.printMainCourses();
        int clientChoice = getClientChoice(mainCourses.size());
        order.add(cuisine.getCourse(clientChoice - 1));
    }

    private static void getDessert() {
        int clientChoice;
        List<Dessert> desserts = menu.getDesserts();
        System.out.println("Please choose one of our desserts:");
        listAll(desserts);
        clientChoice = getClientChoice(desserts.size());
        order.add(menu.getDessert(clientChoice - 1));
    }

    private static void handleDrinkOrder() {
        getDrink();
        handleIce();
        handleLemon();
    }

    private static void getDrink() {
        int clientChoice = -1;
        List<Drink> drinks = menu.getDrinks();
        System.out.println("Please choose one of our drinks:");
        listAll(drinks);
        clientChoice = getClientChoice(drinks.size());
        order.add(menu.getDrink(clientChoice - 1));
    }

    private static int getClientChoice(int listSize) {
        int clientChoice = getNextLine();
        while (clientChoice > listSize || clientChoice <= 0) {
            System.out.println("There is no such position in our menu. Please choose again.");
            clientChoice = getNextLine();
        }
        return clientChoice;
    }

    private static int getNextLine() {
        if (scanner.hasNextInt())
            return scanner.nextInt();
        else
            scanner.next();
        return -1;
    }

    private static void handleIce() {
        System.out.println("Would you like to have some ice in your drink?");
        if (clientDecidesYes())
            order.add(new Ice());
    }

    private static void handleLemon() {
        System.out.println("Would you like to have some lemon in your drink?");
        if (clientDecidesYes())
            order.add(new Lemon());
    }

    private static boolean clientDecidesYes() {
        String clientChoice = "";
        while (!"Y".equals(clientChoice) && !"N".equals(clientChoice)) {
            System.out.println("Y - if yes; N - if no");
            clientChoice = scanner.next();
        }
        return "Y".equals(clientChoice);
    }

    private static void provideBill() {
        System.out.println("You ordered:");
        System.out.println(order);
        System.out.println("Which total cost is: " + order.charge());
    }
}
