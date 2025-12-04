import React from "react";
import { useState } from "react";
import Swal from "sweetalert2";
import "../css/AddSup.css";
const AddSup = (props) => {
  const [form, setForm] = useState({
    login: "",
    password: "",
    firstName: "",
    lastName: "",
    email: "",
    langKey: "vi",
    authorities: ["ROLE_ASSISTANT"],
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch("http://localhost:8080/api/vets/assistants", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwt")}`,
        },
        body: JSON.stringify(form),
      });

      if (res.status === 201) {
        Swal.fire({
          title: "Thêm trợ lý thành công!",
          icon: "success",
        });
        setForm({
          login: "",
          password: "",
          firstName: "",
          lastName: "",
          email: "",
          langKey: "vi",
          authorities: ["ROLE_ASSISTANT"],
        });
      } else {
        Swal.fire({
          title: "Thêm thất bại!",
          icon: "error",
        });
      }
    } catch (error) {
      console.error(error);
      setMessage("⚠️ Lỗi kết nối server!");
    }
  };

  return (
    <div className="main-container">

      <div className="add-sup-container">
        <h2>Thêm Trợ Lý</h2>

        {message && <p className="form-message">{message}</p>}

        <form onSubmit={handleSubmit} className="sup-form">

          <div className="name-row">
            <input
              type="text"
              name="firstName"
              placeholder="Họ"
              value={form.firstName}
              onChange={handleChange}
              required
            />
            <input
              type="text"
              name="lastName"
              placeholder="Tên"
              value={form.lastName}
              onChange={handleChange}
            />
          </div>

          <input
            type="email"
            name="email"
            placeholder="Email"
            value={form.email}
            onChange={handleChange}
            required
          />

          <input
            type="text"
            name="login"
            placeholder="Tên đăng nhập"
            value={form.login}
            onChange={handleChange}
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Mật khẩu"
            value={form.password}
            onChange={handleChange}
            required
          />

          <button type="submit">{props.assistant ? "Cập nhật" : "Thêm"}</button>
          <button type="button" onClick={props.onCancel}>
            Hủy
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddSup;
