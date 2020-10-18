import java.util.Random;

public class Customer extends Thread {
    private String id;
    private FoodType firstChoice;
    private FoodType secondChoice;
    private Restaurant restaurant; //Keeps track of restaurant instance
    private Table table;


    public Customer(String id, Restaurant restaurant){
        this.id = id;
        this.restaurant = restaurant;

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
        enterRestaurantThroughDoor(this.restaurant);
        sitAtTable();
        callWaiter(this.table.waiter);
        eatFood();
        leaveTable();
        payBill();
        exit();
    }

    private void enterRestaurantThroughDoor(Restaurant restaurant){
    }

    private void sitAtTable(){
        Table firstChoiceTable = this.restaurant.tableChoice(this.firstChoice);

        if(firstChoiceTable.tableOrLine(this, false)){
            this.table = firstChoiceTable;
            return; //If customer got first choice, stop
        } else {
            Table secondChoiceTable = Client.restaurant.tableChoice(this.secondChoice);
            if(secondChoiceTable.tableOrLine(this, false)){
                this.table = secondChoiceTable;
                return; //If customer got second choice, stop
            }
        }

        //If both the first and second choice are crowded then give up and get in line at first choice
        firstChoiceTable.tableOrLine(this, true);
        this.table = firstChoiceTable;
    }

    private void callWaiter(Waiter waiter){

    }

    private void eatFood(){}

    private void leaveTable(){}

    private void payBill(){

    }

    private void exit(){}

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
