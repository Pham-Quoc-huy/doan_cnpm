# Ví dụ JSON cho API POST /api/appointments

## Sau khi sửa (bỏ endTime bắt buộc)

### 📤 REQUEST - Tạo appointment mới

**Endpoint:** `POST /api/appointments`

**Headers:**

```
Authorization: Bearer <token>
Content-Type: application/json
```

**Body (KHÔNG cần timeEnd):**

```json
{
  "timeStart": "2024-12-25T10:00:00Z",
  "type": "CHECKUP",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "notes": "Khám định kỳ cho chó",
  "pet": {
    "id": 1
  },
  "vet": {
    "id": 1
  }
}
```

**Body (có thể bỏ qua timeEnd hoặc để null):**

```json
{
  "timeStart": "2024-12-25T10:00:00Z",
  "timeEnd": null,
  "type": "CHECKUP",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "notes": "Khám định kỳ cho chó",
  "pet": {
    "id": 1
  },
  "vet": {
    "id": 1
  }
}
```

### ✅ RESPONSE - Thành công (201 Created)

```json
{
  "id": 123,
  "timeStart": "2024-12-25T10:00:00Z",
  "timeEnd": null,
  "type": "CHECKUP",
  "status": "PENDING",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "notes": "Khám định kỳ cho chó",
  "pet": {
    "id": 1,
    "name": "Buddy",
    "species": "Dog",
    "breed": "Golden Retriever",
    "sex": "Male",
    "dateOfBirth": "2020-05-15",
    "weight": 25.5,
    "allergies": null,
    "notes": null,
    "imageUrl": null,
    "ownerId": 1
  },
  "vet": {
    "id": 1,
    "licenseNo": "VET-001",
    "specialization": "General Practice",
    "userId": 2
  },
  "owner": {
    "id": 1,
    "name": "Nguyễn Văn A",
    "phone": "0901234567",
    "address": "123 Đường ABC, Quận 1, TP.HCM",
    "userId": 1
  }
}
```

### ❌ RESPONSE - Lỗi validation (400 Bad Request)

**Thiếu timeStart:**

```json
{
  "type": "https://www.jhipster.tech/problem/constraint-violation",
  "title": "Constraint Violation",
  "status": 400,
  "message": "error.validation",
  "fieldErrors": [
    {
      "objectName": "appointmentDTO",
      "field": "timeStart",
      "message": "must not be null"
    }
  ]
}
```

**Vet không có sẵn tại thời điểm đó:**

```json
{
  "type": "https://www.jhipster.tech/problem/problem-with-message",
  "title": "Bad Request",
  "status": 400,
  "message": "error.vetnotavailable",
  "params": "appointment"
}
```

## 📋 Các trường bắt buộc và tùy chọn

### ✅ Bắt buộc (Required):

- `timeStart` - Thời gian bắt đầu (ZonedDateTime)
- `type` - Loại appointment (String)
- `appointmentType` - "NORMAL" hoặc "EMERGENCY" (String)
- `locationType` - "AT_CLINIC" hoặc "AT_HOME" (String)
- `pet.id` - ID của pet (Long)
- `vet.id` - ID của vet (Long)

### ⚪ Tùy chọn (Optional):

- `timeEnd` - Thời gian kết thúc (ZonedDateTime) - **KHÔNG BẮT BUỘC NỮA**
- `notes` - Ghi chú (String)
- `id` - Không được gửi khi tạo mới

## 🔄 So sánh trước và sau

### ❌ TRƯỚC (timeEnd bắt buộc):

```json
{
  "timeStart": "2024-12-25T10:00:00Z",
  "timeEnd": "2024-12-25T11:00:00Z", // ← BẮT BUỘC
  "type": "CHECKUP",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "pet": { "id": 1 },
  "vet": { "id": 1 }
}
```

### ✅ SAU (timeEnd không bắt buộc):

```json
{
  "timeStart": "2024-12-25T10:00:00Z",
  // timeEnd có thể bỏ qua hoặc để null
  "type": "CHECKUP",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "pet": { "id": 1 },
  "vet": { "id": 1 }
}
```

## 📝 Lưu ý

1. **Format thời gian**: Sử dụng ISO 8601 format với timezone (Z = UTC)

   - Ví dụ: `"2024-12-25T10:00:00Z"` hoặc `"2024-12-25T10:00:00+07:00"`

2. **appointmentType**: Chỉ chấp nhận `"NORMAL"` hoặc `"EMERGENCY"`

3. **locationType**: Chỉ chấp nhận `"AT_CLINIC"` hoặc `"AT_HOME"`

4. **Kiểm tra conflict**: Hệ thống sẽ kiểm tra xem vet có appointment tại `timeStart` đó không. Nếu có, sẽ trả về lỗi.

5. **Status tự động**: Khi tạo mới, `status` sẽ tự động được set thành `"PENDING"` và `owner` sẽ được set tự động từ user hiện tại.
