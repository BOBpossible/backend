package cmc.bobpossible.store.dto;

import cmc.bobpossible.operation_time.OperationTime;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class OperationTimeRes {

    private Long operationTimeId;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    private boolean hasOperationTime;
    private boolean hasBreak;

    public OperationTimeRes(OperationTime operationTime) {
        this.operationTimeId = operationTime.getId();
        this.dayOfWeek = operationTime.getDayOfWeek();
        this.startTime = operationTime.getStartTime();
        this.endTime = operationTime.getEndTime();
        this.breakStartTime = operationTime.getBreakStartTime();
        this.breakEndTime = operationTime.getBreakEndTime();
        this.hasOperationTime = operationTime.isHasOperationTime();
        this.hasBreak = operationTime.isHasBreak();
    }
}
