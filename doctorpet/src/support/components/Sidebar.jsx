// import React from "react";

// const Sidebar = () => {
//     return (
//         <div className="dashboard-container">
//             <div className="sidebar">
//                 <div className="profile-section">
//                     <img className="avatar" src="../public/assets/meme.jpg"></img>
//                     <input
//                         type="text"
//                         placeholder="Tên người dùng"
//                         className="info-input"
//                     />
//                     <input type="text" placeholder="Email" className="info-input" />
//                     <input
//                         type="text"
//                         placeholder="Số điện thoại"
//                         className="info-input"
//                     />
//                 </div>

//                 <div className="menu-section">
//                     <button className="menu-btn">Hồ sơ thú cưng</button>
//                     <button className="menu-btn">Đặt lịch khám</button>
//                     <button className="menu-btn">Lịch đã đặt</button>
//                     <button className="menu-btn">Đặt câu hỏi</button>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default Sidebar;
import React, { useState, useEffect } from "react";

const Sidebar = () => {
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
        <div className="dashboard-container">
            <div className="sidebar">
                <div className="profile-section">
                    <img className="avatar" src={user.avatar} alt="avatar" />
                    <input type="text" value={user.name} readOnly className="info-input" />
                    <input type="text" value={user.name} readOnly className="info-input" />
                    <input type="text" value={user.userId} readOnly className="info-input" />
                </div>

                <div className="menu-section">
                    <button className="menu-btn">Danh sách lịch hẹn</button>
                </div>
            </div>
        </div>
    );
};

export default Sidebar;

