import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class Storage {
    ArrayList<Task> tasks = new ArrayList<>();
    int count = 0;
    File file;
    public Storage(File file) {
        this.file = file;
    }

    public int getCount() {
        return this.count;
    }

    public ArrayList<Task> load() {
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
            return new ArrayList<>();
        }
        return tasks;
    }
}
