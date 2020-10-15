package com.usage.pattern.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @DisplayName("사용일수 계산 - 완료일자가 없는 경우")
    @Test
    void calculateUseDay() {
        Item tissue = Item.builder()
                .category("tissue")
                .useStartDay(LocalDate.now().minusDays(1))
                .build();

        //when
        int useDayCount = tissue.calculateDayCount();

        //then
        assertEquals(2, useDayCount);
    }

    @DisplayName("사용일수 계산 - 완료일자가 있는 경우")
    @Test
    void calculateUseDayWithFinishDay() {
        Item tissue = Item.builder()
                .category("tissue")
                .useStartDay(LocalDate.now().minusDays(5))
                .useFinishDay(LocalDate.now().minusDays(3))
                .build();

        //when
        int useDayCount = tissue.calculateDayCount();

        //then
        assertEquals(3, useDayCount);
    }
}