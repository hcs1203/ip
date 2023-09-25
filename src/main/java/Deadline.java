import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    LocalDate dueDate;

    public Deadline(String name, String due) {
        super(name);
        this.dueDate = LocalDate.parse(due, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String printTask() {
        String formattedDueDate = dueDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return (isDone) ? "[D] [X] " + name + " (" + formattedDueDate + ")\n"
                : "[D] [ ] " + name + " (" + formattedDueDate + ")\n";
    }

    @Override
    public void addToFile() {
        try {
            String formattedDueDate = dueDate.format(DateTimeFormatter.ISO_DATE);
            FileWriter Writer
                    = new FileWriter("data/data.txt", true);
            Writer.write("D | 0 | " + name + " | " + formattedDueDate + "\n");
            Writer.close();
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public String toSaveString() {
        return "D | " + (isDone ? "1" : "0") + " | " + this.name + " | " + this.dueDate;
    }

}