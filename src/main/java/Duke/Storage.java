package Duke;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(TaskList tasks) {
        try {
            FileWriter writer = new FileWriter(this.filePath);

            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toSaveString() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks.");
            e.printStackTrace();
        }
    }

    public ArrayList<Task> loadTasks() {
        File file = new File(this.filePath);

        if (!file.exists()) {
            System.out.println("No save file detected. Attempting to create one...");
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();  // This creates the directory structure if it doesn't exist
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating a new save file.");
                e.printStackTrace();
            }
            System.out.println("Save file created successfully at " + this.filePath);
        }

        List<String> lines;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            System.out.println("An error occurred while loading tasks.");
            return null;
        }

        ArrayList<Task> tasks = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(" \\| ");
            boolean isMarked = parts[1].equals("1");
            Task task;

            switch (parts[0]) {
                case "T":
                    task = new ToDos(parts[2]);
                    if (isMarked) {
                        task.mark();
                    }
                    tasks.add(task);
                    break;
                case "D":
                    task = new Deadline(parts[2], parts[3]);
                    if (isMarked) {
                        task.mark();
                    }
                    tasks.add(task);
                    break;
                case "E":
                    task = new Events(parts[2], parts[3], parts[4]);
                    if (isMarked) {
                        task.mark();
                    }
                    tasks.add(task);
                    break;
            }
        }
        return tasks;
    }
}