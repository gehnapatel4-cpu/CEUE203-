import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

// ==========================================
// 1. ANNOTATIONS & REFLECTION (Practical 7, 13)
// ==========================================
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {
    String message() default "must be > 0";
}

// ==========================================
// 2. EXCEPTIONS LAYER (Practical 8)
// ==========================================
class BankException extends Exception {
    public BankException(String message) { super(message); }
}

class InsufficientFundsException extends BankException {
    private final long shortfall;
    public InsufficientFundsException(long shortfall) {
        super("Insufficient funds. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }
    public long getShortfall() { return shortfall; }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) { super(message); }
}

class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String accountNumber) {
        super("Account not found: " + accountNumber);
    }
}

// ==========================================
// 3. MODEL LAYER (Practical 2, 3, 5, 11)
// ==========================================
abstract class Account implements Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    private String ownerName;

    @Positive
    private long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public synchronized long getBalance() { return balance; }

    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be positive.");
        this.balance += amount;
    }

    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive.");
        if (!canWithdraw(amount)) {
            long shortfall = amount - balance;
            throw new InsufficientFundsException(shortfall > 0 ? shortfall : 0);
        }
        this.balance -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public int compareTo(Account o) {
        return this.accountNumber.compareTo(o.accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%s | Owner: %-10s | Balance: %d | Rate: %.1f%%", accountNumber, ownerName, balance, interestRate());
    }
}

class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long minBalance = 500;

    public SavingsAccount(String accountNumber, String ownerName, long balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public double interestRate() { return 4.0; }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= minBalance;
    }
}

// ==========================================
// 4. VALIDATOR & COMMAND PARSER (Practical 4)
// ==========================================
class Validator {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static boolean isValidMobile(String mobile) { return MOBILE_PATTERN.matcher(mobile).matches(); }
    public static boolean isValidEmail(String email) { return EMAIL_PATTERN.matcher(email).matches(); }
}

// ==========================================
// 5. REPOSITORY LAYER (Practical 12, 13)
// ==========================================
class Repository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    public T save(T entity) {
        try {
            String key = null;
            for (Field field : entity.getClass().getSuperclass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    key = (String) field.get(entity);
                    break;
                }
            }
            if (key == null) {
                for (Field field : entity.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class)) {
                        field.setAccessible(true);
                        key = (String) field.get(entity);
                        break;
                    }
                }
            }
            if (key != null) storage.put(key, entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public T findById(String id) { return storage.get(id); }
    public List<T> findAll() { return new ArrayList<>(storage.values()); }
}

// ==========================================
// 6. BANK SERVICE LAYER (Practical 14)
// ==========================================
class BankService {
    private final Repository<Account> repository = new Repository<>();
    private static int accountCounter = 100;

    public String openAccount(String ownerName, long initialDeposit) throws InvalidAmountException {
        if (initialDeposit < 500) {
            throw new InvalidAmountException("Opening deposit must be at least 500 for Savings Account.");
        }
        String accNo = "AC" + (++accountCounter);
        Account account = new SavingsAccount(accNo, ownerName, initialDeposit);
        repository.save(account);
        return accNo;
    }

    public void deposit(String accNo, long amount) throws AccountNotFoundException, InvalidAmountException {
        Account acc = repository.findById(accNo);
        if (acc == null) throw new AccountNotFoundException(accNo);
        acc.deposit(amount);
    }

