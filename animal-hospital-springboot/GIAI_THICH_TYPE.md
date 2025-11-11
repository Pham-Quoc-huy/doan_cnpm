# Giải thích về trường `type` trong JSON

## 🔍 Phân biệt 2 trường dễ nhầm lẫn

### 1. `type` - Loại dịch vụ (Service Type)

- **Mục đích**: Mô tả loại dịch vụ/khám chữa bệnh
- **Bắt buộc**: ✅ Có
- **Giá trị**: String tự do (không có enum cụ thể)
- **Ví dụ**:
  - `"CHECKUP"` - Khám định kỳ
  - `"VACCINATION"` - Tiêm chủng
  - `"SURGERY"` - Phẫu thuật
  - `"DENTAL"` - Chăm sóc răng miệng
  - `"GROOMING"` - Chăm sóc lông
  - `"EMERGENCY"` - Cấp cứu
  - `"HOME_VISIT"` - Khám tại nhà
  - Hoặc bất kỳ string nào bạn muốn

### 2. `appointmentType` - Mức độ khẩn cấp (Priority Level)

- **Mục đích**: Phân loại appointment theo mức độ khẩn cấp
- **Bắt buộc**: ✅ Có
- **Giá trị**: Chỉ 2 giá trị được chấp nhận:
  - `"NORMAL"` - Bình thường
  - `"EMERGENCY"` - Khẩn cấp
- **Có validation**: ✅ Có kiểm tra trong code

## 📝 Ví dụ JSON

### Ví dụ 1: Khám định kỳ bình thường

```json
{
  "timeStart": "2024-12-25T10:00:00Z",
  "type": "CHECKUP", // ← Loại dịch vụ
  "appointmentType": "NORMAL", // ← Mức độ khẩn cấp
  "locationType": "AT_CLINIC",
  "pet": { "id": 1 },
  "vet": { "id": 1 }
}
```

### Ví dụ 2: Tiêm chủng bình thường

```json
{
  "timeStart": "2024-12-25T14:00:00Z",
  "type": "VACCINATION", // ← Loại dịch vụ
  "appointmentType": "NORMAL", // ← Mức độ khẩn cấp
  "locationType": "AT_CLINIC",
  "pet": { "id": 1 },
  "vet": { "id": 1 }
}
```

### Ví dụ 3: Cấp cứu khẩn cấp

```json
{
  "timeStart": "2024-12-25T15:30:00Z",
  "type": "EMERGENCY", // ← Loại dịch vụ (có thể dùng "EMERGENCY" hoặc "CHECKUP")
  "appointmentType": "EMERGENCY", // ← Mức độ khẩn cấp (BẮT BUỘC phải là "EMERGENCY")
  "locationType": "AT_CLINIC",
  "pet": { "id": 2 },
  "vet": { "id": 1 }
}
```

### Ví dụ 4: Phẫu thuật bình thường

```json
{
  "timeStart": "2024-12-26T09:00:00Z",
  "type": "SURGERY", // ← Loại dịch vụ
  "appointmentType": "NORMAL", // ← Mức độ khẩn cấp
  "locationType": "AT_CLINIC",
  "notes": "Phẫu thuật cắt bỏ u",
  "pet": { "id": 1 },
  "vet": { "id": 1 }
}
```

## ⚠️ Lưu ý quan trọng

1. **`type` không có validation cụ thể** - Bạn có thể dùng bất kỳ string nào
2. **`appointmentType` có validation** - Chỉ chấp nhận `"NORMAL"` hoặc `"EMERGENCY"`
3. **Có thể dùng `"EMERGENCY"` cho cả 2 trường**:
   ```json
   {
     "type": "EMERGENCY",
     "appointmentType": "EMERGENCY"
   }
   ```
4. **Hoặc dùng `"CHECKUP"` cho type nhưng `"EMERGENCY"` cho appointmentType**:
   ```json
   {
     "type": "CHECKUP",
     "appointmentType": "EMERGENCY"
   }
   ```

## 📊 Bảng so sánh

| Trường            | Mục đích        | Bắt buộc? | Giá trị                        | Validation |
| ----------------- | --------------- | --------- | ------------------------------ | ---------- |
| `type`            | Loại dịch vụ    | ✅ Có     | String tự do                   | ❌ Không   |
| `appointmentType` | Mức độ khẩn cấp | ✅ Có     | `"NORMAL"` hoặc `"EMERGENCY"`  | ✅ Có      |
| `locationType`    | Địa điểm        | ✅ Có     | `"AT_CLINIC"` hoặc `"AT_HOME"` | ✅ Có      |

## 💡 Gợi ý giá trị cho `type`

Các giá trị thường dùng:

- `"CHECKUP"` - Khám định kỳ
- `"VACCINATION"` - Tiêm chủng
- `"SURGERY"` - Phẫu thuật
- `"DENTAL"` - Chăm sóc răng miệng
- `"GROOMING"` - Chăm sóc lông
- `"EMERGENCY"` - Cấp cứu
- `"HOME_VISIT"` - Khám tại nhà
- `"FOLLOW_UP"` - Tái khám
- `"CONSULTATION"` - Tư vấn
- `"LAB_TEST"` - Xét nghiệm

Bạn có thể tự định nghĩa giá trị khác tùy theo nhu cầu!
