import StudentAppItem from "./StudentAppItem";
import CancelApplication from "./CancelApplication";
import ModifyFaultyData from "./ModifyFaultyData";
import Container from 'react-bootstrap/Container';
import './StudentApplications.css';
import React, { useState, useEffect } from "react";

function ISODashboard() {
    const [name, setName] = useState("");
    const [selectedFile, setSelectedFile] = useState(null);
    
    return (
        <div>
            <form>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <input
                    type="file"
                    value={selectedFile}
                    onChange={(e) => setSelectedFile(e.target.files[0])}
                />
            </form>
        </div>
    );
};

export default ISODashboard;