import React, { useState, useEffect } from "react";
import { Col, Container, Row, Button, ListGroup, ListGroupItem, ButtonGroup, ToggleButton, ToggleButtonGroup, ModalBody } from "react-bootstrap";
import FormList from './FormList';
import CancelTwoToneIcon from '@mui/icons-material/CancelTwoTone';
import CheckCircleTwoToneIcon from '@mui/icons-material/CheckCircleTwoTone';
import VisibilityTwoToneIcon from '@mui/icons-material/VisibilityTwoTone';
import { Modal,Form } from "react-bootstrap";
import { Page, Document, pdfjs, Outline } from "react-pdf";
import axios from "axios";
import './FormListContainer.css';
import { handleBreakpoints } from "@mui/system";

const FormsPage = () => {
    const [forms, setForms] = useState([]);
    //const [pdfResponse, setPdfResponse] = useState();
    const [selectedForm, setSelectedForm] = useState(null);
    const [modalShow, setModalShow] = React.useState(false);
    const [modalDetailsShow, setModalDetailsShow] = useState();
    const [feedback, setFeedback] = useState("");
    const pdfHandler = async (event) => {
        event.preventDefault();
    }
    // TOdo: get all pdfs
    // eklenecek {forms.map(form => (    ))}
    //  <div key={form.id} className="content">
    //</div>
    //modal açma eklenecek

    /*<form className="add-course-form" onSubmit={handleSubmit}>
                                                <label htmlFor="course-name">Course Name:</label>
                                                <input
                                                    id="course-name"
                                                    type="text"
                                                    value={courseName}
                                                    onChange={(event) => setCourseName(event.target.value)}
                                                />
                                            </form>*/


    //fetch all forms from the server
    useEffect(() => {
        fetch("http://localhost:8080/course/bilkent/all")
            .then((response) => response.json())
            .then((forms) => setForms(forms));
    }, []);

    //Save rejection feedback
    const saveFeedback = async (event) => {
        event.preventDefault();
        fetch("http://localhost:8080/course/bilkent/all", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(
                feedback
            )
        })
            .then(res => res.json())
            .then((result) => {
                alert(result);
            },
                (error) => {
                    alert('Failed')
                })

    }

    const handleChange = () => { }
    // download pdfs from the endpoint;
    const downloadpdf = () => {
        axios.get("http://localhost:8080/api/download/a_b_PreApproval.pdf", {
            responseType: 'arraybuffer',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/pdf'
            }
        })
            //.then(response => setPdfResponse(response.data));
            .then((response) => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', 'file.pdf'); //or any other extension
                document.body.appendChild(link);
                link.click();
            })
            .catch((error) => console.log(error));
    };

    const acceptForm = () => {

    };
    /*async function handleSubmit(event) {
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
      }
*/
    const [filteredItems, setFilteredItems] = useState(forms);

    //render the component
    return (
        <div>
            <Container className="FormsPage">
                <Row>
                    <ToggleButtonGroup type="radio" name="options" defaultValue={1}>
                        <ToggleButton id="tbg-radio-1" value={1}>
                            Forms
                        </ToggleButton>
                        <ToggleButton id="tbg-radio-2" value={2}>
                            Active Forms
                        </ToggleButton>
                    </ToggleButtonGroup>
                </Row>
                <Row>
                    <div className="FormList">
                        <p> {forms.map((course) => (
                            <tr key={course.id}>
                                <div className="FormButtons" >
                                    <div className="view" onClick={() => { downloadpdf() }}>
                                        <VisibilityTwoToneIcon></VisibilityTwoToneIcon>
                                    </div>
                                    <div className="accept" onClick={() => { acceptForm() }}>
                                        <CheckCircleTwoToneIcon></CheckCircleTwoToneIcon>
                                    </div>
                                    <div className="reject" onClick={() => { setModalShow(true) }}>
                                        <CancelTwoToneIcon></CancelTwoToneIcon>
                                    </div>
                                </div>
                                <Modal
                                    show={modalShow}
                                    size="lg"
                                    aria-labelledby="contained-modal-title-vcenter"
                                    centered>
                                    <Modal.Body>
                                        <Form>
                                            <Form.Group
                                                className="mb-3"
                                                controlId="exampleForm.ControlTextarea1"
                                            >
                                                <Form.Label>Feedback: </Form.Label>
                                                <Form.Control as="textarea" rows={4} type="text"
                                                    onChange={(event) => setFeedback(event.target.value)}
                                                    value={feedback}
                                                    placeholder="Give feedback here" />
                                            </Form.Group>
                                        </Form>

                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button type="submit" onClick={() => setModalShow(false)}>
                                            Save
                                        </Button>
                                        <Button onClick={() => setModalShow(false)}>Close</Button>
                                    </Modal.Footer>

                                </Modal>

                            </tr>
                        ))}</p>
                    </div>
                </Row>
            </Container>

        </div>
    )
};

export default FormsPage; 