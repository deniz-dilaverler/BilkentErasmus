import { Container } from 'react-bootstrap';
import './CancelApplication.css';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';


function CancelApplication(props) {

    // cancel all applications!
    const cancelAllApplicationHandler = () => {
        const statusData = "ALL"
        props.cancelApplicationStatusData(statusData);
        console.log("Cancel all applications!")
        setOpen(false);
        window.location.reload(false);
    }

    // cancel current application
    const cancelCurrentApplicationHandler = () => {
        const statusData = {
            statType: "CURRENT",
            no: props.no
        }
        props.cancelApplicationStatusData(statusData);
        console.log("Cancel current application!")
        setOpenCur(false);
        window.location.reload(false);
    }

    // states for opening dialogs
    const [open, setOpen] = useState(false);
    const [openCur, setOpenCur] = useState(false);

    // handle open and close dialogs:
    const handleAllClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleCurClose = () => {
        setOpenCur(false);
    }

    const handleCurClickOpen = () => {
        setOpenCur(true);
    }


    // if publication is published and student is placed to an institution:
    if (props.status === "PLACED") {
        return (
            <Container className="cancel">
                <Row>
                    <Col></Col>
                    <Col>
                        <button type="button" onClick={handleCurClickOpen}>Cancel Placed Application</button>
                        <Dialog open={openCur} onClose={handleClose}>
                            <DialogTitle >
                                Cancel Current Application?
                            </DialogTitle>
                            <DialogContent>
                                <DialogContentText>
                                    Are you sure you want to cancel THIS PLACED institution application?
                                    Cancelling will put you in waiting list.
                                </DialogContentText>
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={handleCurClose}>Disagree</Button>
                                <Button onClick={cancelCurrentApplicationHandler} autoFocus>
                                    Agree
                                </Button>
                            </DialogActions>
                        </Dialog>
                    </Col>
                    <Col>
                        <button type="button" onClick={handleAllClickOpen}>Cancel All Applications</button>
                        <Dialog open={open} onClose={handleClose}>
                            <DialogTitle >
                                Cancel All Applications?
                            </DialogTitle>
                            <DialogContent>
                                <DialogContentText>
                                    Are you sure you want to cancel ALL applications?
                                </DialogContentText>
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={handleClose}>Disagree</Button>
                                <Button onClick={cancelAllApplicationHandler} autoFocus>
                                    Agree
                                </Button>
                            </DialogActions>
                        </Dialog>
                    </Col>
                    <Col></Col>
                </Row>
            </Container>
        );
    }
    if (props.status !== "PLACED" || props.status !== "CANCELED") {
        // user can only cancel all applications since user is not in an institution currently
        return (
            <Container className="cancel">
                <Row>
                    <Col></Col>
                    <Col>
                        <button type="button" onClick={handleAllClickOpen}>Cancel All Applications</button>
                        <Dialog open={open} onClose={handleClose}>
                            <DialogTitle >
                                Cancel All Applications?
                            </DialogTitle>
                            <DialogContent>
                                <DialogContentText>
                                    Are you sure you want to cancel ALL applications?
                                </DialogContentText>
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={handleClose}>Disagree</Button>
                                <Button onClick={cancelAllApplicationHandler} autoFocus>
                                    Agree
                                </Button>
                            </DialogActions>
                        </Dialog>
                    </Col>
                    <Col></Col>
                </Row>
            </Container>
        );
    }

}

export default CancelApplication;