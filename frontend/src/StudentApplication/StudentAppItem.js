import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import './StudentAppItem.css';
function StudentAppItem(props) {


    return (
      <Container className="application-item" fluid>
        <Row>
          <Col>
          <h1>{props.no}</h1>
          </Col>
          <Col>
          <h2>{props.school}</h2>
          </Col>
          <Col>
          <h2>{props.semester}</h2>
          </Col>
        </Row>
      </Container>
    );
  }
  
  export default StudentAppItem;