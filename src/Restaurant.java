public class Restaurant {
    private Waiter[] waiters;
    private Customer[] customers;

    public Restaurant(int numOfWaiters, int numOfCustomers) {
        this.waiters = new Waiter[numOfWaiters];
        this.customers = new Customer[numOfCustomers];
    }

    public void createAndLaunchThreads() {
        for (int i = 0; i < waiters.length; i++) {
            waiters[i] = new Waiter(new String("Waiter " + i));
            waiters[i].start();
        }

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(new String("Customer " + i));
        }
    }
}
