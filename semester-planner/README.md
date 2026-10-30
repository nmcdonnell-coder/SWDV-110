1. Why did Java replace `java.util.Date` and `Calendar` with `java.time`? Give two concrete problems the old API had.
Java replaced 'java.util.Date' and 'Calendar' with 'java.time' because the new API is simply easier to work with. Two problems the old API had involves mixing date and time information together, in addition to some of its older methods treating months as zero-based.

2. When do you use `Period`, and when `Duration`? Use an example from this project.
I use 'Period' when measuring time in years/months/days, and I use 'Duration' when measuring hours/minutes/seconds. I used the former to calculate the length of the semester (3 months and 26 days), and the latter to calculate the total class time (each class being 2.5 hours equals 75 hours total over the course of all 30 meetings).

3. In the `daylightSavingTime` test, Boise and Phoenix show the *same* clock time in January but are an hour apart in April. Explain why.
Boise and Phoenix show the same clock time in January because they both use Mountain Standard Time (UTC-7). They are an hour apart in April because Boise observes daylight saving time and goes forward one hour to become Mountain Daylight Time (UTC-6). Phoenix on the other hand doesn't observe daylight saving time and remains on Mountain Standard Time. This results in Boise and Phoenix being an hour apart in April. 

4. Your program uses `LocalDate` for class dates but `ZonedDateTime` for the online-student times. Why not use `ZonedDateTime` everywhere?
'LocalDate' was used for class dates because the semester schedule only needed to know the calendar date. 'ZonedDateTime' was used for displaying the time online students had classes regardless of their location. I chose not to use 'ZonedDateTime' everywhere because it would incorporate time zone information that wasn't needed into calculations that would only require a calendar date. 