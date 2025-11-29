import React from "react";
import "../css/SupItem.css";

const SupItem = ({ assistant, onEdit, onDelete }) => {
  return (
    <div className="sup-card">
      <p><strong>Email:</strong> {assistant.email}</p>
      <p><strong>Name:</strong> {assistant.firstName} {assistant.lastName}</p>

      <div className="sup-actions">
        <button className="btn-edit" onClick={() => onEdit(assistant)}>Sửa</button>
        <button className="btn-delete" onClick={() => onDelete(assistant.id)}>Xóa</button>
      </div>
    </div>
  );
};

export default SupItem;
