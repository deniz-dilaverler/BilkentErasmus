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
    const [cancelled, setCancelled] = useState();
    const cancelAllApplicationHandler = () => {
        //TODO Cancel application in database!!!
        fetch('http://localhost:8080/application/erasmus/' + props.applicationID +'/true', { method: 'DELETE' })
        .then((cancelled) => setCancelled(cancelled));
        // tekrar yüklenmesi de lazım, yani isCancelled useState olmalı herhade
        console.log("Cancel all applications!")
        console.log(props.applicationID)
        setOpen(false);
    }

    const cancelCurrentApplicationHandler = () => {
        //TODO Cancel application in database!!!
        // tekrar yüklenmesi de lazım sayfanın
        console.log("Cancel current application!")
        setOpenCur(false)
    }

    // fetch application publish data:
    const isPublished = () => {
        return false;
    }

    const [open, setOpen] = useState(false);
    const [openCur, setOpenCur] = useState(false);

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


    // if publication is published
    if ( isPublished() )
    {
        if ( props.placedInst === -1 )
        {
            return(
                <Container className = "cancel">
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
        return(
            <Container className = "cancel">
            <Row>
                <Col></Col>
                <Col>
                <button type="button" onClick={handleCurClickOpen}>Cancel Current Application</button>
                <Dialog open={openCur} onClose={handleClose}>
                <DialogTitle >
                    Cancel Current Application?
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Are you sure you want to cancel ALL applications?
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
    // if publication is not published, user can only cancel all applications
    else if ( !isPublished() )
    {
        return(
            <Container className = "cancel">
            <Row>
                <Col></Col>
                <Col>
                <button type="button" onClick={handleAllClickOpen}>Cancel All Applications</button>
                <Dialog
                    open={open}
                    onClose={handleClose}
                >
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