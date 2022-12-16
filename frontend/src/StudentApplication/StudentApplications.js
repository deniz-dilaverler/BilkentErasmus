import StudentAppCard from "./StudentAppCard";

function StudentApplications(props) {

    // get student application status: (cancelled or not):
    const isCancelled= () => {
        return true;
    }

    // get if application is published or not
    const isApplicationsPublished = () => {
        return false;
    }

    // get if application placement process started or not
    const isApplicationsStarted = () => {
        return false;
    }

    // if all student application is cancelled, show cancelled:
    if ( isCancelled() ) 
    {
        return(
            <StudentAppCard className = "applications">
                <div><h2>There are no active applications found.</h2></div>
            </StudentAppCard>
        );
    }
    // if student application is not started, just view all applications
    else if ( !isCancelled() && !isApplicationsStarted() ) {
        return(
            <StudentAppCard className = "applications">
                <div>
                {props.institutions.map((application) => 
                    <SchoolItem
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
        // cancel stated application 

        // change semester
    }
    // if applications are published and stated person is placed: show placement
    else if ( !isCancelled() && isApplicationsPublished() )
    {
        // show published application

        // can cancel application
    }

}

export default StudentApplications;