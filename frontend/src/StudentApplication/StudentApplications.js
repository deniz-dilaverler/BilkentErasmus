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
    const [faultyData, setFaultyData] = useState();

    console.log("denize götten giriyim")
    console.log(isPlacementStarted)
    const applicationID = props.applications.id;

    useEffect(() => {
        fetch("http://localhost:8080/application/erasmus/wrongsemester/" + applicationID)
            .then((response) => response.json())
            .then((faultyData) => setFaultyData(faultyData));
    }, []);
    console.log("faulty data:")
    console.log(faultyData)

    const [cancelled, setCancelled] = useState(false);
    console.log("odddddddddddddddffffffffff")
    console.log(status)
    const cancelApplicationStatusHandler = (statusData) => {
        console.log(statusData)
        if (statusData === "ALL") {
            console.log("It is canceled!")
            fetch('http://localhost:8080/application/erasmus/' + applicationID + '/true', { method: 'DELETE' })
                .then((status) => setStatus("CANCELED"));
            setStatus("CANCELED")
        }
        else if (statusData.statType === "CURRENT" && status === "PLACED" ) {
            fetch('http://localhost:8080/application/erasmus/' + applicationID + '/false', { method: 'DELETE' })
                .then((cancelled) => setCancelled(false));
            console.log("Şey ben current cancellıycam uwu")
        }
        else if (statusData.statType === "CURRENT") {
            fetch('http://localhost:8080/application/erasmus/cancelChoice/semester/' + applicationID + '/' + statusData.no, { method: 'PUT' })
                .then((cancelled) => setCancelled(false));
            console.log("Şey ben current cancellıycam uwu")
        }
        else if (statusData.statType === "SEMESTER_CHANGE") {
            fetch('http://localhost:8080/application/erasmus/change/semester/' + applicationID + '/' + statusData.no, { method: 'PUT' })
                .then((cancelled) => setCancelled(false));

            console.log("Şey ben SEMESTER DEYİŞCEMM uwu")
        }
    };

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
    if (props.applications.semester2 !== undefined) {
        choices.push(
            {
                semester: props.applications.semester2,
                choiceNo: 2,
                choiceName: props.applications.choice2.name,
            })
    }
    if (props.applications.semester3 !== undefined) {
        choices.push(
            {
                semester: props.applications.semester3,
                choiceNo: 3,
                choiceName: props.applications.choice3.name,
            })
    }
    if (props.applications.semester4 !== undefined) {
        choices.push(
            {
                semester: props.applications.semester4,
                choiceNo: 4,
                choiceName: props.applications.choice4.name,
            },
        )
    }
    if (props.applications.semester5 !== undefined) {
        choices.push(
            {
                semester: props.applications.semester5,
                choiceNo: 5,
                choiceName: props.applications.choice5.name,
            },
        )
    }

    let faultyDatas = [];
    if (faultyData !== undefined) {
        for (let i = 0; i < 5; i++) {
            if (faultyData[i] === false) {
                faultyDatas.push(
                    {
                        no: i + 1,
                        semester: choices[i].semester,
                        name: choices[i].choiceName,
                    }
                );
            }
        }
    }

    console.log(faultyDatas)
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
    else if ((status !== "CANCELED" && isPlacementStarted === "APPS_CREATED") || (status !== "CANCELED" && isPlacementStarted === "APPS_CORRECT")) {
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
                    <CancelApplication cancelApplicationStatusData={cancelApplicationStatusHandler}></CancelApplication>
                </Container>
            );
        }
    }
    // if not cancelled and applications are started but not published, ACTIVATED status means that there are mistakes
    // Hence, show mistakes if there any in this student
    else if (status !== "CANCELED" && isPlacementStarted === "ACTIVATED") {
        // fetch faulty data -> ask for a change
        // can cancel all applications, too
        if (faultyDatas !== undefined) {
            if (faultyDatas.length >= 1) {
                return (
                    <Container className="applications">
                        <div>
                            <h4>You have some applications you need to modify:</h4>
                            {faultyDatas.map((app) =>
                                <ModifyFaultyData
                                    key={app.no}
                                    no={app.no}
                                    school={app.name}
                                    semester={app.semester}
                                    cancelApplicationStatusData={cancelApplicationStatusHandler}
                                />
                            )}
                            <CancelApplication cancelApplicationStatusData={cancelApplicationStatusHandler} isPublished={status}></CancelApplication>
                        </div>
                    </Container>
                );
            }
            else {
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
                        <CancelApplication cancelApplicationStatusData={cancelApplicationStatusHandler} isPublished={status}></CancelApplication>
                    </Container>
                );
            }
        }
    }
    // if applications are published and stated person is placed: show placement results!
    else if (status === "PLACED") {
        if (props.applications.placedSchool !== undefined) {
            return (
                <Container className="applications">
                    <div><h3>Placed Institution:</h3></div>
                    <StudentAppItem country={props.applications.placedSchool.country}
                        school={props.applications.placedSchool.name}
                        semester={props.applications.placedSchool.languageRequirement}
                    >
                    </StudentAppItem>
                    <CancelApplication cancelApplicationStatusData={cancelApplicationStatusHandler} status={status}></CancelApplication>
                </Container>
            );
        }
    }
    else if (status === "WAITING_BIN") {
        // if placed institution is null, it means that user is in waiting list
        // Hence, return following:
        return (
            <Container className="applications">
                <div><h3>Placed Institution:</h3></div>
                <div><h4>You are currently in waiting list.</h4></div>
                <CancelApplication cancelApplicationStatusData={cancelApplicationStatusHandler} isPublished={status}></CancelApplication>
            </Container>
        )
    }

}

export default StudentApplications;