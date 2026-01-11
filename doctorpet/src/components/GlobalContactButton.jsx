import React, { useState, useEffect } from "react";
import ContactButton from "./ContactButton";
import ChatBox from "../message/ChatBox";
import Swal from "sweetalert2";
import "remixicon/fonts/remixicon.css";

const GlobalContactButton = () => {
  const [isChatOpen, setIsChatOpen] = useState(false);
  const [isChatMinimized, setIsChatMinimized] = useState(false);
  const [selectedAppointmentId, setSelectedAppointmentId] = useState(null);
  const [allAppointments, setAllAppointments] = useState([]);
  const [selectedAppointment, setSelectedAppointment] = useState(null);

  // Lấy thông tin user hiện tại
  const getCurrentUser = () => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      try {
        const user = JSON.parse(savedUser);
        const fullName = `${user.firstName} ${user.lastName}`.trim();
        return {
          id: user.id,
          name: fullName || "Bạn",
        };
      } catch {
        return null;
      }
    }
    return {
      id: null,
      name: "Bạn",
    };
  };

  // Lấy danh sách appointments khi user đã đăng nhập
  useEffect(() => {
    const jwt = localStorage.getItem("jwt");
    const user = localStorage.getItem("user");
    
    if (!jwt || !user) {
      setAllAppointments([]);
      return;
    }

    const fetchAppointments = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/appointments", {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!response.ok) return;
        
        const data = await response.json();
        const appointmentList = data.content || data;
        setAllAppointments(appointmentList);
      } catch (err) {
        console.error("Lỗi khi lấy appointments:", err);
      }
    };

    fetchAppointments();
    
    // Polling mỗi 10 giây để cập nhật danh sách appointments
    const interval = setInterval(fetchAppointments, 10000);
    return () => clearInterval(interval);
  }, []);

  // Lấy thông tin appointment được chọn
  useEffect(() => {
    if (!selectedAppointmentId) {
      setSelectedAppointment(null);
      return;
    }
    const appointment = allAppointments.find((appt) => appt.id === selectedAppointmentId);
    setSelectedAppointment(appointment);
  }, [selectedAppointmentId, allAppointments]);

  // Xử lý khi click nút "Liên hệ"
  const handleContactClick = () => {
    const jwt = localStorage.getItem("jwt");
    const user = localStorage.getItem("user");

    // Kiểm tra đăng nhập
    if (!jwt || !user) {
      Swal.fire({
        title: "Vui lòng đăng nhập",
        text: "Bạn cần đăng nhập để sử dụng tính năng liên hệ.",
        icon: "info",
        confirmButtonText: "Đăng nhập ngay",
        confirmButtonColor: "#ed8a43",
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = "/login";
        }
      });
      return;
    }

    // Kiểm tra có appointments không
    if (allAppointments.length > 0) {
      // Lấy appointment đầu tiên
      const firstAppointment = allAppointments[0];
      setSelectedAppointmentId(firstAppointment.id);
      setIsChatOpen(true);
      setIsChatMinimized(false);
    } else {
      // Nếu chưa có appointment, thông báo cho user
      Swal.fire({
        title: "Chưa có lịch hẹn",
        text: "Bạn cần đặt lịch hẹn trước khi có thể liên hệ với bác sĩ.",
        icon: "info",
        confirmButtonText: "Đặt lịch ngay",
        confirmButtonColor: "#ed8a43",
      }).then((result) => {
        if (result.isConfirmed) {
          // Kiểm tra user role để chuyển đến trang phù hợp
          try {
            const userData = JSON.parse(user);
            const authorities = userData.authorities || [];
            
            if (authorities.includes("ROLE_USER")) {
              window.location.href = "/user";
            } else {
              window.location.href = "/login";
            }
          } catch {
            window.location.href = "/login";
          }
        }
      });
    }
  };

  return (
    <>
      {/* Ẩn nút "Tư vấn" khi khung chat đang mở (kể cả khi thu nhỏ) */}
      {!isChatOpen && <ContactButton onClick={handleContactClick} />}
      
      {isChatOpen && selectedAppointmentId && (
        <ChatBox
          appointmentId={selectedAppointmentId}
          currentUser={getCurrentUser()}
          recipientName={
            selectedAppointment?.vet?.fullName ||
            selectedAppointment?.vet?.name ||
            "Bác sĩ"
          }
          recipientAvatar={selectedAppointment?.vet?.avatar}
          isOpen={isChatOpen}
          isMinimized={isChatMinimized}
          onClose={() => {
            setIsChatOpen(false);
            setSelectedAppointmentId(null);
          }}
          onMinimize={() => setIsChatMinimized(!isChatMinimized)}
        />
      )}
    </>
  );
};

export default GlobalContactButton;

