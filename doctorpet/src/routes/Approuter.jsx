import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import LoginForm from "../pages/Login";
import RegisterForm from "../pages/Register";
import UserLayout from "../user/layouts/UserLayout";
import VetLayout from "../vet/layouts/VetLayout";
import SupportApp from "../support/routes/App";
const AppRouter = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<RegisterForm />} />
      <Route path="/user" element={<UserLayout />} />
      <Route path="/vet" element={<VetLayout />} />
      <Route path="/support/*" element={<SupportApp />} />
    </Routes>
  );
};

export default AppRouter;
