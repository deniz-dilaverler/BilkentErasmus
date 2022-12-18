import React from "react";
import "./Sidebar.css";
import { SidebarData } from "./SidebarData";
import { useState, useEffect } from "react";
import LogoutButton from "../Login/LogoutButton";

function Sidebar(props) {
  const [sidebarVisible, setSidebarVisible] = useState(true);

  useEffect(() => {
    // check if the user is authenticated
    const authToken = sessionStorage.getItem("authToken");
    const username = sessionStorage.getItem("username");

    if (!authToken || !username) {
      // hide the sidebar if the user is not authenticated, true for now
      setSidebarVisible(true);
    }
  }, []);

  if (!sidebarVisible) {
    return null;
  }

  return (
    <div className="Sidebar">
      <ul className="SidebarList">
        {SidebarData.map((val, key) => {
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
        <LogoutButton />
      </ul>
    </div>
  );
}

export default Sidebar;
