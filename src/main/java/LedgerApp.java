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

    // This is where the main menu will be. This menu screen will keep running until user click exit. When users exit on other screen this menu will be the last menu to show up before closing the app.

    public static void homeScreen() {
        boolean run = true;  //this boolean flag will keep it running til it sees the flag = false on the exit button.
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
                    addDeposit(); //use to open add deposit screen
                    break;
                case "2":
                    makePayment(); //use to open make payment screen
                    break;
                case "3":
                    showLedger(); //shows the ledger menu
                    break;
                case "4":
                    System.out.println("You chose to exit the app.");
                    run = false;  //this is where it closes the app.
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1-4 options."); //this is here so when user type other than the buttons present on the screen. It will give an error message to try again.

            }
        }
    }
    // This is case: "1". the method where adding the deposit entries go. The user inputs the amount, payer and description to be saved on a csv file.
    public static void addDeposit(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        //used this to locate the current time when deposit is happening.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a"); //used dtf to show format of [05-01-2024 1:00 AM]
        String formattedDate = currentDateTime.format(dtf);

        double deposit = 0.0;
        while (true) {
            System.out.println("  * * ADD DEPOSIT * *");
            System.out.println("Please input the amount of money to deposit:");
            String depositInput = scanner.nextLine().trim();
            //this is where the user input the amount of money to deposit
            try {
                deposit = Double.parseDouble(depositInput);
                //converting the string input number to a double

                if (deposit <= 0) {
                    System.out.println("Invalid input. Number must be positive."); //number to be input should be a positive number.
                    System.out.println();
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number."); //if user puts letters or symbols this error will show up as input should be a number.
                System.out.println();
            }
        }
            System.out.println("Please input the payer for this deposit:");
            String payer = scanner.nextLine(); //user input the payer for the transaction. (ex. Employers, Bank, etc.)

            System.out.println("Add a description for this deposit:");
            String description = scanner.nextLine(); //user inputs the description of the transactions (ex. paycheck, refunds, etc.)

            System.out.println("You have deposited $" + String.format("%.2f",deposit) + " from " + payer + " as " + description + " on this date [ " + formattedDate + " ]");
            //"You have deposited $0.00 from PAYER as 'paycheck' on this date [07-07-2024 10:59 AM]"

            System.out.println();
            System.out.println("Press enter to go back to the main menu.");
            scanner.nextLine();

        try{
            FileWriter fw = new FileWriter("transactions.csv",true);
            fw.write(formattedDate + " | DEPOSIT | " + description.toUpperCase() + " | " + payer.toUpperCase() + " | " + String.format("%.2f",deposit) + "\n");
            fw.close();
            // File writes every entry as time | type | description | payer | amount on a csv file called "transactions.csv"

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    //This is case "2". This method is the one to make user input the entries for their Payment transactions same as Add Deposit that will be saved in the csv file.
    public static void makePayment(){

        LocalDateTime currentDateTime = LocalDateTime.now();
        //used this to locate the current time when payment is happening.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        String formattedDate = currentDateTime.format(dtf);

        double payment = 0.0;

        while(true) {
            System.out.println("  * * MAKE PAYMENT * *");
            System.out.println("Please input the amount of money you paid:");
            String paymentInput = scanner.nextLine().trim();
            //this is where they input the amount of money to pay for their transaction.

            try{
                payment = Double.parseDouble(paymentInput);
                if (payment <= 0) {
                    System.out.println("Invalid input. Number must be positive."); //user can't input negative number
                    System.out.println();
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number."); //user can't input letter or symbols
                System.out.println();
            }
        }
            System.out.println("Please input the vendor for this payment:");
            String payer = scanner.nextLine(); //user inputs the vendor for their payment. (ex. Cheesecake Factory, Zara, Costco)

            System.out.println("Add a description for this payment:");
            String description = scanner.nextLine(); //user input the description for their payment transaction. (ex. dinner, jacket, groceries)

            System.out.println();
            System.out.println("You paid $" + String.format("%.2f", payment)  + " for this [" + description + "] to " + payer + " on this date [ " + formattedDate + " ]");
            //"You paid $0.00 from VENDOR as 'item' on this date [07-07-2024 10:59 AM]"

            System.out.println();
            System.out.println("Press enter to go back to the main menu.");
            scanner.nextLine();

        try{
            FileWriter fw = new FileWriter("transactions.csv",true);
            fw.write(formattedDate + " | PAYMENT | " + description.toUpperCase() + " | " + payer.toUpperCase() + " | " + String.format("%.2f",(payment * -1)) + "\n");
            // File writes every entry as time | type | description | payer | amount on a csv file called "transactions.csv"
            // (amount input * -1) to show negative charge.

            fw.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //This is case: "3" on the main menu screen. It provides another menu screen to the user to give options on how to view their transactions.
    public static void showLedger(){

        boolean runLedger = true; //boolean flag to keep ledger menu running until they exit out of the ledger menu to go back to the main menu.
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
                    showAllEntries(); //opens the show all entries screen
                    break;
                case "b":
                    showDeposits(); //user can only see the deposit transactions when prompt.
                    break;
                case "c":
                    showPayments(); //user can only see the payment transactions when prompt.
                    break;
                case "d":
                    reports(); //opens up a new menu which is called reports
                    break;
                case "e":
                    runLedger = false; //exits the ledger menu
                    break;
                default:
                    System.out.println("Invalid option. Please choose between the options on the screen."); //give the user an error when user doesn't use the right buttons on the ledger menu.
                    System.out.println();


            }

        }

    }
    //This method is used to show all transactions to the user from oldest to newest
    public static void showAllEntries(){
        try(BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            System.out.println();
            System.out.println("Here are all your transactions: ");
            System.out.println();

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
             //used BufferedReader to read every line on the csv file which includes all the transactions that was logged by the user.

            }
            System.out.println();
            System.out.println("Press enter to go back to the ledger screen."); //prompts user to go back to the ledger screen after viewing all entries.
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // method is used to show user all the deposited transactions they have logged.
    public static void showDeposits(){
        try(BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;
            System.out.println("Here are all your deposited transactions: ");
            System.out.println();
            while ((line = reader.readLine()) != null) {

                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    String type = entries[1].trim();
                //used to split the entries into 5 parts with |. Using index 1 to match all the deposited transactions.

                    if (type.equalsIgnoreCase("DEPOSIT")) {
                        System.out.println(line); //print all transactions that has 'DEPOSIT' on index 1.
                    }
                }
            }
            System.out.println();
            System.out.println("Press enter to go back to the ledger screen."); //prompts user to go back to the ledger screen after viewing all entries.
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // method is used to show user all the payment transactions they have logged.
    public static void showPayments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            System.out.println("Here are all your payment transactions: ");
            System.out.println();
            while ((line = reader.readLine()) != null) {

                String[] entries = line.split("\\|");
                if (entries.length >= 5) {
                    String type = entries[1].trim();
                    //used to split the entries into 5 parts with |. Using index 1 to match all the deposited transactions.

                    if (type.equalsIgnoreCase("PAYMENT")) {
                        System.out.println(line);
                        //print all transactions that has 'PAYMENT' on index 1.
                    }
                }
            }
            System.out.println();
            System.out.println("Press enter to go back to the ledger screen.");
            scanner.nextLine(); //prompts user to go back to the ledger screen after viewing all entries.
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }
    //created a reports menu where user can choose options on how they want to view their transactions by time periods.
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
                    showMonthToDate(); //shows user the month to date transactions
                    break;
                case "2":
                    showPreviousMonth(); //shows user previous month transactions
                    break;
                case "3":
                    showYearToDate(); //shows user the year to date transactions
                    break;
                case "4":
                    showPreviousYear(); //shows previous year transactions
                    break;
                case "5":
                    searchByVendor(); //user can input the name of vendor to show their transactions
                    break;
                case "0":
                    reportsMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose between the options on the screen.");
            }

        }

    }
    public static void showMonthToDate(){
        LocalDate now = LocalDate.now();
        int thisYear = now.getYear();
        int thisMonth = now.getMonthValue();
        String monthName = now.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        //created variables to show this month and this year transactions.

        boolean found = false;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Month to Date Transactions [" + monthName + "]: ");
            System.out.println();
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);
                    //use to split the entries into 5 parts with |. Using index 0 to be the entry date for every transaction made.

                    if(entryDate.getYear() == thisYear && entryDate.getMonthValue() == thisMonth){
                        System.out.println(line);
                        //entry date that matches this year and this month will be printed.
                        found = true;
                    }
                }

            }
            if (!found){
                System.out.println("* You have no transactions from this month. *");
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
            //created variable for this previous month by today's year, getting today's month and subtracting 1
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
                    //use to split the entries into 5 parts with |. Using index 0 to use as the entry date.
                    if(entryDate.getYear() == thisYear && entryDate.getMonthValue() == lastMonth){
                        System.out.println(line);
                     //if entry date matches this year and matches last month then print lines
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

        //created a variable to use to get the entry date to start at January 1 of this year
        boolean found = false;


        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;

            System.out.println("Year To Date Transactions [" + startOfYear.getYear() + "]:");
            System.out.println();
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\|");
                if (entries.length >= 5){
                    LocalDate entryDate = LocalDate.parse(entries[0].trim(),dtf);
                    //use to split the entries into 5 parts with |. Using index 0 to get the entry date of the transaction.
                    if(entryDate.isEqual(startOfYear) || entryDate.isAfter(startOfYear) && entryDate.isBefore(today) || entryDate.isEqual(today)){
                        System.out.println(line); //if entry date matches from the start of this year then print transactions.
                        found = true;
                    }
                }

            }
            if (!found){
                System.out.println("* You have no transactions from year to date. *");
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
            // created a variable for previous year by getting today's year and subtracting by 1.
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
                    //used to split the entries into 5 parts with |. Using index 0 get entry date of every transaction.
                    if(entryDate.getYear() == previousYear){
                        System.out.println(line);
                        //if entry date matches all previous year transactions then print lines.
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
        //user inputs the vendor/payer to search for
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
        String line;
            System.out.println();
            System.out.println("Here are your transactions with " + vendor + ": \n");

            while ((line = reader.readLine()) != null) {

            String[] entries = line.split("\\|");
            if (entries.length >= 5) {
                String type = entries[3].trim();
                //used to split the entries into 5 parts with |. Using index 3 to match the vendor/payer.

                if (type.equalsIgnoreCase(vendor)) {
                    System.out.println(line);
                    //if the user input matches the vendor/payer on index 3 then print lines
                    found = true;
                }
            }
        }
            if (!found){
                System.out.println("No transactions with " + vendor + " found.");
                //if there is no match with the user input on the file, then print no transactions found.
            }

            System.out.println();
            System.out.println("Press enter to go back to the reports menu.");
            scanner.nextLine();

    } catch (Exception e) {
        System.out.println(e.getMessage());

    }

    }


}


