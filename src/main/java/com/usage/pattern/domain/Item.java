package com.usage.pattern.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private BigDecimal price;
    private BigDecimal amount;

    //사용일수 계산
    public int calculateDayCount() {
        LocalDate finishDay = useFinishDay == null ? LocalDate.now() : this.useFinishDay;

        Period period = useStartDay.until(finishDay);
        return period.getDays() + 1; // include endDay
    }

    //일별 사용금액 계산
    public BigDecimal calculatePricePerDay() {
        return divideWithUseDayCount(this.price);
    }

    //용량별 사용일수
    BigDecimal calculateUsageDayPerAmount() {
        return new BigDecimal(calculateDayCount()).divide(this.amount, 2, RoundingMode.HALF_UP);
    }

    //일별 사용수량 계산
    public BigDecimal calculateAmountPerDay() {
        return divideWithUseDayCount(this.amount);
    }

    private BigDecimal divideWithUseDayCount(BigDecimal target) {
        int useDayCount = calculateDayCount();
        return target.divide(new BigDecimal(useDayCount), 2, RoundingMode.HALF_UP);
    }

    String getCategory() {
        return category;
    }
}
