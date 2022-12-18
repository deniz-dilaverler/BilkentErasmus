import React from "react";

import './FormList.css'
function FormList({ title = 'FormList', forms = ['No form'], onViewClick=(form)=>{console.log(form)} }) {
    return (
        <div className="FormList">
            <div className="title">
                <h2>{title}</h2>
            </div>
        </div>
    )

}
export default FormList;