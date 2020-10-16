import java.util.Random;

public class Customer extends Thread {
    private String id;
    private FoodType firstChoice;
    private FoodType secondChoice;

    public Customer(String id){
        this.id = id;

        //Generate foodChoices
        int random = new Random().nextInt(100);
        if(random <= 33) {
            this.firstChoice = FoodType.PASTA;
            this.secondChoice = FoodType.SEAFOOD;
        } else if(random > 33 && random <= 66) {
            this.firstChoice = FoodType.SEAFOOD;
            this.secondChoice = FoodType.STEAK;
        } else {
            this.firstChoice = FoodType.STEAK;
            this.secondChoice = FoodType.PASTA;
        }
    }

    public String getCustomerId(){
        return this.id;
    }

    public FoodType getFirstChoice(){
        return this.firstChoice;
    }

    public FoodType getSecondChoice(){
        return this.secondChoice;
    }

    @Override
    public void run(){

    }
}
