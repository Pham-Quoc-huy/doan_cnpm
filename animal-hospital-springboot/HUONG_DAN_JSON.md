# Hướng dẫn truyền JSON cho API POST /api/appointments

## 📝 JSON mẫu đơn giản nhất

```json
{
  "timeStart": "2024-12-25T10:00:00Z",
  "type": "CHECKUP",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "pet": {
    "id": 1
  },
  "vet": {
    "id": 1
  }
}
```

## 📝 JSON đầy đủ (có notes)

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

## 📝 JSON cho appointment khẩn cấp

```json
{
  "timeStart": "2024-12-25T14:30:00Z",
  "type": "EMERGENCY",
  "appointmentType": "EMERGENCY",
  "locationType": "AT_CLINIC",
  "notes": "Chó bị tai nạn, cần khám ngay",
  "pet": {
    "id": 2
  },
  "vet": {
    "id": 1
  }
}
```

## 📝 JSON cho appointment tại nhà

```json
{
  "timeStart": "2024-12-25T09:00:00Z",
  "type": "HOME_VISIT",
  "appointmentType": "NORMAL",
  "locationType": "AT_HOME",
  "notes": "Khám tại nhà cho mèo",
  "pet": {
    "id": 3
  },
  "vet": {
    "id": 2
  }
}
```

## 🔑 Giải thích các trường

| Trường            | Bắt buộc?        | Giá trị                        | Ví dụ                                     |
| ----------------- | ---------------- | ------------------------------ | ----------------------------------------- |
| `timeStart`       | ✅ **BẮT BUỘC**  | ISO 8601 datetime              | `"2024-12-25T10:00:00Z"`                  |
| `timeEnd`         | ❌ **KHÔNG CẦN** | ISO 8601 datetime hoặc null    | Bỏ qua hoặc `null`                        |
| `type`            | ✅ **BẮT BUỘC**  | String                         | `"CHECKUP"`, `"VACCINATION"`, `"SURGERY"` |
| `appointmentType` | ✅ **BẮT BUỘC**  | `"NORMAL"` hoặc `"EMERGENCY"`  | `"NORMAL"`                                |
| `locationType`    | ✅ **BẮT BUỘC**  | `"AT_CLINIC"` hoặc `"AT_HOME"` | `"AT_CLINIC"`                             |
| `notes`           | ❌ Tùy chọn      | String                         | `"Khám định kỳ"`                          |
| `pet.id`          | ✅ **BẮT BUỘC**  | Số (Long)                      | `1`                                       |
| `vet.id`          | ✅ **BẮT BUỘC**  | Số (Long)                      | `1`                                       |

## ⏰ Format thời gian (timeStart)

### UTC (khuyến nghị):

```json
"timeStart": "2024-12-25T10:00:00Z"
```

### Timezone +07:00 (Việt Nam):

```json
"timeStart": "2024-12-25T10:00:00+07:00"
```

### Format đầy đủ:

```
YYYY-MM-DDTHH:mm:ssZ
YYYY-MM-DDTHH:mm:ss+HH:mm
```

## ✅ Ví dụ hoàn chỉnh

### Request:

```bash
POST http://localhost:8080/api/appointments
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "timeStart": "2024-12-25T10:00:00Z",
  "type": "CHECKUP",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "notes": "Khám định kỳ",
  "pet": {
    "id": 1
  },
  "vet": {
    "id": 1
  }
}
```

### Response (201 Created):

```json
{
  "id": 123,
  "timeStart": "2024-12-25T10:00:00Z",
  "timeEnd": null,
  "type": "CHECKUP",
  "status": "PENDING",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "notes": "Khám định kỳ",
  "pet": {
    "id": 1,
    "name": "Buddy",
    "species": "Dog",
    ...
  },
  "vet": {
    "id": 1,
    "licenseNo": "VET-001",
    ...
  },
  "owner": {
    "id": 1,
    "name": "Nguyễn Văn A",
    ...
  }
}
```

## ⚠️ Lưu ý quan trọng

1. **KHÔNG cần gửi `timeEnd`** - Bạn có thể bỏ qua hoàn toàn
2. **KHÔNG gửi `id`** khi tạo mới - Hệ thống tự tạo
3. **KHÔNG gửi `status`** - Tự động set thành `"PENDING"`
4. **KHÔNG gửi `owner`** - ✅ **TỰ ĐỘNG LẤY TỪ USER ĐĂNG NHẬP**
   - Hệ thống tự động lấy thông tin owner từ JWT token
   - Kiểm tra pet phải thuộc về owner hiện tại
   - Nếu pet không thuộc về owner, sẽ báo lỗi
5. Chỉ cần gửi `pet.id` và `vet.id`, không cần toàn bộ object

## 🔐 Cách hoạt động của Owner tự động

### Quy trình:

1. **Lấy user hiện tại** từ JWT token (Authorization header)
2. **Tìm Owner** tương ứng với user đó
3. **Kiểm tra Pet** phải thuộc về owner này
4. **Tự động set owner** vào appointment
5. **Tự động set status** = `"PENDING"`

### Code thực tế:

```java
// 1. Lấy user đăng nhập
String currentUserLogin = SecurityUtils.getCurrentUserLogin();

// 2. Tìm Owner từ user
Owner currentOwner = ownerRepository.findByUser_Login(currentUserLogin);

// 3. Kiểm tra Pet thuộc về owner
if (!pet.getOwner().getId().equals(currentOwner.getId())) {
    throw new BadRequestAlertException("Pet does not belong to current owner");
}

// 4. Tự động set owner
appointmentDTO.setOwner(new OwnerDTO(currentOwner));
appointmentDTO.setStatus("PENDING");
```

### ⚠️ Lỗi có thể xảy ra:

- **"Owner profile not found"**: User chưa có profile Owner
- **"Pet does not belong to current owner"**: Pet không thuộc về owner hiện tại

## 🧪 Test với cURL

```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "timeStart": "2024-12-25T10:00:00Z",
    "type": "CHECKUP",
    "appointmentType": "NORMAL",
    "locationType": "AT_CLINIC",
    "pet": {"id": 1},
    "vet": {"id": 1}
  }'
```

## 🧪 Test với Postman

1. **Method**: POST
2. **URL**: `http://localhost:8080/api/appointments`
3. **Headers**:
   - `Content-Type: application/json`
   - `Authorization: Bearer <your_token>`
4. **Body** (raw JSON):

```json
{
  "timeStart": "2024-12-25T10:00:00Z",
  "type": "CHECKUP",
  "appointmentType": "NORMAL",
  "locationType": "AT_CLINIC",
  "pet": { "id": 1 },
  "vet": { "id": 1 }
}
```
