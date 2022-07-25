package cmc.bobpossible.store.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class OperationTimeVO {
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    private boolean hasOperationTime;
    private boolean hasBreak;
}
