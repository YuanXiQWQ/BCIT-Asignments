### Requirements
In this assignment, you are required to write a program that reads the content of a file and writes a sorted output to a file. The number of lines in the file is not known (it could be thousands of lines). The format of each line is:

```
>> cat input.txt  
Mary Jackson Feb-2-1990 4.0 I 60  
Jack He Feb-3-1990 2.45 D  
Mike Johnson Sep-2-1980 3.125 D  
Jane Zhang Mar-2-1970 3.8 I 120  
```

You must use a merge sort to implement the sorting algorithm. Below are the sorting criteria where 1 is the first criteria to use and use the next criteria if the current criteria result in a tie.

1. Year of birth  
2. Month of birth  
3. Day of birth  
4. Last name (alphabetical order)  
5. First name (alphabetical order)  
6. GPA  
7. TOEFL (no TOEFL score is provided, then domestic students take precedence)  
8. Domestic > International  

The birthday has the month written in a string. Only the following abbreviation will be used for input and must be used for the output.  

```
Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec  
```

It must be in ascending order. For example, using the sorting criteria 1, someone born earlier must be outputted first. All sorting criteria must follow this ascending order. Below are additional specifications for this assignment.

1. An international student has an integer TOEFL score ranging from 0-120.  
2. DomesticStudent struct does not have the TOEFL field.  
3. I stands for an international student and D stands for a domestic student in the status column.  
4. If the format of a student does not conform to the specified format, your program must write the appropriate message to an output file and exit (“Error: XXX”). Replace XXX with an appropriate message.  
5. The first argument is the input file, the second argument is the output file name, and the third argument is the option (specification 6). The run command will be:

```
./<name of executable> <input file> <output file> <option>  
```

6. The option field in the command line is an integer (1, 2, or 3). Here are descriptions of options:
   - Option 1 only saves the sorted output of domestic students (no international students in the output file).  
   - Option 2 only saves the sorted output of international students (no domestic students in the output file).  
   - Option 3 only saves the sorted output of all students.  
7. You must handle corner cases including input format errors with the output message as in specification 4.  
8. Assume that everyone has a first name and last name. No middle name. All names are case insensitive, but the output must have the same capitalization as the input.  
9. GPA ranges from 0.0 to 4.3 and assume that the input only has up to 3 decimal places.  
10. Birthday must be displayed with a three-letter abbreviation as shown above with the first letter being the capital.  
11. Year of birth will range from 1950 to 2010.  

---

### Restrictions
- For any reason, if your code does not compile/run, it will result in 0.  
- If you use any standard library functions other than `stdio.h`, `stdlib.h`, `string.You` must consult with me or your dedicated lab instructor before using it.  
- Every line must end with `\n` including the last line in the output with no extra trailing spaces.  

---

### Grading Criteria:
1. **Correctness (60 points)**  
   - (30 points) Correctly implements merge sort according to the specified sorting criteria.  
   - (15 points) Properly handles the differentiation between domestic and international students, including TOEFL scores.  
   - (15 points) Generates accurate error messages for any improperly formatted input data.  

2. **Error Handling (10 points)**  
   - (5 points) Handles all specified error cases correctly.  
   - (5 points) Provides clear and accurate error messages.  

3. **Code Quality (10 points)**  
   - (5 points) Code is well-organized and follows best practices.  
   - (5 points) Code is properly commented and easy to understand.  

4. **Adherence to Restrictions (10 points)**  
   - (5 points) **Does not use the `strtok` function.**  
   - (5 points) Only uses allowed standard library functions (`stdio.h`, `stdlib.h`, `string.h`) unless otherwise approved.  

5. **Peer Evaluation (10 points)**  
   - (10 points) Peer evaluation of group members’ contributions and collaboration.  

*Note: Not required if you are submitting individually.*

---

### Submission Files
- You must submit only one `.c` file named: `a2.c` (case sensitive).  
- Take screenshots of the output and give them appropriate names.  
- Submit the peer evaluation form along with the other files. If working alone, not required.  
- Submission will be via Learning Hub.

---
