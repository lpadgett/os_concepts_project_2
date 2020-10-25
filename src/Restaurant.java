import java.util.EnumMap;
import java.util.concurrent.Semaphore;

public class Restaurant {
    private EnumMap<FoodType, Table> tables;
    private Waiter[] waiters;
    private int numOfCustomersServed;
    private int numOfCustomersToServe;

    //Public is shared data
    public Semaphore doorsSemaphore;
    public Semaphore kitchenSemaphore;

    public Restaurant(int numOfWaiters, int numOfCustomersToServe) {
        this.doorsSemaphore = new Semaphore(2); //2 Customers can enter at once
        this.kitchenSemaphore = new Semaphore(1); //Only one waiter can enter at once
        this.waiters = new Waiter[3];
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
        waiters[0].chooseTable(this.tables.get(FoodType.STEAK));
        this.tables.get(FoodType.SEAFOOD).waiter = waiters[1];
        waiters[1].chooseTable(this.tables.get(FoodType.SEAFOOD));
        this.tables.get(FoodType.PASTA).waiter = waiters[2];
        waiters[2].chooseTable(this.tables.get(FoodType.PASTA));

        this.numOfCustomersServed = 0;
        this.numOfCustomersToServe = numOfCustomersToServe;
    }

    public Table tableChoice(FoodType choice) {
        return tables.get(choice);
    }

    public int getNumOfCustomersServed(){
        return this.numOfCustomersServed;
    }

    public void customerEntersRestaurant(){
        this.numOfCustomersServed++;
    }

    public synchronized void payBill(Customer customer) throws InterruptedException { //Only one customer can pay at a time, hence synchronized. Requirements did not specify what bill-paying entails, so customer just waits 15ms
        customer.sleep(15);
    }

    public boolean allCustomersServed() {
        return (this.numOfCustomersServed == this.numOfCustomersToServe);
    }

    public void customerHasBeenServed() {
        this.numOfCustomersServed++;
    }
}
