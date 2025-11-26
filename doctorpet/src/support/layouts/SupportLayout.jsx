import React from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import Sidebar from "../components/Sidebar";
import "remixicon/fonts/remixicon.css";
import AppointmentList from "../pages/AppointmentList"
import AppointmentDetail from "../pages/AppointmentDetail";
import { formatDateTime, getLocation, getBadgeClass } from "../components/ProfileSupport";

const SupportLayout = () => {


    return (
        <>
            <Header />

            <div className="dashboard-container">
                <Sidebar />
                <AppointmentList
                    formatDateTime={formatDateTime}
                    getLocation={getLocation}
                    getBadgeClass={getBadgeClass}
                />
                {/* <AppointmentDetail
                    appointments={appointments}
                    formatDateTime={formatDateTime}
                    getLocation={getLocation}
                    getBadgeClass={getBadgeClass}
                />  */}
                <Outlet />


            </div>

            <Footer />
        </>
    );
};

export default SupportLayout;