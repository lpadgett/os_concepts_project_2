import java.util.Random;

public class Customer extends Thread {
    private String id;
    private int doorNum;
    private FoodType firstChoice;
    private FoodType secondChoice;


    public Customer(String id){
        this.id = id;
        this.doorNum = new Random().nextInt(2);

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

    @Override
    public void run(){

    }

    private void enterRestaurantThroughDoor(){

    }

    private void sitAtTable(){
        Table firstChoiceTable = Client.restaurant.tableChoice(this.firstChoice);

        if(firstChoiceTable.tableOrLine(this, false)){
            return; //If customer got first choice, stop
        } else {
            Table secondChoiceTable = Client.restaurant.tableChoice(this.secondChoice);
            if(secondChoiceTable.tableOrLine(this, false)){
                return; //If customer got second choice, stop
            }
        }

        //If both the first and second choice are crowded then give up and get in line at first choice
        firstChoiceTable.tableOrLine(this, true);
    }

    private void callWaiter(){

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
}
