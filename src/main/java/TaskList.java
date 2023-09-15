import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> tasks;
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    Task getTask(int taskNo) {
        return tasks.get(taskNo);
    }

    Task removeTask(int taskNo) {
        return tasks.remove(taskNo);
    }

     void addTask(Task task) {
        tasks.add(task);
    }
}
