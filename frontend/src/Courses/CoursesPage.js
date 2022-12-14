import React, { useState, useEffect } from "react";
import { Modal } from "react-bootstrap";
import "./Courses.css";

const CoursesPage = () => {
  const [courses, setCourses] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/courses")
      .then((response) => response.json())
      .then((courses) => setCourses(courses));
  }, []);

  const [filteredItems, setFilteredItems] = useState(courses);

  const openModal = (course) => {
    setSelectedCourse(course);
  };

  const closeModal = () => {
    setSelectedCourse(null);
  };

  function handleFilter() {
    //only show approved for now
    setFilteredItems(courses.filter((course) => course.isApproved === true));
  }

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>Course Name</th>
            <th>School Name</th>
            <th>Equivalent Course</th>
            <th>Approval Status</th>
            <th>Details</th>
            <td>
              <button>Add Course</button>
            </td>
            <td>
              <button onClick={handleFilter}>Filter</button>
            </td>
          </tr>
        </thead>
        <tbody>
          {filteredItems.map((course) => (
            <tr key={course.id}>
              <td>{course.name}</td>
              <td>{course.school}</td>
              <td>{course.equivalent}</td>
              <td>{course.isApproved ? "Approved" : "Not Approved"}</td>
              <td>
                <button onClick={() => openModal(course)}>Details</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Modal
        isOpen={selectedCourse !== null}
        onRequestClose={closeModal}
        shouldCloseOnOverlayClick={true}
      >
        {selectedCourse !== null && (
          <div>
            <h2>{selectedCourse.name}</h2>
            <p>{selectedCourse.details}</p>
            <button onClick={closeModal}>Close</button>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default CoursesPage;
