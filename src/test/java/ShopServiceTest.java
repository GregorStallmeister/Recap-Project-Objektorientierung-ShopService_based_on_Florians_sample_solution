import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, Instant.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrdersByStatusExpectedOneWhenOneWithSpecifiedStatusAdded() {
        // given
        ShopService shopService = new ShopService();
        List<String> productIDs = List.of("1");
        shopService.addOrder(productIDs);

        // when
        List<Order> orders = shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        // then
        assertEquals("1", orders.getFirst().products().getFirst().id());
    }

    @Test
    void getOrdersByStatusExpectedEmptyListWhenOneWithOtherThanSpecifiedStatusAdded() {
        // given
        ShopService shopService = new ShopService();
        List<String> productIDs = List.of("1");
        shopService.addOrder(productIDs);

        // when
        List<Order> orders = shopService.getOrdersByStatus(OrderStatus.COMPLETED);

        // then
        assertTrue(orders.isEmpty());
    }
}
