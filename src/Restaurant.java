import java.util.EnumMap;
import java.util.concurrent.Semaphore;

public class Restaurant {
    private EnumMap<FoodType, Table> tables;
    private static Waiter[] waiters;

    //Public is shared data
    public Semaphore doorsSemaphore;
    public Semaphore kitchenIsOccupied;

    public Restaurant(int numOfWaiters) {
        tables = new EnumMap<FoodType, Table>(FoodType.class);

        tables.put(FoodType.PASTA, new Table());
        tables.put(FoodType.SEAFOOD, new Table());
        tables.put(FoodType.STEAK, new Table());

        for (int i = 0; i < numOfWaiters; i++) {
            waiters[i] = new Waiter(new String("Waiter " + i), this);
            waiters[i].start();
        }

        tables.get(FoodType.STEAK).waiter = waiters[0];
        tables.get(FoodType.SEAFOOD).waiter = waiters[1];
        tables.get(FoodType.PASTA).waiter = waiters[2];

        doorsSemaphore = new Semaphore(2); //2 Customers can enter at once
        kitchenIsOccupied = new Semaphore(1);
    }

    public Table tableChoice(FoodType choice) {
        return tables.get(choice);
    }

    public void enter(Customer customer) {

    }
}
