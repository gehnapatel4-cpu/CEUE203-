abstract class Media {
    String title;
    int lateDays;

    Media(String title, int lateDays) {
        this.title = title;
        this.lateDays = lateDays;
    }

    abstract double lateFee();
}

class Book extends Media {

    Book(String title, int lateDays) {
        super(title, lateDays);
    }

    double lateFee() {
        return lateDays * 2;
    }
}

class DVD extends Media {

    DVD(String title, int lateDays) {
        super(title, lateDays);
    }

    double lateFee() {
        return lateDays * 5;
    }
}

class Magazine extends Media {

    Magazine(String title, int lateDays) {
        super(title, lateDays);
    }

    double lateFee() {
        return lateDays * 1.5;
    }
}

public class Main {
    public static void main(String[] args) {

        Media[] items = {
            new Book("Java Programming", 4),
            new DVD("Avengers", 3),
            new Magazine("Tech Today", 5),
            new Book("Data Structures", 2)
        };

        double total = 0;

        for (Media m : items) {
            double fee = m.lateFee();

            System.out.println(
                m.title + " | Late Days: " + m.lateDays +
                " | Late Fee: ₹" + fee
            );

            total += fee;
        }

        System.out.println("-------------------------");
        System.out.println("Total Late Fees = ₹" + total);
    }
}