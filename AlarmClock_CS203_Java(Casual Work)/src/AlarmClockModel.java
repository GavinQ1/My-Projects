import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlarmClockModel {
    private LocalDateTime alarm;
    private boolean locked, changeHour;
    private final DateTimeFormatter CLOCK_HOUR_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public AlarmClockModel() {
        this.alarm = null;
        this.locked = false;
        this.changeHour = false;
    }
    
    public void setOff() {
        this.alarm = null;
    }
    
    public boolean isTime() {
        if (alarm == null)
            return false;
        LocalDateTime now = LocalDateTime.now();
        int nhr = now.getHour(), nmin = now.getMinute();
        int ahr = alarm.getHour(), amin = alarm.getMinute();
        
        return nhr == ahr && nmin == amin;
    }
    
    public String getCurrentClockTime() {
        return "      " + CLOCK_HOUR_FORMATTER.format(LocalDateTime.now());
    }
    
    public String getAlarmClockTime() {
        if (this.alarm == null)
            return "   No alarm set!";
        return "      " + CLOCK_HOUR_FORMATTER.format(this.alarm);
    }
    
    public boolean isLocked() {
        return this.locked;
    }
    
    public void unlock() {
        this.locked = false;
    }
    
    public void lock() {
        this.locked = true;
    }
    
    public void changeHour() {
        this.changeHour = true;
    }
    
    public void changeMinute() {
        this.changeHour = false;
    }
    
    public void setAlarm(int delta, boolean up) {
        if (this.alarm == null) 
            this.alarm = LocalDateTime.now();
        this.clearSeconds();
        if (this.changeHour) {
            if (up) this.hourUp(delta);
            else this.hourDown(delta);
        } else {
            if (up) this.minuteUp(delta);
            else this.minuteDown(delta);
        }
    }
    
    private void clearSeconds() {
        this.alarm =  this.alarm.minusSeconds(this.alarm.getSecond());
    }
    
    private void hourUp(int hour) {
        this.alarm = this.alarm.plusHours(hour);
    }
    
    private void hourDown(int hour) {
        this.alarm = this.alarm.minusHours(hour);
    }
    
    private void minuteUp(int hour) {
        this.alarm = this.alarm.plusMinutes(hour);
    }
    
    private void minuteDown(int hour) {
        this.alarm = this.alarm.minusMinutes(hour);
    }
    
    public static void main(String[] args) {
        AlarmClockModel clock = new AlarmClockModel();
        System.out.println(clock);
    }
}
