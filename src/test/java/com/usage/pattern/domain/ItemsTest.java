package com.usage.pattern.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemsTest {

    @DisplayName("1용량 단위당 사용일수 - 정상계산 Case")
    @Test
    void calculateAverageUsageDay() {
        //given
        Item item = Item.builder()
                .category("tissue")
                .amount(new BigDecimal(10))
                .useStartDay(LocalDate.now().minusDays(10))
                .useFinishDay(LocalDate.now().minusDays(6))
                .build();

        Item item2 = Item.builder()
                .category("tissue")
                .amount(new BigDecimal(30))
                .useStartDay(LocalDate.now().minusDays(30))
                .useFinishDay(LocalDate.now().minusDays(1))
                .build();

        Items items = Items.builder()
                .items(Arrays.asList(item, item2))
                .build();

        //when
        Map<String, BigDecimal> actual = items.calculateAverageGroupByCategory();

        //then
        Map<String, BigDecimal> expected = new HashMap<>();
        expected.put("tissue", new BigDecimal("0.75"));

        assertEquals(expected, actual);
    }

    @DisplayName("1용량 단위당 사용일수 - 계산대상이 없는 경우")
    @Test
    void calculateAverageUsageDayWrongCase() {
        Items items = Items.builder().build();

        //when
        Map<String, BigDecimal> actual = items.calculateAverageGroupByCategory();

        //then
        Map<String, BigDecimal> expected = new HashMap<>();
        assertEquals(expected, actual);
    }
}