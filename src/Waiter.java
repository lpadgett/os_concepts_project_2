import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    public Semaphore lock;
    private Restaurant restaurant; //keeps track of instance
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

    @Override
    public void run(){
        while(true){

        }
    }
}
