1. Strings are **immutable**. Name a place in your code where forgetting that would have caused a bug.
A place in my code where forgetting the immutability of strings would have caused a bug is in my 'normalizeEmail()' method. This is because if I forgot to assign/return the result of email.trim().toLowerCase(), a new string would've been returned instead of the original string being changed. 

2. Why does `split("|")` behave differently from `split("\\|")`?
The former behaves differently from the latter because the former is a special regular-expression operation that means 'OR' and can split strings into individual characters when the expression involves an empty alternative. The latter results in '|' being escaped so it is perceived as quite literally a delimiter. 

3. Where did you choose `StringBuilder` instead of `+`, and why?
I chose 'StringBuilder' instead of '+' in formatPhone() and ContactReport.buildReport(). That way, I would be able to assign characters to sequences of text without needing to create a String object for every append operation. 

4. Paste the output of `mvn test` (the summary line is enough).
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------