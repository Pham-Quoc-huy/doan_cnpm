import React from "react";
import "../css/ScheduleItem.css";
const ScheduleItem = (props) => {
  const statusMap = {
    PENDING: "Chờ duyệt",
    APPROVED: "Đã duyệt",
    REJECTED: "Từ chối",
    RESCHEDULED: "Đổi lịch",
  };
  return (
    /*<div className={`history-item ${props.appointmentType === "EMERGENCY" ? "emergency-item" : ""}`}>
      <div className="history-time">
        {new Date(props.timeStart).toLocaleString("vi-VN")}
      </div>

      <div className="history-detail">
        <div className="column-1">
          <h3 className="name-pet">
            Thú cưng: {props.pet?.name || `ID: ${props.pet?.id}`}
          </h3>
          <p>Địa điểm: {props.locationType}</p>
          <p>Loại khám: {props.type}</p>
          <p>
            Loại lịch khám: 
            <span
              className={
                props.appointmentType === "EMERGENCY" ? "emergency" : ""
              }
            >
              {props.appointmentType}
            </span>
          </p>
        </div>
        <div className="column-2">
          <h3 className="name-vet">
            Bác sĩ: {props.vet?.name || `ID: ${props.vet?.id}`}
          </h3>
          <p>Ghi chú:{props.notes || "Không có"}</p>
        </div>
      </div>
      <div className="schedule-details">
        <button className="btn-schedule-details">Xem chi tiết</button>
      </div>
    </div>*/
    <>
      <div>
        <div className="appointment-card">
          <div className="card-header">
            <div className="pet-info">
              <div className="pet-avatar">{props.pet.name}</div>
              <div>
                <h3 className="pet-name">{props.pet.name}</h3>
                <p className="pet-vet">với {props.vet.name}</p>
              </div>
            </div>
            <div
              className={`appointmentType ${
                props.appointmentType === "EMERGENCY" ? "emergency" : ""
              } `}
            >
              <p>
                {props.appointmentType === "EMERGENCY"
                  ? "Khẩn Cấp"
                  : "Bình Thường"}
              </p>
            </div>
          </div>

          <div className="info-grid">
            <div className="info-item">
              <i
                className="ri-calendar-line icon"
                style={{ color: "#2563eb" }}
              ></i>
              <p>
                <strong>Thời gian:</strong>
                {new Date(props.timeStart).toLocaleString("vi-VN")}
              </p>
            </div>

            <div className="info-item">
              <i
                className={`icon ${
                  props.locationType === "AT_HOME"
                    ? "ri-home-4-line"
                    : props.locationType === "AT_CLINIC"
                    ? "ri-hospital-line"
                    : ""
                }`}
              ></i>
              <span>
                <p>
                  <strong>Hình thức:</strong>{" "}
                  {props.locationType === "AT_HOME"
                    ? "Tại nhà"
                    : "Tại phòng khám"}
                </p>
              </span>
            </div>
            <div className={`type `}>
              <i
                className="ri-stethoscope-line"
                style={{ color: "#0ea5e9" }}
              ></i>
              <strong>Loại khám:</strong>{" "}
              {props.type === "SURGERY"
                ? "Phẫu thuật"
                : props.type === "VACCINE"
                ? "Chích Vaccine"
                : "Kiểm tra sức khỏe"}
            </div>
          </div>

          <div className="info-grid">
            <div className="info-item">
              <i
                className="ri-file-text-line icon"
                style={{ color: "#6b7280" }}
              ></i>
              <span>
                <strong>Ghi chú:</strong> {props.notes}
              </span>
            </div>
            <div className="info-item">
              <span>
                <p>
                  <strong>Trạng thái:</strong>{" "}
                  {statusMap[props.status] || props.status}
                </p>
              </span>
            </div>
            <div className="info-item"></div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ScheduleItem;
