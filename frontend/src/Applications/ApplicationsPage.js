import React, { useState, useEffect } from 'react';
import { Modal } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import '../Courses/Courses.css';

const ApplicationsPage = () => {
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/applications')
      .then(response => response.json())
      .then(applications => setApplications(applications));
  }, []);

  return (
    <table>
      <thead>
        <tr>
          <th>Student Name</th>
          <th>GPA</th>
          <th>Schools</th>
          <th>Details</th>
          <button>Start Placements</button>
        </tr>
      </thead>
      <tbody>
        {applications.map(application => (
          <tr key={application.id}>
            <td>{application.name}</td>
            <td>{application.gpa}</td>
            <td>{application.schools}</td>
            <td>
              <button onClick={() => handleClick(application.id)} className="details__button">Details</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

function getCourseDetails(applicationID) {
  // Make an HTTP request to the backend to get the details for the specified course
  return fetch(`http://localhost:8080/courses/${applicationID}`)
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

export default ApplicationsPage;