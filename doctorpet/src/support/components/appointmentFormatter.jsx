// src/pages/ProfileSupport.js

// export const appointments = [
//     {
//         timeStart: "2025-01-25T14:00:00+07:00",
//         type: "Cấp cứu",
//         appointmentType: "EMERGENCY",
//         locationType: "AT_HOME",
//         pet: { id: 2, name: "Mít" },
//         vet: { id: 3, name: "BS. Nguyễn Văn A" },
//         notes: "Thú cưng bị nôn mửa và không ăn được "
//     },
// ];

export const formatDateTime = (dateString) => {
    const d = new Date(dateString);
    const pad = (n) => n.toString().padStart(2, '0');
    return `${pad(d.getDate())}/${pad(d.getMonth() + 1)}/${d.getFullYear()} - ${pad(d.getHours())}:${pad(d.getMinutes())}`;
};

export const getLocation = (loc) => {
    return loc === "AT_HOME" ? "Tại nhà" :
        loc === "AT_CLINIC" ? "Tại phòng khám" :
            loc === "ONLINE" ? "Tư vấn online" : loc;
};

export const getBadgeClass = (type) => {
    const map = {
        EMERGENCY: "badge-emergency",
        NORMAL: "badge-normal"
    };
    return map[type] || "badge";
};
