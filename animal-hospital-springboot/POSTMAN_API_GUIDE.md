# Hướng Dẫn Test API - Animal Hospital Management System

## 📋 Mục Lục
1. [Thiết Lập Môi Trường](#thiết-lập-môi-trường)
2. [Authentication APIs](#authentication-apis)
3. [Account Management APIs](#account-management-apis)
4. [Pet APIs](#pet-apis)
5. [Appointment APIs](#appointment-apis)
6. [Vet Workflow APIs](#vet-workflow-apis)
7. [Vet Management APIs](#vet-management-apis)
8. [Owner Management APIs](#owner-management-apis)
9. [Assistant APIs](#assistant-apis)

---

## 🔧 Thiết Lập Môi Trường

### Base URL
```
http://localhost:8080
```

### Cấu Hình Postman Environment Variables
Tạo environment trong Postman với các biến sau:
- `base_url`: `http://localhost:8080`
- `vet_token`: JWT token của vet (sẽ được set sau khi login)
- `owner_token`: JWT token của owner (sẽ được set sau khi login)
- `assistant_token`: JWT token của assistant (sẽ được set sau khi login)
- `appointment_id`: ID của appointment (ví dụ: `2`)
- `pet_id`: ID của pet (ví dụ: `1`)
- `vet_id`: ID của vet (ví dụ: `1`)

### Pre-request Script (Tùy chọn)
Thêm vào Pre-request Script của collection để tự động thêm Authorization header:
```javascript
if (pm.environment.get("vet_token")) {
    pm.request.headers.add({
        key: 'Authorization',
        value: 'Bearer ' + pm.environment.get("vet_token")
    });
}
```

---

## 🔐 Authentication APIs

### 1. Đăng Nhập (Login)
**Endpoint:** `POST /api/authenticate`

**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "username": "vet_username",
  "password": "vet_password",
  "rememberMe": false
}
```

**Response (200 OK):**
```json
{
  "id_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Lưu ý:** Copy `id_token` và lưu vào environment variable `vet_token` hoặc `owner_token`

---

### 2. Kiểm Tra Authentication
**Endpoint:** `GET /api/authenticate`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
- `204 No Content`: Đã authenticated
- `401 Unauthorized`: Chưa authenticated

---

## 👤 Account Management APIs

### 1. Đăng Ký Owner
**Endpoint:** `POST /api/register`

**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "login": "owner1",
  "password": "password123",
  "firstName": "Nguyễn",
  "lastName": "Văn A",
  "email": "owner1@example.com",
  "langKey": "vi",
  "imageUrl": ""
}
```

**Response:** `201 Created` (không có body)

---

### 2. Đăng Ký Vet
**Endpoint:** `POST /api/register-vet`

**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "login": "vet1",
  "password": "password123",
  "firstName": "Trần",
  "lastName": "Thị B",
  "email": "vet1@example.com",
  "specialization": "Phẫu thuật",
  "yearsOfExperience": 5,
  "phoneNumber": "0123456789"
}
```

**Response:** `201 Created` (không có body)

---

### 3. Lấy Thông Tin Tài Khoản Hiện Tại
**Endpoint:** `GET /api/account`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "login": "owner1",
  "firstName": "Nguyễn",
  "lastName": "Văn A",
  "email": "owner1@example.com",
  "activated": true,
  "langKey": "vi",
  "authorities": ["ROLE_USER"]
}
```

---

### 4. Cập Nhật Tài Khoản
**Endpoint:** `POST /api/account`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "firstName": "Nguyễn",
  "lastName": "Văn A",
  "email": "newemail@example.com",
  "langKey": "vi"
}
```

**Response:** `200 OK` (không có body)

---

### 5. Đổi Mật Khẩu
**Endpoint:** `POST /api/account/change-password`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "currentPassword": "oldpassword",
  "newPassword": "newpassword123"
}
```

**Response:** `200 OK` (không có body)

---

## 🐾 Pet APIs

### 1. Tạo Pet
**Endpoint:** `POST /api/pets`

**Headers:**
```
Authorization: Bearer {owner_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "name": "Lucky",
  "species": "Dog",
  "breed": "Golden Retriever",
  "age": 3,
  "weight": 25.5,
  "gender": "MALE",
  "color": "Vàng",
  "notes": "Dễ thương, thân thiện"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Lucky",
  "species": "Dog",
  "breed": "Golden Retriever",
  "age": 3,
  "weight": 25.5,
  "gender": "MALE",
  "color": "Vàng",
  "notes": "Dễ thương, thân thiện",
  "ownerId": 1
}
```

---

### 2. Lấy Danh Sách Pet Của Owner
**Endpoint:** `GET /api/pets`

**Headers:**
```
Authorization: Bearer {owner_token}
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Lucky",
    "species": "Dog",
    ...
  }
]
```

---

### 3. Lấy Chi Tiết Pet
**Endpoint:** `GET /api/pets/{id}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Lucky",
  ...
}
```

---

### 4. Cập Nhật Pet
**Endpoint:** `PUT /api/pets/{id}`

**Headers:**
```
Authorization: Bearer {owner_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "id": 1,
  "name": "Lucky Updated",
  "age": 4,
  ...
}
```

**Response (200 OK):** PetDTO đã cập nhật

---

### 5. Xóa Pet
**Endpoint:** `DELETE /api/pets/{id}`

**Headers:**
```
Authorization: Bearer {owner_token}
```

**Response:** `204 No Content`

---

## 📅 Appointment APIs

### 1. Tạo Appointment
**Endpoint:** `POST /api/appointments`

**Headers:**
```
Authorization: Bearer {owner_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "timeStart": "2024-01-25T10:00:00+07:00",
  "timeEnd": "2024-01-25T11:00:00+07:00",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "pet": {
    "id": 1
  },
  "vet": {
    "id": 1
  },
  "notes": "Khám định kỳ"
}
```

**Lưu ý về format thời gian:**
- Format: `yyyy-MM-ddTHH:mm:ss+07:00` (ISO 8601 với timezone)
- Ví dụ: `2024-01-25T10:00:00+07:00`

**Response (201 Created):**
```json
{
  "id": 2,
  "timeStart": "2024-01-25T10:00:00+07:00",
  "timeEnd": "2024-01-25T11:00:00+07:00",
  "status": "PENDING",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "pet": { ... },
  "vet": { ... },
  "owner": { ... }
}
```

---

### 2. Lấy Danh Sách Appointments
**Endpoint:** `GET /api/appointments`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "timeStart": "2024-01-25T10:00:00+07:00",
    ...
  }
]
```

**Lưu ý:** 
- Owner sẽ thấy appointments của mình
- Vet sẽ thấy appointments được phân công cho mình

---

### 3. Lấy Chi Tiết Appointment
**Endpoint:** `GET /api/appointments/{id}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** AppointmentDTO

---

### 4. Kiểm Tra Vet Availability
**Endpoint:** `POST /api/appointments/vet/available`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "vetId": 1,
  "startTime": "2024-01-25T14:00:00+07:00",
  "endTime": "2024-01-25T15:00:00+07:00"
}
```

**Response (200 OK):**
```json
true
```
hoặc
```json
false
```

---

### 5. Lấy Lịch Sử Appointment Của Pet
**Endpoint:** `GET /api/appointments/pet/{petId}/history`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** Danh sách AppointmentDTO

---

### 6. Lấy Regular Appointments Theo Ngày và Vet
**Endpoint:** `GET /api/appointments/regular?date=2024-01-25&vetId=1`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** Danh sách AppointmentDTO

---

### 7. Lấy Emergency Appointments Theo Ngày và Vet
**Endpoint:** `GET /api/appointments/emergency?date=2024-01-25&vetId=1`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** Danh sách AppointmentDTO

---

### 8. Gửi Tin Nhắn Trong Appointment
**Endpoint:** `POST /api/appointments/{id}/messages`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "message": "Xin chào, tôi muốn hỏi về lịch hẹn"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "message": "Xin chào, tôi muốn hỏi về lịch hẹn",
  "timestamp": "2024-01-25T10:30:00+07:00",
  "appointmentId": 2,
  "senderId": 1,
  "senderLogin": "owner1",
  "senderName": "Nguyễn Văn A"
}
```

---

### 9. Lấy Tin Nhắn Của Appointment
**Endpoint:** `GET /api/appointments/{id}/messages`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "message": "Xin chào...",
    "timestamp": "2024-01-25T10:30:00+07:00",
    ...
  }
]
```

---

## 🏥 Vet Workflow APIs

**Lưu ý:** Tất cả các API này yêu cầu quyền `DOCTOR` (vet)

### 1. Lấy Chi Tiết Appointment (Vet)
**Endpoint:** `GET /api/vet/appointments/{id}/detail`

**Headers:**
```
Authorization: Bearer {vet_token}
```

**Response (200 OK):** AppointmentDTO (không có thông tin vet)

---

### 2. Duyệt Appointment
**Endpoint:** `POST /api/vet/appointments/{id}/approve`

**Headers:**
```
Authorization: Bearer {vet_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
"Ghi chú duyệt lịch hẹn"
```

**Response (200 OK):** AppointmentDTO với status = "APPROVED"

---

### 3. Từ Chối Appointment
**Endpoint:** `POST /api/vet/appointments/{id}/reject`

**Headers:**
```
Authorization: Bearer {vet_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
"Lý do từ chối"
```

**Response (200 OK):** AppointmentDTO với status = "REJECTED"

---

### 4. Đổi Lịch Appointment ⭐
**Endpoint:** `POST /api/vet/appointments/{id}/reschedule`

**Headers:**
```
Authorization: Bearer {vet_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "newTimeStart": "2024-01-25T14:00:00+07:00",
  "notes": "Đổi lịch do bác sĩ có việc đột xuất"
}
```

**Response (200 OK):**
```json
{
  "id": 2,
  "timeStart": "2024-01-25T14:00:00+07:00",
  "timeEnd": "2024-01-25T15:00:00+07:00",
  "status": "RESCHEDULED",
  ...
}
```

**Lưu ý:** 
- Hệ thống tự động gửi tin nhắn thông báo đổi lịch cho owner
- Thời gian kết thúc được tự động tính dựa trên thời lượng ban đầu

---

### 5. Yêu Cầu Khám Tại Nhà ⭐
**Endpoint:** `POST /api/vet/appointments/{id}/request-home-visit`

**Headers:**
```
Authorization: Bearer {vet_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "notes": "Bác sĩ sẽ đến thăm khám tại nhà. Vui lòng chuẩn bị không gian sạch sẽ."
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "actionType": "REQUEST_HOME_VISIT",
  "status": "PENDING",
  "description": "Home visit requested",
  "notes": "Bác sĩ sẽ đến thăm khám tại nhà...",
  "appointmentId": 2,
  ...
}
```

**Lưu ý:** 
- Hệ thống tự động cập nhật `locationType` = "AT_HOME"
- Hệ thống tự động gửi tin nhắn thông báo cho owner

---

### 6. Phân Công Assistant
**Endpoint:** `POST /api/vet/appointments/{id}/assign-assistant`

**Headers:**
```
Authorization: Bearer {vet_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "assistantId": 1,
  "notes": "Phân công thu thập mẫu xét nghiệm"
}
```

**Response (200 OK):** AppointmentActionDTO

---

### 7. Yêu Cầu Xét Nghiệm
**Endpoint:** `POST /api/vet/appointments/{id}/request-lab-test`

**Headers:**
```
Authorization: Bearer {vet_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "testName": "Xét nghiệm máu",
  "testType": "BLOOD_TEST",
  "description": "Kiểm tra công thức máu đầy đủ"
}
```

**Response (200 OK):** LabTestDTO

---

### 8. Lấy Danh Sách Actions Của Appointment
**Endpoint:** `GET /api/vet/appointments/{id}/actions`

**Headers:**
```
Authorization: Bearer {vet_token}
```

**Response (200 OK):** Danh sách AppointmentActionDTO

---

### 9. Lấy Danh Sách Lab Tests Của Appointment
**Endpoint:** `GET /api/vet/appointments/{id}/lab-tests`

**Headers:**
```
Authorization: Bearer {vet_token}
```

**Response (200 OK):** Danh sách LabTestDTO

---

## 👨‍⚕️ Vet Management APIs

### 1. Lấy Danh Sách Vets
**Endpoint:** `GET /api/vets`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** Danh sách VetDTO

---

### 2. Lấy Chi Tiết Vet
**Endpoint:** `GET /api/vets/{id}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** VetDTO

---

### 3. Tạo Vet (Admin)
**Endpoint:** `POST /api/vets`

**Headers:**
```
Authorization: Bearer {admin_token}
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "specialization": "Phẫu thuật",
  "yearsOfExperience": 5,
  "phoneNumber": "0123456789",
  "user": {
    "id": 1
  }
}
```

**Response (201 Created):** VetDTO

---

## 👥 Owner Management APIs

### 1. Lấy Danh Sách Owners
**Endpoint:** `GET /api/owners`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** Danh sách OwnerDTO

---

### 2. Lấy Chi Tiết Owner
**Endpoint:** `GET /api/owners/{id}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):** OwnerDTO

