import order.MainCourse;
import order.OrderPosition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Cuisine {
    private final String name;
    private List<MainCourse> mainCourses;

    public List<MainCourse> getMainCourses() {
        return mainCourses;
    }

    public Cuisine(File file) {
        String[] splitFileName = file.getName().split("\\.");
        this.name = splitFileName[0];
        getCourses(file);
    }

    private void getCourses(File file) {
        try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
            this.mainCourses = stream.map(MainCourse::new)
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void printMainCourses() {
        for (int i = 0; i < mainCourses.size(); i++)
            System.out.println((i+1) + " - " + mainCourses.get(i).getName());
    }

    public OrderPosition getCourse(int id) {
        return mainCourses.get(id);
    }

}
