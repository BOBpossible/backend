package cmc.bobpossible.store.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class OperationTimeVO {
    private DayOfWeek dayOfWeek;

    @Pattern(regexp = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$", message = "time 형식 오류 hh:mm:ss")
    private LocalTime startTime;
    @Pattern(regexp = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$", message = "time 형식 오류 hh:mm:ss")
    private LocalTime endTime;

    @Pattern(regexp = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$", message = "time 형식 오류 hh:mm:ss")
    private LocalTime breakStartTime;
    @Pattern(regexp = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$", message = "time 형식 오류 hh:mm:ss")
    private LocalTime breakEndTime;

    private boolean hasOperationTime;
    private boolean hasBreak;
}
