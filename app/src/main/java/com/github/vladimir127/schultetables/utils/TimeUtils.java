package com.github.vladimir127.schultetables.utils;

import java.util.Locale;

public class TimeUtils {
    /**
     * Преобразует время в миллисекундах в строку формата Минуты : Секунды : Сотые доли секунды
     * @param timeMillis Время в миллисекундах
     * @return Строка времени формата Минуты : Секунды : Сотые доли секунды
     */
    public static String millisecondsToTimeString(int timeMillis) {
        int minutes = timeMillis / 60000;
        int seconds = (timeMillis % 60000) / 1000;
        int centiseconds = (timeMillis / 10) % 100;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, centiseconds);
    }
}
