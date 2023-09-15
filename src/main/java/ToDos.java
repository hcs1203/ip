import java.io.FileWriter;
import java.io.IOException;
class ToDos extends Task {
    public ToDos(String name) {
        super(name);
    }

    @Override
    public String printTask() {
        return (isDone) ? "[T] [X] " + name + "\n" : "[T] [ ] " + name + "\n";
    }

    @Override
    public void addToFile() {
        try {
            FileWriter Writer
                    = new FileWriter("data/data.txt", true);
            Writer.write("T | 0 | " + name + "\n");
            Writer.close();
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }
}