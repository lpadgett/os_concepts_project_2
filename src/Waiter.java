import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    public Semaphore lock;
    private Restaurant restaurant; //keeps track of restaurant instance
    private Table table; //Keeps track of table being served
    private String id;
    private String order;

    public Waiter(String id, Restaurant restaurant){
        this.lock = new Semaphore(0);
        this.restaurant = restaurant;
        this.id = id;
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

        while(true){

        }
    }
}
