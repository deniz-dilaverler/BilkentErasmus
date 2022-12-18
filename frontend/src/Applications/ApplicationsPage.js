import React, { useState, useEffect } from "react";
import { Modal } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import "../Courses/Courses.css";

const ApplicationsPage = ({loggedIn, setLoggedIn}) => {
  const [applications, setApplications] = useState([]);
  const [selectedApplication, setselectedApplication] = useState(null);

  setLoggedIn(true);
  useEffect(() => {
    fetch("http://localhost:8080/applications")
      .then((response) => response.json())
      .then((applications) => setApplications(applications));
  }, []);

  const openModal = (applications) => {
    setselectedApplication(applications);
  };

  const closeModal = () => {
    setselectedApplication(null);
  };

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
        {applications.map((application) => (
          <tr key={application.id}>
            <td>{application.name}</td>
            <td>{application.gpa}</td>
            <td>{application.schools}</td>
            <td>
              <button
                onClick={() => openModal(application)}
                className="details__button"
              >
                Details
              </button>
            </td>
          </tr>
        ))}
      </tbody>
      <Modal
        isOpen={selectedApplication !== null}
        onRequestClose={closeModal}
        shouldCloseOnOverlayClick={true}
      >
        {selectedApplication !== null && (
          <div>
            <h2>{selectedApplication.name}</h2>
            <p>{selectedApplication.details}</p>
            <button onClick={closeModal}>Close</button>
          </div>
        )}
      </Modal>
    </table>
  );
};

export default ApplicationsPage;
