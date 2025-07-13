📌 Project Overview
This project is a desktop-based Patient Record Management System developed using Java Swing for the graphical user interface and PostgreSQL as the backend database. It simulates a basic hospital record system where authenticated users can manage patient information efficiently.

🔐 User Authentication
A login form prompts the user to enter their credentials.

The system authenticates against the loginfield table in the PostgreSQL database.

Only valid username-password pairs are granted access to the main system.

📋 Core Features
1. Login System
User credentials are securely checked from the loginfield table.

Invalid login attempts are blocked with appropriate messages.

2. View Patient Records
After successful login, a table displays:

ID

Name

Date of Birth (DOB)

All data is dynamically fetched from the patient_details table in PostgreSQL.

3. Add New Patient
A separate form allows input of:

Patient ID

Name

DOB (Year, Month, Day)

Diagnosis

Medication

Record Date

Upon submission, the data is inserted into the database and the main table refreshes.

4. Clear All Patients
This option deletes all records from the patient_details table.

Proper confirmation dialogs can be integrated to avoid accidental deletion.

5. View Patient Details
Selecting a patient entry opens a detailed view showing:

Diagnosis

Medication

Record Date

This supports informed medical record review and tracking.

🗃️ Database Schema (PostgreSQL)
Table: loginfield
Column	Type
username	VARCHAR
password	VARCHAR

Table: patient_details
Column	Type
id	INTEGER
name	VARCHAR
dob	DATE
diagnosis	TEXT
medication	TEXT
recorddate	DATE

🛠️ Technologies Used
Java – Core language

Swing – GUI framework

JDBC – Database connectivity

PostgreSQL – Relational database

GridBagLayout – For structured UI layout

✅ Future Improvements (Suggestions)
Encrypt passwords in the database using hashing (e.g., SHA-256).

Add user roles (admin vs. viewer).

Implement search and filter functionality in the patient table.

Use a date picker component (e.g., JDatePicker) for better UX.

Package the application into an executable .jar or .exe.

Add pagination if patient count grows large.
