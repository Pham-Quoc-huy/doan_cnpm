import "../css/ScheduleItem.css";
const ScheduleItem = (props) => {
  const handleViewDetail = () => {
    props.onDetail(props.id); // báo lên parent
  };
  return (
    <>
      <div>
        <div className="appointment-card">
          <div className="card-header">
            <div className="pet-info">
              <div className="pet-avatar">{props.pet.name}</div>
              <div>
                <h3 className="pet-name">{props.pet.name}</h3>
                <p className="pet-vet">với B.sĩ: {props.nameVet}</p>
              </div>
            </div>
            <div className="column">
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
              <button className="view-detail-btn" onClick={handleViewDetail}>
                Xem chi tiết
              </button>
            </div>
          </div>

          <div className="info-grid">
            <div className="info-item">
              <i
                className="ri-calendar-line icon"
                style={{ color: "#2563eb" }}
              ></i>
              <span>
                <strong>Thời gian:</strong>{" "}
                {new Date(props.timeStart).toLocaleString("vi-VN", {
                  day: "2-digit",
                  month: "2-digit",
                  year: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
                })}
              </span>
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
                <strong>Hình thức:</strong>{" "}
                {props.locationType === "AT_HOME"
                  ? "Tại nhà"
                  : props.locationType === "AT_CLINIC"
                  ? "Tại phòng khám"
                  : ""}
              </span>
            </div>
            <div className={`type `}>
              <i
                className="ri-stethoscope-line"
                style={{ color: "#0ea5e9" }}
              ></i>
              <strong> Loại khám: </strong> {""}
              {props.type === "CHECKUP"
                ? "Kiểm tra sức khỏe"
                : props.type === "VACCINE"
                ? "Tiêm chủng"
                : "Phẫu thuật"}
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
              <strong>Trạng thái:</strong>{" "}
              {props.status === "PENDING"
                ? "Chờ duyệt"
                : props.status === "APPROVED"
                ? "Đã duyệt"
                : props.status === "REJECTED"
                ? "Từ chối"
                : "Đổi lịch"}
            </div>
            <div className="info-item"></div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ScheduleItem;
