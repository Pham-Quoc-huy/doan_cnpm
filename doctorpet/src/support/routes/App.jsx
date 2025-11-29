import React from "react";
import { Routes, Route } from "react-router-dom";
import SupportLayout from "../layouts/SupportLayout";
import AppointmentList from "../pages/AppointmentList";
import AppointmentDetail from "../pages/AppointmentDetail";

function SupportApp() {
    return (
        <Routes>
            <Route element={<SupportLayout />}>
                <Route index element={<AppointmentList />} />
                <Route path="appointments/:id" element={<AppointmentDetail />} />
            </Route>

        </Routes>
    );
}

export default SupportApp;
