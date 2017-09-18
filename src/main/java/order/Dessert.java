package order;

import java.math.BigDecimal;

public class Dessert implements OrderPosition {
    private final String name;
    private final BigDecimal price;

    public Dessert(String s) {
        String[] temp = s.split(",");
        this.name = temp[0];
        this.price = new BigDecimal(temp[1]);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dessert dessert = (Dessert) o;

        if (!name.equals(dessert.name)) return false;
        return price.equals(dessert.price);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
