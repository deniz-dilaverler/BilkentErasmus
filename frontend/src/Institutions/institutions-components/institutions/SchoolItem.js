import "./SchoolItem.css";
import React, { useState } from "react";
import { Modal } from 'react-bootstrap';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';

function SchoolItem(props) {

  // state for showing dialog:
  const [modalShow, setModalShow] = React.useState(false);


  // return school item with institution information and details button
  return (
    <Container className="school-item">
      <Row>
        <Col><h2>{props.name}</h2></Col>
        <Col><h2>{props.country}</h2></Col>
        <Col><h2>{props.language}</h2></Col>
        <Col><button
          onClick={() => setModalShow(true)}>
          Details
        </button></Col>
        <Modal
          show={modalShow}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
        <Modal.Body>
        <h4>{props.name}</h4>
          <p>Program type: {props.programType}</p>
          <p>Institution accepted semester: {props.semester}</p>
          <p>Institution quota: {props.programs !== undefined? props.programs[0].quota: console.log("") }</p>
          <p>Institution allowance: {props.allowance}</p>
          <p>Institution accepted departments: {props.programs !== undefined? props.programs[0].department: console.log("")}</p>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={() => setModalShow(false)}>Close</Button>
        </Modal.Footer>
      </Modal>
      </Row>
    </Container>
  );
}

export default SchoolItem;   
