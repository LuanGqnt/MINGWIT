public class Medication {
    private String name;
    private String dosage;
    private String schedule;
    private int durationDays;
    private int dosesGiven;
    private boolean completed;

    public Medication(String name, String dosage, String schedule, int durationDays) {
        setName(name);
        setDosage(dosage);
        setSchedule(schedule);
        setDurationDays(durationDays);
        this.dosesGiven = 0;
        this.completed = false;
    }

    // Validates and builds a new medication record
    public static Medication addMedication(String name, String dosage, String schedule, int durationDays) {
        return new Medication(name, dosage, schedule, durationDays);
    }

    // Records that one dose was given. fails if dose is already given
    public void recordDose() {
        if (completed) {
            throw new IllegalStateException(name + " treatment is already completed.");
        }
        dosesGiven++;
    }

    //treatment is completed
    public void markCompleted() {
        completed = true;
    }

    public void displayMedication() {
        System.out.println("Medication : " + name);
        System.out.println("Dosage     : " + dosage);
        System.out.println("Schedule   : " + schedule);
        System.out.println("Duration   : " + durationDays + " day(s)");
        System.out.println("Doses given: " + dosesGiven);
        System.out.println("Status     : " + (completed ? "Completed" : "Ongoing"));
    }

    // Getters
    public String getName() { return name; }
    public String getDosage() { return dosage; }
    public String getSchedule() { return schedule; }
    public int getDurationDays() { return durationDays; }
    public int getDosesGiven() { return dosesGiven; }
    public boolean isCompleted() { return completed; }

    // Setters 
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Medication name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setDosage(String dosage) {
        if (dosage == null || dosage.trim().isEmpty()) {
            throw new IllegalArgumentException("Dosage cannot be empty.");
        }
        this.dosage = dosage.trim();
    }

    public void setSchedule(String schedule) {
        if (schedule == null || schedule.trim().isEmpty()) {
            throw new IllegalArgumentException("Schedule cannot be empty.");
        }
        this.schedule = schedule.trim();
    }

    public void setDurationDays(int durationDays) {
        if (durationDays <= 0) {
            throw new IllegalArgumentException("Treatment duration must be at least 1 day.");
        }
        this.durationDays = durationDays;
    }

    @Override
    public String toString() {
        return name + " (" + dosage + ", " + schedule + ", " + durationDays + " days)"
                + (completed ? " [Completed]" : "");
    }
}