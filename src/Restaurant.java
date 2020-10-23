import java.util.EnumMap;
import java.util.concurrent.Semaphore;

public class Restaurant {
    private EnumMap<FoodType, Table> tables;
    private static Waiter[] waiters;

    //Public is shared data
    public Semaphore doorsSemaphore;
    public Semaphore kitchenSemaphore;

    public Restaurant(int numOfWaiters) {
        doorsSemaphore = new Semaphore(2); //2 Customers can enter at once
        kitchenSemaphore = new Semaphore(1); //Only one waiter can enter at once

        tables = new EnumMap<FoodType, Table>(FoodType.class);
        tables.put(FoodType.PASTA, new Table());
        tables.put(FoodType.SEAFOOD, new Table());
        tables.put(FoodType.STEAK, new Table());

        for (int i = 0; i < numOfWaiters; i++) { //Start waiters
            waiters[i] = new Waiter(new String("Waiter " + i), this);
            waiters[i].start();
        }

        //Assign waiters to tables
        tables.get(FoodType.STEAK).waiter = waiters[0];
        tables.get(FoodType.SEAFOOD).waiter = waiters[1];
        tables.get(FoodType.PASTA).waiter = waiters[2];
    }

    public Table tableChoice(FoodType choice) {
        return tables.get(choice);
    }
}
