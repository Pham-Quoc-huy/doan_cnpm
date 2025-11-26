import React from "react";
import "remixicon/fonts/remixicon.css";
import "../css/AppointmentDetail.css";

const AppointmentDetail = () => {
    // Lấy lịch hẹn đầu tiên trong mảng
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
        <div className="detail-content">
            <div className="detail-wrapper">

                {/* Tiêu đề */}
                <h2 className="detail-title">
                    <i className="ri-file-list-3-line"></i>
                    Chi tiết lịch hẹn
                </h2>

                {/* Card chính */}
                <div className="detail-card">

                    {/* Header: Thú cưng + Loại hẹn */}
                    <div className="detail-header">
                        <div className="detail-pet">
                            <div className="pet-avatar-detail">
                                {appointment.pet.name[0]}
                            </div>
                            <div>
                                <h3 className="pet-name-detail">{appointment.pet.name}</h3>
                                <p className="pet-vet-detail">với {appointment.vet.name}</p>
                            </div>
                        </div>
                        <span className={`badge-detail ${getBadgeClass(appointment.appointmentType)}`}>
                            {appointment.type}
                        </span>
                    </div>

                    {/* Thông tin cơ bản */}
                    <div className="detail-info-grid">
                        <div className="info-row">
                            <i className="ri-calendar-line icon-info"></i>
                            <div>
                                <strong>Thời gian</strong>
                                <p>{formatDateTime(appointment.timeStart)}</p>
                            </div>
                        </div>
                        <div className="info-row">
                            <i className={`icon-info ${appointment.locationType === 'AT_HOME' ? 'ri-home-4-line text-green-600' :
                                appointment.locationType === 'AT_CLINIC' ? 'ri-hospital-line text-indigo-600' :
                                    'ri-video-chat-line text-purple-600'}`}>
                            </i>
                            <div>
                                <strong>Hình thức</strong>
                                <p>{locationText}</p>
                            </div>
                        </div>
                        <div className="info-row">
                            <i className="ri-time-line icon-info text-orange-600"></i>
                            <div>
                                <strong>Trạng thái</strong>
                                <p className="text-orange-700 font-medium">Đang chờ xác nhận</p>
                            </div>
                        </div>
                    </div>

                    {/* Ghi chú */}
                    {appointment.notes && (
                        <div className="detail-section">
                            <h4 className="section-title">
                                <i className="ri-file-text-line"></i> Ghi chú
                            </h4>
                            <p className="notes-text">{appointment.notes}</p>
                        </div>
                    )}

                    {/* Tổng tiền (nếu có) */}
                    <div className="detail-footer">
                        <div className="price-box">
                            <span className="price-label">Tổng chi phí</span>
                            <span className="price-value">Chưa xác định</span>
                        </div>
                        <div className="action-buttons">
                            <button className="btn-edit">
                                <i className="ri-edit-line"></i> Chỉnh sửa
                            </button>
                            <button className="btn-cancel">
                                <i className="ri-close-line"></i> Hủy hẹn
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AppointmentDetail;