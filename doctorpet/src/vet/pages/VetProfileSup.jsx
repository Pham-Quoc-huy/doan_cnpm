import React, { useEffect, useState } from "react";
import AddSup from "../components/AddSup";
import SupItem from "../components/SupItem";
import "../css/VetProfileSup.css";

const VetProfileSup = () => {
  const [assistants, setAssistants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showSidebar, setShowSidebar] = useState(false);
  const [editingAssistant, setEditingAssistant] = useState(null);

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

      console.log("Assistants fetched:", data);
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

  // 
  const handleAddNew = () => {
    setEditingAssistant(null);
    setShowSidebar(true);
  };

  // 
  const handleEdit = (assistant) => {
    setEditingAssistant(assistant);
    setShowSidebar(true);
  };

  // 
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

      setAssistants((prev) => prev.filter((a) => a.id !== assistantId));
    } catch (err) {
      alert(err.message);
    }
  };

  //
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

      <div className="assistant-list">
        {assistants.length === 0 && !loading && <p>Không có trợ lý nào.</p>}

        {assistants.map((a) => (
          <SupItem
            key={a.id}
            assistant={a}
            onEdit={handleEdit}
            onDelete={() => handleDelete(a.id)}
          />
        ))}
      </div>

      {showSidebar && (

        <div className="sidebar-form">
          <AddSup
            assistant={editingAssistant}
            onCreated={handleSaved}
            onCancel={() => setShowSidebar(false)}
          />
        </div>
      )}
    </div>
  );
};

export default VetProfileSup;
