import order.Dessert;
import order.Drink;
import order.OrderPosition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Menu {
    private static final String RESOURCES_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    private List<Dessert> desserts;
    private List<Drink> drinks;
    private List<Cuisine> cuisines = new ArrayList<>();

    public List<Dessert> getDesserts() {
        return desserts;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public Menu() {
        loadCuisines();
        loadDesserts();
        loadDrinks();
    }

    private void loadCuisines() {
        File dir = new File(RESOURCES_PATH + "cuisines");
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile())
                this.cuisines.add(new Cuisine(file));
        }
    }

    private void loadDesserts() {
        String fileName = RESOURCES_PATH + "desserts.csv";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            this.desserts = stream.map(Dessert::new)
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDrinks() {
        String fileName = RESOURCES_PATH + "drinks.csv";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            this.drinks = stream.map(Drink::new)
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cuisine getCuisine(int cuisineNumber) {
        return cuisines.get(cuisineNumber-1);
    }

    public OrderPosition getDrink(int id) {
        return drinks.get(id);
    }

    public OrderPosition getDessert(int id) {
        return desserts.get(id);
    }
}
