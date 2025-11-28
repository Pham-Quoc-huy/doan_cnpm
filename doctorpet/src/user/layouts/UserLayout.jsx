import React, { useState, useEffect } from "react";
import "../css/UserLayout.css";
import Header from "../../components/Header";
import "remixicon/fonts/remixicon.css";
import ProfilePet from "../pages/ProfilePet";
import Appointment from "../pages/Appointment";
import Schedule from "../pages/Schedule";
import Question from "../pages/Question";
import Swal from "sweetalert2";
const UserLayout = () => {
  const [active, setActive] = useState("profile");
  // State lưu thông tin user (owner)
  const [owners, setOwners] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const savedUser = localStorage.getItem("user");
  const user = JSON.parse(savedUser);
  useEffect(() => {
    const fetchOwners = async () => {
      try {
        const jwt = localStorage.getItem("jwt");
        const response = await fetch("http://localhost:8080/api/owners", {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
          },
        });
        if (!response.ok) {
          throw new Error(`Lỗi khi fetch: ${response.status}`);
        }
        const data = await response.json();
        setOwners(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchOwners();
  }, []); 
  const [userInfo, setUserInfo] = useState({
    name: "",
    address: "",
    phone: "",
    id: "",
    user_id: user.id,
  });
  const [isEditing, setIsEditing] = useState(false);
  useEffect(() => {
    if (!user || owners.length === 0) return;

    const ownerMatches = owners.filter((owner) => owner.userId === user.id);
    if (ownerMatches.length === 0) return;

    const ownerId = ownerMatches[0].id;
    const fetchOwner = async () => {
      try {
        const jwt = localStorage.getItem("jwt");
        const response = await fetch(
          `http://localhost:8080/api/owners/${ownerId}`,
          {
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
            credentials: "include",
          }
        );
        if (!response.ok) throw new Error("Failed to fetch owner info");
        const data = await response.json();

        setUserInfo({
          name: `${user.firstName} ${user.lastName}`,
          address: data.address || "",
          phone: data.phone || "",
          id: ownerId,
          user_id: user.id,
        });
      } catch (error) {
        console.error("Lỗi khi fetch thông tin owner:", error);
      }
    };

    fetchOwner();
  }, [owners, user?.id]);
  // cập nhật người dùng
  const updateOwner = async () => {
    // 3. Lấy JWT để sử dụng trong header Authorization
    const jwt = localStorage.getItem("jwt");

    if (!jwt) {
      alert("Không tìm thấy mã xác thực. Vui lòng đăng nhập lại.");
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/owners/${userInfo.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            // 4. Sử dụng JWT đã lấy để xác thực
            Authorization: `Bearer ${jwt}`,
          },
          body: JSON.stringify({
            name: userInfo.name,
            id: userInfo.id,
            phone: userInfo.phone,
            address: userInfo.address,
            user_id: userInfo.user_id,
          }),
        }
      );

      if (!response.ok) throw new Error("Failed to update owner");
      const data = await response.json();
      Swal.fire({
        title: "Tốt lắm!",
        text: "Bạn đã cập nhật thành công!",
        icon: "success",
      });
      setUserInfo((prev) => ({
        ...prev,
        phone: data.phone,
        address: data.address,
      }));
    } catch (error) {
      console.error("Lỗi khi cập nhật owner:", error);
      Swal.fire({
        title: "Cập nhật thất bại!",
        text: error.message,
        icon: "error",
      });
    }
  };
  if (loading) return <p>Đang tải dữ liệu...</p>;
  if (error) return <p>Lỗi: {error}</p>;
  console.log("User Info:", userInfo);
  return (
    <>
      <Header />
      <div className="dashboard-container">
        {/* Sidebar */}
        <div className="sidebar">
          <div className="profile-section">
            <img
              className="avatar"
              src="../public/assets/meme.jpg"
              alt="avatar"
            />
            <input
              type="text"
              placeholder="Tên người dùng"
              className="info-input"
              value={userInfo.name}
              readOnly
            />
            <input
              type="text"
              placeholder="Số điện thoại"
              className="info-input"
              value={userInfo.phone}
              onChange={
                isEditing
                  ? (e) => setUserInfo({ ...userInfo, phone: e.target.value })
                  : undefined
              }
              readOnly={!isEditing}
            />
            <input
              type="text"
              placeholder="Địa chỉ"
              className="info-input"
              value={userInfo.address}
              onChange={
                isEditing
                  ? (e) => setUserInfo({ ...userInfo, address: e.target.value })
                  : undefined
              }
              readOnly={!isEditing}
            />
            <button
              className="edit-profile-btn"
              onClick={() => {
                // Khi nhấn Lưu (isEditing là true), gọi API update, sau đó chuyển sang chế độ Sửa
                if (isEditing) updateOwner();
                // Đảo ngược trạng thái isEditing
                setIsEditing(!isEditing);
              }}
            >
              {isEditing ? "Lưu" : "Sửa"}
            </button>
          </div>

          <div className="menu-section">
            <button
              className={`menu-btn ${active === "profile" ? "active" : ""}`}
              onClick={() => setActive("profile")}
            >
              Hồ sơ thú cưng
            </button>
            <button
              className={`menu-btn ${active === "appointment" ? "active" : ""}`}
              onClick={() => setActive("appointment")}
            >
              Đặt lịch khám
            </button>
            <button
              className={`menu-btn ${active === "schedule" ? "active" : ""}`}
              onClick={() => setActive("schedule")}
            >
              Lịch đã đặt
            </button>
            <button
              className={`menu-btn ${active === "question" ? "active" : ""}`}
              onClick={() => setActive("question")}
            >
              Đặt câu hỏi
            </button>
          </div>
        </div>

        {/* Main content */}
        <div className="main-content">
          {active === "profile" && <ProfilePet ownerId={userInfo.id} />}
          {active === "appointment" && <Appointment ownerId={userInfo.id} />}
          {active === "schedule" && <Schedule ownerId={userInfo.id} />}
          {active === "question" && <Question ownerId={userInfo.id} />}
        </div>
      </div>
    </>
  );
};

export default UserLayout;
