import React, { useState, useEffect } from "react";
import { Modal } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import "./Courses.css";

const CoursesPage = ({loggedIn, setLoggedIn}) => {
  const [courses, setCourses] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [modalShow, setModalShow] = React.useState(false);

  //addCourseForm
  const [courseName, setCourseName] = useState("");
  const [schoolName, setSchoolName] = useState("");
  const [equivalentCourse, setEquivalentCourse] = useState("");
  const [approvalStatus, setApprovalStatus] = useState("");
  
  setLoggedIn(true);
  
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

  // Handles submitting the form to the Spring backend
  async function handleSubmit(event) {
    event.preventDefault();
    const response = await fetch("/api/courses", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        courseName,
        schoolName,
        equivalentCourse,
        approvalStatus,
      }),
    });
    // Clear the form fields and close the form
    setCourseName("");
    setSchoolName("");
    setEquivalentCourse("");
    setApprovalStatus("");
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
            <th>School Name</th>
            <th>Equivalent Course</th>
            <th>Approval Status</th>
            <th>Details</th>
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
                    <label htmlFor="school-name">School Name:</label>
                    <input
                      id="school-name"
                      type="text"
                      value={schoolName}
                      onChange={(event) => setSchoolName(event.target.value)}
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
                    <label htmlFor="approval-status">Approval Status:</label>
                    <select
                      id="approval-status"
                      value={approvalStatus}
                      onChange={(event) =>
                        setApprovalStatus(event.target.value)
                      }
                    >
                      <option value="approved">Approved</option>
                      <option value="pending">Pending</option>
                      <option value="rejected">Rejected</option>
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
          {filteredItems.map((course) => (
            <tr key={course.id}>
              <td>{course.courseCode}</td>
              <td>{course.school}</td>
              <td>{course.equivalent}</td>
              <td>{course.isApproved ? "Approved" : "Not Approved"}</td>
              <td>
                <button onClick={() => openModal(course)}>Details</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Modal
        isOpen={selectedCourse !== null}
        onRequestClose={closeModal}
        shouldCloseOnOverlayClick={true}
      >
        {selectedCourse !== null && (
          <div>
            <h2>{selectedCourse.name}</h2>
            <p>{selectedCourse.details}</p>
            <button onClick={closeModal}>Close</button>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default CoursesPage;
