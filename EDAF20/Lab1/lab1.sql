-- a) What are the names (first name, last name) of all students?
SELECT firstName, lastName FROM students

-- b) Same as in a) but produce a sorted listing.  Sort first by last name and then by firstname
SELECT firstName, lastName FROM students ORDER BY lastName, firstName ASC

-- c) Which students were born in 1975?
SELECT * FROM students WHERE pNbr LIKE "75%"

-- d) What  are  the  names  of  the  female  students,  and  which  are  their  person  numbers?
SELECT * FROM students WHERE mod(substr(pNbr, 10, 1), 2)="0"

-- e) How many students are registered in the database?
SELECT COUNT(*) FROM students

-- f) Which courses are offered by the department of Mathematics?
SELECT courseName, courseCode FROM courses WHERE substr(courseCode, 1, 3)="FMA";

-- g) Which courses give more than five credits?
SELECT * FROM courses WHERE credits > 5

-- h) Which courses (course codes only)  have been taken by the student with person num-ber 790101-1234?
SELECT courseCode FROM takencourses WHERE pNbr="790101-1234"

-- i) What are the names of these courses, and how many credits do they give?
SELECT courseName, credits FROM courses, takencourses WHERE pNbr="790101-1234" AND courses.courseCode = takencourses.courseCode

-- j) How many credits has the student taken?
SELECT SUM(credits) FROM courses, takencourses WHERE pNbr="790101-1234" AND courses.courseCode = takencourses.courseCode

-- k) Which is the student's grade average on the courses that he has taken?
SELECT AVG(credits) FROM courses, takencourses WHERE pNbr="790101-1234" AND courses.courseCode = takencourses.courseCode

-- h2) Which courses (course codes only)  have been taken by the student Eva Alm?
SELECT takencourses.courseCode
FROM students
INNER JOIN takencourses 
ON students.pNbr = takencourses.pNbr
WHERE students.firstName = "Eva"
AND students.lastName = "Alm"

-- i2) What are the names of these courses, and how many credits do they give?
SELECT courses.courseName, courses.credits
FROM students
JOIN takencourses 
    ON students.pNbr = takencourses.pNbr
JOIN courses
    ON courses.courseCode = takencourses.courseCode
WHERE students.firstName = "Eva"
AND students.lastName = "Alm"

-- j2) How many credits has the studen taken?
SELECT SUM(credits)
FROM students
JOIN takencourses 
    ON students.pNbr = takencourses.pNbr
JOIN courses
    ON courses.courseCode = takencourses.courseCode
WHERE students.firstName = "Eva"
AND students.lastName = "Alm"

-- k2) Which is the student's grade average on the courses that she has taken?
SELECT AVG(credits)
FROM students
JOIN takencourses 
    ON students.pNbr = takencourses.pNbr
JOIN courses
    ON courses.courseCode = takencourses.courseCode
WHERE students.firstName = "Eva"
AND students.lastName = "Alm"

-- m) Which students have taken 0 credits?
SELECT students.firstName, students.lastName, students.pNbr
FROM students
LEFT JOIN takencourses
    ON students.pNbr = takencourses.pNbr
    WHERE takencourses.pNbr IS NULL

-- n) Which student has the highest grade average?
CREATE VIEW creditView AS
SELECT pNbr, AVG(grade) AS avg_grade
FROM takencourses
GROUP BY pNbr;

SELECT students.firstName, students.lastName, students.pNbr, MAX(creditView.avg_grade) AS Grade
FROM students
JOIN creditView
    ON students.pNbr = creditView.pNbr;

-- o) List the person number and total number of credits for all students.  Students withno credits should be included in the list!
CREATE VIEW noCredits AS 
SELECT students.pNbr, 0 AS totalCredit
FROM students
LEFT JOIN takencourses
    ON students.pNbr = takencourses.pNbr
    WHERE takencourses.pNbr IS NULL

CREATE VIEW sumCredits AS
SELECT pNbr, SUM(grade) AS totalCredit
FROM takencourses
GROUP BY pNbr;

SELECT * FROM noCredits
UNION ALL
SELECT * FROM sumCredits

-- p) Same question as in question o) but with names instead of person numbers
CREATE VIEW noCredits_names AS 
SELECT students.firstName, students.lastName, 0 AS totalCredit
FROM students
LEFT JOIN takencourses
    ON students.pNbr = takencourses.pNbr
    WHERE takencourses.pNbr IS NULL

SELECT students.firstName, students.lastName, sumCredits.totalCredit
FROM students
LEFT JOIN sumCredits
    ON students.pNbr = sumcredits.pNbr;

-- q) Is there more than one student with the same name?  If so, who are these students?
SELECT * FROM students
WHERE (firstName, lastName) IN (
    SELECT firstName, lastName
    FROM students
    GROUP BY firstName, lastName
    HAVING COUNT(*) > 1
)