-- =====================================================
-- Script sửa foreign key của bảng appointment_assistant
-- Database: animalhospital
-- =====================================================

USE animalhospital;

-- Xóa foreign key cũ (nếu có)
-- Lưu ý: Nếu foreign key không tồn tại, sẽ báo lỗi nhưng không ảnh hưởng
ALTER TABLE appointment_assistant 
DROP FOREIGN KEY fk_assistant_user;

-- Thêm foreign key mới trỏ đến bảng assistant
ALTER TABLE appointment_assistant 
ADD CONSTRAINT fk_assistant_assistant_id 
FOREIGN KEY (assistant_id) REFERENCES assistant(id) ON DELETE CASCADE;

