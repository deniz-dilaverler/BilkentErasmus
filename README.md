# BilkentErasmus
This repository consists of CS-319 term project of [bizim grup] at Bilkent University.


Student ---------
-User enters account
-Can view the schhols, their quotas, the language requirements, applicable semesters
-Can view the state of their application
-Whenever state changes or an aplication is accepted, send the student a mail to notfiy them.
-Can view preapproved courses
-Can cancel their application.



When the Student is Accepted:
-Can cancel their application.
-When an approved student cancels their application, the highest student on the waiting list will be informed trough an email and will be given a set time period to decide wether to apply or not. If they apply, a mail is sent to the coordinator to apply.
-Fills the preapproval form on the system which the coordinator can view
-If preapproval form is denied, rewrite the preapproval form and send again.
-If they chose a project course at the host uni, they will upload the final report of the course to the system.
-Enter required info if the course is not present in the previously accepted courses list.


Coordinators -------
-Enters the schhols, their quotas, the language requirements, applicable semesters, 
-Before placementsi the system will check if there are anything wrong with students' applications (e.g. wrong semester). If there is an error, the system asks for the correction of application from student or cancel their application for that university. System also notifies the coordinators by adding these information to their to do list.
-Calls our system to get the rankings and the palcements assigned by the server
-When an accepted student cancels their application the system notifies the coordinators trough email and an event in the system. If the student on the waiting list approves the new position coordinator devides whether they will be accepted. If not, coordinate will choose to terminate the application process.

-Add/edit the preaproved courses.
-Check preapproval form, decide whether to accept or deny. Write feedback if denied via the system and send the feedback to student.

-To do list for upcoming events
-Events go to the coordinator that the University is assigned to

-Preaproval forms go to the coordinator for confirmation. The form can also be exported as pdf.


Exchange Coordinator (Yeldo) -------
-Approved students' information is sent to the secretary to contact with the host university.
-Enters the applicaation links of the host universities which will be directed to the corresponding student trough the system. System sends the corresponding student a notification mail.

Dean---------
-Signs the validity of required documents.

International Students Office------
-ISO sends the excel file containing the applicants, their GPA, Erasmus points, up to5 preferd schools.

Admin --------
-Assigns schools to coordinators
