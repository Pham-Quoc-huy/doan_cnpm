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
        <div>
          <button className={`btn-tab ${activeTab === "TODAY" ? "active" : ""}`}
            onClick={() => setActiveTab("TODAY")}
          >
            Hôm nay
          </button>
          <button className={`btn-tab ${activeTab === "APPROVED" ? "active" : ""}`}
            onClick={() => setActiveTab("APPROVED")}
          >
            Đã duyệt
          </button>
          <button className={`btn-tab ${activeTab === "REJECTED" ? "active" : ""}`}
            onClick={() => setActiveTab("REJECTED")}
          >
            Từ chối
          </button>
          <button className={`btn-tab ${activeTab === "RESCHEDULED" ? "active" : ""}`}
            onClick={() => setActiveTab("RESCHEDULED")}
          >
            Đã đổi lịch
          </button>
        </div>
      )}

      {/* Chi tiết appointment */}
      {detailId ? (
        <DetailAppointment
         data = {appointments}
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
