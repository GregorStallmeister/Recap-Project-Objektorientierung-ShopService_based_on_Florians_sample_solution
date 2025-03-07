import java.time.Instant;
import lombok.With;

import java.util.List;

@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus orderStatus,
        Instant orderDate
) {
}
