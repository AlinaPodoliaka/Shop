package internetshop;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.lib.Injector;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;
import internetshop.service.BucketService;
import internetshop.service.ItemService;
import internetshop.service.OrderService;
import internetshop.service.UserService;

import java.util.List;

public class Main {

    @Inject
    private static UserService userService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws DataProcessingException {

        User userTest = userService.create(new User("UserTest"));

        Bucket bucketUserTest = new Bucket(userTest.getId());
        bucketService.create(bucketUserTest);

        Item item1 = itemService.create(new Item("item1", 1.0));
        Item item2 = itemService.create(new Item("item2", 2.0));

        bucketService.addItem(bucketUserTest.getId(), item1.getId());
        bucketService.addItem(bucketUserTest.getId(), item2.getId());

        orderService.completeOrder(bucketUserTest.getItems(), userTest);
        bucketService.clear(bucketUserTest);

        List<Order> ordersForUserTest = orderService.getUserOrders(userTest);
        System.out.println(ordersForUserTest);
    }
}
