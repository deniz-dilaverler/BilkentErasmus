import "./SchoolItem.css";
import InstitutionCard from "../UI/InstitutionCard";
import React, { useState } from "react";
import { Modal } from 'react-bootstrap';
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
          onClick={() => openModal(props)}
        >
          Details
        </button>
      </div>
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
    </InstitutionCard>
  );
}

export default SchoolItem;