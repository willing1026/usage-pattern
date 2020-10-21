package com.usage.pattern.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public class Items {
    private List<Item> items;

    //카테코리별 평균 사용일수
    public Map<String, BigDecimal> calculateAverageGroupByCategory() {
        if (isEmpty()) {
            return new HashMap<>();
        }

        Map<String, List<Item>> categories = items.stream()
                .collect(Collectors.groupingBy(Item::getCategory));

        Map<String, BigDecimal> result = new HashMap<>();
        for (String category : categories.keySet()) {
            List<Item> items = categories.get(category);
            result.put(category, calculateAverageUsageDay(items));
        }

        return result;
    }

    //평균사용일수
    private BigDecimal calculateAverageUsageDay(List<Item> items) {
        Optional<BigDecimal> average = items.stream()
                .map(Item::calculateUsageDayPerAmount)
                .reduce((i1, i2) -> {
                    i1 = i1.add(i2);
                    return i1;
                });

        if (average.isEmpty()) {
            return new BigDecimal(0);
        }

        return average.get().divide(new BigDecimal(items.size()), 2, RoundingMode.HALF_UP);
    }

    private boolean isEmpty() {
        return items == null || items.isEmpty();
    }
}
