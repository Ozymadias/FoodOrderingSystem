package order;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class Lemon implements OrderPosition {
    private final String name = "Lemon";
    private final BigDecimal price = ZERO;

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

        Lemon lemon = (Lemon) o;

        if (!name.equals(lemon.name)) return false;
        return price.equals(lemon.price);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
