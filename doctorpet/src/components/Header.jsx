import React, { useEffect, useState } from "react";
import "../css/Header.css";
import { Link } from "react-router-dom";

const Header = () => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }

    // Hàm logout khi người dùng rời khỏi trang
    const handleLogout = () => {
      localStorage.removeItem("jwt");
      localStorage.removeItem("user");
    };

    // Cleanup khi component unmount
    return () => {
      window.removeEventListener("beforeunload", handleLogout);
    };
  }, []);

  return (
    <div className="header">
      <div className="img-header">
        <img src="/assets/logo.png" alt="logo" />
      </div>

      <div>
        {user ? (
          <h2>Xin chào, {user.firstName} {user.lastName} 👋</h2>
        ) : (
          <h2>Hệ thống đặt lịch khám thú cưng dành cho bạn</h2>
        )}
      </div>

      <div>
        {!user ? (
          <>
            <Link to="/login">
              <button className="header-btn-login">Đăng Nhập</button>
            </Link>
            <Link to="/register">
              <button className="header-btn-register">Đăng Ký</button>
            </Link>
            <Link to="/register-vet/">
              <button className="header-btn-vet">Đăng Ký Bác Sĩ</button>
            </Link>
          </>
        ) : (
          <button
            className="header-btn-login"
            onClick={() => {
              localStorage.removeItem("jwt");
              localStorage.removeItem("user");
              window.location.href = "/";
            }}
          >
            Đăng Xuất
          </button>
        )}
      </div>
    </div>
  );
};

export default Header;
