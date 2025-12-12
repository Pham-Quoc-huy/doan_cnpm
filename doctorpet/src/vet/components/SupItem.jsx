import React from "react";
import "../css/SupItem.css";

const SupItem = ({ assistant, onEdit, onDelete, onViewSchedule }) => {
  // Lấy assistantId: ưu tiên assistantId, nếu không có thì dùng id
  const assistantId =
    assistant.assistantId || assistant.assistant_id || assistant.id;

  return (
    <div className="sup-card">
      <p>
        <strong>Email:</strong> {assistant.email}
      </p>
      <p>
        <strong>Name:</strong> {assistant.firstName} {assistant.lastName}
      </p>

      <div className="sup-actions">
        <button className="btn-edit" onClick={() => onEdit(assistant)}>
          Sửa
        </button>
        <button className="btn-delete" onClick={() => onDelete(assistant.id)}>
          Xóa
        </button>
        {onViewSchedule && (
          <button
            className="btn-schedule"
            onClick={() => onViewSchedule(assistantId)}
          >
            Xem lịch
          </button>
        )}
      </div>
    </div>
  );
};

export default SupItem;
