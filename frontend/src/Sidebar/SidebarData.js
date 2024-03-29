import React from "react";
import HomeIcon from "@mui/icons-material/Home";
import SchoolIcon from '@mui/icons-material/School';
import BorderColorIcon from '@mui/icons-material/BorderColor';
import HistoryEduIcon from '@mui/icons-material/HistoryEdu';
import TableViewIcon from '@mui/icons-material/TableView';

export const SidebarData = [
  {
    title: "Dashboard",
    icon: <HomeIcon />,
    link: "/dashboard",
  },
  {
    title: "Instutitions",
    icon: <SchoolIcon />,
    link: "/instutitions",
  },
  {
    title: "Applications",
    icon: <BorderColorIcon />,
    link: "/applications",
  },
  {
    title: "Courses ",
    icon: <HistoryEduIcon />,
    link: "/courses",
  },
  {
    title: "Forms",
    icon: <TableViewIcon />,
    link: "/forms",
  },
];