---

## 🧪 Assistant APIs

### 1. Lấy Appointments Đã Được Phân Công (Assistant)
**Endpoint:** `GET /api/appointments/assistant/assigned?status=PENDING`

**Headers:**
```
Authorization: Bearer {assistant_token}
```

**Query Parameters:**
- `status` (optional): "PENDING", "COMPLETED", etc.

**Response (200 OK):** Danh sách AppointmentActionDTO

---

### 2. Lấy Pending Assignments (Assistant)
**Endpoint:** `GET /api/appointments/assistant/assigned/pending`

**Headers:**
```
Authorization: Bearer {assistant_token}
```

**Response (200 OK):** Danh sách AppointmentActionDTO với status = "PENDING"

---

### 3. Lấy Chi Tiết Appointment Đã Được Phân Công (Assistant)
**Endpoint:** `GET /api/appointments/assistant/{id}/detail`

**Headers:**
```
Authorization: Bearer {assistant_token}
```

**Response (200 OK):** AppointmentDTO

---

## 📝 Ghi Chú Quan Trọng

### Format Thời Gian
- Tất cả thời gian phải theo format ISO 8601 với timezone
- Ví dụ: `2024-01-25T14:00:00+07:00` (25/01/2024 lúc 14:00, timezone +07:00)

### Authentication
- Hầu hết các API yêu cầu JWT token trong header `Authorization: Bearer {token}`
- Token được lấy từ API `/api/authenticate`
- Token có thời hạn, cần đăng nhập lại khi hết hạn

