import java.util.Random;
import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    public Semaphore currentlyServing;
    private Restaurant restaurant; //keeps track of restaurant instance
    private Table table; //Keeps track of table instance
    private String id;
    private String order;

    public Waiter(String id, Restaurant restaurant){
        this.currentlyServing = new Semaphore(1); //Initialize to 0 since a waiter can serve only one customer at a time
        this.restaurant = restaurant;
        this.id = id;
        this.order = null;
    }

    public String getWaiterId(){
        return this.id;
    }

    @Override
    public void run(){
        try { //Wait 0.5 seconds for customer threads to queue
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(this.currentlyServing.hasQueuedThreads() || this.restaurant.getNumOfCustomersServed() < 40) {
            if(this.restaurant.allCustomersServed() == true){
                break;
            }
        }

        exit();
    }

    public void takeOrder(String customerId) {
        this.order = customerId;
        System.out.println(this.id + " took " + this.order + "'s order.");
        serve(this.order);

    }

    private void serve(String customerId){
        Customer customerServing = this.table.getSeatedCustomer(this.order);

        customerServing.waiterIsReady();

        try {
            goToKitchenFromTable();
        } catch (InterruptedException e) {
            System.out.println(this.id + " forgot he had a customer to serve and got stuck in the kitchen.");
            e.printStackTrace();
        }

        try {
            waitOutsideKitchen();
        } catch (InterruptedException e) {
            System.out.println(this.id + " forgot he was waiting for an order to be finished in the kitchen.");
            e.printStackTrace();
        }

        try {
            getOrderFromInsidekitchen();
        } catch (InterruptedException e) {
            System.out.println(this.id + " forgot to get the order when he went to the kitchen again.");
            e.printStackTrace();
        }

        bringCustomerOrder(customerServing);
    }

    public void chooseTable(Table table){
        if(this.table == null) {
            this.table = table;
        }
    }

    private void goToKitchenFromTable() throws InterruptedException {
        System.out.println(this.id + " is going to the kitchen from the table for " + this.order + "'s order.");
        long timeToSpendInKitchen = new Random().nextInt(400) + 100; //Spends anywhere from 100 to 500 ms in kitchen
        Thread.sleep(timeToSpendInKitchen);
    }

    private void getOrderFromInsidekitchen() throws InterruptedException {
        System.out.println(this.id + " is going back inside the kitchen to get " + this.order + "'s order (it is done).");
        long timeToSpendInKitchen = new Random().nextInt(400) + 100; //Spends anywhere from 100 to 500 ms in kitchen
        Thread.sleep(timeToSpendInKitchen);
    }

    private void waitOutsideKitchen() throws InterruptedException {
        System.out.println(this.id + " is waiting outside the kitchen for " + this.order + "'s order.");
        long timeToSpendOutsideKitchen = new Random().nextInt(700) + 300; //Spends anywhere from 300 to 1000 ms in kitchen
        Thread.sleep(timeToSpendOutsideKitchen);
    }

    private void bringCustomerOrder(Customer customer){
        System.out.println(this.id + " is bringing " + this.order + " their order from the kitchen.");
        customer.receiveFoodFromWaiter();
    }

    private void exit() {
        System.out.println(this.id + " has cleaned up the table and left the restaurant.");
    }
}
