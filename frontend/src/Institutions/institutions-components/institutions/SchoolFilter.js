import React from 'react';

import './SchoolFilter.css';

const SchoolFilter = (props) => {

    const filterChangeHandler = (event) => {
        console.log(event.target.value);
        props.onChangeFilter(event.target.value);
    }
  return (
    <div className='expenses-filter'>
      <div className='expenses-filter__control'>
        <select value={props.selected} onChange={filterChangeHandler}>
          <option value='Germany'>Germany</option>
          <option value='France'>France</option>
        </select>
      </div>
    </div>
  );
};

export default SchoolFilter;