import './CancelApplication.css';
import StudentAppCard from './StudentAppCard';
function CancelApplication(props) {
    return(
        <StudentAppCard className = "cancel">
        <div className = "new-expense__actions">
            {props.isPublished && <button type="button">Cancel Current Application</button>}
            <button type="button">Cancel All Applications</button>
        </div>
        </StudentAppCard>
    );
}

export default CancelApplication;