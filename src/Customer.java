import java.util.UUID;

public class Customer {
    private UUID uuid;

    public Customer(){
        this.uuid = UUID.randomUUID(); //Give customer a unique identifier.
    }

    public UUID getUuid(){
        return this.uuid;
    }
}
