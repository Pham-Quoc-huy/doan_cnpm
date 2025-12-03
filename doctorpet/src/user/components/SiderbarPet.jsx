import React, { useState, useEffect } from "react";
import "../css/SidebarPet.css";

const SiderbarPet = (props) => {
  const pet = props.petItem;
  const [formData, setFormData] = useState({
    name: "",
    species: "",
    breed: "",
    sex: "",
    dateOfBirth: "",
    weight: "",
    allergies: "",
    notes: "",
    imageUrl: "",
    ownerId: props.ownerId,
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (pet) {
      setFormData({
        name: pet.name || "",
        species: pet.species || "",
        breed: pet.breed || "",
        sex: pet.sex || "",
        dateOfBirth: pet.dateOfBirth || "",
        weight: pet.weight || "",
        allergies: pet.allergies || "",
        notes: pet.notes || "",
        imageUrl: pet.imageUrl || "",
        ownerId: props.ownerId,
      });
    } else {
      setFormData({
        name: "",
        species: "",
        breed: "",
        sex: "",
        dateOfBirth: "",
        weight: "",
        allergies: "",
        notes: "",
        imageUrl: "",
        ownerId: props.ownerId,
      });
    }
    setError("");
  }, [pet]);

  // xử lý input
  const handleChange = (e) => {
    // Nếu input là cân nặng, đảm bảo nó là số dương hoặc rỗng
    if (
      e.target.name === "weight" &&
      e.target.value !== "" &&
      Number(e.target.value) < 0
    ) {
      // Có thể thêm setError tại đây nếu muốn cảnh báo ngay lập tức
      return;
    }
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // submit form
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    // 1. Validation cơ bản (Đã có, giữ nguyên)
    if (
      !formData.name ||
      !formData.breed ||
      !formData.sex ||
      !formData.dateOfBirth
    ) {
      setError("Vui lòng điền đầy đủ các trường bắt buộc (*)");
      return;
    }

    // 2. Cải tiến validation cho Cân nặng (Cho phép rỗng, nhưng phải là số dương nếu có)
    const weightValue = Number(formData.weight);
    if (formData.weight !== "" && (isNaN(weightValue) || weightValue <= 0)) {
      setError("Cân nặng phải là một số lớn hơn 0 (hoặc để trống)");
      return;
    }
    if (imageFile) {
    setFormData.imageUrl("image", imageFile); // đính kèm file
  }
    setLoading(true); 
    try {
      // Gửi dữ liệu đi (chỉ gửi các trường cần thiết, tránh gửi các trường có thể gây lỗi nếu rỗng)
      await props.handleSavePet(formData);

      // *THÊM*: Sau khi lưu thành công, bạn có thể thông báo nhỏ hoặc tự động đóng form
    } catch (err) {
      console.error("Lỗi khi lưu:", err);
      // THAY ĐỔI: Sử dụng thông báo lỗi trả về từ ProfilePet nếu có
      setError(err.message || "Lỗi khi lưu dữ liệu. Vui lòng thử lại.");
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = (e) => {
    e.preventDefault();
    props.handleCancelPetForm();
  };
  const [imageFile, setImageFile] = useState(null);
  const handleImageChange = (e) => {
  const file = e.target.files[0];
  if (!file) return;
  setImageFile(file);
};

  return (
    <div className={`pet-sidebar show`}>
      <form className="pet-form" onSubmit={handleSubmit}>
        <h3>Thú Cưng Của Bạn</h3>

        {error && <p className="error-message">{error}</p>}

        <div className="row">
          <div className="column">
            <label className="pet-form-label">Tên thú cưng*</label>
            <input
              className="pet-form-input"
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>

          <div className="column">
            <label className="pet-form-label">Loài</label>
            <input
              className="pet-form-input"
              type="text"
              name="species"
              value={formData.species}
              onChange={handleChange}
              placeholder="VD: Huy, Chó, Mèo,..."
            />
          </div>

          <div className="column">
            <label className="pet-form-label">Giống loài*</label>
            <input
              className="pet-form-input"
              type="text"
              name="breed"
              value={formData.breed}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="row">
          <div className="column">
            <label className="pet-form-label">Giới tính*</label>
            <select
              className="pet-form-select"
              name="sex"
              value={formData.sex}
              onChange={handleChange}
              required
            >
              <option value="">--Chọn giới tính--</option>
              <option value="Đực">Đực</option>
              <option value="Cái">Cái</option>
            </select>
          </div>
          <div className="column">
            <label className="pet-form-label">Ngày sinh*</label>
            <input
              className="pet-form-input"
              type="date"
              name="dateOfBirth"
              value={formData.dateOfBirth}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="row">
          <div className="column">
            <label className="pet-form-label">Cân nặng (kg)</label>
            <input
              className="pet-form-input"
              type="number"
              name="weight"
              value={formData.weight}
              onChange={handleChange}
              min="0"
            />
          </div>
          <div className="column">
            <label className="pet-form-label">Dị ứng</label>
            <input
              className="pet-form-input"
              type="text"
              name="allergies"
              value={formData.allergies}
              onChange={handleChange}
            />
          </div>
        </div>

        <label className="pet-form-label">Ghi chú</label>
        <textarea
          className="pet-form-textarea"
          name="notes"
          value={formData.notes}
          onChange={handleChange}
        ></textarea>

        <label className="pet-form-label">Chọn ảnh</label>
        <input type="file" accept="image/*" onChange={handleImageChange} />

        <div className="sb-footer">
          <button className="save-btn" type="submit" disabled={loading}>
            {loading ? "Đang lưu..." : "Lưu"}
          </button>
          <button
            className="cancel-btn"
            onClick={handleCancel}
            disabled={loading}
          >
            Hủy
          </button>
        </div>
      </form>
    </div>
  );
};

export default SiderbarPet;
