import React, { useState, useEffect } from "react";
import { Modal } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import "./MyFormsPage.css";

const MyFormsPage = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState(null);
    const [modalShow, setModalShow] = React.useState(false);
    const [modalNewCourseShow, setModalNewCourseShow] = React.useState(false);
    const [modalDetailsShow, setModalDetailsShow] = useState();
    var username= "";
    //addCoursetoPreApproval
    const [courseName, setCourseName] = useState("");
    const [courseCredit, setCourseCredit] = useState("");
    const [equivalentCourse, setEquivalentCourse] = useState("");
    const [equivalentCourseCredit, setEquivalentCourseCredit] = useState("");

    //fetch existing courses from database
    useEffect(() => {
        fetch("http://localhost:8080/api/send-form-item")
            .then((response) => response.json())
            .then((courses) => setCourses(courses));
    }, []);

    const [filteredItems, setFilteredItems] = useState(courses);

    const openModal = (course) => {
        setSelectedCourse(course);
    };

    const closeModal = () => {
        setSelectedCourse(null);
    };
    const setDetailsModalHandler = (course) => {
        setModalDetailsShow(true);
        setSelectedCourse(course);
    }
    //Saving the values in popup to database
    const savePopup = async (event)=>{
        event.preventDefault();
        fetch("http://localhost:8080/api/sent-form-item",{
            method: 'POST',
            headers:{
                'Accept': 'application/json',
                'Content-Type':'application/json'
        },
            body: JSON.stringify(
                courseName,
                courseCredit,
                equivalentCourse,
                equivalentCourseCredit,
                username= "22001111"
            )
        })
        .then(res =>res.json())
        .then((result)=>{
            alert(result);
        },
        (error) => {alert('Failed')
        })
            console.log(courseName, courseCredit,equivalentCourse,equivalentCourseCredit);
       

        setModalShow(false);
       // document.getElementById('txtNmeComp').value = document.getElementById('course-credit').value;
        //document.getElementById('txtNmeComp').value = document.getElementById('equivalent-course').value;
        //document.getElementById('txtNmeComp').value = document.getElementById('equivalent-course-credit').value;
    }

    // Handles submitting the form to the Spring backend
    async function handleSubmit(event) {
        event.preventDefault();
        const response = await fetch("/api/courses", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                courseName,
                courseCredit,
                equivalentCourse,
                equivalentCourseCredit,
            }),
        });
        // Clear the form fields and close the form
        setCourseName("");
        setCourseCredit("");
        setEquivalentCourse("");
        setEquivalentCourseCredit("");
    }

    function handleFilter() {
        //only show approved for now
        setFilteredItems(courses.filter((course) => course.isApproved === true));
    }

    return (

        <div>
            <table>
                <thead>
                    <tr>
                        <th>Course Name</th>
                        <th>Course Credit</th>
                        <th>Equivalent Course</th>
                        <th>Equivalent Course Credit</th>
                        <td>
                            <button onClick={() => setModalShow(true)}>Add Existing Course</button>
                            <Modal
                                show={modalShow}
                                size="lg"
                                aria-labelledby="contained-modal-title-vcenter"
                                centered
                            >
                            <Modal.Body>
                                <form className="add-course-form" onSubmit={handleSubmit}>
                                    <label htmlFor="course-name">Course Name:</label>
                                    <input
                                        id="course-name"
                                        type="text"
                                        value={courseName}
                                        onChange={(event) => setCourseName(event.target.value)}
                                    />
                                     <label htmlFor="course-credit">Course Credit:</label>
                                    <input
                                        id="course-credit"
                                        type="number"
                                        value={courseCredit}
                                        onChange={(event) => setCourseCredit(event.target.value)}
                                    />
                                    <label htmlFor="equivalent-course">
                                        Equivalent Course:
                                    </label>
                                    <input
                                        id="equivalent-course"
                                        type="text"
                                        value={equivalentCourse}
                                        onChange={(event) =>
                                            setEquivalentCourse(event.target.value)
                                        }
                                    />
                                    <label htmlFor="equivalent-course-credit">Equivalent Course Credit:</label>
                                    <input
                                        id="equivalent-course-credit"
                                        type="number"
                                        value={equivalentCourseCredit}
                                        onChange={(event) =>
                                            setEquivalentCourseCredit(event.target.value)
                                        }
                                    />
                                </form>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button type="submit" onClick={savePopup}>
                                    Save
                                </Button>
                                <Button onClick={() => setModalShow(false)}>Close</Button>
                            </Modal.Footer>
                        </Modal>
                    </td>
                    <td>
                        <button onClick={handleFilter}>Filter</button>
                    </td>
                </tr>
            </thead>
            <tbody>
                {courses.map((course) => (
                    <tr key={course.id}>
                        <td>{course.courseCode}</td>
                        <td >{course.courseCredit}</td>
                        <td>{course.equivalentCourse}</td>
                        <td>{course.equivalentCourseCredit}</td>
                        <td>
                            <button onClick={() => setDetailsModalHandler(course)}>Details</button>
                        </td>
                        <Modal
                            show={modalDetailsShow}
                            size="lg"
                            aria-labelledby="contained-modal-title-vcenter"
                            centered
                        >
                            <Modal.Body>
                                <h4>{course.courseCode}</h4>
                                <p>
                                    Quota:
                                </p>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button onClick={() => setModalDetailsShow(false)}>Close</Button>
                            </Modal.Footer>
                        </Modal>
                    </tr>
                ))}
            </tbody >
        </table >
                </div >

                );

};

export default MyFormsPage; 