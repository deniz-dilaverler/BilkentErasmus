import React from 'react';

import './InstitutionCard.css';

function InstitutionCard(props) {
  const classes = 'card ' + props.className;

  return <div className={classes}>{props.children}</div>;
};

export default InstitutionCard;