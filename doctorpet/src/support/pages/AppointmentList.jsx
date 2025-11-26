import { useEffect, useState } from "react";
import "../css/AppointmentList.css";

const AppointmentList = () => {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [eye, setEye] = useState(null);

  // Giả lập fetch API thật
  const fetchAppointments = async () => {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) {
      alert("Không tìm thấy mã xác thực. Vui lòng đăng nhập lại.");
      return;
    }
    try {
      setLoading(true);

      const res = await fetch("http://localhost:8080/api/appointments", {
        headers: {
          Authorization: `Bearer ${jwt}`
        }
      }
      );
      const data = await res.json();

      setAppointments(data); // data là mảng nhiều lịch
    } catch (err) {
      console.error("Lỗi fetch:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAppointments();
  }, []);

  const formatDateTime = (timeStr) => {
    const d = new Date(timeStr);
    const pad = (n) => n.toString().padStart(2, '0');
    return `${pad(d.getDate())}/${pad(d.getMonth() + 1)}/${d.getFullYear()} - ${pad(d.getHours())}:${pad(d.getMinutes())}`;
  };

  const getBadgeClass = (type) => {
    const map = {
      EMERGENCY: "badge-emergency",
      NORMAL: "badge-normal"
    };
    return map[type] || "badge";
  };

  const getLocation = (loc) => {
    return loc === "AT_HOME" ? "Tại nhà" :
      loc === "AT_CLINIC" ? "Tại phòng khám" :
        loc === "ONLINE" ? "Tư vấn online" : loc;
  };

  const onDetailClick = (app) => {
    alert(`Xem chi tiết lịch của ${app.pet.name}`);
  };

  return (
    <div className="main-content">
      <div className="content-wrapper">
        {/* <h2>Danh sách lịch hẹn</h2>

        <button className="btn-refresh" onClick={fetchAppointments}>
          Làm mới dữ liệu
        </button> */}

        {loading ? (
          <p>Đang tải dữ liệu...</p>
        ) : appointments.length === 0 ? (
          <p>Không có lịch nào.</p>
        ) : (
          appointments.map((app) => (
            <div key={app.id} className="appointment-card">
              <div className="card-header">
                <div className="pet-info">
                  <div className="pet-avatar">{app.pet.name[0]}</div>
                  <div>
                    <h3 className="pet-name">{app.pet.name}</h3>
                    <p className="pet-vet">với {app.vet.name}</p>
                  </div>
                </div>
                <div className={`appointmentType ${getBadgeClass(app.appointmentType)}`}>
                  {app.appointmentType === "EMERGENCY" ? "KHẨN CẤP" : "Bình Thường"}
                </div>
              </div>

              <div className="info-grid">
                <div className="info-item">
                  <i className="ri-calendar-line icon" style={{ color: "#2563eb" }}></i>
                  <span>
                    <strong>Thời gian:</strong> {formatDateTime(app.timeStart)}
                  </span>
                </div>

                <div className="info-item">
                  <i
                    className={`icon ${app.locationType === "AT_HOME"
                      ? "ri-home-4-line"
                      : app.locationType === "AT_CLINIC"
                        ? "ri-hospital-line"
                        : "ri-video-chat-line"
                      }`}
                  ></i>
                  <span>
                    <strong>Hình thức:</strong> {getLocation(app.locationType)}
                  </span>
                </div>

                <div className="type">
                  <i className="ri-stethoscope-line" style={{ color: "#0ea5e9" }}></i>
                  <strong> Loại khám: </strong> {app.type}
                </div>
              </div>

              <div className="info-grid">
                <div className="info-item">
                  <i className="ri-file-text-line icon" style={{ color: "#6b7280" }}></i>
                  <span>
                    <strong>Ghi chú:</strong> {app.notes || "Không có"}
                  </span>
                </div>
              </div>

              <div className="detail-button-container">
                <button
                  className="btn-detail"
                  onClick={() => onDetailClick(app)}
                  onMouseEnter={() => setEye(app.id)}
                  onMouseLeave={() => setEye(null)}
                >
                  <i className={eye === app.id ? "ri-eye-line" : "ri-eye-off-line"}></i>
                  Xem chi tiết
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default AppointmentList;

