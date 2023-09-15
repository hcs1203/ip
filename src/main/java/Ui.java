import java.io.File;
import java.util.Scanner;

public class Ui {
    private final String div = "____________________________________________________________\n";

    void showWelcomeMessage() {
        System.out.println(div + "Hello! I'm CarrotCake\nWhat can I do for you?\n" + div);
    }

    void showGoodByeMessage() {
        System.out.println(div + "Bye. Hope to see you again soon!\n" + div);
    }

    void run() {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(new File("data/tasks.txt"));
        int count = storage.getCount();
        String input = scanner.nextLine();
        TaskList tasks = new TaskList(storage.load());

        while (!input.equalsIgnoreCase("bye")) {
            System.out.println(div);
            if (input.equalsIgnoreCase("list")) {
                // Print tasks
                if (count < 1) {
                    System.out.println("List is empty.");
                    System.out.println(div);
                    input = scanner.nextLine();
                    continue;
                }
                for (int i = 0; i < count; i++) {
                    System.out.print((i + 1) + ". " + tasks.getTask(i).printTask());
                }
                System.out.println(div);
                input = scanner.nextLine();
                continue;
            }
            if (input.toLowerCase().startsWith("mark ")) {
                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]) - 1;
                if (taskNumber > count) {
                    System.out.println("OOPS!!! Such a task doesn't exist :-(\n" + div);
                    input = scanner.nextLine();
                    continue;
                }
                tasks.getTask(taskNumber).mark();
                System.out.println("Nice! I've marked this task as done:\n [X] " + tasks.getTask(taskNumber).name + "\n" + div);
                input = scanner.nextLine();
                continue;
            }
            if (input.toLowerCase().startsWith("unmark ")) {
                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]) - 1;
                if (taskNumber > count) {
                    System.out.println("OOPS!!! Such a task doesn't exist :-(\n" + div);
                    input = scanner.nextLine();
                    continue;
                }
                tasks.getTask(taskNumber).unmark();
                System.out.println("OK, I've marked this task as not done yet:\n [ ] "
                        + tasks.getTask(taskNumber).name + "\n" + div);
                input = scanner.nextLine();
                continue;
            }
            if (input.toLowerCase().startsWith("delete ")) {
                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]) - 1;
                if (taskNumber > count) {
                    System.out.println("OOPS!!! Such a task doesn't exist :-(\n" + div);
                    input = scanner.nextLine();
                    continue;
                }
                count--;
                System.out.println("Noted. I've removed this task:\n" + tasks.getTask(taskNumber).printTask() +
                        "\nNow you have " + count + " tasks in the list.\n" + div);
                tasks.removeTask(taskNumber);
                input = scanner.nextLine();
                continue;
            }

            if (input.toLowerCase().startsWith("todo ")) {
                input = input.substring(4);
                System.out.println("Got it. I've  added this task: \n[T] [ ]" + input +
                        "\nNow you have " + (count + 1) + " tasks in the list.\n" + div);
                ToDos todo = new ToDos(input);
                tasks.addTask(todo);
                todo.addToFile();
                count++;
            } else if (input.toLowerCase().startsWith("deadline ")) {
                String[] parts = input.split("/");
                String due = parts[1];
                input = parts[0].substring(8);
                System.out.println("Got it. I've  added this task: \n[D] [ ]" + input + " (" + due + ")" +
                        "\nNow you have " + (count + 1) + " tasks in the list.\n" + div);
                Deadline deadline = new Deadline(input, due);
                tasks.addTask(deadline);
                deadline.addToFile();
                count++;
            } else if (input.toLowerCase().startsWith("event ")) {
                String[] parts = input.split("/");
                String start = parts[1];
                String end = parts[2];
                input = parts[0].substring(5);
                System.out.println("Got it. I've  added this task: \n[E] [ ]" + input + " (from: " + start + " to: " + end + ")" +
                        "\nNow you have " + (count + 1) + " tasks in the list.\n" + div);
                Events event = new Events(input, start, end);
                tasks.addTask(event);
                event.addToFile();
                count++;
            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(\n" + div);
                input = scanner.nextLine();
                continue;
            }

            input = scanner.nextLine();
        }
        this.showGoodByeMessage();
    }
}
