-- Table: `Employee`
CREATE TABLE IF NOT EXISTS `Employee` (
`id` int,
`firstName` varchar(100) NOT NULL,
`lastName` varchar(100) NOT NULL,
`role` varchar(20) NOT NULL,
UNIQUE (`id`)
);

-- Table: `Project`
CREATE TABLE IF NOT EXISTS `Project` (
`name` varchar(100) NOT NULL,
`description` text NOT NULL,
 	`funding` int NOT NULL,
 	`projectLeader` varchar(100) NOT NULL,
 	PRIMARY KEY (`name`)
);

ALTER TABLE `Project` 
ADD CONSTRAINT `Project_ProjectLeaderFK` FOREIGN KEY (`projectLeader`) REFERENCES `Employee` (`id`) ON UPDATE CASCADE;

-- Table: `TimeLog`
CREATE TABLE IF NOT EXISTS TimeLog (
  employeeID int NOT NULL,
  projectName varchar(100) NOT NULL,
  date date NOT NULL,
  hoursWorked decimal(5,2) NOT NULL,
  approved boolean NOT NULL,
  PRIMARY KEY (employeeID, projectName, date)
);

CREATE TABLE IF NOT EXISTS ProjectExpenditureTimeline (
  projectName varchar(100) NOT NULL,
  totalApprovedHours decimal(10,2) NOT NULL,
  PRIMARY KEY (projectName)
);

ALTER TABLE TimeLog
ADD CONSTRAINT TimeLog_employeeID_fk FOREIGN KEY (employeeID) REFERENCES Employee(id);

ALTER TABLE TimeLog
ADD CONSTRAINT TimeLog_projectName_fk FOREIGN KEY (projectName) REFERENCES Project(name);

ALTER TABLE ProjectExpenditureTimeline
ADD CONSTRAINT PET_projectName_fk FOREIGN KEY (projectName) REFERENCES Project(name);

ALTER TABLE Employee
ADD COLUMN middleName VARCHAR(100);

ALTER TABLE Employee
MODIFY COLUMN role TEXT NOT NULL;

ALTER TABLE Employee
ADD CONSTRAINT unique_first_last_name
UNIQUE (firstName, lastName);

ALTER TABLE Employee
ADD COLUMN mainProject VARCHAR(100) NOT NULL DEFAULT 'Website Setup';

ALTER TABLE Employee
ADD CONSTRAINT fk_mainProject
FOREIGN KEY (mainProject) REFERENCES Project(name);

INSERT INTO TimeLog (employeeID, projectName, date, hoursWorked, approved)
VALUES (2019, 'Website Setup', '2020-05-03', 4.0, TRUE);

--Setp 1
DELETE FROM TimeLog WHERE employeeID = 2048;
--Step 2
DELETE FROM Employee WHERE id = 2048;

INSERT INTO ProjectExpenditureTimeline (projectName, totalApprovedHours)
SELECT projectName, SUM(hoursWorked) AS totalApprovedHours
FROM TimeLog
WHERE approved = TRUE
GROUP BY projectName;

--Step 1
INSERT INTO Employee (id, firstName, lastName, role, mainProject)
VALUES (2930, 'Charlie', 'Johnson', 'Manager', 'Website Setup');
--Step 2
INSERT INTO Project (name, description, funding, projectLeader)
VALUES (
  'New Zealand expansion analysis',
  'Look into the economic and logistical feasibility of setting up a branch of Dirt Road Driving in New Zealand.',
  8000.00,
  2930
);
--Step 3
UPDATE Employee
SET mainProject = 'New Zealand expansion analysis'
WHERE id = 2930;
