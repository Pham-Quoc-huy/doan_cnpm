import React, { useEffect, useState } from "react";
import AddSup from "../components/AddSup";
import SupItem from "../components/SupItem";
import "../css/VetProfileSup.css"; // bạn tự tạo style

const VetProfileSup = () => {
  const [assistants, setAssistants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [activeAssistantId, setActiveAssistantId] = useState(null); // đang sửa
  const [showSidebar, setShowSidebar] = useState(false);

  const jwt = localStorage.getItem("jwt");

  // Lấy danh sách trợ lý
  const fetchAssistants = async () => {
    try {
      setLoading(true);
      const res = await fetch("http://localhost:8080/api/vets/assistants", {
        headers: { Authorization: `Bearer ${jwt}` },
      });
      if (!res.ok) throw new Error("Không thể tải danh sách trợ lý");
      const data = await res.json();
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
    setActiveAssistantId(null);
    setShowSidebar(true);
  };

  const handleEdit = (assistantId) => {
    setActiveAssistantId(assistantId);
    setShowSidebar(true);
  };

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
      setAssistants(assistants.filter((a) => a.id !== assistantId));
    } catch (err) {
      alert(err.message);
    }
  };

  const activeAssistant = assistants.find((a) => a.id === activeAssistantId);

  // Khi AddSup submit thành công
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

      <div className="assistant-list">
        {assistants.map((a) => (
          <SupItem
            key={a.id}
            assistant={a}
            onEdit={() => handleEdit(a.id)}
            onDelete={handleDelete}
          />
        ))}
      </div>

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
