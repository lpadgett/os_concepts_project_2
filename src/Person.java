//Wrapper class for waiter/customer so they can be stored in thread-safe hashtable.
public abstract class Person extends Thread {
    private String id; //Could have chosen UUID but I did not for the sake of readability and keeping the design simple

    public String getPersonId(){
        return this.id;
    }

    protected void setPersonId(String id) { //Only let id be set once
        if(this.id == null){
            this.id = id;
        }
    }
}