    public void withdraw(String accNo, long amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException {
        Account acc = repository.findById(accNo);
        if (acc == null) throw new AccountNotFoundException(accNo);
        acc.withdraw(amount);
    }

    public void transfer(String fromAcc, String toAcc, long amount) throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException {
        Account source = repository.findById(fromAcc);
        Account target = repository.findById(toAcc);
        if (source == null) throw new AccountNotFoundException(fromAcc);
        if (target == null) throw new AccountNotFoundException(toAcc);

        // Deadlock Prevention Strategy (Practical 10): Lock lower account number first
        Account firstLock = source.getAccountNumber().compareTo(target.getAccountNumber()) < 0 ? source : target;
        Account secondLock = firstLock == source ? target : source;

        synchronized (firstLock) {
            synchronized (secondLock) {
                source.withdraw(amount);
                target.deposit(amount);
            }
        }
    }

    public String getStatement(String accNo) throws AccountNotFoundException {
        Account acc = repository.findById(accNo);
        if (acc == null) throw new AccountNotFoundException(accNo);
        return "STATEMENT: " + acc.toString();
    }

    public List<Account> listAccountsSorted() {
        List<Account> accounts = repository.findAll();
        accounts.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return accounts;
    }

    public void processConcurrentBatch() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        System.out.println("Executing concurrent batch deposits...");
        for (Account acc : repository.findAll()) {
            executor.submit(() -> {
                try {
                    acc.deposit(1000);
                    System.out.println("Batch Deposit +1000 to " + acc.getAccountNumber());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {}
    }
}

// ==========================================
// 7. MAIN INTERACTIVE APPLICATION (Practical 1, 14)
// ==========================================
enum MenuOption { OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, STATEMENT, LIST_ALL, BATCH_PROCESS, EXIT }

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        Scanner scanner = new Scanner(System.in);

        // Seed initial data
        try {
            bankService.openAccount("Alice", 2000);
            bankService.openAccount("Bob", 5000);
        } catch (Exception ignored) {}

        System.out.println("=========================================");
        System.out.println("   WELCOME TO MINIBANK SYSTEM (CAPSTONE) ");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Statement");
            System.out.println("6. List Accounts (Sorted)");
            System.out.println("7. Run Concurrent Batch Operations");
            System.out.println("8. Exit");
            System.out.print("Select choice (1-8): ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 8.");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Owner Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Initial Deposit: ");
                        long deposit = Long.parseLong(scanner.nextLine());
                        String accNo = bankService.openAccount(name, deposit);
                        System.out.println("Account opened successfully! Assigned Account Number: " + accNo);
                    }
                    case 2 -> {
                        System.out.print("Enter Account Number: ");
                        String accNo = scanner.nextLine();
                        System.out.print("Enter Deposit Amount: ");
                        long amt = Long.parseLong(scanner.nextLine());
                        bankService.deposit(accNo, amt);
                        System.out.println("Deposit successful!");
                    }
                    case 3 -> {
                        System.out.print("Enter Account Number: ");
                        String accNo = scanner.nextLine();
                        System.out.print("Enter Withdrawal Amount: ");
                        long amt = Long.parseLong(scanner.nextLine());
                        bankService.withdraw(accNo, amt);
                        System.out.println("Withdrawal successful!");
                    }
                    case 4 -> {
                        System.out.print("From Account Number: ");
                        String from = scanner.nextLine();
                        System.out.print("To Account Number: ");
                        String to = scanner.nextLine();
                        System.out.print("Enter Transfer Amount: ");
                        long amt = Long.parseLong(scanner.nextLine());
                        bankService.transfer(from, to, amt);
                        System.out.println("Transfer successful!");
                    }
                    case 5 -> {
                        System.out.print("Enter Account Number: ");
                        String accNo = scanner.nextLine();
                        System.out.println(bankService.getStatement(accNo));
                    }
                    case 6 -> {
                        System.out.println("\n--- Accounts Sorted by Balance (Highest First) ---");
                        for (Account acc : bankService.listAccountsSorted()) {
                            System.out.println(acc);
                        }
                    }
                    case 7 -> bankService.processConcurrentBatch();
                    case 8 -> {
                        running = false;
                        System.out.println("Thank you for using MiniBank! Goodbye.");
                    }
                    default -> System.out.println("Invalid choice! Please select 1-8.");
                }
            } catch (BankException e) {
                System.out.println("BANK ERROR: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
