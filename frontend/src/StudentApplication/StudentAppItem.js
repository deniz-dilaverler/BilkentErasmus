function StudentAppItem(props) {

    return (
      <StudentAppCard className="application-item">
        <div className="application-item__description">
          <h2>{props.no}</h2>
        </div>
        <div className="application-item__description">
          <h2>{props.school}</h2>
        </div>
        <div className="application-item__description">
          <h2>{props.semester}</h2>
        </div>
        <div className="application-item__description">
          <button
            className="school-item__description_button"
            onClick={() => openModal(props)}
          >
            Details
          </button>
        </div>
      </StudentAppCard>
    );
  }
  
  export default StudentAppItem;