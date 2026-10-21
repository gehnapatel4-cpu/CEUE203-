import java.util.Objects;
class Customer {
    private String name;     private String email;     private String mobile;     private String customerId;     private Address address;
    public Customer(String name, String email, String mobile,                     String customerId, Address address) {         this.name = name;         this.email = email;         this.mobile = mobile;         this.customerId = customerId;         this.address = address;
    }
    public static class Address {
        private String line;         private String city;         private String pincode;
        public Address(String line, String city, String pincode) {             this.line = line;             this.city = city;             this.pincode = pincode;
        }
        public String getLine() {             return line;
        }
        public String getCity() {             return city;
        }
        public String getPincode() {             return pincode;
        }
    }
    public Address getAddress() {         return address;
    }
    public Customer clone() {         return new Customer(             name,             email,             mobile,             customerId,             new Address(                 address.getLine(),                 address.getCity(),                 address.getPincode()
            )
        );
    }
}
class Account {
    private String accountNumber;     private String ownerName;     private double balance;
    public Account(String accountNumber, String ownerName, double balance) {         this.accountNumber = accountNumber;         this.ownerName = ownerName;         this.balance = balance;     }
    @Override     public String toString() {
        return "Account Number: " + accountNumber +
               ", Owner: " + ownerName +
               ", Balance: " + balance;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)             return true;
        if (!(o instanceof Account))             return false;
        Account a = (Account) o;
        return accountNumber.equals(a.accountNumber);
    }
    @Override     public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
public class Main {
    public static void main(String[] args) {
        Customer.Address addr =             new Customer.Address(
                "101 ABC Street",
                "Anand",
                "388001"
            );
        Customer c1 =             new Customer(
                "Rahul",
                "rahul@gmail.com",
                "9876543210",                 "C101",                 addr             );
        Customer c2 = c1.clone();
        Account a1 =
            new Account("A1001", "Rahul", 5000);
        Account a2 =
            new Account("A1001", "Rahul Patel", 9000);
        Account a3 =
            new Account("A1002", "Priya", 7000);         System.out.println(a1);
        System.out.println(a3);
        System.out.println();
        System.out.println("a1 equals a2: " + a1.equals(a2));
        System.out.println("a1 equals a3: " + a1.equals(a3));
        System.out.println();
        System.out.println(
            "a1 instanceof Account: " +
            (a1 instanceof Account)
        );
        System.out.println(
            "c1 instanceof Customer: " +
            (c1 instanceof Customer)
        );
        System.out.println();
        System.out.println(             "Customer City: " +             c1.getAddress().getCity()
        );
        System.out.println();
        System.out.println(
            "Customer cloned successfully: " +
            (c2 != null)
        );
    }
