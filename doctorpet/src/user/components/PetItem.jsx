import "remixicon/fonts/remixicon.css";
import "../css/PetItem.css";
import { useState } from "react";

const PetItem = (props) => {
  const [showMenu, setShowMenu] = useState(false);
  const handleShowMenu = () => {
    setShowMenu(!showMenu);
  };
  const handleViewOrEdit = () => {
    props.handleShowSidebarPetID(props.id);
    setShowMenu(false);
  };
  const handleDelete = () => {
    props.handleDeletePet(props.id);
    setShowMenu(false);
  };
  return (
    <div className="pet-item">
      <div className="pet-menu" onClick={handleShowMenu}>
        <i className="ri-more-2-fill"></i>
      </div>
      {showMenu && (
        <div className="pet-option">
          <button className="pet-delete" onClick={handleDelete}>
            Xóa
          </button>
          <button className="pet-update" onClick={handleViewOrEdit}>
            Sửa
          </button>
          <button className="pet-read" onClick={handleViewOrEdit}>
            Xem
          </button>
        </div>
      )}

      <img
        className="pet-avatar"
        src={props.imageUrl || "/assets/meme.jpg"}
        alt={props.name || "pet"}
        onError={(e) => {
          // Nếu ảnh lỗi, hiển thị ảnh mặc định
          if (e.target.src !== "/assets/meme.jpg") {
            e.target.src = "/assets/meme.jpg";
          }
        }}
      />

      <div className="pet-info-profile">
        <div className="pet-field">
          <i className="ri-paw-fill"></i>
          <span className="pet-label">Tên</span>
          <span>{props.name}</span>
        </div>

        <div className="pet-field">
          <i className="ri-calendar-2-fill"></i>
          <span className="pet-label">Sinh</span>
          <span>{props.dateOfBirth}</span>
        </div>

        <div className="pet-field">
          <i className="ri-weight-fill"></i>
          <span className="pet-label">Cân nặng</span>
          <span>{props.weight}</span>
        </div>

        <div className="pet-field">
          <i className="ri-alert-fill"></i>
          <span className="pet-label">Dị ứng</span>
          <span>{props.allergies}</span>
        </div>
      </div>
    </div>
  );
};

export default PetItem;
