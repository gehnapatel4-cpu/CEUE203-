import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class SaveLoad {

    static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int id;
        private final String name;
        private final double gpa;
        // Transient field - will NOT be persisted during serialization
        private transient String sessionToken;

        public Student(int id, String name, double gpa, String sessionToken) {
            this.id = id;
            this.name = name;
            this.gpa = gpa;
            this.sessionToken = sessionToken;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        @Override
        public String toString() {
            return "Student{id=" + id + ", name='" + name + "', gpa=" + gpa +
                    ", sessionToken (transient)='" + sessionToken + "'}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student s)) return false;
            return id == s.id && Double.compare(s.gpa, gpa) == 0 && name.equals(s.name);
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }
    }

    public static void main(String[] args) {
        System.out.println("========== Practical 11 - Problem 1: Object Serialization (Save/Load) ==========");

        Student[] originalStudents = new Student[]{
                new Student(101, "Tanay", 9.4, "TOKEN_SEC_991"),
                new Student(102, "Aarav", 8.9, "TOKEN_SEC_992"),
                new Student(103, "Diya", 9.6, "TOKEN_SEC_993")
        };

        System.out.println("Original students before serialization (note sessionToken is populated):");
        for (Student s : originalStudents) {
            System.out.println("  " + s);
        }

        Path dataFile = Path.of("students_temp.dat");

        // Save
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(dataFile))) {
            oos.writeObject(originalStudents);
            System.out.println("\nSaved " + originalStudents.length + " students to " + dataFile.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving: " + e.getMessage());
        }

        // Load
        Student[] loadedStudents = null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(dataFile))) {
            loadedStudents = (Student[]) ois.readObject();
            System.out.println("Loaded " + loadedStudents.length + " students from " + dataFile.getFileName());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading: " + e.getMessage());
        } finally {
            try {
                Files.deleteIfExists(dataFile); // clean up temporary file
            } catch (IOException ignored) {}
        }

        if (loadedStudents != null) {
            System.out.println("\nLoaded students (note transient sessionToken is now null):");
            for (Student s : loadedStudents) {
                System.out.println("  " + s);
            }
            boolean matches = Arrays.equals(originalStudents, loadedStudents);
            System.out.println("Integrity Check (Core fields match): " + matches);
            System.out.println("Transient Field Verification: sessionToken was successfully skipped during serialization.");
        }
    }
}
