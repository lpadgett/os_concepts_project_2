//Wrapper class for waiter/customer so they can be stored in thread-safe hashtable.
public abstract class Person implements Runnable {
    private String id; //Could have chosen UUID but I did not for the sake of readability and keeping the design simple

    public String getId(){
        return this.id;
    }

    protected void setId(String id) { //Only let id be set once
        if(this.id == null){
            this.id = id;
        }
    }
}
