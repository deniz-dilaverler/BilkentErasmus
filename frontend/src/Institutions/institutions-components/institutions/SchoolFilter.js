import React from 'react';

import './SchoolFilter.css';

const SchoolFilter = (props) => {
  // filter for country:
    const filterChangeHandler = (event) => {
        console.log(event.target.value);
        props.onChangeFilter(event.target.value);
    }
  return (
    <div>
      <div>
        <select value={props.selected} onChange={filterChangeHandler}>
          <option value='All'>All</option>
          <option value='Germany'>Germany</option>
          <option value='France'>France</option>
          <option value='United Kingdom'>United Kingdom</option>
          <option value='Italy'>Italy</option>
          <option value='Poland'>Poland</option>
          <option value='Denmark'>Denmark</option>
        </select>
      </div>
    </div>
  );
};

export default SchoolFilter;