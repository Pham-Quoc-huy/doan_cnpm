import React, { useState, useEffect } from "react";
import ScheduleItem from "../components/ScheduleItem";
import "../css/Schedule.css";

const Schedule = () => {
  const [appointments, setAppointments] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  // Lấy JWT từ localStorage
  const jwt = localStorage.getItem("jwt");
  useEffect(() => {
    const fetchAppointments = async () => {
      if (!jwt) {
        setError("Vui lòng đăng nhập để xem lịch hẹn.");
        setIsLoading(false);
        return;
      }

      const headers = {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      };
      
      try {
        const response = await fetch("http://localhost:8080/api/appointments", {
          method: "GET",
          headers: headers,
        });

        if (response.ok) {
          const data = await response.json();
          

          const appointmentList = data.content || data; 
        
          
          setAppointments(appointmentList);
          setError(null);
        } else if (response.status === 401) {
          setError("Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
        } else {
          const errorText = await response.text();
          setError(`Lỗi khi tải lịch hẹn: ${response.status} - ${errorText.substring(0, 100)}`);
          console.error("Phản hồi lỗi server:", errorText);
        }
      } catch (err) {
        console.error("Lỗi kết nối:", err);
        setError("Không thể kết nối đến máy chủ.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchAppointments();
  }, [jwt]);

  if (isLoading) {
    return (
      <div className="schedule">
        <p style={{ color: "#007bff", marginTop: "20px" }}>Đang tải danh sách lịch hẹn...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="schedule">
        <p style={{ color: "red", marginTop: "20px" }}>❌ Lỗi: {error}</p>
      </div>
    );
  }

  if (appointments.length === 0) {
    return (
      <div className="schedule">
        <p style={{ color: "gray", marginTop: "20px" }}>Không có lịch hẹn nào.</p>
      </div>
    );
  }

  return (
    <div className="schedule">
      {appointments.map((item) => (
        <ScheduleItem key={item.id} {...item} />
      ))}
    </div>
  );
};

export default Schedule;