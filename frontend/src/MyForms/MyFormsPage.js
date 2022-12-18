import React, { useState, useEffect } from "react";
import { Modal } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import "./MyFormsPage.css";

const MyFormsPage = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState(null);
    const [modalShow, setModalShow] = React.useState(false);
    const [modalDetailsShow, setModalDetailsShow] = useState();

    //addCoursetoPreApproval
    const [courseName, setCourseName] = useState("");
    const [courseCredit, setCourseCredit] = useState("");
    const [equivalentCourse, setEquivalentCourse] = useState("");
    const [equivalentCourseCredit, setEquivalentCourseCredit] = useState("");

    //kullanma fronteddeki arrayden fecthle
    useEffect(() => {
        fetch("http://localhost:8080/course/bilkent/all")
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
                            <button onClick={() => setModalShow(true)}>Add Course</button>
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
                                    <label htmlFor="school-name">Course Credit:</label>
                                    <input
                                        id="course-credit"
                                        type="text"
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
                                    <select
                                        id="equivalent-course-credit"
                                        value={equivalentCourseCredit}
                                        onChange={(event) =>
                                            setEquivalentCourseCredit(event.target.value)
                                        }
                                    >
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                    </select>
                                </form>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button type="submit" onClick={() => setModalShow(false)}>
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
                        <td>{course.courseCredit}</td>
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