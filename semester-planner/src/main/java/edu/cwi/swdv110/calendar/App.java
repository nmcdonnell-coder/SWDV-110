package edu.cwi.swdv110.calendar;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
public class App {
static final ZoneId CAMPUS = ZoneId.of("America/Boise");
public static void main(String[] args) {
Semester sem = new Semester("SWDV-110 – Spring 2027",
LocalDate.of(2027, 1, 11), LocalDate.of(2027, 5, 7),
Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
LocalTime.of(18, 0), LocalTime.of(20, 30));
sem.addHoliday(DateTools.nthWeekday(2027, Month.JANUARY, 3, DayOfWeek.MONDAY));
sem.addHoliday(DateTools.nthWeekday(2027, Month.FEBRUARY, 3, DayOfWeek.MONDAY));
sem.addBreak(LocalDate.of(2027, 3, 22), LocalDate.of(2027, 3, 26));
    System.out.println(sem.getName());
    System.out.println("=".repeat(sem.getName().length()));
    int previousWeek = -1;
        for (LocalDate meeting : sem.meetingDates()) {
            int week = sem.weekNumber(meeting);
        if (week != previousWeek) {
            System.out.printf("Week %d %s%n",
                week, DateTools.formatShort(meeting));
                previousWeek = week;
        } else {
            System.out.println(DateTools.formatShort(meeting));
            }
        }
    System.out.println();
    System.out.println("Days off: " + sem.getDaysOff().size());
    System.out.println("Meetings: " + sem.meetingDates().size());
    Duration totalTime = sem.totalClassTime();
    System.out.printf("Class time: %dh %dm%n",
        totalTime.toHours(), totalTime.toMinutesPart());
    Period semesterLength = sem.length();
    System.out.printf("Length: %d months, %d days%n",
        semesterLength.getMonths(), semesterLength.getDays());
    LocalDate firstMeeting = sem.meetingDates().get(0);
    ZonedDateTime firstClass = ZonedDateTime.of(
        firstMeeting, LocalTime.of(18, 0), CAMPUS);
    List<ZoneId> zones = List.of(
    ZoneId.of("America/Boise"),
    ZoneId.of("America/Los_Angeles"),
    ZoneId.of("America/New_York"),
    ZoneId.of("Asia/Tokyo"));
    System.out.println();
    System.out.println("First class, for online students:");
        for (ZoneId zone : zones) {
    System.out.println(zone + " "
        + DateTools.inZone(firstClass, zone));
        }
Scanner sc = new Scanner(System.in);
    while (true) {
        System.out.print("\nEnter a date (or 'quit'): ");
        String input = sc.nextLine().trim();
    if (input.equalsIgnoreCase("quit")) {
        break;
            }
    try {
        LocalDate enteredDate = DateTools.parseFlexible(input);
        LocalDate nextClass = sem.nextMeeting(enteredDate);
        if (nextClass == null) {
        System.out.println(
            "No more classes – enjoy your summer!");
        continue;
            }
        System.out.println("Next class: "
            + DateTools.formatShort(nextClass)
            + " (week " + sem.weekNumber(nextClass) + ")");
        System.out.println("Days until the semester ends: "
            + sem.daysLeft(enteredDate));
        LocalDate dueDate = DateTools.addSchoolDays(
            nextClass, 5, sem.getDaysOff());
        System.out.println("A lab assigned that day is due "
            + DateTools.formatShort(dueDate));
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage()
            + " – try 2027-03-15, 3/15/2027 or Mar 15, 2027");
            }
        }
sc.close();
    }
}
