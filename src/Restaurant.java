import java.util.EnumMap;

public class Restaurant {
    private EnumMap<FoodType, Table> tables;
    private Waiter[] waiters;
    private Customer[] customers;
    //TODO: Restaurant doors

    public Restaurant(int numOfWaiters, int numOfCustomers) {
        this.waiters = new Waiter[numOfWaiters];
        this.customers = new Customer[numOfCustomers];
        this.tables = new EnumMap<FoodType, Table>(FoodType.class);
        tables.put(FoodType.PASTA, new Table());
        tables.put(FoodType.SEAFOOD, new Table());
        tables.put(FoodType.STEAK, new Table());

        tables.get(FoodType.STEAK).waiter = waiters[0];
        tables.get(FoodType.SEAFOOD).waiter = waiters[1];
        tables.get(FoodType.PASTA).waiter = waiters[2];
    }

    public void createAndLaunchThreads() {
        for (int i = 0; i < waiters.length; i++) {
            waiters[i] = new Waiter(new String("Waiter " + i));
            waiters[i].start();
        }

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(new String("Customer " + i));
            customers[i].start();
        }
    }

    public Table tableChoice(FoodType choice) {
        return tables.get(choice);
    }
}
