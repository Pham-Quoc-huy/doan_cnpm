import React, { useEffect, useState } from "react";
import Swal from "sweetalert2"; // dùng SweetAlert2 cho alert đẹp

const VetProfileSup = () => {
  const [assistants, setAssistants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
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

  // Thêm trợ lý mới
  const handleAddAssistant = async () => {
    const { value: formValues } = await Swal.fire({
      title: "Thêm trợ lý mới",
      html:
        '<input id="swal-firstName" class="swal2-input" placeholder="Họ">' +
        '<input id="swal-lastName" class="swal2-input" placeholder="Tên">' +
        '<input id="swal-email" class="swal2-input" placeholder="Email">' +
        '<input id="swal-password" type="password" class="swal2-input" placeholder="Password">',
      focusConfirm: false,
      preConfirm: () => {
        return {
          firstName: document.getElementById("swal-firstName").value,
          lastName: document.getElementById("swal-lastName").value,
          email: document.getElementById("swal-email").value,
          password: document.getElementById("swal-password").value,
        };
      },
    });

    if (!formValues) return;

    try {
      const res = await fetch("http://localhost:8080/api/vets/assistants", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify(formValues),
      });

      if (!res.ok) throw new Error("Thêm trợ lý thất bại");
      Swal.fire("✅ Thêm thành công!");
      fetchAssistants();
    } catch (err) {
      Swal.fire("❌ Lỗi", err.message, "error");
    }
  };

  // Xóa trợ lý
  const handleDeleteAssistant = async (id) => {
    const result = await Swal.fire({
      title: "Bạn có chắc muốn xóa trợ lý này?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Xóa",
      cancelButtonText: "Hủy",
    });

    if (result.isConfirmed) {
      try {
        const res = await fetch(`http://localhost:8080/api/vets/assistants/${id}`, {
          method: "DELETE",
          headers: { Authorization: `Bearer ${jwt}` },
        });
        if (!res.ok) throw new Error("Xóa thất bại");
        Swal.fire("✅ Xóa thành công!");
        fetchAssistants();
      } catch (err) {
        Swal.fire("❌ Lỗi", err.message, "error");
      }
    }
  };

  // Sửa trợ lý
  const handleEditAssistant = async (assistant) => {
    const { value: formValues } = await Swal.fire({
      title: "Cập nhật trợ lý",
      html:
        `<input id="swal-firstName" class="swal2-input" placeholder="Họ" value="${assistant.firstName || ""}">` +
        `<input id="swal-lastName" class="swal2-input" placeholder="Tên" value="${assistant.lastName || ""}">` +
        `<input id="swal-email" class="swal2-input" placeholder="Email" value="${assistant.email || ""}">`,
      focusConfirm: false,
      preConfirm: () => {
        return {
          firstName: document.getElementById("swal-firstName").value,
          lastName: document.getElementById("swal-lastName").value,
          email: document.getElementById("swal-email").value,
        };
      },
    });

    if (!formValues) return;

    try {
      const res = await fetch(`http://localhost:8080/api/vets/assistants/${assistant.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify(formValues),
      });

      if (!res.ok) throw new Error("Cập nhật thất bại");
      Swal.fire("✅ Cập nhật thành công!");
      fetchAssistants();
    } catch (err) {
      Swal.fire("❌ Lỗi", err.message, "error");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Quản lý Trợ lý</h2>
      <button onClick={handleAddAssistant} style={{ marginBottom: "10px" }}>
        Thêm trợ lý mới
      </button>
      {loading && <div>Đang tải...</div>}
      {error && <div style={{ color: "red" }}>{error}</div>}
      <table border="1" cellPadding="5" style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Họ</th>
            <th>Tên</th>
            <th>Email</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {assistants.map((a) => (
            <tr key={a.id}>
              <td>{a.id}</td>
              <td>{a.firstName}</td>
              <td>{a.lastName}</td>
              <td>{a.email}</td>
              <td>
                <button onClick={() => handleEditAssistant(a)}>Sửa</button>{" "}
                <button onClick={() => handleDeleteAssistant(a.id)}>Xóa</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default VetProfileSup;
