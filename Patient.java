public class Patient extends PatientBase {

    public Patient(String name, String time) {
        super(name, time);
    }

    @Override
    public int compareTo(PatientBase o) {
        /* Add your code here! */
        return time(this.getTime()) - time(o.getTime());
    }

    /* Add any extra functions below */
    public int time (String patientTime) {
        String[] parts = patientTime.split(":", 2); // can't use array?
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        int total = hour * 60 + minute;
        return total;
    }
}
