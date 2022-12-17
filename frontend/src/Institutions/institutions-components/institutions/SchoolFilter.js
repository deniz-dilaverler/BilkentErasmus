import React from 'react';

import './SchoolFilter.css';

const SchoolFilter = (props) => {

    const filterChangeHandler = (event) => {
        console.log(event.target.value);
        props.onChangeFilter(event.target.value);
    }
  return (
    <div>
      <div>
        <select value={props.selected} onChange={filterChangeHandler}>
          <option value='Germany'>Germany</option>
          <option value='France'>France</option>
        </select>
      </div>
    </div>
  );
};

export default SchoolFilter;