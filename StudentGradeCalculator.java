import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeCalculator {
    private static final int MAX_MARKS = 100;
    private static final String[] GRADE_LETTERS = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F"};
    private static final double[] GRADE_THRESHOLDS = {95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 0};

    private ArrayList<Subject> subjects;
    private String studentName;
    private String studentID;
    private Scanner scanner;

    public StudentGradeCalculator() {
        subjects = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    static class Subject {
        String name;
        double marks;
        double creditHours;

        Subject(String name, double marks, double creditHours) {
            this.name = name;
            this.marks = marks;
            this.creditHours = creditHours;
        }
    }

    public void start() {
        displayWelcomeBanner();
        getStudentInfo();
        inputSubjectMarks();
        displayResults();
        scanner.close();
    }

    private void displayWelcomeBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║      STUDENT GRADE CALCULATOR        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("\nWelcome to the Academic Performance Analyzer!");
    }

    private void getStudentInfo() {
        System.out.print("\nEnter Student Name: ");
        studentName = scanner.nextLine().trim();

        System.out.print("Enter Student ID: ");
        studentID = scanner.nextLine().trim();
    }

    private void inputSubjectMarks() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("        SUBJECT INFORMATION        ");
        System.out.println("═══════════════════════════════════");

        while (true) {
            inputSingleSubject();

            System.out.print("\nDo you want to add another subject? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.startsWith("y")) {
                break;
            }
        }
    }

    private void inputSingleSubject() {
        while (true) {
            try {
                System.out.print("\nEnter Subject Name: ");
                String subjectName = scanner.nextLine().trim();

                System.out.print("Enter Credit Hours: ");
                double creditHours = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Enter Marks (out of 100): ");
                double marks = Double.parseDouble(scanner.nextLine().trim());

                if (marks < 0 || marks > MAX_MARKS) {
                    System.out.println("\n⚠️ Error: Marks must be between 0 and 100");
                    continue;
                }

                if (creditHours <= 0) {
                    System.out.println("\n⚠️ Error: Credit hours must be greater than 0");
                    continue;
                }

                subjects.add(new Subject(subjectName, marks, creditHours));
                break;

            } catch (NumberFormatException e) {
                System.out.println("\n⚠️ Error: Please enter valid numbers for marks and credit hours");
            }
        }
    }

    private void displayResults() {
        if (subjects.isEmpty()) {
            System.out.println("\n⚠️ No subjects entered. Cannot calculate grades.");
            return;
        }

        double totalMarks = 0;
        double totalCreditHours = 0;

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    GRADE REPORT CARD                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.out.println("\nStudent Name: " + studentName);
        System.out.println("Student ID: " + studentID);

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.printf("%-20s %-15s %-15s %-10s%n", "Subject", "Credit Hours", "Marks", "Grade");
        System.out.println("═══════════════════════════════════════════════════════════════");

        for (Subject subject : subjects) {
            totalMarks += subject.marks * subject.creditHours;
            totalCreditHours += subject.creditHours;

            System.out.printf("%-20s %-15.1f %-15.1f %-10s%n",
                    subject.name,
                    subject.creditHours,
                    subject.marks,
                    getGrade(subject.marks));
        }

        double weightedAverage = totalMarks / totalCreditHours;
        String finalGrade = getGrade(weightedAverage);

        System.out.println("───────────────────────────────────────────────────────────────");
        System.out.printf("Total Credit Hours: %.1f%n", totalCreditHours);
        System.out.printf("Weighted Average: %.2f%%%n", weightedAverage);
        System.out.println("Final Grade: " + finalGrade);

        displayPerformanceComment(weightedAverage);
    }

    private String getGrade(double marks) {
        for (int i = 0; i < GRADE_THRESHOLDS.length; i++) {
            if (marks >= GRADE_THRESHOLDS[i]) {
                return GRADE_LETTERS[i];
            }
        }
        return "F";
    }

    private void displayPerformanceComment(double average) {
        System.out.println("\nPerformance Analysis:");
        System.out.println("═══════════════════════");

        if (average >= 90) {
            System.out.println("🌟 Outstanding Performance! Keep up the excellent work!");
        } else if (average >= 80) {
            System.out.println("🎯 Great Achievement! You're on the right track!");
        } else if (average >= 70) {
            System.out.println("👍 Good Performance! Keep pushing yourself!");
        } else if (average >= 60) {
            System.out.println("📚 Fair Performance. There's room for improvement.");
        } else if (average >= 50) {
            System.out.println("⚠️ Pass, but needs significant improvement.");
        } else {
            System.out.println("❗ Attention needed. Please seek academic support.");
        }

        System.out.println("\nNote: This analysis is based on your weighted average across all subjects.");
    }

    public static void main(String[] args) {
        StudentGradeCalculator calculator = new StudentGradeCalculator();
        calculator.start();
    }
}