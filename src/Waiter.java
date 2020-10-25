import java.util.Random;
import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    public Semaphore currentlyServing;
    private Restaurant restaurant; //keeps track of restaurant instance
    private Table table; //Keeps track of table instance
    private String id;
    private String order;

    public Waiter(String id, Restaurant restaurant){
        this.currentlyServing = new Semaphore(1); //Initialize to 1 since a waiter can serve only one customer at a time
        this.restaurant = restaurant;
        this.id = id;
        this.order = null;
    }

    public String getWaiterId(){
        return this.id;
    }

    @Override
    public void run(){ //TODO: Rethink the whole order-taking mechanism here
        String lastOrder = null;
        while(!this.restaurant.haveCustomersEnteredRestaurant()){
            if(this.restaurant.haveCustomersEnteredRestaurant()){
                break;
            }
        }
        while(this.restaurant.getNumOfCustomersInRestaurant() > 0) { //TODO: Find a way to terminate the waiter when all of the customers have left the restaurant
            if(this.order != lastOrder) { //Keep track of when a new customer is being served by the changing id
                lastOrder = this.order;
                Customer customerServing = this.table.getSeatedCustomer(this.order);
                customerServing.waiterIsReady();

                try {
                    goToKitchen();
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
                    goToKitchen();
                } catch (InterruptedException e) {
                    System.out.println(this.id + " forgot to get the order when he went to the kitchen again.");
                    e.printStackTrace();
                }

                bringCustomerOrder(customerServing);
            }

            System.out.println(this.id + " has cleaned up the table and left the restaurant.");
        }
    }

    public void takeOrder(String customerId) {
        this.order = customerId;
    }

    public void chooseTable(Table table){
        if(this.table == null) {
            this.table = table;
        }
    }

    private void goToKitchen() throws InterruptedException {
        long timeToSpendInKitchen = new Random().nextInt(400) + 100; //Spends anywhere from 100 to 500 ms in kitchen
        wait(timeToSpendInKitchen);
    }

    private void waitOutsideKitchen() throws InterruptedException {
        long timeToSpendOutsideKitchen = new Random().nextInt(700) + 300; //Spends anywhere from 300 to 1000 ms in kitchen
        wait(timeToSpendOutsideKitchen);
    }

    private void bringCustomerOrder(Customer customer){
        customer.receiveFoodFromWaiter();
    }
}
