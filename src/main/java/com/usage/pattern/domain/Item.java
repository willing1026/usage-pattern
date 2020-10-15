package com.usage.pattern.domain;

import lombok.Builder;

import java.time.LocalDate;
import java.time.Period;

/**
 * 역할 - 상품에 대한 소비패턴 수치를 보여준다.
 */
@Builder
public class Item {
    private String category;
    private LocalDate useStartDay;
    private LocalDate useFinishDay;

    //사용일수 계산
    public int calculateDayCount() {
        LocalDate finishDay = useFinishDay == null ? LocalDate.now() : this.useFinishDay;

        Period period = useStartDay.until(finishDay);
        return period.getDays() + 1; // include endDay
    }
}
