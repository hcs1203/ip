package Duke;

import java.io.FileWriter;
import java.io.IOException;

class Task {
    protected String name;
    protected boolean isDone = false;
    public Task(String name) {
        this.name = name;
    }
    public void mark(){
        isDone = true;
    }
    public void unmark(){
        isDone = false;
    }

    public String printTask() {
        return (isDone) ? "[X] " + name + "\n" : "[ ] " + name + "\n";
    }

    public void addToFile() {
        try {
            FileWriter Writer
                    = new FileWriter("data/data.txt", true);
            Writer.write(
                    "  | 0 | " + name);
            Writer.close();
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public String toSaveString() {
        return (isDone ? "1" : "0") + " | " + this.name;
    }

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + this.name;
    }
}