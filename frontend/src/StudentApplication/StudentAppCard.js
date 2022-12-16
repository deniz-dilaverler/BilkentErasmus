import React from 'react';

import './StudentAppCard.css';

function StudentAppCard(props) {
  const classes = 'card ' + props.className;

  return <div className={classes}>{props.children}</div>;
};

export default StudentAppCard;