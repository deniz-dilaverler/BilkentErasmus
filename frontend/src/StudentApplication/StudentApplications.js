import StudentAppCard from "./StudentAppCard";
import StudentAppItem from "./StudentAppItem";
import CancelApplication from "./CancelApplication";
import './StudentApplications.css';

function StudentApplications(props) {

    // get student application status: (cancelled or not):
    const isCancelled= () => {
        return false;
    }

    // get if application is published or not
    const isApplicationsPublished = () => {
        return true;
    }

    // get if application placement process started or not
    const isApplicationsStarted = () => {
        return true;
    }

    // if all student application is cancelled, show cancelled:
    if ( isCancelled() ) 
    {
        return(
            <StudentAppCard className = "applications">
                <div><h3>There are no active applications found.</h3></div>
            </StudentAppCard>
        );
    }
    // if student application is not started, just view all applications
    else if ( !isCancelled() && !isApplicationsStarted() ) {
        return(
            <StudentAppCard className = "applications">
                <div>
                {props.applications.map((application) => 
                    <StudentAppItem
                        key = {application.no}
                        no = {application.no}
                        school = {application.school}
                        semester = {application.semester}
                    />
                )}
                </div>
            </StudentAppCard>
        );
    }
    // if not cancelled and applications are started but not published, show if there are any mistakes!
    else if ( !isCancelled() && isApplicationsStarted() && !isApplicationsPublished() ) 
    {
        // fetch faulty data if there is any -> ask for a change
        // show applications

        // cancel all applications
        return (
            <StudentAppCard className = "applications">
                {props.applications.map((application) => 
                    <StudentAppItem
                        key = {application.no}
                        no = {application.no}
                        school = {application.school}
                        semester = {application.semester}
                    />
                )}
                <CancelApplication isPublished={isApplicationsPublished}></CancelApplication>
            </StudentAppCard>
        );
        // cancel stated application 

        // change semester
    }
    // if applications are published and stated person is placed: show placement
    else if ( !isCancelled() && isApplicationsPublished() )
    {
        console.log("aloo")
        // placed institution, databaseden çek, şimdilik mock data:
        
        const placedInstitution = 
        {
            no: 1,
            school: 'Bamberg University',
            semester: 'Spring',
        };
        
       //const placedInstitution = -1;
        // if there is no placed institution:
        // show published application
        if ( placedInstitution === -1 )
        {
            return (
                <StudentAppCard className="applications">
                <div><h3>Placed Institution:</h3></div>
                <div><h4>You are currently in waiting list.</h4></div>
                </StudentAppCard>
            )
        }
        else {
            if (true) // database'den alınca buraya nullcheck yap!
            return(
                <StudentAppCard className="applications">
                <div><h3>Placed Institution:</h3></div>
                <StudentAppItem no = {placedInstitution.no}
                                school = {placedInstitution.school}
                                semester = {placedInstitution.semester}
                >
                </StudentAppItem>
                <CancelApplication isPublished={isApplicationsPublished}></CancelApplication>
            </StudentAppCard>
            );
        } 
    }

}

export default StudentApplications;