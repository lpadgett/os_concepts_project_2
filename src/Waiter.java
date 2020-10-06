import java.util.UUID;

public class Waiter extends Thread {
    private UUID uuid;

    public Waiter(){
        this.uuid = UUID.randomUUID(); //Give waiter a unique identifier.
    }

    public UUID getUuid(){
        return this.uuid;
    }
}
