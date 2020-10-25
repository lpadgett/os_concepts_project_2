import java.util.EnumMap;
import java.util.concurrent.Semaphore;

public class Restaurant {
    private EnumMap<FoodType, Table> tables;
    private static Waiter[] waiters;
    private int numOfCustomersInRestaurant;

    //Public is shared data
    public Semaphore doorsSemaphore;
    public Semaphore kitchenSemaphore;

    public Restaurant(int numOfWaiters) {
        this.doorsSemaphore = new Semaphore(2); //2 Customers can enter at once
        this.kitchenSemaphore = new Semaphore(1); //Only one waiter can enter at once

        this.tables = new EnumMap<FoodType, Table>(FoodType.class);
        this.tables.put(FoodType.PASTA, new Table(FoodType.PASTA));
        this.tables.put(FoodType.SEAFOOD, new Table(FoodType.SEAFOOD));
        this.tables.put(FoodType.STEAK, new Table(FoodType.STEAK));

        for (int i = 0; i < numOfWaiters; i++) { //Start waiters
            this.waiters[i] = new Waiter(new String("Waiter " + i), this);
            this.waiters[i].start();
        }

        //Assign waiters to tables
        this.tables.get(FoodType.STEAK).waiter = waiters[0];
        this.tables.get(FoodType.SEAFOOD).waiter = waiters[1];
        this.tables.get(FoodType.PASTA).waiter = waiters[2];

        this.numOfCustomersInRestaurant = 0;
    }

    public Table tableChoice(FoodType choice) {
        return tables.get(choice);
    }

    public int getNumOfCustomersInRestaurant(){
        return this.numOfCustomersInRestaurant;
    }

    public void removeCustomerFromRestaurant(){
        this.numOfCustomersInRestaurant--;
    }
}
