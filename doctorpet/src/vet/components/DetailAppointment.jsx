import React, { useEffect, useState } from "react";
import "../css/DetailAppointment.css";
import Swal from "sweetalert2";
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
      Swal.fire({
        icon: "error",
        title: "Không thể duyệt lịch!",
      });
    }
  };
  // từ chối
  const handleReject = async () => {
    if (!rejectReason.trim()) return alert("Vui lòng nhập lý do từ chối");

    try {
      const res = await fetch(
        `http://localhost:8080/api/vet/appointments/${appointmentId}/reject`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${vetToken}`,
          },
          body: JSON.stringify(rejectReason),
        }
      );

      if (!res.ok) throw new Error("Từ chối lịch thất bại");

      const updated = await res.json();
      setAppointment(updated);
      onApproved?.(updated);
      Swal.fire({
        icon: "success",
        title: "Từ chối thành công!",
      });
    } catch (err) {
      console.error(err);
      Swal.fire({
        icon: "error",
        title: "Lỗi!",
        text: "Từ chối thất bại.",
      });
    }
  };
  // đổi lịch
  const handleReschedule = async () => {
    if (!newDate) return alert("Vui lòng chọn ngày giờ mới");

    try {
      // Chuyển sang ISOString để backend parse thành ZonedDateTime
      const newTimeStart = new Date(newDate).toISOString();

      const res = await fetch(
        `http://localhost:8080/api/vet/appointments/${appointmentId}/reschedule`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${vetToken}`,
          },
          body: JSON.stringify({
            newTimeStart,
            notes: note, // có thể dùng chung notes
          }),
        }
      );

      if (!res.ok) throw new Error("Đổi lịch thất bại");

      const updated = await res.json();
      setAppointment(updated);
      onApproved?.(updated);
      Swal.fire({
        icon: "success",
        title: "Đổi lịch thành công!",
        text: `Lịch hẹn đã được cập nhật.`,
      });
    } catch (err) {
      console.error(err);
      Swal.fire({
        icon: "error",
        title: "Lỗi!",
        text: "Đổi lịch thất bại.",
      });
    }
  };
  if (loading) return <div>Đang tải chi tiết lịch hẹn...</div>;
  if (error) return <div style={{ color: "red" }}>{error}</div>;
  if (!appointment) return <div>Không tìm thấy lịch hẹn.</div>;

  return (
    <div className="appointment-detail">
      <div className="row">
        <button className="back-btn" onClick={onBack}>
          ⬅️ Quay lại danh sách
        </button>
      </div>

      <p>
        <strong>ID:</strong> {appointment.id}
      </p>
      <p>
        <strong>Trạng thái:</strong> {appointment.status}
      </p>
      <p>
        <strong>Thời gian bắt đầu:</strong>{" "}
        {new Date(appointment.timeStart).toLocaleString()}
      </p>
      <p>
        <strong>Thời gian kết thúc:</strong>{" "}
        {new Date(appointment.timeEnd).toLocaleString()}
      </p>
      <p>
        <strong>Pet:</strong> {appointment.pet?.name || "Chưa có thông tin"}
      </p>
      <p>
        <strong>Owner:</strong> {appointment.owner?.name || "Chưa có thông tin"}
      </p>
      <p>
        <strong>Ghi chú:</strong> {appointment.notes || "Không có"}
      </p>

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
