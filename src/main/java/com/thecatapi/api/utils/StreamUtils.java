package com.thecatapi.api.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import static java.util.stream.Collector.of;

@UtilityClass
public class StreamUtils {

    public static <T> Collector<T, List<T>, T> randomItem() {
        return of(
                ArrayList::new,
                List::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                list -> list.isEmpty() ? null : list.get(RandomUtils.RANDOM.nextInt(list.size())));
    }
}
