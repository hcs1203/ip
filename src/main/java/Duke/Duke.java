package Duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Duke class represents a simple task management program.
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Duke object.
     *
     * @param filePath The file path for storing task data.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList();
    }

    /**
     * The main method of the Duke program.
     *
     */
    public void run() {
        String div = "____________________________________________________________\n";

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        int count = 0;
        File file = new File("data/data.txt");
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String input = fileReader.nextLine();
                String[] parts = input.split("\\|");
                if (parts[0].trim().equalsIgnoreCase("t")) {
                    Task task = new ToDos(parts[2]);
                    tasks.add(task);
                    count++;
                    if (parts[1].trim().equalsIgnoreCase("1")) {
                        task.mark();
                    }
                } else if (parts[0].trim().equalsIgnoreCase("d")) {
                    String due = parts[3].trim();
                    Task task = new Deadline(parts[2], due);
                    tasks.add(task);
                    count++;
                    if (parts[1].trim().equalsIgnoreCase("1")) {
                        task.mark();
                    }
                } else if (parts[0].trim().equalsIgnoreCase("e")) {
                    String start = parts[3].trim();
                    String end = parts[4].trim();
                    Task task = new Events(parts[2], start, end);
                    tasks.add(task);
                    count++;
                    if (parts[1].trim().equalsIgnoreCase("1")) {
                        task.mark();
                    }
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
        System.out.println(div + "Hello! I'm CarrotCake\nWhat can I do for you?\n" + div);
        String input = scanner.nextLine();
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
                    System.out.print((i + 1) + ". " + tasks.get(i).printTask());
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
                tasks.get(taskNumber).mark();
                System.out.println("Nice! I've marked this task as done:\n [X] " + tasks.get(taskNumber).name + "\n" + div);
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
                tasks.get(taskNumber).unmark();
                System.out.println("OK, I've marked this task as not done yet:\n [ ] "
                        + tasks.get(taskNumber).name + "\n" + div);
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
                System.out.println("Noted. I've removed this task:\n" + tasks.get(taskNumber).printTask() +
                        "\nNow you have " + count + " tasks in the list.\n" + div);
                tasks.remove(taskNumber);
                input = scanner.nextLine();
                continue;
            }
            if (input.toLowerCase().startsWith("todo ")) {
                input = input.substring(4);
                System.out.println("Got it. I've  added this task: \n[T] [ ]" + input +
                        "\nNow you have " + (count + 1) + " tasks in the list.\n" + div);
                ToDos todo = new ToDos(input);
                tasks.add(todo);
                todo.addToFile();
                count++;
            } else if (input.toLowerCase().startsWith("deadline ")) {
                String[] parts = input.split("/");
                String due = parts[1];
                input = parts[0].substring(8);
                System.out.println("Got it. I've  added this task: \n[D] [ ]" + input + " (" + due + ")" +
                        "\nNow you have " + (count + 1) + " tasks in the list.\n" + div);
                Deadline deadline = new Deadline(input, due);
                tasks.add(deadline);
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
                tasks.add(event);
                event.addToFile();
                count++;
            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(\n" + div);
                input = scanner.nextLine();
                continue;
            }
            input = scanner.nextLine();
        }
        System.out.println(div + "Bye. Hope to see you again soon!\n" + div);
    }

    /**
     * The main method to start the Duke program.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }
}
