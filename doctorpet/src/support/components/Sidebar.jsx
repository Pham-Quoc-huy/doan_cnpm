
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Sidebar = () => {
    const navigate = useNavigate();
    const [user, setUserInfo] = useState({
        name: "",
        address: "",
        phone: "",
        id: "",
        user_id: ""
    });

    useEffect(() => {
        //const storedUser = localStorage.getItem("user");
        // 1. Lấy thông tin user từ localStorage
        const savedUser = localStorage.getItem("user");
        // Lấy JWT từ localStorage (không dùng ở đây nhưng cần cho hàm updateOwner)
        const jwt = localStorage.getItem("jwt");
        if (savedUser) {
            const user = JSON.parse(savedUser);

            // Lưu id của user làm ownerId
            const userId = user.id;

            setUserInfo({
                name: `${user.firstName} ${user.lastName}`,
                address: "", // sẽ fetch sau từ API
                phone: "", // sẽ fetch sau từ API
                id: userId, // set ownerId = id
                user_id: userId,
            });
        }
    }, []);

    return (
        <div className="dashboard-containe">
            <div className="sidebar">
                <div className="profile-section">
                    <img className="avatar" src={user.avatar} alt="avatar" />
                    <input type="text" value={user.name} readOnly className="info-input" />
                    <input type="text" value={user.name} readOnly className="info-input" />
                    <input type="text" value={user.userId} readOnly className="info-input" />
                </div>

                <div className="menu-section">
                    <button className="menu-btn"
                        onClick={() => navigate("/support")}>
                        Danh sách lịch hẹn
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Sidebar;

