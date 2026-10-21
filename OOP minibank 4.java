import java.util.regex.Pattern;
class Validator {
    private static final Pattern MOBILE =         Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern EMAIL =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PAN =         Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    private static final Pattern IFSC =         Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
    public static boolean isValidMobile(String mobile) {         return MOBILE.matcher(mobile).matches();
    }
    public static boolean isValidEmail(String email) {         return EMAIL.matcher(email).matches();
    }
    public static boolean isValidPan(String pan) {         return PAN.matcher(pan).matches();
    }
    public static boolean isValidIfsc(String ifsc) {         return IFSC.matcher(ifsc).matches();
    }
}
enum TransactionType {
    DEPOSIT, WITHDRAW, TRANSFER
}
record Command(TransactionType type, String accountNumber, long amount) {}
class CommandParser {
    public static Command parse(String line) {
        String[] parts = line.trim().split("\\s+");
        TransactionType type =
            TransactionType.valueOf(parts[0]);         String accountNumber = parts[1];
        long amount = Long.parseLong(parts[2]);
        return new Command(type, accountNumber, amount);
    }
}
class Account {
    private String accountNumber;     private String ownerName;     private long balance;
    public Account(String accountNumber, String ownerName, long balance) {         this.accountNumber = accountNumber;         this.ownerName = ownerName;
        this.balance = balance;
    }
    public String getAccountNumber() {         return accountNumber;
    }
    public String getOwnerName() {         return ownerName;
    }
    public long getBalance() {         return balance;
    }
}
class StatementFormatter {
    public static String buildStatement(Account account) {         StringBuilder statement = new StringBuilder();
        statement.append("----- MiniBank Statement -----\n");         statement.append("Account Number: ")                   .append(account.getAccountNumber())
                  .append("\n");
        statement.append("Owner: ")                   .append(account.getOwnerName())
                  .append("\n");
        statement.append("Balance: ₹")
                  .append(account.getBalance())
                  .append("\n");
        statement.append("------------------------------");
        return statement.toString();
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("VALIDATION TESTS");
        System.out.println(
            "Mobile valid: " +
            Validator.isValidMobile("9876543210")
        );
        System.out.println(
            "Mobile invalid: " +
            Validator.isValidMobile("12345")
        );
        System.out.println(
            "Email valid: " +
            Validator.isValidEmail("rahul@gmail.com")
        );
        System.out.println(
            "Email invalid: " +
            Validator.isValidEmail("rahul@gmail")
        );
        System.out.println(
            "PAN valid: " +
            Validator.isValidPan("ABCDE1234F")
        );
        System.out.println(
            "PAN invalid: " +
            Validator.isValidPan("ABC123")
        );
        System.out.println(
            "IFSC valid: " +
            Validator.isValidIfsc("SBIN0001234")
        );
        System.out.println(
            "IFSC invalid: " +
            Validator.isValidIfsc("SBI123")
        );
        System.out.println();
        String line = "DEPOSIT AC0001 500";
        Command command = CommandParser.parse(line);         System.out.println("COMMAND");
        System.out.println("Type: " + command.type());
        System.out.println("Account: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());
        System.out.println();
        Account account =
            new Account("AC0001", "Rahul", 5000);
        System.out.println(
            StatementFormatter.buildStatement(account)
        );
    }
}
 