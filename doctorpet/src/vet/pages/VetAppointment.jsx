import React from "react";
import { useState, useEffect } from "react";
import ScheduleItem from "../components/ScheduleItem";
import DetailAppointment from "../components/DetailAppointment";

const VetAppointment = (props) => {
  const [appointments, setAppointments] = useState([]);
  const jwt = localStorage.getItem("jwt");

  useEffect(() => {
    if (!jwt) return;

    fetch("http://localhost:8080/api/appointments", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => {
        // Lọc appointments phân công cho vet hiện tại
        const vetAppointments = data.filter(
          (appt) => appt.vet?.id === props.vetId
        );
        console.log("Appointments của vet:", vetAppointments);
        setAppointments(vetAppointments);
      })
      .catch((err) => console.error("Lỗi khi lấy appointments:", err));
  }, [jwt, props.vetId]);
  const [detailId, setDetailId] = useState(null);
  return (
    <div className="appointments-list" style={{ width: "100%" }}>
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
          {appointments.map((item) => (
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
          ))}
        </div>
      )}
    </div>
  );
};

export default VetAppointment;
