package Duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Events extends Task {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    public Events(String name, String startDateTime, String endDateTime) {
        super(name);
        this.startDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.endDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public String printTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        String formattedStartDateTime = startDateTime.format(formatter);
        String formattedEndDateTime = endDateTime.format(formatter);

        return (isDone) ? "[E] [X] " + name + " (" + formattedStartDateTime + " - " + formattedEndDateTime + ")\n"
                : "[E] [ ] " + name + " (from " + formattedStartDateTime + " to " + formattedEndDateTime + ")\n";
    }

    @Override
    public void addToFile() {
        try {
            FileWriter writer = new FileWriter("data/data.txt", true);
            DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedStartDateTime = startDateTime.format(fileFormatter);
            String formattedEndDateTime = endDateTime.format(fileFormatter);

            writer.write("E | " + (isDone ? "X" : " ") + " | " + name + " | " + formattedStartDateTime + " | " + formattedEndDateTime + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }
}