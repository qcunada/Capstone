import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LedgerApp {
        static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("WELCOME TO THE BANK APP");
        homeScreen();



    }public static void homeScreen() {
        boolean run = true;
        while (run) {


            System.out.println("What do you want to do today?");
            System.out.println("(1) Add Deposit");
            System.out.println("(2) Make Payment");
            System.out.println("(3) Open Ledger Screen");
            System.out.println("(4) Exit");


            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    showLedger();
                    break;
                case 4:
                    System.out.println("You chose to exit the app.");
                    run = false;
                    break;




            }
        }
    }
    public static void addDeposit(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = currentDateTime.format(dtf);

        try{
            System.out.println("Please input the amount of money to deposit");
            double deposit = Double.parseDouble(scanner.nextLine());

            System.out.println("Please input the payer for this deposit");
            String payer = scanner.nextLine();

            System.out.println("Add a description for this deposit");
            String description = scanner.nextLine();

            System.out.println("You have deposited $" + deposit + " From " + payer + " as a" + description + " on this date [ " + formattedDate + " ]");

            FileWriter fw = new FileWriter("transactions.csv",true);
            fw.write(deposit + "," + payer + "," + description + "," + formattedDate + "\n");
            fw.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }
    public static void makePayment(){
        String var = "hi";
    }
    public static void showLedger(){

    }


}


