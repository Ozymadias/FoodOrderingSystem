import order.OrderPosition;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

public class Order {
    private Map<OrderPosition, Integer> order = new LinkedHashMap<>();

    public void add(OrderPosition clientChoice) {
        Integer numberOfOccurrence = order.getOrDefault(clientChoice, 0);
        order.put(clientChoice, numberOfOccurrence + 1);
    }

    public BigDecimal charge() {
        BigDecimal sum = ZERO;
        for (Map.Entry<OrderPosition, Integer> entry : order.entrySet()) {
            OrderPosition orderPosition = entry.getKey();
            Integer amount = entry.getValue();
            sum = sum.add(orderPosition.getPrice().multiply(new BigDecimal(amount)));
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<OrderPosition, Integer> entry : order.entrySet()) {
            OrderPosition orderPosition = entry.getKey();
            Integer amount = entry.getValue();
            stringBuilder
                    .append(amount)
                    .append("x ")
                    .append(orderPosition.getName())
                    .append(": ").append(orderPosition.getPrice().multiply(new BigDecimal(amount)))
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
