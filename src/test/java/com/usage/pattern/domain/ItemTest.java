package com.usage.pattern.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @CsvSource(value = {"1333,190.43", "2100,300"})
    @ParameterizedTest
    void calculatePricePerDay(String price, String expect) {
        //given
        Item item = Item.builder()
                .category("tissue")
                .useStartDay(LocalDate.now().minusDays(6))
                .price(new BigDecimal(price))
                .build();

        //when
        BigDecimal actual = item.calculatePricePerDay();

        //then
        BigDecimal expected = new BigDecimal(expect).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, actual);
    }

    @CsvSource(value = {"1333,190.43", "2100,300"})
    @ParameterizedTest
    void calculateAmountPerDay(String quantity, String expect) {
        //given
        Item item = Item.builder()
                .category("tissue")
                .useStartDay(LocalDate.now().minusDays(6))
                .amount(new BigDecimal(quantity))
                .build();

        //when
        BigDecimal actual = item.calculateAmountPerDay();

        //then
        BigDecimal expected = new BigDecimal(expect).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, actual);
    }
}