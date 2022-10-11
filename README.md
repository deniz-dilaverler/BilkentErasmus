# BilkentErasmus
This repository consists of CS-319 term project of CADDY (our team) at Bilkent University.
Following features are divided according to users.

## Student
- User enters an account
- Can view the schhols, their quotas, the language requirements, applicable semesters
- Can view the state of their application
- Whenever state changes or an aplication is accepted, send the student a mail to notfiy them.
- Can view preapproved courses
- Can cancel their application.



### When the Student is Accepted:
- Can cancel their application.
- When an approved student cancels their application, the highest student on the waiting list will be informed trough an email and will be given a set time period to decide wether to apply or not. If they apply, a mail is sent to the coordinator to apply.
- Fills the preapproval form on the system which the coordinator can view
- If there is another course that is not already in the system's preapproved courses list, the student can enter required info (Syllabus etc.) if the course is not present in the previously accepted courses list and wait for the coordinator's approval.
- The student will be notified if the student has less than 30 or more than 35 ECTS. 
- If preapproval form is denied, rewrite the preapproval form and send again.


- After returning from the program, if they chose a project course at the host uni, they will upload the final report of the course to the system.





## Coordinators
- Enters the schhols, their quotas, the language requirements, applicable semesters, 
- Before placementsi the system will check if there are anything wrong with students' applications (e.g. wrong semester). If there is an error, the system asks for the correction of application from student or cancel their application for that university. System also notifies the coordinators by adding these information to their to do list.
- Calls our system to get the rankings and the palcements assigned by the server
- When an accepted student cancels their application the system notifies the coordinators trough email and an event in the system. If the student on the waiting list approves the new position coordinator devides whether they will be accepted. If not, coordinate will choose to terminate the application process.



- To do list for upcoming events
- Events go to the coordinator that the University is assigned to

- Add/edit the preaproved courses.
- Preaproval forms go to the coordinator for confirmation. The form can also be exported as pdf. 
- Check preapproval form, decide whether to accept or deny. Write feedback if denied via the system and send the feedback to student.

- Coordinators will create a course transfer form.



## Exchange Coordinator (Yeldo)
- Approved students' information is sent to the secretary to contact with the host university.
- Enters the applicaation links of the host universities which will be directed to the corresponding student trough the system. System sends the corresponding student a notification mail.

## THE BOARD
- Signs the validity of required documents such as Course Transfer Form.

## International Students Office
- ISO sends the excel file containing the applicants, their GPA, Erasmus points, up to5 preferd schools.

## Admin (Team CADDY)
- Assigns schools to coordinators
- Responsible for the maintenance of the system.

## Additional Features:
- Mail feauture (with a mail button) for students and coordinators to send email to each other.
- Mail feature (with a mail button) for students to reach administrative coordinators.
