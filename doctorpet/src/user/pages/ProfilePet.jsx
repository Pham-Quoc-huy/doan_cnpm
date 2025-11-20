import React, { useState, useEffect } from "react";
import PetItem from "../components/PetItem";
import SiderbarPet from "../components/SiderbarPet";
import "../css/ProfilePet.css";

const ProfilePet = (props) => {
  const [pets, setPets] = useState([]);
  const [showSidebarPet, setShowSidebarPet] = useState(false);
  const [activePetItemId, setActivePetItemId] = useState(null);

  const jwt = localStorage.getItem("jwt"); // lấy token

  // Lấy danh sách pet từ API khi component mount
  useEffect(() => {
    const fetchPets = async () => {
      const jwt = localStorage.getItem("jwt"); // Lấy JWT

      if (!jwt) return; // Đảm bảo có token

      try {
        const res = await fetch(
          `http://localhost:8080/api/pets`,
          {
            headers: { Authorization: `Bearer ${jwt}` },
          }
        );

        if (!res.ok) throw new Error("Không thể tải danh sách pet");
        const data = await res.json();
        setPets(data); // Lưu danh sách pets
      } catch (err) {
        console.error("Lỗi khi tải danh sách pet:", err);
      }
    };
    fetchPets();
  }, []);

  const handleShowSidebarPet = () => {
    setActivePetItemId(null);
    setShowSidebarPet(true);
  };

  const handleCancelPetForm = () => {
    setShowSidebarPet(false);
  };

  const handleShowSidebarPetID = (petId) => {
    setActivePetItemId(petId);
    setShowSidebarPet(true);
  };

  const activePetItem = pets.find((pet) => pet.id === activePetItemId);

  // Thêm mới hoặc cập nhật pet qua API
  const handleSavePet = async (data) => {
    // 3. Tiêm ownerId vào data trước khi gửi
    const bodyData = { ...data, ownerId: props.ownerId };
    // Chuẩn bị URL và Method
    const url = activePetItemId
      ? `http://localhost:8080/api/pets/${activePetItemId}`
      : "http://localhost:8080/api/pets";
    const method = activePetItemId ? "PUT" : "POST";

    // Nếu cập nhật, cần đảm bảo ID của pet item được đưa vào body
    if (activePetItemId) {
      bodyData.id = activePetItemId;
    }

    try {
      const res = await fetch(url, {
        method: method,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify(bodyData), // Sử dụng bodyData đã có ownerId
      });

      if (!res.ok) {
        // ... (xử lý lỗi giữ nguyên)
        const errorData = await res.json();
        const errorMessage =
          errorData.message ||
          `Lỗi server (${res.status}): ${
            activePetItemId ? "Cập nhật" : "Thêm mới"
          } thất bại.`;
        throw new Error(errorMessage);
      }

      const savedPet = await res.json();

      // Cập nhật State
      if (activePetItemId) {
        setPets(pets.map((p) => (p.id === activePetItemId ? savedPet : p)));
      } else {
        setPets([...pets, savedPet]);
      }

      // Đóng form
      setShowSidebarPet(false);
      setActivePetItemId(null);
    } catch (err) {
      console.error("Lỗi API (Lưu):", err);
      throw err;
    }
  };

  const handleDeletePet = async (petId) => {
    try {
      const res = await fetch(`http://localhost:8080/api/pets/${petId}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${jwt}` },
      });
      if (!res.ok) throw new Error("Xóa thất bại");
      setPets(pets.filter((p) => p.id !== petId));
      if (activePetItemId === petId) {
        setShowSidebarPet(false);
        setActivePetItemId(null);
      }
    } catch (err) {
      console.error(err);
      alert(err.message);
    }
  };

  return (
    <>
      <div className="main-item">
        {pets.map(
          (
            pet
          ) => (
            <PetItem
              key={pet.id}
              {...pet}
              handleShowSidebarPetID={handleShowSidebarPetID}
              handleDeletePet={handleDeletePet}
            />
          )
        )}
        <div>
          <div className="add-item" onClick={handleShowSidebarPet}>
            <div className="add-item-icon">
              <i className="ri-add-line"></i>
            </div>
          </div>
        </div>
      </div>
      {showSidebarPet && (
        <SiderbarPet
          key={activePetItemId || "new"}
          petItem={activePetItem}
          handleCancelPetForm={handleCancelPetForm}
          handleSavePet={handleSavePet}
          ownerId = {props.ownerId}
        />
      )}
    </>
  );
};

export default ProfilePet;
