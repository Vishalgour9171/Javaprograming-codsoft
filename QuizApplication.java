import java.util.Scanner;
import java.util.concurrent.*;

class QuizQuestion {
    private String question;
    private String[] options;
    private int correctAnswer;

    public QuizQuestion(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}

class Quiz {
    private QuizQuestion[] questions;
    private int score;
    private int currentQuestionIndex;
    private Scanner scanner;
    private static final int TIME_LIMIT_SECONDS = 10;

    public Quiz(QuizQuestion[] questions) {
        this.questions = questions;
        this.score = 0;
        this.currentQuestionIndex = 0;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Quiz!");
        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            displayQuestion(questions[currentQuestionIndex]);
        }
        displayResults();
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + question.getQuestion());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        int userAnswer = getUserAnswerWithTimeout();
        if (userAnswer == -1) {
            System.out.println("Time's up!");
        } else if (userAnswer == question.getCorrectAnswer()) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Wrong! The correct answer was: " + question.getCorrectAnswer());
        }
    }

    private int getUserAnswerWithTimeout() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                return scanner.nextInt();
            }
        });

        try {
            return future.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return -1; // Indicates timeout
        } catch (Exception e) {
            return -1; // Handle other exceptions
        } finally {
            executor.shutdown();
        }
    }

    private void displayResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your score is: " + score + " out of " + questions.length);
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + questions[i].getQuestion());
            System.out.println("Your answer: " + (i < currentQuestionIndex ? "Answered" : "Not Answered"));
            System.out.println("Correct answer: " + questions[i].getCorrectAnswer());
        }
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        QuizQuestion[] questions = {
            new QuizQuestion("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3),
            new QuizQuestion("Who wrote 'Hamlet'?", new String[]{"1. Charles Dickens", "2. William Shakespeare", "3. Mark Twain", "4. Jane Austen"}, 2),
            new QuizQuestion("What is the largest planet in our solar system?", new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Saturn"}, 3)
        };

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
