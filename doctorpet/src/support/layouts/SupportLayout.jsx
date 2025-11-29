import React from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import Sidebar from "../components/Sidebar";
import "remixicon/fonts/remixicon.css";
import AppointmentList from "../pages/AppointmentList"
import AppointmentDetail from "../pages/AppointmentDetail";
import { Outlet, Link } from "react-router-dom";
import { formatDateTime, getLocation, getBadgeClass } from "../components/ProfileSupport";

const SupportLayout = () => {


    return (
        <>
            <Header />

            <div className="dashboard-container">
                <Sidebar />
                <Outlet />


            </div>

            <Footer />
        </>
    );
};

export default SupportLayout;