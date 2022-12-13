import './SchoolItem.css';
import InstitutionCard from '../UI/InstitutionCard';
import React, { useState } from 'react';


function SchoolItem(props) {
    const [details, showDetails] = useState();

    function detailsClickHandler() {
        showDetails("Here are details");
        console.log("Clicked!")
    }

    return (
        <InstitutionCard className="school-item">
            <div className='school-item__description'><h2>{props.name}</h2></div>
            <div className='school-item__description'><h2>{props.country}</h2></div>
            <div className='school-item__description'><h2>{props.language}</h2></div>
            <div className='school-item__description'><button className='school-item__description_button' onClick={detailsClickHandler}>Details</button></div> 

        </InstitutionCard>
    );
}
export default SchoolItem;