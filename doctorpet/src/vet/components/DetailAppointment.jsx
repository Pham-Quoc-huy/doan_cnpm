import React, { useEffect, useState } from "react";
import "../css/DetailAppointment.css";
import Swal from "sweetalert2";

const DetailAppointment = ({ appointmentId, onBack, onApproved }) => {
  const [appointment, setAppointment] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const vetToken = localStorage.getItem("jwt");

  // LẤY CHI TIẾT LỊCH HẸN
  useEffect(() => {
    const fetchAppointment = async () => {
      setLoading(true);
      try {
        const res = await fetch(
          `http://localhost:8080/api/vet/appointments/${appointmentId}/detail`,
          {
            headers: { Authorization: `Bearer ${vetToken}` },
          }
        );

        if (!res.ok) throw new Error("Không thể lấy chi tiết lịch hẹn");

        const data = await res.json();
        setAppointment(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchAppointment();
  }, [appointmentId, vetToken]);
  // API DUYỆT LỊCH
  const approveAppointment = async (assistantId, note) => {
    try {
      const res = await fetch(
        `http://localhost:8080/api/vet/appointments/${appointmentId}/approve`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${vetToken}`,
          },
          body: JSON.stringify({
            notes: note || "",
            assistantId: assistantId || null,
          }),
        }
      );

      if (!res.ok) throw new Error("Duyệt lịch thất bại");

      const updated = await res.json();
      setAppointment(updated);
      onApproved?.(updated);

      Swal.fire("Thành công!", "Lịch hẹn đã được duyệt", "success");
    } catch (err) {
      Swal.fire("Lỗi!", err.message, "error");
    }
  };

  // DUYỆT LỊCH - UI
  const handleApprove = async () => {
    const ask = await Swal.fire({
      title: "Phân công trợ lý?",
      text: "Bạn có muốn chọn trợ lý hỗ trợ?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Có",
      cancelButtonText: "Không",
    });

    if (!ask.isConfirmed) {
      approveAppointment(null, "");
      return;
    }

    // Lấy danh sách trợ lý
    try {
      const res = await fetch("http://localhost:8080/api/vets/assistants", {
        headers: { Authorization: `Bearer ${vetToken}` },
      });

      const assistants = await res.json();
      console.log("Assistants:", assistants);
      const optionsHtml = assistants
        .map((a) => `<option value="${a.id}">${a.firstName} ${a.lastName}</option>`)
        .join("");

      const { value: formData } = await Swal.fire({
        title: "Chọn trợ lý & ghi chú",
        html: `
          <select id="assistantSelect" class="swal2-select">${optionsHtml}</select>
          <textarea id="noteInput" class="swal2-textarea" placeholder="Ghi chú..."></textarea>
        `,
        focusConfirm: false,
        preConfirm: () => ({
          assistantId: document.getElementById("assistantSelect").value,
          note: document.getElementById("noteInput").value,
        }),
      });

      if (formData) {
        approveAppointment(formData.assistantId, formData.note);
      }
    } catch (err) {
      Swal.fire("Lỗi!", err.message, "error");
    }
  };

  // TỪ CHỐI 
  const handleReject = async () => {
    const ask = await Swal.fire({
      title: "Đổi lịch mới?",
      text: "Bạn có muốn đề xuất thời gian mới không?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Có",
      cancelButtonText: "Không",
    });

    // 
    if (!ask.isConfirmed) {
      const { value: reason } = await Swal.fire({
        title: "Nhập lý do từ chối",
        input: "textarea",
        inputPlaceholder: "Nhập lý do...",
        showCancelButton: true,
        confirmButtonText: "Xác nhận",
      });

      if (!reason) return;

      try {
        const res = await fetch(
          `http://localhost:8080/api/vet/appointments/${appointmentId}/reject`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${vetToken}`,
            },
            body: JSON.stringify({ reason }),
          }
        );

        const updated = await res.json();
        setAppointment(updated);
        onApproved?.(updated);

        Swal.fire("Đã từ chối lịch!", "", "success");
      } catch (err) {
        Swal.fire("Lỗi!", err.message, "error");
      }

      return;
    }

    // 
    const { value: newDate } = await Swal.fire({
      title: "Chọn ngày giờ mới",
      html: `<input type="datetime-local" id="dateInput" class="swal2-input">`,
      focusConfirm: false,
      preConfirm: () => document.getElementById("dateInput").value,
    });

    if (!newDate) {
      return Swal.fire("Thiếu thông tin!", "Bạn chưa chọn thời gian.", "error");
    }

    try {
      const res = await fetch(
        `http://localhost:8080/api/vet/appointments/${appointmentId}/reschedule`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${vetToken}`,
          },
          body: JSON.stringify({
            newTimeStart: new Date(newDate).toISOString(),
            notes: "Đổi lịch theo yêu cầu",
          }),
        }
      );

      const updated = await res.json();
      setAppointment(updated);
      onApproved?.(updated);

      Swal.fire("Đổi lịch thành công!", "", "success");
    } catch (err) {
      Swal.fire("Lỗi!", err.message, "error");
    }
  };
  // UI 
  if (loading) return <div>Đang tải chi tiết lịch hẹn...</div>;
  if (error) return <div style={{ color: "red" }}>{error}</div>;
  if (!appointment) return <div>Không tìm thấy lịch hẹn.</div>;

  const isPending = appointment.status === "PENDING";

  return (
    <div className="appointment-detail">
      <button className="back-btn" onClick={onBack}>⬅️ Quay lại</button>

      <h2>Chi tiết lịch hẹn</h2>

      <p><strong>ID:</strong> {appointment.id}</p>
      <p><strong>Trạng thái:</strong> {appointment.status}</p>
      <p><strong>Bắt đầu:</strong> {new Date(appointment.timeStart).toLocaleString()}</p>
      <p><strong>Kết thúc:</strong> {new Date(appointment.timeEnd).toLocaleString()}</p>

      <p><strong>Pet:</strong> {appointment.pet?.name}</p>
      <p><strong>Chủ nuôi:</strong> {appointment.owner?.name}</p>
      <p><strong>Ghi chú:</strong> {appointment.notes || "Không có"}</p>

      {/* BUTTON */}
      <div className="btn-group">
        <button className="approve" disabled={!isPending} onClick={handleApprove}>
          Duyệt lịch
        </button>

        <button className="reject" disabled={!isPending} onClick={handleReject}>
          Từ chối
        </button>
      </div>
    </div>
  );
};

export default DetailAppointment;
