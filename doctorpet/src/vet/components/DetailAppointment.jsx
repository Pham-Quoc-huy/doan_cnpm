import React, { useEffect, useState } from "react";

const DetailAppointment = ({ appointmentId, onBack, onApproved }) => {
  const [appointment, setAppointment] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [rejectReason, setRejectReason] = useState("");
  const [newDate, setNewDate] = useState("");
  const vetToken = localStorage.getItem("jwt");

  console.log("DetailAppointment nhận:", appointment, onApproved);

  useEffect(() => {
    const fetchAppointment = async () => {
      setLoading(true);
      setError("");
      try {
        const response = await fetch(
          `http://localhost:8080/api/vet/appointments/${appointmentId}/detail`,
          {
            headers: {
              Authorization: `Bearer ${vetToken}`,
            },
          }
        );
        if (!response.ok) {
          throw new Error("Không thể lấy chi tiết appointment");
        }
        const data = await response.json();
        setAppointment(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchAppointment();
  }, [appointmentId, vetToken]);

  // duyệt lịch
  const [note, setNote] = useState("");

  const handleApprove = async () => {
    try {
      const updated = await fetch(
        `http://localhost:8080/api/vet/appointments/${appointmentId}/approve`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${vetToken}`,
          },
          body: JSON.stringify(note),
        }
      );

      if (!updated.ok) {
        throw new Error("Lỗi duyệt lịch!");
      }

      const result = await updated.json();
      setAppointment(result);

    } catch (error) {
      console.error(error);
      alert("Không thể duyệt lịch!");
    }
  };
  // từ chối 
   const handleReject = async () => {
    if (!rejectReason.trim()) {
      return alert("Vui lòng nhập lý do từ chối");
    }

    try {
      const res = await fetch(
        `http://localhost:8080/api/vet/appointments/${appointmentId}/reject`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${vetToken}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(rejectReason),
        }
      );

      if (!res.ok) throw new Error("Reject thất bại");

      const updated = await res.json();
      setAppointment(updated);
      onApproved?.(updated);
    } catch (err) {
      alert(err.message);
    }
  };
  // đổi lịch 
  const handleReschedule = async () => {
    if (!newDate) return alert("Chọn ngày/giờ mới");

    try {
      const res = await fetch(
        `http://localhost:8080/api/vet/appointments/${appointmentId}/reschedule`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${vetToken}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ newDate }),
        }
      );

      if (!res.ok) throw new Error("Đổi lịch thất bại");

      const updated = await res.json();
      setAppointment(updated);
      onApproved?.(updated);
    } catch (err) {
      alert(err.message);
    }
  };
  if (loading) return <div>Đang tải chi tiết lịch hẹn...</div>;
  if (error) return <div style={{ color: "red" }}>{error}</div>;
  if (!appointment) return <div>Không tìm thấy lịch hẹn.</div>;

  return (
    <div className="appointment-detail">
      <h2>Chi tiết lịch hẹn</h2>
      <button className="back-btn" onClick={onBack}>
        ⬅️ Quay lại danh sách
      </button>

      <p><strong>ID:</strong> {appointment.id}</p>
      <p><strong>Trạng thái:</strong> {appointment.status}</p>
      <p><strong>Thời gian bắt đầu:</strong> {new Date(appointment.timeStart).toLocaleString()}</p>
      <p><strong>Thời gian kết thúc:</strong> {new Date(appointment.timeEnd).toLocaleString()}</p>
      <p><strong>Pet:</strong> {appointment.pet?.name || "Chưa có thông tin"}</p>
      <p><strong>Owner:</strong> {appointment.owner?.name || "Chưa có thông tin"}</p>
      <p><strong>Ghi chú:</strong> {appointment.notes || "Không có"}</p>

      <div style={{ marginTop: "20px" }}>
        {/* Ghi chú duyệt */}
        <textarea
          placeholder="Nhập ghi chú duyệt lịch..."
          value={note}
          onChange={(e) => setNote(e.target.value)}
        ></textarea>

        <div className="actions">
          <button className="approve" onClick={handleApprove}>
            Duyệt lịch
          </button>
        </div>

        {/* REJECT */}
      <div style={{ marginTop: 20 }}>
        <textarea
          placeholder="Lý do từ chối..."
          value={rejectReason}
          onChange={(e) => setRejectReason(e.target.value)}
          rows={3}
          style={{ width: "100%", marginBottom: 10 }}
        />
        <button
          onClick={handleReject}
          disabled={appointment.status !== "PENDING"}
          style={{ background: "red", color: "white" }}
        >
          Từ chối
        </button>
      </div>

      {/* RESCHEDULE */}
      <div style={{ marginTop: 20 }}>
        <label>Ngày giờ mới:</label>
        <input
          type="datetime-local"
          value={newDate}
          onChange={(e) => setNewDate(e.target.value)}
          style={{ width: "100%", marginTop: 5 }}
        />
        <button
          onClick={handleReschedule}
          style={{ marginTop: 10, background: "#007bff", color: "white" }}
        >
          Đổi lịch
        </button>
      </div>
      </div>
    </div>
  );
};

export default DetailAppointment;
