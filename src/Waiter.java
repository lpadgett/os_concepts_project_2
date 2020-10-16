public class Waiter extends Thread {
    private String id;
    private String order;

    public Waiter(String id){
        this.id = id;
    }

    public String getWaiterId(){
        return this.id;
    }

    @Override
    public void run(){

    }
}
