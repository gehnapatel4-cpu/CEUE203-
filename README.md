# Object Oriented Programming (CEUE203) - Semester 3 Lab Works

This repository contains the complete practical solutions for **Object Oriented Programming (CEUE203)** covering all 14 Practicals:
- **Part A**: Practice Programs (Hours 1 of Practicals 1 to 14) segregated into individual folders `Practical-1` to `Practical-14`.
- **Part B**: Project Work (MiniBank console banking application) segregated into the `Project/` folder.

---

## 📁 Repository Structure

```text
├── Practical-1/
│   ├── RPSLS.java              # Rock-Paper-Scissors-Lizard-Spock with enums & switch expressions
│   ├── TollBooth.java          # Toll collection record Vehicle & switch expressions
│   └── VendingMachine.java     # Coin enum, switch expression & coin accumulation
├── Practical-2/
│   ├── CinemaShow.java         # Seat booking & cancellation with constructor chaining
│   ├── ParkingLot.java         # Two/four-wheeler parking management & revenue tracking
│   └── Thermostat.java         # Thermostat control with encapsulation & chaining
├── Practical-3/
│   ├── Card.java & Driver2.java # Card deck duplicate detection using equals() / hashCode()
│   ├── Fraction.java & Driver3.java # Fraction GCD reduction and equality
│   └── Point.java & Driver.java # 2D Points distinct counting using equals() / hashCode()
├── Practical-4/
│   ├── chat-log-filter/        # String splitting and keyword filtering with StringBuilder
│   ├── password-strength/      # Password strength evaluator using regular expressions
│   └── template-filler/        # Dynamic string template placeholder replacement
├── Practical-5/
│   ├── Area.java               # Abstract Shape hierarchy (Circle, Rectangle, Triangle)
│   ├── Main.java               # Media late fee hierarchy (Book, DVD, Magazine)
│   └── Salary.java             # Abstract Employee payroll (FullTime, PartTime, Intern)
├── Practical-6/
│   ├── discount-engine/        # Functional interface & lambdas for discount calculations
│   ├── notifications/          # Functional Notifier interface and Urgent marker interface
│   └── remote-control/         # Switchable interface with default methods & policy lambdas
├── Practical-7/
│   ├── column-mapper/          # Custom @Column annotation and reflection mapper
│   ├── form-validator/         # @NotBlank & @MaxLength validation via reflection
│   └── mini-test-runner/       # Custom @Run annotation test runner using reflection
├── Practical-8/
│   ├── auto-closeable/         # Try-with-resources with custom AutoCloseable resource
│   ├── guarded-calculator/     # Custom DivideByZeroException with structured exception handling
│   └── warehouse/              # Inventory issue with checked OutOfStockException & shortfall
├── Practical-9/
│   ├── array-sum/              # Parallel array reduction: Unsafe vs Synchronized vs Partial sums
│   ├── counter-race/           # Multi-threaded race condition demonstration and synchronized fix
│   └── seat-booking/           # Concurrent ticket booking race condition & thread safety
├── Practical-10/
│   ├── deadlock-demo/          # Mutual lock ordering deadlock reproduction and resolution
│   ├── producer-consumer/      # Coordinated bounded buffer using wait() and notify()
│   └── thread-pool-runner/     # Managed ExecutorService thread pool with task submission
├── Practical-11/
│   ├── log-analyzer/           # NIO DirectoryStream and BufferedReader keyword scanner
│   ├── save-load/              # ObjectOutputStream / ObjectInputStream with transient field check
│   └── tree-walker/            # NIO Files.walkFileTree directory traversal summary
├── Practical-12/
│   ├── leaderboard/            # Player score sorting with Map.Entry comparator (score descending)
│   ├── unique-visitors/        # HashSet vs LinkedHashSet for tracking distinct visitors
│   └── word-frequency/         # Text tokenisation and frequency ranking with HashMap & Comparator
├── Practical-13/
│   ├── bounded-stats/          # Generic bounded type methods <N extends Number> (average & max)
│   ├── generic-containers/     # Generic Box<T>, Pair<A, B> and getFirst(T[]) method
│   └── generic-stack/          # Type-safe array-backed GenericStack<T>
├── Practical-14/
│   ├── packaged-app/           # Layered persistent data store with binary serialization
│   └── tiny-layered-app/       # Layered architecture with reflection-based @Test runner
└── Project/                    # Part B: MiniBank Console Banking Application
    ├── Account.java            # Abstract base account with encapsulation, @Id, @Positive
    ├── AccountStore.java       # Collection-based storage with balance sorting
    ├── AccountWorker.java      # Runnable worker for concurrent deposit simulation
    ├── AnnotationValidator.java# Reflection validator for @Positive & @MaxLength
    ├── BankException.java      # Custom checked exception hierarchy (shortfall tracking)
    ├── BankInfo.java           # Record for bank header info
    ├── BankingInterfaces.java  # Transactable, InterestBearing, WithdrawRule, Premium
    ├── BankService.java        # Core banking service wiring repository, validation & exceptions
    ├── Command.java            # Record for parsed banking commands
    ├── CommandParser.java      # Command interpreter (DEPOSIT / WITHDRAW / TRANSFER)
    ├── CurrentAccount.java     # Overdraft facility subclass
    ├── Customer.java           # Customer entity with static ID generator and nested Address
    ├── FixedDepositAccount.java# Term deposit with locked funds
    ├── MenuOption.java         # Enum of actions
    ├── MiniBank.java           # Milestone menu driver
    ├── MiniBankApp.java        # Full capstone menu driver with persistence & batch processor
    ├── MiniBankTests.java      # Comprehensive automated unit test suite
    ├── minibank.jar            # Packaged runnable JAR
    ├── ReportGenerator.java    # NIO log parser and end-of-day summary report generator
    ├── Repository.java         # Generic reflective CRUD repository with bounded methods
    ├── SavingsAccount.java     # Savings account with interest and minimum balance
    ├── StatementFormatter.java # StringBuilder-based statement formatter
    ├── StatePersister.java     # Object serialization save / load mechanism
    ├── TransactionLog.java     # Append-only transaction logger using NIO Files
    ├── TransactionProcessor.java# Concurrency manager (ExecutorService, producer-consumer, deadlock-free)
    ├── TransactionType.java    # Enum of transaction types
    ├── ValidationAnnotations.java# Custom runtime annotations (@Id, @Positive, @MaxLength)
    └── Validator.java          # Regex pattern validation (Mobile, Email, PAN, IFSC)
```

---

## 🚀 How to Compile and Run

### 1. Part A Practicals (Examples)

#### Practical 1:
```bash
# Vending Machine
cd Practical-1
javac VendingMachine.java
java VendingMachine

# Toll Booth
javac TollBooth.java
java TollBooth

# RPSLS
javac RPSLS.java
java RPSLS
```

#### Practical 9 (Concurrency):
```bash
cd Practical-9/counter-race
javac CounterRace.java
java CounterRace
```

#### Practical 10 (Deadlock Demo):
```bash
cd Practical-10/deadlock-demo
javac DeadlockDemo.java
java DeadlockDemo
```

---

### 2. Part B: MiniBank Project

#### Run the Runnable JAR directly:
```bash
cd Project
java -jar minibank.jar
```

#### Run Automated Unit Tests:
```bash
cd Project
javac -d . *.java
java MiniBankTests
```

#### Run the Interactive Application from source:
```bash
cd Project
javac -d . *.java
java MiniBankApp
```
