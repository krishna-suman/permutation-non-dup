import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeTaken {

    public LocalTime getStartTime() {
        return LocalTime.now();
    }

    public LocalTime getEndTime() {
        return LocalTime.now();
    }

    public void getTimeElapsedInNano(LocalTime startTime, LocalTime endTime) {
        System.out.println("startTm:"+startTime+", endTm:"+endTime);
        Duration dt = Duration.between(startTime, endTime);
        System.out.println("elapsed time in Nano:" + dt.get(ChronoUnit.NANOS));
    }

}
