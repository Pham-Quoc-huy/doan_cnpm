import React, { useEffect, useState } from "react";
import AddSup from "../components/AddSup";
import SupItem from "../components/SupItem";
import "../css/VetProfileSup.css";
import Swal from "sweetalert2";
const VetProfileSup = () => {
  const [assistants, setAssistants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [editingAssistant, setEditingAssistant] = useState(null);
  const [showSidebar, setShowSidebar] = useState(false);
  const [selectedAssistantId, setSelectedAssistantId] = useState(null);
  const [assistantAppointments, setAssistantAppointments] = useState([]);
  const [loadingAppointments, setLoadingAppointments] = useState(false);

  const jwt = localStorage.getItem("jwt");

  const fetchAssistants = async () => {
    try {
      setLoading(true);

      const res = await fetch("http://localhost:8080/api/vets/assistants", {
        headers: {
          Authorization: `Bearer ${jwt}`,
          "Content-Type": "application/json",
        },
      });

      if (!res.ok) throw new Error("Không thể tải danh sách trợ lý");
      const text = await res.text();
      const data = text ? JSON.parse(text) : [];

      setAssistants(data);
    } catch (err) {
      console.error(err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAssistants();
  }, []);

  const handleAddNew = () => {
    setEditingAssistant(null);
    setShowSidebar(true);
  };
  const handleEdit = (assistant) => {
    setEditingAssistant(assistant);
    setShowSidebar(true);
  };

const handleDelete = async (assistantId) => {
  const result = await Swal.fire({
    title: "Xác nhận xóa",
    text: "Bạn có chắc muốn xóa trợ lý này?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Xóa",
    cancelButtonText: "Hủy",
    confirmButtonColor: "#d33",
    cancelButtonColor: "#3085d6",
  });

  // ❌ Người dùng bấm Hủy
  if (!result.isConfirmed) return;

  try {
    // ⏳ Hiện loading
    Swal.fire({
      title: "Đang xóa...",
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      },
    });

    const res = await fetch(
      `http://localhost:8080/api/vets/assistants/${assistantId}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      }
    );

    if (!res.ok) throw new Error("Xóa thất bại");

    // ✅ Cập nhật state
    setAssistants((prev) => prev.filter((a) => a.id !== assistantId));

    // 🎉 Thành công
    Swal.fire({
      icon: "success",
      title: "Đã xóa!",
      text: "Trợ lý đã được xóa thành công.",
      timer: 1500,
      showConfirmButton: false,
    });
  } catch (err) {
    Swal.fire({
      icon: "error",
      title: "Lỗi",
      text: err.message || "Có lỗi xảy ra",
    });
  }
};


  // Lấy lịch của assistant cụ thể
  const handleViewSchedule = async (assistantId) => {
    try {
      setLoadingAppointments(true);
      setSelectedAssistantId(assistantId);

      const res = await fetch(
        `http://localhost:8080/api/vets/assistants/${assistantId}/appointments`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (!res.ok) throw new Error("Không thể tải lịch của trợ lý");

      const data = await res.json();
      setAssistantAppointments(data);
      console.log("Lịch của assistant:", data);
    } catch (err) {
      console.error(err);
      alert(err.message);
      setAssistantAppointments([]);
    } finally {
      setLoadingAppointments(false);
    }
  };


  // Khi form thêm/sửa lưu thành công → reload list
  const handleSaved = () => {
    setShowSidebar(false);
    setEditingAssistant(null);
    fetchAssistants();
  };

  return (
    <div className="vet-profile-sup-container">
      <button className="btn-add" onClick={handleAddNew}>
        Thêm trợ lý
      </button>

      {loading && <p>Đang tải...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* Danh sách trợ lý */}
      <div className="assistant-list">
        {assistants.length === 0 && !loading && (
          <p>Không có trợ lý nào.</p>
        )}

        {assistants.map((a) => (
          <SupItem
            key={a.id}
            assistant={a}
            onEdit={handleEdit}
            onDelete={() => handleDelete(a.id)}
            onViewSchedule={handleViewSchedule}
          />
        ))}
      </div>

      {/* Form thêm / sửa */}
      {showSidebar && (
        <AddSup
          assistant={editingAssistant}
          onCreated={handleSaved}
          onCancel={() => setShowSidebar(false)}
        />
      )}

      {/* Hiển thị lịch của assistant */}
      {selectedAssistantId && (
        <div className="assistant-schedule-section" style={{ marginTop: "20px" }}>
          <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "10px" }}>
            <h3>Lịch của trợ lý</h3>
            <button onClick={() => {
              setSelectedAssistantId(null);
              setAssistantAppointments([]);
            }}>Đóng</button>
          </div>
          
          {loadingAppointments ? (
            <p>Đang tải lịch...</p>
          ) : assistantAppointments.length === 0 ? (
            <p>Không có lịch hẹn nào.</p>
          ) : (
            <div className="appointments-list">
              {assistantAppointments.map((appt) => (
                <div key={appt.id} style={{ 
                  border: "1px solid #ddd", 
                  padding: "10px", 
                  marginBottom: "10px",
                  borderRadius: "5px"
                }}>
                  <p><strong>Thú cưng:</strong> {appt.pet?.name}</p>
                  <p><strong>Thời gian:</strong> {new Date(appt.timeStart).toLocaleString("vi-VN")}</p>
                  <p><strong>Trạng thái:</strong> {appt.status}</p>
                  <p><strong>Loại:</strong> {appt.appointmentType}</p>
                </div>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default VetProfileSup;
