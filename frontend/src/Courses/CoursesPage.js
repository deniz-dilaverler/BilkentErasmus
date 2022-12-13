import React, { useState, useEffect } from 'react';
import { Modal } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import './Courses.css';

const CoursesPage = () => {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/courses')
      .then(response => response.json())
      .then(courses => setCourses(courses));
  }, []);

  return (
    <table>
      <thead>
        <tr>
          <th>Course Name</th>
          <th>School Name</th>
          <th>Equivalent Course</th>
          <th>Approval Status</th>
          <th>Details</th>
        </tr>
      </thead>
      <tbody>
        {courses.map(course => (
          <tr key={course.id}>
            <td>{course.name}</td>
            <td>{course.schoolName}</td>
            <td>{course.equivalentCourse}</td>
            <td>{course.approvalStatus}</td>
            <td>
              <button onClick={() => handleClick(course.id)} className="details__button">Details</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

function getCourseDetails(courseID) {
  // Make an HTTP request to the backend to get the details for the specified course
  return fetch(`http://localhost:8080/courses/${courseID}`)
    .then(response => response.json());
}

function handleClick(courseID) {
  // Get the course details from somewhere (database, API, etc.)
  //const courseDetails = getCourseDetails(courseID);

  // Set the course details in state
  this.setState({
    showModal: true,
    //courseDetails,
  });
}


// Modal component
const CourseModal = (props) => {
  return (true
    /*
    <Modal show={props.showModal} onHide={props.closeModal}>
      <Modal.Header closeButton>
        <Modal.Title>{props.courseDetails.name}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>{props.courseDetails.description}</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.closeModal}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
    */
  );
}

export default CoursesPage;