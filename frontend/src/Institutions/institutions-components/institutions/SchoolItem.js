import "./SchoolItem.css";
import InstitutionCard from "../UI/InstitutionCard";
import React, { useState } from "react";
import { Modal } from 'react-bootstrap';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';

function SchoolItem(props) {
  const [details, showDetails] = useState();

  const [selectedInstitution, setSelectedInstitution] = useState(null);

  const openModal = (props) => {
    setSelectedInstitution(props);
  };

  const closeModal = () => {
    setSelectedInstitution(null);
  };

  return (
    <Container className="school-item">
      <Row>
        <Col><h2>{props.name}</h2></Col>
        <Col><h2>{props.country}</h2></Col>
        <Col><h2>{props.language}</h2></Col>
        <Col><button
          className="school-item__description_button"
          onClick={() => openModal(props)}>
          Details
        </button></Col>
        <Modal
        isOpen={selectedInstitution !== null}
        onRequestClose={closeModal}
        shouldCloseOnOverlayClick={true}
      >
        {selectedInstitution !== null && (
          <div>
            <h2>{selectedInstitution.name}</h2>
            <p>{selectedInstitution.details}</p>
            <button onClick={closeModal}>Close</button>
          </div>
        )}
      </Modal>
      </Row>
    </Container>
  );
}

export default SchoolItem;