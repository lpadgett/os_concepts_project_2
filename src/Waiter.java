import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    public Semaphore currentlyServing;
    private Restaurant restaurant; //keeps track of restaurant instance
    private Table table; //Keeps track of table being served
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

    public void setTable(Table table) {
        if(this.table == null) {
            this.table = table; //If the waiter hasn't been assigned to a table yet, assign to table.
        }
    }

    @Override
    public void run(){
        String lastOrder = null;
        while(true) {
            if(this.order != lastOrder) { //Keep track of when a new customer is being served by the changing id
                lastOrder = this.order;
            }
        }
    }
}
