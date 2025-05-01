import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class LedgerApp {
        static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println();
        System.out.println("** WELCOME TO THE BANK APP **");
        homeScreen();

    }


    public static void homeScreen() {
        boolean run = true;
        while (run) {

            System.out.println();
            System.out.println("opening the main menu...");
            System.out.println();
            System.out.println("   * * * MAIN MENU * * *");
            System.out.println("What do you want to do today?");
            System.out.println(" (1) Add Deposit");
            System.out.println(" (2) Make Payment");
            System.out.println(" (3) Open Ledger Screen");
            System.out.println(" (4) Exit");
            System.out.println("Choose an option: ");


            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addDeposit();
                    break;
                case "2":
                    makePayment();
                    break;
                case "3":
                    showLedger();
                    break;
                case "4":
                    System.out.println("You chose to exit the app.");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1-4 options.");

            }
        }
    }


    public static void addDeposit(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        String formattedDate = currentDateTime.format(dtf);

        double deposit = 0.0;
        while (true) {
            System.out.println("  * * ADD DEPOSIT * *");
            System.out.println("Please input the amount of money to deposit:");
            String depositInput = scanner.nextLine().trim();

            try {
                deposit = Double.parseDouble(depositInput);
                if (deposit <= 0) {
                    System.out.println("Invalid input. Number must be positive.");
                    System.out.println();
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.println();
            }
        }
            System.out.println("Please input the payer for this deposit:");
            String payer = scanner.nextLine();

            System.out.println("Add a description for this deposit:");
            String description = scanner.nextLine();

            System.out.println("You have deposited $" + String.format("%.2f",deposit) + " From " + payer + " as " + description + " on this date [ " + formattedDate + " ]");

        try{
            FileWriter fw = new FileWriter("transactions.csv",true);
            fw.write(formattedDate + " | DEPOSIT | " + description.toUpperCase() + " | " + payer.toUpperCase() + " | " + String.format("%.2f",deposit) + "\n");
            fw.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }


    public static void makePayment(){

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        String formattedDate = currentDateTime.format(dtf);

        double payment = 0.0;

        while(true) {
            System.out.println("  * * MAKE PAYMENT * *");
            System.out.println("Please input the amount of money you paid:");
            String paymentInput = scanner.nextLine().trim();

            try{
                payment = Double.parseDouble(paymentInput);
                if (payment <= 0) {
                    System.out.println("Invalid input. Number must be positive.");
                    System.out.println();
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.println();
            }
        }

            System.out.println("Please input the vendor for this payment:");
            String payer = scanner.nextLine();

            System.out.println("Add a description for this payment:");
            String description = scanner.nextLine();

            System.out.println();
            System.out.println("You paid $" + String.format("%.2f", payment)  + " for this [" + description + "] to " + payer + " on this date [ " + formattedDate + " ]");

            System.out.println();
            System.out.println("Press enter to go back to the main menu.");
            scanner.nextLine();

        try{
            FileWriter fw = new FileWriter("transactions.csv",true);
            fw.write(formattedDate + " | PAYMENT | " + description.toUpperCase() + " | " + payer.toUpperCase() + " | " + String.format("%.2f",(payment * -1)) + "\n");

            fw.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    public static void showLedger(){

        boolean runLedger = true;
        while (runLedger) {

            System.out.println();
            System.out.println("Opening the ledger screen...");
            System.out.println();
            System.out.println("* * * LEDGER SCREEN * * *");
            System.out.println(" (a) Show all entries.");
            System.out.println(" (b) Display only deposits.");
            System.out.println(" (c) Display only payments.");
            System.out.println(" (d) Reports.");
            System.out.println(" (e) Exit.");
            System.out.println("choose an option:");

            String choiceLedger = scanner.nextLine().toLowerCase();
            switch (choiceLedger) {
                case "a":
                    showAllEntries();
                    break;
                case "b":
                    showDeposits();
                    break;
                case "c":
                    showPayments();
                    break;
                case "d":
                    reports();
                    break;
                case "e":
                    runLedger = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose between the options on the screen.");
                    System.out.println();


            }

        }

    }

    public static void showAllEntries(){
        try(BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            System.out.println();
            System.out.println("Here are all your transactions: ");
            System.out.println();

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
            System.out.println("Press enter to go back to the ledger screen.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void showDeposits(){
        try(BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;
            System.out.println("Here are all your deposited transactions: ");
            System.out.println();
            while ((line = reader.readLine()) != null) {

                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    String type = entries[1].trim();

                    if (type.equalsIgnoreCase("DEPOSIT")) {
                        System.out.println(line);
                    }
                }
            }
            System.out.println();
            System.out.println("Press enter to go back to the ledger screen.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void showPayments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            System.out.println("Here are all your payment transactions:");
            System.out.println();
            while ((line = reader.readLine()) != null) {

                String[] entries = line.split("\\|");
                if (entries.length >= 5) {
                    String type = entries[1].trim();

                    if (type.equalsIgnoreCase("PAYMENT")) {
                        System.out.println(line);
                    }
                }
            }
            System.out.println();
            System.out.println("Press enter to go back to the ledger screen.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    public static void reports () {
        boolean reportsMenu = true;

        while (reportsMenu) {
            System.out.println();
            System.out.println("Opening the reports menu... ");
            System.out.println();
            System.out.println("* * * REPORTS MENU * * *");
            System.out.println(" (1) Month To Date");
            System.out.println(" (2) Previous Month");
            System.out.println(" (3) Year To Date");
            System.out.println(" (4) Previous Year");
            System.out.println(" (5) Search By Vendor/Payer");
            System.out.println(" (0) Exit");
            System.out.println("Choose an option: ");

            String choiceReports = scanner.nextLine().trim();
            switch (choiceReports) {
                case "1":
                    showMonthToDate();
                    break;
                case "2":
                    showPreviousMonth();
                    break;
                case "3":
                    showYearToDate();
                    break;
                case "4":
                    showPreviousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    reportsMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose between the options on the screen. ");
            }

        }

    }
    public static void showMonthToDate(){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        String monthName = now.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Month to Date Transactions [" + monthName + "]: ");
            System.out.println();
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);

                    if(entryDate.getYear() == year && entryDate.getMonthValue() == month){
                        System.out.println(line);
                    }
                }

            }
            System.out.println();
            System.out.println("Press enter to go back to the reports menu.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void showPreviousMonth(){
        LocalDate previousMonthDate = LocalDate.now().minusMonths(1);
        int thisYear = previousMonthDate.getYear();
        int lastMonth = previousMonthDate.getMonthValue();
        String lastMonthName = LocalDate.now().minusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        boolean found = false;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Previous Month Transactions [" + lastMonthName + "]: ");
            System.out.println();
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);

                    if(entryDate.getYear() == thisYear && entryDate.getMonthValue() == lastMonth){
                        System.out.println(line);
                        found = true;
                    }
                }

            }
            if (!found) {
                System.out.println("* You have no transactions from the previous month. *");
            }
            System.out.println();
            System.out.println("Press enter to go back to the reports menu.");
            scanner.nextLine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void showYearToDate(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");

        LocalDate today = LocalDate.now();
        LocalDate startOfYear = LocalDate.of(today.getYear(), 1,1);




        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Year To Date Transactions [" + startOfYear.getYear() + "]:");
            System.out.println();
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);

                    if(entryDate.isEqual(startOfYear) || entryDate.isAfter(startOfYear) && entryDate.isBefore(today) || entryDate.isEqual(today)){
                        System.out.println(line);
                    }
                }

            }
            System.out.println();
            System.out.println("Press enter to go back to the reports menu. ");
            scanner.nextLine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showPreviousYear(){
        LocalDate today = LocalDate.now();
        int previousYear = today.getYear() -1;

        boolean found = false;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Previous Year Transactions [" + previousYear + "]: ");
            System.out.println();
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);

                    if(entryDate.getYear() == previousYear){
                        System.out.println(line);
                        found = true;
                    }
                }

            }
            if (!found){
                System.out.println("* You have no transactions from the previous year. *");
            }
            System.out.println();
            System.out.println("Press enter to go back to the reports menu.");
            scanner.nextLine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchByVendor(){
        System.out.println("Enter the vendor/payer name to search: ");
        String vendor = scanner.nextLine().trim().toUpperCase();

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
        String line;
            System.out.println();
            System.out.println("Here are your transactions with " + vendor + ": ");
            System.out.println();
            while ((line = reader.readLine()) != null) {

            String[] entries = line.split("\\|");
            if (entries.length >= 5) {
                String type = entries[3].trim();


                if (type.equalsIgnoreCase(vendor)) {
                    System.out.println(line);
                    found = true;
                }
            }
        }
            if (!found){
                System.out.println("No transactions with " + vendor + " found.");
            }

            System.out.println();
            System.out.println("Press enter to go back to the reports menu.");
            scanner.nextLine();

    } catch (Exception e) {
        System.out.println(e.getMessage());

    }

    }


}


