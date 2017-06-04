package Model;

import java.time.LocalDateTime;

public class Check extends Identified {
    public Long ActivityId;
    public LocalDateTime Date;

    public Check(Long activityId) {
        GenerateId();
        ActivityId = activityId;
        Date = LocalDateTime.now();
    }
}
