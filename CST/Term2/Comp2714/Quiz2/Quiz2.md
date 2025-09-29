
**Question 1:**
Using the Orders table, which of the following tuples is returned by the query below?

| orderID | orderDate | orderPrice | customer |
|---------|-----------|------------|----------|
| 1       | 11/3/2020 | 1000       | Bush     |
| 2       | 23/4/2020 | 1600       | Carter   |
| 3       | 12/8/2020 | 500        | Bush     |
| 4       | 15/1/2021 | 300        | Bush     |
| 5       | 23/3/2021 | 2000       | Adams    |
| 6       | 05/4/2021 | 100        | Carter   |

```sql
SELECT customer, SUM(orderPrice)
FROM Orders
WHERE (customer='Bush' OR customer='Adams') AND orderPrice>=500
GROUP BY customer
HAVING SUM(orderPrice)>=1500
ORDER BY SUM(orderPrice) ASC;
```

选项：
- A.  
  | customer | SUM(orderPrice) |
  |----------|------------------|
  | Adams    | 2000            |
  | Bush     | 1500            |
- B.  
  | customer | SUM(orderPrice) |
  |----------|------------------|
  | Bush     | 1500            |
  | Adams    | 2000            |
- C.  
  | customer | SUM(orderPrice) |
  |----------|------------------|
  | Adams    | 2000            |
  | Bush     | 1800            |
- D.  
  | customer | SUM(orderPrice) |
  |----------|------------------|
  | Bush     | 1800            |
  | Adams    | 2000            |

---

**Question 2:**
Which of the following answers would successfully delete only the second row of the Album table?


**Artists表**
| ArtistId | ArtistName | Desc                   |
|----------|------------|------------------------|
| 1        | AC/DC      | One of t...            |
| 2        | U2         | Another...             |
| 3        | Nelly      | When y...              |
| 4        | Lorde      | From N...              |

**Albums表**
| AlbumId | AlbumName   | ArtistId |
|---------|-------------|----------|
| 1       | NellyVille  | 3        |
| 2       | Black Ice   | 1        |
| 3       | Ballbreaker | 1        |
| 4       | October     | 2        |

**Ratings表**
| RatingId | AlbumId | Rating |
|----------|---------|--------|
| 1        | 2       | 5      |
| 2        | 1       | 3.5    |
| 3        | 4       | 3      |
| 4        | 3       | 4      |


选项：
- A. `DELETE FROM Album WHERE ArtistID = 1;`
- B. `ALTER TABLE Album DROP WHERE AlbumID = 2;`
- C. `DELETE FROM Album WHERE AlbumID = 2;`
- D. `ALTER TABLE Album DELETE ROW WHERE AlbumID(2);`

---

**Question 3:**
Which SQL Query will return the tuple containing Dave Smith's information from the below Employee database?

| ID | fName | lName    | Salary |
|----|-------|----------|--------|
| 1  | Dave  | Smith    | 50000  |
| 2  | Mike  | Michaels | 60000  |
| 3  | John  | Leonard  | 70000  |
| 4  | Edward| Smith    | 80000  |

选项：
- A.  
  ```sql
  SELECT * 
  FROM Employee 
  WHERE Employee.Salary > 50000
  ```
- B.  
  ```sql
  SELECT * 
  FROM Employee 
  WHERE Employee.Name = 'Dave Smith'
  ```
- C.  
  ```sql
  SELECT * 
  FROM Employee 
  WHERE Employee = 1
  ```
- D.  
  ```sql
  SELECT * 
  FROM Employee 
  WHERE Employee.fName = 'Dave' AND Employee.lName = 'Smith'
  ```

---

**Question 4:**
What is the correct SQL syntax to remove the constraint called "empMgrFK" from the table called "Employees"?

选项：
- A. `DROP CONSTRAINT Employees.empMgrFK;`
- B. `ALTER TABLE Employees UNLINK CONSTRAINT empMgrFK;`
- C. `UPDATE IN Employees DROP CONSTRAINT empMgrFK;`
- D. `ALTER TABLE Employees DROP CONSTRAINT empMgrFK CASCADE;`

---

**Question 5:**
**Question 5:** Read the following information and answer the question.

- Student 表: `[StudentID(PK), name, age, sex, major]`
- CourseGrade 表: `[CourseID, StudentID, year, CourseName, grade]`
- `CourseGrade.StudentID` references `Student.StudentID`
- Primary Key(`CourseGrade.CourseID, CourseGrade.StudentID, CourseGrade.year`)

