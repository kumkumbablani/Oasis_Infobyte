package out.production.OASIS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private final String username;
    private final String password;
    private String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

class Question {
    private final String questionText;
    private final List<String> options;
    private final int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}

public class OnlineExamination {
    private static User currentUser;
    private static List<Question> questions;
    private static List<Integer> selectedAnswers;
    private static Timer timer;
    private static int remainingTimeInSeconds;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeQuestions();
        login();
    }

    private static void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Result of 34*34?", List.of("1152", "1154", "1156", "1166"), 2));
        questions.add(new Question("Among the following Which planet is the largest in the solar system?", List.of("Earth", "Venus", "Saturn", "Jupiter"), 3));
        questions.add(new Question("What is the chemical symbol for gold?", List.of("P", "Au", "Cu", "He"), 1));
        questions.add(new Question("What is the gravitational pull of the Earth?", List.of("10 m/s²", "9.807 m/s²", "1.62 m/s²", "12.54 m/s²"), 1));
        questions.add(new Question("Who painted the Mona Lisa?", List.of("Mr. Paul", "Aristotle", "Leonardo da Vinci", "James Vinci"), 2));
        questions.add(new Question("Among the following, which is considered the Silicon Valley of India?", List.of("Mumbai", "Hyderabad", "Madurai", "Bengaluru"), 3));
        questions.add(new Question("Which is the largest country in the world?", List.of("China", "Russia", "India", "America"), 1));
        questions.add(new Question("What is 5+5*5?", List.of("30", "35", "53", "10"), 0));
        questions.add(new Question("Who invented the telescope?", List.of("Albert Einstein", "Hans Lipperhey", "Galileo Galilei", "Aristotle"), 1));
        questions.add(new Question("Which country won the World Cup in 1983?", List.of("Australia", "England", "Pakistan", "India"), 3));
    }

    private static void login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (authenticateUser(username, password)) {
            currentUser = new User(username, password, username);
            showMainMenu();
        } else {
            System.out.println("Login failed. Please try again.");
            login();
        }
    }

    private static boolean authenticateUser(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private static void showMainMenu() {
        System.out.println("Welcome, " + currentUser.getFullName() + "!");
        System.out.println("1. Start Exam");
        System.out.println("2. Update Profile");
        System.out.println("3. Change Password");
        System.out.println("4. Logout");

        int choice = getValidIntInput();
        switch (choice) {
            case 1:
                startExam();
                break;
            case 2:
                updateProfile();
                break;
            case 3:
                changePassword();
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainMenu();
                break;
        }
    }

    private static int getValidIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void startExam() {
        selectedAnswers = new ArrayList<>();
        remainingTimeInSeconds = 1800;
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTimeInSeconds--;
                if (remainingTimeInSeconds <= 0) {
                    autoSubmit();
                }
            }
        }, 1000, 1000);

        System.out.println("You have 30 minutes to complete the exam.");
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            List<String> options = question.getOptions();
            for (int j = 0; j < options.size(); j++) {
                System.out.println((j + 1) + ". " + options.get(j));
            }
            System.out.print("Select an option: ");
            int answer = getValidIntInput();
            if (answer < 1 || answer > options.size()) {
                System.out.println("Invalid option. Please try again.");
                i--; // repeat the same question
            } else {
                selectedAnswers.add(answer - 1);
            }
        }
        autoSubmit();
    }

    private static void autoSubmit() {
        if (timer != null) {
            timer.cancel();
        }
        System.out.println("Time's up! Submitting your answers.");
        showResult();
    }

    private static void showResult() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int selectedAnswerIndex = selectedAnswers.get(i);
            if (selectedAnswerIndex == question.getCorrectOptionIndex()) {
                score++;
            }
        }
            if(score>=7) {
                System.out.println("Well Done!!!");
            }
        System.out.println("You scored " + score + " out of 10.");
        logout();
    }

    private static void updateProfile() {
        System.out.print("Enter your new full name: ");
        String newFullName = scanner.nextLine();
        currentUser.setFullName(newFullName);
        System.out.println("Profile updated successfully.");
        showMainMenu();
    }

    private static void changePassword() {
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
        if (currentPassword.equals(currentUser.getPassword())) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            currentUser = new User(currentUser.getUsername(), newPassword, currentUser.getFullName());
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect password. Please try again.");
        }
        showMainMenu();
    }

    private static void logout() {
        System.out.println("Logging out. Goodbye!");
        scanner.close();
        System.exit(0);
    }
}
