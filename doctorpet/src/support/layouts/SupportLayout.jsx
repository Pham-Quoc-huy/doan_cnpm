import React from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import Sidebar from "../components/Sidebar";
import { Outlet } from "react-router-dom";

const SupportLayout = () => {


    return (
        <>
            <Header />
            {/* <Sidebar />
            <Outlet /> */}

            <div className="dashboard-container">
                <Sidebar />
                <Outlet />
            </div>
        </>
    );
};

export default SupportLayout;