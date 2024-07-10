package rabs;

import java.util.Scanner;
import java.util.function.*;

public class Input {
    private static Scanner sc = new Scanner(System.in);

    public static String getInput() {
        return getInput("Enter something");
    }

    public static String getInput(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }

    public static void loopInputAndRun(Consumer<String> func) {
        loopInputAndRun("Type your message to send: ", func);
    }

    public static void loopInputAndRun(String prompt, Consumer<String> func) {
        while (true) {
            String message = getInput(prompt);
            func.accept(message);
        }
    }

    public static void loopInputAndRun(String[] prompts, Consumer<String[]> func) {
        while (true) {
            int len = prompts.length;
            String[] replies = new String[len];
            for (int i = 0; i < len; i++) {
                replies[i] = getInput(prompts[i]);
            }

            func.accept(replies);
        }
    }
}
