import React from "react";
import "../css/Appointment.css";
import { useState } from "react";
const Appointment = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    timeStart: "",
    type: "CHECKUP",
    appointmentType: "NORMAL",
    locationType: "AT_CLINIC",
    notes: "",
    petId: "",
    vetId: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      timeStart: new Date(formData.timeStart).toISOString(),
      type: formData.type,
      appointmentType: formData.appointmentType,
      locationType: formData.locationType,
      notes: formData.notes,
      pet: { id: Number(formData.petId) },
      vet: { id: Number(formData.vetId) },
    };

    if (onSubmit) onSubmit(payload);

    console.log("Dữ liệu gửi:", payload);
  };

  return (
    <form className="form-appointment" onSubmit={handleSubmit}>
      <div className="row-1">
        <div className="column-1">
          <label>Thời gian khám:</label>
          <input
            type="datetime-local"
            name="timeStart"
            value={formData.timeStart}
            onChange={handleChange}
            required
          />
        </div>

        <div className="column-1">
          <label>Loại lịch khám:</label>
          <select
            name="appointmentType"
            value={formData.appointmentType}
            onChange={handleChange}
          >
            <option value="NORMAL">Bình thường</option>
            <option value="EMERGENCY">Khẩn cấp</option>
          </select>
        </div>
      </div>
      <label>Loại khám:</label>
      <select name="type" value={formData.type} onChange={handleChange}>
        <option value="CHECKUP">Kiểm tra sức khỏe</option>
        <option value="VACCINE">Tiêm ngừa</option>
        <option value="SURGERY">Phẫu thuật</option>
      </select>

      <label>Địa điểm:</label>
      <select
        name="locationType"
        value={formData.locationType}
        onChange={handleChange}
      >
        <option value="AT_CLINIC">Tại phòng khám</option>
        <option value="AT_HOME">Tại nhà</option>
      </select>

      <label>Ghi chú:</label>
      <textarea
        name="notes"
        value={formData.notes}
        onChange={handleChange}
        placeholder="Ghi chú nếu có…"
      ></textarea>

      <div className="row-1">
        <div className="column-1">
          <label>Thú cưng:</label>
          <input
            type="number"
            name="petId"
            value={formData.petId}
            onChange={handleChange}
            required
          />
        </div>
        <div className="column-1">
          <label>Bác sĩ thú y:</label>
          <input
            type="number"
            name="vetId"
            value={formData.vetId}
            onChange={handleChange}
            required
          />
        </div>
      </div>

      <button className="btn-appointment" type="submit" style={{ marginTop: "10px" }}>
        Đặt lịch
      </button>
    </form>
  );
};

export default Appointment;
