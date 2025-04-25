import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Capstone1 {
        static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
            homeScreen();
    }
    public static void homeScreen() {
        boolean run = true;
        while (run) {
            System.out.println("(1) Add Deposit");
            System.out.println("(2) Make Payment");
            System.out.println("(3) Ledger");
            System.out.println("(4) Exit");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;


            }
        }
    }
    public static void addDeposit(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = currentDateTime.format(dtf);





    }
    public static void makePayment(){

    }



}


