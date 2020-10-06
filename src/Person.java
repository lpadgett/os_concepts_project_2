import java.util.UUID;

//Wrapper class for waiter/customer so they can be stored in thread-safe hashtable.
public abstract class Person implements Runnable {
    private UUID uuid;

    public UUID getUuid(){
        return this.uuid;
    }
}
