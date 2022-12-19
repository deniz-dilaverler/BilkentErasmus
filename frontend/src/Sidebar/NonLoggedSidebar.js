import React from "react";
import "./Sidebar.css";
import { NonLoggedSidebarData } from "./NonLoggedSidebarData";
import { useState, useEffect } from "react";
function NonLoggedSidebar() {
  return (
    <div className="Sidebar">
      <ul className="SidebarList">
        {NonLoggedSidebarData.map((val, key) => {
          return (
            <li
              key={key}
              className="row"
              id={window.location.pathname === val.link ? "active" : ""}
              onClick={() => {
                window.location.pathname = val.link;
              }}
            >
              <div id="icon">{val.icon}</div>
              <div id="title">{val.title}</div>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

export default NonLoggedSidebar;
