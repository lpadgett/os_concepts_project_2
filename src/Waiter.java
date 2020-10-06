import java.util.UUID;

public class Waiter extends Person {
    private UUID uuid;

    public Waiter(){
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void run() {

    }
}
