class Customer {

private String name;
private String email;
private String mobile;
private final String customerId;

private static long customerCounter = 100;

private static String generateCustomerId() {
customerCounter++;
return &quot;CUST&quot; + customerCounter;
}

public Customer(String name, String email, String mobile) {
this.name = name;
this.email = email;
this.mobile = mobile;
this.customerId = generateCustomerId();
}

public String getName() {
return name;
}

public String getEmail() {
return email;
}

public String getMobile() {
return mobile;
}

public String getCustomerId() {
return customerId;
}
}

public class Main {

private final String accountNumber;
private String ownerName;
private long balance;
private boolean active;

private static int accountCounter = 1;

private static String generateAccountNumber() {
String id = String.format(&quot;AC%04d&quot;, accountCounter);
accountCounter++;
return id;
}

public Main(String ownerName, long balance) {
this.accountNumber = generateAccountNumber();
this.ownerName = ownerName;
this.balance = balance;

this.active = true;
}

public Main(String ownerName) {
this(ownerName, 0);
}

public void deposit(long amount) {
if (amount &lt; 0)
return;

balance += amount;
}

public boolean withdraw(long amount) {

if (amount &lt; 0)
return false;

if (balance &gt;= amount) {
balance -= amount;
return true;
}

return false;
}

public static void transfer(Main from, Main to, long amount) {

if (from.withdraw(amount)) {
to.deposit(amount);

System.out.println(&quot;Transferred &quot; + amount);
} else {
System.out.println(&quot;Transfer Failed&quot;);
}
}

public String getAccountNumber() {
return accountNumber;
}

public String getOwnerName() {
return ownerName;
}

public long getBalance() {
return balance;
}

public boolean isActive() {
return active;
}

public static void main(String[] args) {

Customer c1 = new Customer(&quot;Rahul&quot;, &quot;rahul@gmail.com&quot;, &quot;9876543210&quot;);
Customer c2 = new Customer(&quot;Priya&quot;, &quot;priya@gmail.com&quot;, &quot;9876501234&quot;);
Customer c3 = new Customer(&quot;Amit&quot;, &quot;amit@gmail.com&quot;, &quot;9123456789&quot;);

Main[] accounts = new Main[3];

accounts[0] = new Main(c1.getName(), 1000);
accounts[1] = new Main(c2.getName(), 2000);

accounts[2] = new Main(c3.getName());

accounts[0].deposit(500);
accounts[1].withdraw(700);
accounts[2].deposit(1000);
accounts[2].withdraw(300);

transfer(accounts[0], accounts[2], 400);

System.out.println(&quot;\nAccount Details&quot;);

for (Main acc : accounts) {
System.out.println(
acc.getAccountNumber() + &quot; | &quot; +
acc.getOwnerName() + &quot; | Balance: &quot; +
acc.getBalance());
}
}
}