Which SQL is right to find the student name who gets the lowest grade?

选项：
- A.  
  ```sql
  SELECT name 
  FROM Student, CourseGrade 
  WHERE grade = MIN(grade)
  ```
- B.  
  ```sql
  SELECT name 
  FROM Student, CourseGrade 
  WHERE CourseGrade.StudentID = Student.StudentID AND grade = MIN(grade)
  ```
- C.  
  ```sql
  SELECT name 
  FROM Student, CourseGrade 
  WHERE CourseGrade.StudentID = Student.StudentID AND grade = (SELECT MIN(grade) FROM CourseGrade)
  ```
- D. `None of above`

---

**Question 6:**
Which one of the following cannot be done with a SQL query?

选项：
- A. Querying the database
- B. Creating a table
- C. Taking user input and storing it
- D. Storing data in a table

---

**Question 7:**
If we need to delete a table "X" in SQL, how do we write?

选项：
- A. `DELETE TABLE X`
- B. `DROP TABLE X`
- C. `DELETE X`
- D. `DROP X`

---

**Question 8:**
Which query will return the number of employees in the EMPLOYEE table?

选项：
- A. `SELECT SUM(*) FROM EMPLOYEE`
- B. `SELECT COUNT(*) FROM EMPLOYEE`
- C. `SELECT DISTINCT Employee_DOB FROM EMPLOYEE`
- D. `SELECT * FROM EMPLOYEE WHERE Name='UNIQUE'`

---

**Question 9:**
What is the result of the SQL query that is based on the following relation?

- Athlete表：\[id(PK), aName, sport, age]

```sql
SELECT DISTINCT A1.aName, A1.age
FROM Athlete A1, Athlete A2
WHERE A1.age < A2.age
```

选项：
- A. The name and age of all of the youngest athlete(s)
- B. The name and age of all athletes that are younger than the oldest athlete(s)
- C. The name and age of one of the oldest athlete(s)
- D. The name and age of all of the oldest athlete(s)
- E. The name and age of one of the youngest athlete(s)

---

**Question 10(填空):**
Database _________ which is the logical design of the database, and the database _______ which is a snapshot of the data in the database at a given instant in time.

选项：
- A. Instance, Schema
- B. Relation, Schema
- C. Relation, Domain
- D. Schema, Instance

---

**Question 11(多选题):**
A nested query is a query within a query. Select all true statements regarding nested queries.

选项：
- A. Superquery is another term for nested query.
- B. Attributes with the same name referenced in correlated queries must be aliased.
- C. The inner query depends on the outer query in a non-correlated query.
- D. A correlated query will typically have a longer runtime than a non-correlated query.

---

**Question 12:**
What kind of join is the following statement describing?

This join will return filling NULLs when joining two tables of tuples if there is no matching information based on the joining condition after the ON. All the information from the table directly following the FROM operator will be included in the result, whereas the table after the JOIN may have NULLs and may have some tuples not present in the result.

选项：
- A. Equi Join
- B. Full Outer Join
- C. Left Join
- D. Right Join

---

**Question 13:**
Which SQL query below will only return user's name that ends with "el"?

选项：
- A. 
  ```sql
  SELECT name 
  FROM Student 
  WHERE name = "el"
  ```
- B.
  ```sql
  SELECT name 
  FROM Student 
  WHERE name LIKE "%eL_"
  ```
- C.
  ```sql
  SELECT name 
  FROM Student 
  WHERE name LIKE "%el"
  ```
- D.
  ```sql
  SELECT name 
  FROM Student 
  WHERE name LIKE "_eL_"
  ```

---

**Question 14:**
Which statement(s) is correct?

选项：
- A. A non-correlated subquery is executed multiple times.
- B. A correlated subquery is executed only once.
- C. A non-correlated subquery depends upon the outer query.
- D. A correlated subquery needs examination in the inner subqueries for each row of the outer query.

---

**Question 15:**
Student\[name]

Tutor\[name]

Which query below will return the name of all students that are NOT also tutors?

选项：
- A.
  ```sql
  SELECT name FROM Student 
  UNION 
  SELECT name FROM Tutor
  ```
- B.
  ```sql
  SELECT name FROM Student 
  UNION ALL 
  SELECT name FROM Tutor
  ```
- C.
  ```sql
  SELECT name FROM Student 
  INTERSECT 
  SELECT name FROM Tutor
  ```
- D.
  ```sql
  SELECT name FROM Student 
  EXCEPT 
  SELECT name FROM Tutor
  ```
