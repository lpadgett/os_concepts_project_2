import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Client {

    //Create ConcurrentHashMap instead of normal hashmap for extra thread safety.
    public static ConcurrentHashMap<String, Person> threadIdLookup;

    public static void main(String[] args){
        initialize();
    }

    public static void initialize(){
        threadIdLookup = new ConcurrentHashMap<>();
    }
}