### Phân Quyền
- **ROLE_USER**: Owner - có thể quản lý pets, appointments của mình
- **ROLE_DOCTOR**: Vet - có thể quản lý appointments được phân công
- **ROLE_ASSISTANT**: Assistant - có thể xem appointments được phân công
- **ROLE_ADMIN**: Admin - có quyền quản lý tất cả

### Lỗi Thường Gặp
- **401 Unauthorized**: Token không hợp lệ hoặc đã hết hạn
- **403 Forbidden**: Không có quyền truy cập
- **400 Bad Request**: Dữ liệu request không hợp lệ
- **404 Not Found**: Resource không tồn tại

---

## 🔄 Workflow Test Case Mẫu

### Test Case 1: Owner Tạo Appointment → Vet Duyệt → Đổi Lịch
1. Owner đăng nhập → lấy `owner_token`
2. Owner tạo appointment: `POST /api/appointments`
3. Vet đăng nhập → lấy `vet_token`
4. Vet xem appointment: `GET /api/vet/appointments/{id}/detail`
5. Vet duyệt: `POST /api/vet/appointments/{id}/approve`
6. Vet đổi lịch: `POST /api/vet/appointments/{id}/reschedule`
7. Owner kiểm tra tin nhắn: `GET /api/appointments/{id}/messages`

### Test Case 2: Vet Yêu Cầu Khám Tại Nhà
1. Vet đăng nhập → lấy `vet_token`
2. Vet yêu cầu khám tại nhà: `POST /api/vet/appointments/{id}/request-home-visit`
3. Owner kiểm tra tin nhắn: `GET /api/appointments/{id}/messages`
4. Kiểm tra appointment đã được cập nhật: `GET /api/appointments/{id}`

---

## 📞 Hỗ Trợ

Nếu gặp vấn đề khi test API, vui lòng kiểm tra:
1. Server đã chạy chưa? (`http://localhost:8080`)
2. Database đã kết nối chưa?
3. Token còn hợp lệ không?
4. Request body đúng format chưa?
5. Có đủ quyền truy cập không?

---

**Tài liệu này được cập nhật lần cuối:** 2024-01-20

