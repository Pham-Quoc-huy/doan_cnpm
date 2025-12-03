import React, { useEffect, useState } from "react";
import AddSup from "../components/AddSup";
import SupItem from "../components/SupItem";
import "../css/VetProfileSup.css";

const VetProfileSup = () => {
  const [assistants, setAssistants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [activeAssistantId, setActiveAssistantId] = useState(null);
  const [showSidebar, setShowSidebar] = useState(false);

  const jwt = localStorage.getItem("jwt");

  // -------------------------------
  // 🔥 Hàm fetch danh sách trợ lý
  // -------------------------------
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

      // 🌟 FIX: Nếu body rỗng → tránh lỗi Unexpected end of JSON input
      const text = await res.text();
      const data = text ? JSON.parse(text) : [];

      setAssistants(data);
      console.log("Assistants fetched:", data);
    } catch (err) {
      console.error(err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // 👉 useEffect chỉ chạy 1 lần để lấy toàn bộ assistants
  useEffect(() => {
    fetchAssistants();
  }, []);

  // -------------------------------
  // Mở form thêm mới
  // -------------------------------
  const handleAddNew = () => {
    setActiveAssistantId(null);
    setShowSidebar(true);
  };

  // -------------------------------
  // Edit trợ lý
  // -------------------------------
  const handleEdit = (assistantId) => {
    setActiveAssistantId(assistantId);
    setShowSidebar(true);
  };

  // -------------------------------
  // Xóa trợ lý
  // -------------------------------
  const handleDelete = async (assistantId) => {
    if (!window.confirm("Bạn có chắc muốn xóa trợ lý này?")) return;

    try {
      const res = await fetch(
        `http://localhost:8080/api/vets/assistants/${assistantId}`,
        {
          method: "DELETE",
          headers: { Authorization: `Bearer ${jwt}` },
        }
      );

      if (!res.ok) throw new Error("Xóa thất bại");

      // Cập nhật UI ngay lập tức
      setAssistants((prev) => prev.filter((a) => a.id !== assistantId));
    } catch (err) {
      alert(err.message);
    }
  };

  // Tìm assistant đang edit
  const activeAssistant = assistants.find(
    (a) => a.id === activeAssistantId
  );

  // Khi form thêm/sửa lưu thành công → reload list
  const handleSaved = () => {
    setShowSidebar(false);
    setActiveAssistantId(null);
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
            onEdit={() => handleEdit(a.id)}
            onDelete={() => handleDelete(a.id)}
          />
        ))}
      </div>

      {/* Form thêm / sửa */}
      {showSidebar && (
        <div className="sidebar-form">
          <AddSup
            assistant={activeAssistant}
            onCreated={handleSaved}
            onCancel={() => setShowSidebar(false)}
          />
        </div>
      )}
    </div>
  );
};

export default VetProfileSup;
