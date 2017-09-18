package order;

import java.math.BigDecimal;

public class Drink implements OrderPosition {
    private final String name;
    private final BigDecimal price;

    public Drink(String s) {
        String[] temp = s.split(",");
        this.name = temp[0];
        this.price = new BigDecimal(temp[1]);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drink drink = (Drink) o;

        if (!name.equals(drink.name)) return false;
        return price.equals(drink.price);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
