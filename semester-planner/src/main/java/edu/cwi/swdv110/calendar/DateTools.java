package edu.cwi.swdv110.calendar;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.Set;
public class DateTools {
    private static final DateTimeFormatter SHORT_FORMAT =
        DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US);
    private static final DateTimeFormatter MEETING_FORMAT =
        DateTimeFormatter.ofPattern("EEE, MMM d 'at' h:mm a z", Locale.US);
    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
        DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US)
            .withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("M/d/uuuu", Locale.US)
            .withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.US)
            .withResolverStyle(ResolverStyle.STRICT)
    );
    public static LocalDate nthWeekday(
        int year, Month month, int n, DayOfWeek day) {
        return LocalDate.of(year, month, 1)
            .with(TemporalAdjusters.dayOfWeekInMonth(n, day));
    }
    public static LocalDate lastWeekday(
        int year, Month month, DayOfWeek day) {
        return LocalDate.of(year, month, 1)
            .with(TemporalAdjusters.lastInMonth(day));
    }
    public static String formatShort(LocalDate date) {
        return date.format(SHORT_FORMAT);
    }
    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
            || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
    public static LocalDate parseFlexible(String text) {
        String trimmed = text.trim();

        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDate.parse(trimmed, formatter);
            } catch (DateTimeParseException e) {
            }
        }
        throw new IllegalArgumentException("Unrecognized date: " + text);
    }
    public static LocalDate addSchoolDays(
            LocalDate start, int days, Set<LocalDate> holidays) {
        LocalDate date = start;
        int added = 0;
        while (added < days) {
            date = date.plusDays(1);

            if (!isWeekend(date) && !holidays.contains(date)) {
                added++;
            }
        }
        return date;
    }
    public static int latePenaltyPercent(
            LocalDateTime due, LocalDateTime submitted) {
        if (!submitted.isAfter(due)) {
            return 0;
        }
        long minutesLate = Duration.between(due, submitted).toMinutes();
        long daysLate = (minutesLate + 1439) / 1440;

        return (int) Math.min(daysLate * 10, 50);
    }
    public static String inZone(ZonedDateTime when, ZoneId zone) {
        return when.withZoneSameInstant(zone).format(MEETING_FORMAT);
    }
}
