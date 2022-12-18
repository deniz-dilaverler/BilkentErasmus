import StudentAppItem from "./StudentAppItem";
import CancelApplication from "./CancelApplication";
import ModifyFaultyData from "./ModifyFaultyData";
import Container from 'react-bootstrap/Container';
import './StudentApplications.css';
import React, { useState, useEffect } from "react";

function StudentApplications(props) {

    // get student application status: (cancelled or not):
    const [status, setStatus] = useState();

    const [isPlacementStarted, setIsPlacementStarted] = useState();

    const choices = [];
    console.log("gir")
    console.log(choices)
    if (props.applications.semester1 !== undefined) {
        choices.push({
            semester: props.applications.semester1,
            choiceNo: 1,
            choiceName: props.applications.choice1.name,
        })
    }
    if ( props.applications.semester2 !== undefined ) {
        choices.push(
        {
            semester: props.applications.semester2,
            choiceNo: 2,
            choiceName: props.applications.choice2.name,
        })
    }
    if ( props.applications.semester3 !== undefined ) {
        choices.push(
        {semester: props.applications.semester3,
            choiceNo: 3,
                choiceName: props.applications.choice3.name,
        })
    }
    if ( props.applications.semester4 !== undefined ) {
        choices.push(
            {
                semester: props.applications.semester4,
                    choiceNo: 4,
                        choiceName: props.applications.choice4.name,
                },
        )
    }
    if ( props.applications.semester5 !== undefined ) {
        choices.push(
            {
                semester: props.applications.semester5,
                    choiceNo: 5,
                        choiceName: props.applications.choice5.name,
                },
        )
    }

    console.log("choices")
    console.log(choices)

    useEffect(() => {
        fetch("http://localhost:8080/application/erasmus/status/3")
            .then((response) => response.json())
            .then((status) => setStatus(status));
    }, []);

    useEffect(() => {
        fetch("http://localhost:8080/state/placement/erasmus/CS")
            .then((response) => response.json())
            .then((isPlacementStarted) => setIsPlacementStarted(isPlacementStarted));
    }, []);


    console.log(isPlacementStarted)

    // get if application is published or not
    const isApplicationsPublished = () => {
        return true;
    }

    // get if application placement process started or not
    const isApplicationsStarted = () => {
        return true;
    }

    // if all student application is cancelled, show cancelled:
    if (status === "CANCELED") {
        return (
            <Container className="applications">
                <div><h3>There are no active applications found.</h3></div>
            </Container>
        );
    }
    // if student application is not started, just view all applications
    // also, student can cancel all applications
    // +
    else if (status !== "CANCELED" && isPlacementStarted === "APPS_CREATED") {
        if (props.applications) {
            return (
                <Container className="applications">
                    <div><h4>Wait for your coordinator to start application placements</h4></div>
                    <div>
                        {choices.map((application) =>
                            <StudentAppItem
                                key={application.choiceNo}
                                no={application.choiceNo}
                                school={application.choiceName}
                                semester={application.semester}
                            />
                        )}
                    </div>
                    <CancelApplication></CancelApplication>
                </Container>
            );
        }
    }
    // if not cancelled and applications are started but not published, show if there are any mistakes!
    //+
    else if (status !== "CANCELED" && isApplicationsStarted() && status === "PLACED") {
        // fetch faulty data if there is any -> ask for a change
        // can cancel all applications, too
        const fetchFaultyData = () => {
            return [
                {
                    key: 1,
                    no: 1,
                    school: "Bamberg University",
                    semester: "Fall",
                },
                {
                    key: 4,
                    no: 4,
                    school: "Kingston University",
                    semester: "Fall",
                }
            ];
        }
        // if there is faulty data, show it, too! + cancel all
        if (fetchFaultyData !== -1) {
            return (
                <Container className="applications">
                    <div>
                        <h4>You have some applications you need to modify:</h4>
                        {fetchFaultyData().map((faultyApplication) =>
                            <ModifyFaultyData
                                key={faultyApplication.no}
                                no={faultyApplication.no}
                                school={faultyApplication.school}
                                semester={faultyApplication.semester}
                            />
                        )}
                        <CancelApplication></CancelApplication>
                    </div>
                </Container>
            );
        }
        // -1 is NULL CHECK, If there is no data, show no data
        else if (fetchFaultyData === -1) {
            return (
                <Container className="applications">
                    <div>
                        <div><h4>Wait for your coordinator to publish application placements</h4></div>
                        <div>
                            {props.applications.map((application) =>
                                <StudentAppItem
                                    key={application.no}
                                    no={application.no}
                                    school={application.school}
                                    semester={application.semester}
                                />
                            )}
                        </div>
                        <CancelApplication></CancelApplication>
                    </div>
                </Container>
            );
        }
    }
    // if applications are published and stated person is placed: show placement
    else if (status !== "CANCELED" && isApplicationsPublished()) {
        // placed institution, databaseden çek, şimdilik mock data:

        const placedInstitution =
        {
            no: 1,
            school: 'Bamberg University',
            semester: 'Spring',
        };


        //const placedInstitution = -1; 
        // if there is no placed institution:
        // show published application, -1 is for null check
        if (placedInstitution === -1) {
            return (
                <Container className="applications">
                    <div><h3>Placed Institution:</h3></div>
                    <div><h4>You are currently in waiting list.</h4></div>
                    <CancelApplication placedInst={placedInstitution}></CancelApplication>
                </Container>
            )
        }
        else {
            if (true) // database'den alınca buraya nullcheck yap!
                return (
                    <Container className="applications">
                        <div><h3>Placed Institution:</h3></div>
                        <StudentAppItem no={placedInstitution.no}
                            school={placedInstitution.school}
                            semester={placedInstitution.semester}
                        >
                        </StudentAppItem>
                        <CancelApplication></CancelApplication>
                    </Container>
                );
        }
    }

}

export default StudentApplications;