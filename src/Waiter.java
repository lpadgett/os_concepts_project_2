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
        while(true) {
            if(this.order != lastOrder) { //Keep track of when a new customer is being served by the changing id
                lastOrder = this.order;

            }
        }
    }

    public void takeOrder(String customerId) {
        this.order = customerId;
    }

    private void interactWithCustomer(){
        //TODO: Do stuff for customer, remember the hashmap in the Table class which keeps track of currently seated customer instances
    }

    public void chooseTable(Table table){
        if(this.table == null) {
            this.table = table;
        }
    }
}
