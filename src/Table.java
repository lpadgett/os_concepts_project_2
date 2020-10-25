import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Table {
    public Semaphore tableSemaphore;
    private ConcurrentHashMap<String, Customer> currentlySeated; //TODO: Determine if using concurrenthashmap over normal hashmap would cause synchronization issues
    public final FoodType serves;
    public Waiter waiter;

    public Table(FoodType foodToBeServed) {
        this.tableSemaphore = new Semaphore(4); //Only 4 seats per table
        this.serves = foodToBeServed;
        currentlySeated = new ConcurrentHashMap<String, Customer>();
    }

    public void sit(Customer customer) {
        this.currentlySeated.put(customer.getCustomerId(), customer);
    }

    public void leave(Customer customer) {
        this.currentlySeated.remove(customer.getCustomerId());
    }
}
