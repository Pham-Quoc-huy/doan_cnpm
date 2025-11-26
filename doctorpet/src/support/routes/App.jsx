import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import AppointmentList from "../pages/AppointmentList";
import AppointmentDetail from "../pages/AppointmentDetail";


export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<AppointmentList />}>
                    <Route path="appointments/:id" element={<AppointmentDetail />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}
