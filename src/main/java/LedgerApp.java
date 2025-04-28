import java.io.BufferedReader;
import java.io.FileReader;
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

            System.out.println("You have deposited $" + deposit + " From " + payer + " as " + description + " on this date [ " + formattedDate + " ]");

            FileWriter fw = new FileWriter("transactions.csv",true);
            fw.write(formattedDate + " | DEPOSIT | " + description + " | " + payer + " | " + deposit + "\n");
            fw.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }




    }
    public static void makePayment(){

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = currentDateTime.format(dtf);

        try{
            System.out.println("Please input the amount of money you paid");
            double deposit = Double.parseDouble(scanner.nextLine());

            System.out.println("Please input the vendor for this payment");
            String payer = scanner.nextLine();

            System.out.println("Add a description for this payment");
            String description = scanner.nextLine();

            System.out.println("You paid $" + deposit  + " for this [" + description + "] to " + payer + " on this date [ " + formattedDate + " ]");

            FileWriter fw = new FileWriter("transactions.csv",true);
            fw.write(formattedDate + " | PAYMENT | " + description + " | " + payer + " | " + (deposit * -1) + "\n");

            fw.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



    public static void showLedger(){

        System.out.println("You opened the ledger.");
        System.out.println("choose an option:");
        System.out.println("(a) Show all entries.");
        System.out.println("(b) Display only deposits.");
        System.out.println("(c) Display only payments.");
        System.out.println("(x) Reports.");

        String choiceLedger = scanner.nextLine();
            switch (choiceLedger){
                case "a":
                    showAllEntries();
                    break;
                case "b":
                    showDeposits();


            }

    }

    public static void showAllEntries(){
        try(BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            System.out.println("Here are all your transactions.");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void showDeposits(){

    }

}


