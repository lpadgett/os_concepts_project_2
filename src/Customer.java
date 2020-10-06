import java.util.UUID;

public class Customer extends Person {
    private UUID uuid;

    public Customer(){
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void run(){

    }
}
