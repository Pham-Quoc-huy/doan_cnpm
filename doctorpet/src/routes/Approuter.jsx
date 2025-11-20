import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import LoginForm from "../pages/Login";
import RegisterForm from "../pages/Register";
import UserLayout from "../user/layouts/UserLayout";

const AppRouter = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<RegisterForm />} />
      <Route path="/user" element={<UserLayout />} />
    </Routes>
  );
};

export default AppRouter;
