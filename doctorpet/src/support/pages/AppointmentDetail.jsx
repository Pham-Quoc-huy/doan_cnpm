import React, { useEffect, useState } from "react";
import "remixicon/fonts/remixicon.css";
import "../css/AppointmentDetail.css";
import { useParams } from "react-router-dom";
import { formatDateTime, getLocation, getBadgeClass } from '../components/appointmentFormatter';
import ChatBox from "../components/Chatbox.jsx";

const AppointmentDetail = () => {
    const { id } = useParams();
    const [appointment, setAppointment] = useState(null);
    const [loading, setLoading] = useState(true);
    const currentUser = JSON.parse(localStorage.getItem("user"));

    const fetchAppointment = async () => {
        const jwt = localStorage.getItem("jwt");
        if (!jwt) {
            alert("Không tìm thấy mã xác thực. Vui lòng đăng nhập lại.");
            return;
        }

        try {
            setLoading(true);
            const res = await fetch(`http://localhost:8080/api/appointments/${id}`, {
                headers: { Authorization: `Bearer ${jwt}` }
            });

            const data = await res.json();
            setAppointment(data);
        } catch (err) {
            console.error("Lỗi fetch:", err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchAppointment();
    }, [id]);

    // const formatDateTime = (timeStr) => {
    //     if (!timeStr) return "";
    //     const d = new Date(timeStr);
    //     const pad = (n) => n.toString().padStart(2, "0");
    //     return `${pad(d.getDate())}/${pad(d.getMonth() + 1)}/${d.getFullYear()} - ${pad(d.getHours())}:${pad(d.getMinutes())}`;
    // };

    // const getBadgeClass = (type) => {
    //     const map = {
    //         EMERGENCY: "badge-emergency",
    //         NORMAL: "badge-normal",
    //     };
    //     return map[type] || "badge";
    // };

    // const getLocation = (loc) => {
    //     return loc === "AT_HOME"
    //         ? "Tại nhà"
    //         : loc === "AT_CLINIC"
    //             ? "Tại phòng khám"
    //             : loc === "ONLINE"
    //                 ? "Tư vấn online"
    //                 : loc;
    // };

    if (loading) return <p>Đang tải dữ liệu...</p>;
    if (!appointment) return <p>Không tìm thấy lịch hẹn.</p>;

    return (
        <div className="detail-content">
            <div className="detail-wrapper">
                <h2 className="detail-title">
                    <i className="ri-file-list-3-line"></i>
                    Chi tiết lịch hẹn
                </h2>

                <div className="detail-card">
                    {/* Header */}
                    <div className="detail-header">
                        <div className="detail-pet">
                            <div className="pet-avatar-detail">
                                {appointment.pet?.name?.charAt(0)}
                            </div>
                            <div>
                                <h3 className="pet-name-detail">{appointment.pet?.name}</h3>
                                <p className="pet-vet-detail">với BS: {appointment.vet.lastName + " " + appointment.vet.firstName}</p>
                            </div>
                        </div>

                        <span className={`badge-detail ${getBadgeClass(appointment.appointmentType)}`}>
                            {appointment.appointmentType}
                        </span>
                    </div>

                    {/* Info */}
                    <div className="detail-info-grid">
                        <div className="info-row">
                            <i className="ri-calendar-line icon-info"></i>
                            <div>
                                <strong>Thời gian</strong>
                                <p>{formatDateTime(appointment.timeStart)}</p>
                            </div>
                        </div>

                        <div className="info-row">
                            <i
                                className={`icon-info ${appointment.locationType === "AT_HOME"
                                    ? "ri-home-4-line text-green-600"
                                    : appointment.locationType === "AT_CLINIC"
                                        ? "ri-hospital-line text-indigo-600"
                                        : "ri-video-chat-line text-purple-600"
                                    }`}
                            ></i>
                            <div>
                                <strong>Hình thức</strong>
                                <p>{getLocation(appointment.locationType)}</p>
                            </div>
                        </div>
                        <div className="type">
                            <i className="ri-stethoscope-line" style={{ color: "#0ea5e9" }}></i>
                            <strong> Loại khám: </strong> {appointment.type}
                        </div>

                        {/* <div className="info-row">
                            <i className="ri-time-line icon-info text-orange-600"></i>
                            <div>
                                <strong>Trạng thái</strong>
                                <p className="text-orange-700 font-medium">
                                    {appointment.status}
                                </p>
                            </div>
                        </div> */}
                    </div>

                    {/* Notes */}
                    {appointment.notes && (
                        <div className="detail-section">
                            <h4 className="section-title">
                                <i className="ri-file-text-line"></i> Ghi chú
                            </h4>
                            <p className="notes-text">{appointment.notes}</p>
                        </div>
                    )}

                    {/* Footer */}
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
                <ChatBox appointmentId={id} currentUser={currentUser} />
            </div>
        </div>
    );
};

export default AppointmentDetail;
