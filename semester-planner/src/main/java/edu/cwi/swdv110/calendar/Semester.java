package edu.cwi.swdv110.calendar;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
public class Semester {
    private final String name;
    private final LocalDate start;
    private final LocalDate end;
    private final Set<DayOfWeek> meetingDays;
    private final LocalTime classStart;
    private final LocalTime classEnd;
    private final Set<LocalDate> daysOff = new TreeSet<>();
    public Semester(
        String name,
        LocalDate start,
        LocalDate end,
        Set<DayOfWeek> meetingDays,
        LocalTime classStart,
        LocalTime classEnd) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException(
                "Semester end cannot be before its start.");
        }
        if (!classEnd.isAfter(classStart)) {
            throw new IllegalArgumentException(
                "Class end must be after class start.");
        }
        this.name = name;
        this.start = start;
        this.end = end;
        this.meetingDays = Set.copyOf(meetingDays);
        this.classStart = classStart;
        this.classEnd = classEnd;
    }
    public void addHoliday(LocalDate date) {
        daysOff.add(date);
    }
    public void addBreak(LocalDate first, LocalDate last) {
        for (LocalDate date = first;
            !date.isAfter(last);
            date = date.plusDays(1)) {
        daysOff.add(date);
        }
    }
    public String getName() {
        return name;
    }
    public Set<LocalDate> getDaysOff() {
        return Set.copyOf(daysOff);
    }
    public java.util.List<LocalDate> meetingDates() {
        java.util.List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = start;
            !date.isAfter(end);
            date = date.plusDays(1)) {
        if (meetingDays.contains(date.getDayOfWeek())
            && !daysOff.contains(date)) {
            dates.add(date);
            }
        }
        return dates;
    }
    public int weekNumber(LocalDate date) {
        LocalDate firstMonday =
            start.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate dateMonday =
            date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return (int) ChronoUnit.WEEKS.between(firstMonday, dateMonday) + 1;
    }
    public Duration classLength() {
        return Duration.between(classStart, classEnd);
    }
    public Duration totalClassTime() {
        return classLength().multipliedBy(meetingDates().size());
    }
    public LocalDate nextMeeting(LocalDate from) {
        for (LocalDate date : meetingDates()) {
            if (!date.isBefore(from)) {
                return date;
            }
        }
        return null;
    }
    public Period length() {
        return Period.between(start, end);
    }
    public long daysLeft(LocalDate from) {
        if (from.isAfter(end)) {
            return 0;
        }
        return ChronoUnit.DAYS.between(from, end);
    }
}
