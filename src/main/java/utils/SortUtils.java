package utils;

import java.util.Comparator;
import java.util.Objects;

public class SortUtils {

    /**
     * Кастомный компаратор для строк, повторяющий поведение сортировки в UI.
     * Логика сортировки:
     * 1. Пустые значения (null, "", пробелы) — всегда в начале
     * 2. Числа (только положительные, например "123") — перед остальными строками
     * 3. Все остальные значения — сортируются лексикографически (через compareTo)
     * ВАЖНО:
     * - Отрицательные числа (например "-100") считаются строками
     * - Спецсимволы и буквы сортируются стандартно (ASCII/Unicode)
     */
    public static final Comparator<String> customStringComparator = (a, b) -> {
        // если строки равны (включая null) — они равнозначны
        if (Objects.equals(a, b)) return 0;
        // пустые значения всегда идут первыми
        if (a == null || a.trim().isEmpty()) return -1;
        if (b == null || b.trim().isEmpty()) return 1;
        // положительные числа идут перед остальными значениями
        if (a.matches("\\d+") && !b.matches("\\d+")) return -1;
        if (!a.matches("\\d+") && b.matches("\\d+")) return 1;
        // для остальных случаев используем стандартное строковое сравнение
        return a.compareTo(b);
    };
}
