import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LedgerApp {
        static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("** WELCOME TO THE BANK APP **");
        homeScreen();

    }


    public static void homeScreen() {
        boolean run = true;
        while (run) {


            System.out.println("What do you want to do today?");
            System.out.println("(1) Add Deposit");
            System.out.println("(2) Make Payment");
            System.out.println("(3) Open Ledger Screen");
            System.out.println("(4) Exit");


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
                    System.out.println("Invalid option. Please choose 1-4 options.");

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

        System.out.println("* You opened the ledger. *");
        System.out.println("(a) Show all entries.");
        System.out.println("(b) Display only deposits.");
        System.out.println("(c) Display only payments.");
        System.out.println("(x) Reports.");
        System.out.println("choose an option:");

        String choiceLedger = scanner.nextLine().toLowerCase();
            switch (choiceLedger){
                case "a":
                    showAllEntries();
                    break;
                case "b":
                    showDeposits();
                    break;
                case "c":
                    showPayments();
                    break;
                case "x":
                    reports();

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
        try(BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;
            System.out.println("Here are all your deposited transactions.");
            while ((line = reader.readLine()) != null) {

                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    String type = entries[1].trim();

                    if (type.equalsIgnoreCase("DEPOSIT")) {
                        System.out.println(line);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void showPayments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            System.out.println("Here are all your payment transactions.");
            while ((line = reader.readLine()) != null) {

                String[] entries = line.split("\\|");
                if (entries.length >= 5) {
                    String type = entries[1].trim();

                    if (type.equalsIgnoreCase("PAYMENT")) {
                        System.out.println(line);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    public static void reports () {
        boolean reportsMenu = true;

        while (reportsMenu) {
            System.out.println("* You opened the reports menu. *");
            System.out.println("(1) Month To Date");
            System.out.println("(2) Previous Month");
            System.out.println("(3) Year To Date");
            System.out.println("(4) Previous Year");
            System.out.println("(5) Search By Vendor/Payer");
            System.out.println("(0) Exit");
            System.out.println("Choose an option: ");

            int choiceReports = Integer.parseInt(scanner.nextLine());
            switch (choiceReports) {
                case 1:
                    showMonthToDate();
                    break;
                case 2:
                    showPreviousMonth();
                    break;
                case 3:
                    showYearToDate();
                    break;
                case 4:
                    showPreviousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    reportsMenu = false;
                    break;
            }

        }

    }
    public static void showMonthToDate(){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Month to Date Transactions: ");
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);

                    if(entryDate.getYear() == year && entryDate.getMonthValue() == month){
                        System.out.println(line);
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void showPreviousMonth(){
        LocalDate previousMonthDate = LocalDate.now().minusMonths(1);
        int year = previousMonthDate.getYear();
        int month = previousMonthDate.getMonthValue();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Previous Month Transactions: ");
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);

                    if(entryDate.getYear() == year && entryDate.getMonthValue() == month){
                        System.out.println(line);
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void showYearToDate(){
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = LocalDate.of(today.getYear(), 1,1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Year To Date Transactions: ");
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);

                    if(entryDate.isEqual(startOfYear) || entryDate.isAfter(startOfYear) && entryDate.isBefore(today) || entryDate.isEqual(today)){
                        System.out.println(line);
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showPreviousYear(){
        LocalDate today = LocalDate.now();
        int previousYear = today.getYear() -1;

        boolean found = false;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Previous Year Transactions: ");
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
                System.out.println("You have no transactions from the previous year.");
            }

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

        while ((line = reader.readLine()) != null) {

            String[] entries = line.split("\\|");
            if (entries.length >= 5) {
                String type = entries[3].trim();

                if (type.equalsIgnoreCase(vendor)) {
                    System.out.println("Here are your transactions with " + vendor + ": ");
                    System.out.println(line);
                    found = true;
                }
            }
        }
        if (!found){
            System.out.println("No transactions with " + vendor + " found.");
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());

    }


    }


}


