import React, { useEffect, useState } from "react";
import ScheduleItem from "../components/ScheduleItem";
import DetailAppointment from "../components/DetailAppointment";

const VetSchedule = ({ vetId }) => {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [activeTab, setActiveTab] = useState("TODAY"); // Tab mặc định là hôm nay
  const [detailId, setDetailId] = useState(null);
  const jwt = localStorage.getItem("jwt");

  useEffect(() => {
    if (!jwt) return;

    const fetchAppointments = async () => {
      try {
        setLoading(true);
        const res = await fetch("http://localhost:8080/api/appointments", {
          headers: { Authorization: `Bearer ${jwt}` },
        });
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        const data = await res.json();
        // Lọc chỉ những lịch của vet hiện tại và trạng thái APPROVED, REJECTED, RESCHEDULED
        const vetAppointments = data.filter(
          (appt) =>
            appt.vet?.id === vetId &&
            ["APPROVED", "REJECTED", "RESCHEDULED"].includes(appt.status)
        );
        setAppointments(vetAppointments);
      } catch (err) {
        console.error(err);
        setError("Không thể tải lịch hẹn");
      } finally {
        setLoading(false);
      }
    };

    fetchAppointments();
  }, [jwt, vetId]);

  if (loading) return <div>Đang tải lịch làm việc...</div>;
  if (error) return <div style={{ color: "red" }}>{error}</div>;

  // Filter theo tab
  const today = new Date();
  const filteredAppointments = appointments.filter((appt) => {
    const apptDate = new Date(appt.timeStart);
    if (activeTab === "TODAY") {
      return (
        apptDate.getFullYear() === today.getFullYear() &&
        apptDate.getMonth() === today.getMonth() &&
        apptDate.getDate() === today.getDate()
      );
    }
    if (activeTab === "APPROVED") return appt.status === "APPROVED";
    if (activeTab === "REJECTED") return appt.status === "REJECTED";
    if (activeTab === "RESCHEDULED") return appt.status === "RESCHEDULED";
    return false;
  });
  return (
    <div style={{ width: "100%" }}>
      {/* NAV / Tabs */}
      {!detailId && (
        <div style={{ display: "flex", gap: "10px", marginBottom: "20px" }}>
          <button
            onClick={() => setActiveTab("TODAY")}
            style={{
              padding: "5px 10px",
              background: activeTab === "TODAY" ? "#007bff" : "#f0f0f0",
              color: activeTab === "TODAY" ? "white" : "black",
            }}
          >
            Hôm nay
          </button>
          <button
            onClick={() => setActiveTab("APPROVED")}
            style={{
              padding: "5px 10px",
              background: activeTab === "APPROVED" ? "#007bff" : "#f0f0f0",
              color: activeTab === "APPROVED" ? "white" : "black",
            }}
          >
            Đã duyệt
          </button>
          <button
            onClick={() => setActiveTab("REJECTED")}
            style={{
              padding: "5px 10px",
              background: activeTab === "REJECTED" ? "#007bff" : "#f0f0f0",
              color: activeTab === "REJECTED" ? "white" : "black",
            }}
          >
            Từ chối
          </button>
          <button
            onClick={() => setActiveTab("RESCHEDULED")}
            style={{
              padding: "5px 10px",
              background: activeTab === "RESCHEDULED" ? "#007bff" : "#f0f0f0",
              color: activeTab === "RESCHEDULED" ? "white" : "black",
            }}
          >
            Đã đổi lịch
          </button>
        </div>
      )}

      {/* Chi tiết appointment */}
      {detailId ? (
        <DetailAppointment
          appointmentId={detailId}
          onBack={() => setDetailId(null)}
          onApproved={(updated) => {
            setAppointments((prev) =>
              prev.map((item) => (item.id === updated.id ? updated : item))
            );
            setDetailId(null);
          }}
        />
      ) : (
        <div>
          {filteredAppointments.length === 0 ? (
            <div>Không có lịch hẹn nào trong mục này.</div>
          ) : (
            filteredAppointments.map((item) => (
              <ScheduleItem
                key={item.id}
                id={item.id}
                pet={item.pet}
                vet={item.vet}
                timeStart={item.timeStart}
                appointmentType={item.appointmentType}
                locationType={item.locationType}
                type={item.type}
                notes={item.notes}
                onDetail={(id) => setDetailId(id)}
              />
            ))
          )}
        </div>
      )}
    </div>
  );
};

export default VetSchedule;
