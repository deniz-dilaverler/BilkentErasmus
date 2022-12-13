import "./SchoolItem.css";
import InstitutionCard from "../UI/InstitutionCard";
import React, { useState } from "react";
import { Modal } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';

function SchoolItem(props) {
  const [details, showDetails] = useState();

  function handleClick(courseID) {
    // Get the course details from somewhere (database, API, etc.)
    const courseDetails = {
        name: "Course Name",
        schoolName: "School Name",
        equivalentCourse: "Equivalent Course",
        approvalStatus: "Approval Status",
    }

    // Set the course details in state
    this.setState({
      showModal: true,
      courseDetails,
    });
  }

  return (
    <InstitutionCard className="school-item">
      <div className="school-item__description">
        <h2>{props.name}</h2>
      </div>
      <div className="school-item__description">
        <h2>{props.country}</h2>
      </div>
      <div className="school-item__description">
        <h2>{props.language}</h2>
      </div>
      <div className="school-item__description">
        <button
          className="school-item__description_button"
          onClick={handleClick}
        >
          Details
        </button>
      </div>
    </InstitutionCard>
  );
}
export default SchoolItem;

// Modal component
const InstutitonsModal = (props) => {
  return (
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
  );
};
