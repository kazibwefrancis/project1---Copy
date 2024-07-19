# Mathematics Challenge - ReadMe

## Introduction

The International Education Services is organizing a mathematics competition for primary school children all over the country. All pupils in registered primary schools are eligible to take part in the competition.

## System Overview

### School and Administrator Registration

- **Schools**: Uploaded by an administrator with details including name, district, school registration number, email of representative, and name of representative.
- **Validation**: Representatives are validated before being registered into the system.
- **Questions and Answers**: Uploaded by a registered administrator from Excel documents (`questions.xlsx` and `answers.xlsx`).

### Challenge Setup

- **Parameters**: Administrators set the opening and closing dates, duration, and number of questions for each challenge.
- **Random Selection**: Each challenge consists of 10 questions randomly selected from 100 uploaded questions.

### Participant Registration

- **Command Line Interface**: Participants register via CLI with the command:
  ```
  Register username firstname lastname emailAddress date_of_birth school_registration_number image_file.png
  ```
- **Challenge Viewing**: Participants can view active challenges using:
  ```
  ViewChallenges
  ```
- **Registration Validation**: If the school registration number matches, the record is added, and the school representative is notified via email to confirm the applicant.

### School Representative Confirmation

- **Confirmation Menu**: Representatives confirm applicants via CLI:
  ```
  viewApplicants
  ```
  To confirm or reject an applicant:
  ```
  confirm yes/no username
  ```

### Challenge Participation

- **Login**: Accepted participants log in and use:
  ```
  viewChallenges
  ```
  To attempt a challenge:
  ```
  attemptChallenge challengeNumber
  ```
- **Question Presentation**: Questions are presented one by one, showing the remaining questions and time.
- **Scoring**: Correct answers earn marks, wrong answers deduct 3 marks, and unsure answers (negative or "-") earn 0 marks.

### Reporting and Analytics

The system provides various reports and analytics including:
1. Most correctly answered questions
2. School Rankings
3. Performance of schools and participants over time
4. Percentage repetition of questions for participants across attempts
5. List of worst performing schools for a given challenge
6. List of best performing schools for all challenges
7. List of participants with incomplete challenges

## Implementation Details

### Project Components

1. **Client (Java)**: Provides menu items and reports.
2. **Server (Java)**: Manages file and database manipulations for the command line interface.
3. **Web Interface (PHP, Laravel Framework)**: Includes design elements like a system logo.


Thank you for participating in the Mathematics Challenge!
