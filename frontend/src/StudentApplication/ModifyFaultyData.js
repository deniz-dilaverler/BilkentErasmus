import React from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import StudentAppItem from './StudentAppItem';
import './ModifyFaultyData.css'

function ModifyFaultyData(props) {

  const cancelCurrentApplicationHandler = () => {
    //TODO Cancel application in database!!!
    // tekrar yüklenmesi de lazım sayfanın
  }

  const changeSemesterHandler = () => {
    //TODO Cancel application in database!!!
    // tekrar yüklenmesi de lazım sayfanın
  }

    return (
        <Container className='modify'>
          <Row>
            <Col>
              Currently selected semester is not applicable to this institution:
          </Col>
          </Row>
          <Row>
            <Col>
            <StudentAppItem
                        key = {props.key}
                        no = {props.no}
                        school = {props.school}
                        semester = {props.semester}
            />
          </Col>
          </Row>
          <Row>
            <Col></Col>
            <Col>
            <button onClick={cancelCurrentApplicationHandler}>Cancel Current Application</button>
            </Col>
            <Col>
            <button onClick={changeSemesterHandler}>Change Semester</button>
            </Col>
            <Col></Col>
          </Row>
        </Container>
      );
}

export default ModifyFaultyData;