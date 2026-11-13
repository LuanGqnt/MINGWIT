public class Medication extends Item {
    private String dosage;
    private String schedule;
    private int durationDays;
    private int dosesGiven;
    private boolean completed;

    public Medication(String name, int value, String description, String dosage, String schedule, int durationDays) {
        super(name, value, description);
        setDosage(dosage);
        setSchedule(schedule);
        setDurationDays(durationDays);
        this.dosesGiven = 0;
        this.completed = false;
    }

    @Override
    public void use(Pet pet) {
        if (completed) {
            System.out.println(this.getName() + " treatment is already completed.");
            return;
        }

        pet.setHealth(pet.getHealth() + 25); 
        recordDose();

        System.out.println(pet.getName() + " was given " + this.getName() + " (" + dosage + ").");
        
        if (dosesGiven >= durationDays) {
            markCompleted();
            System.out.println(this.getName() + " treatment course is now completed!");
        }
    }

    public void recordDose() {
        if (completed) {
            throw new IllegalStateException(this.getName() + " treatment is already completed.");
        }
        dosesGiven++;
    }

    public void markCompleted() {
        completed = true;
    }

    public String getDosage() { return dosage; }
    public String getSchedule() { return schedule; }
    public int getDurationDays() { return durationDays; }
    public int getDosesGiven() { return dosesGiven; }
    public boolean isCompleted() { return completed; }

    public void setDosage(String dosage) {
        this.dosage = dosage.trim();
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule.trim();
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    @Override
    public String toString() {
        return getName() + " (" + dosage + ", " + schedule + ", " + durationDays + " days)"
                + (completed ? " [Completed]" : " [Doses: " + dosesGiven + "/" + durationDays + "]");
    }
}