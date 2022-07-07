package cmc.bobpossible.operation_time;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class OperationTime {

    @Id @GeneratedValue
    @Column(name = "operation_time_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    private boolean hasOperationTime;
    private boolean hasBreak;

    protected OperationTime() {
    }

    @Builder
    public OperationTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, LocalTime breakStartTime, LocalTime breakEndTime, boolean hasOperationTime, boolean hasBreak) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.hasOperationTime = hasOperationTime;
        this.hasBreak = hasBreak;
    }

    public void addStore(Store store) {
        this.store = store;
    }

    public void update(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, LocalTime breakStartTime, LocalTime breakEndTime, boolean hasOperationTime, boolean hasBreak) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.hasOperationTime = hasOperationTime;
        this.hasBreak = hasBreak;
    }
}
