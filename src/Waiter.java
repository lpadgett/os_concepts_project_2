import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    public Semaphore lock;
    private String id;
    private String order;

    public Waiter(String id){
        this.lock = new Semaphore(0);
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
