-- =====================================================
-- Migration Script: Cho phép time_end NULL trong bảng appointment
-- Database: animalhospital
-- Mô tả: Thay đổi column time_end từ NOT NULL sang NULL
-- =====================================================

USE animalhospital;

-- Kiểm tra xem bảng appointment có tồn tại không
SELECT COUNT(*) as table_exists 
FROM information_schema.tables 
WHERE table_schema = 'animalhospital' 
AND table_name = 'appointment';

-- Thay đổi column time_end để cho phép NULL
ALTER TABLE appointment 
MODIFY COLUMN time_end DATETIME(6) NULL;

-- Kiểm tra kết quả
DESCRIBE appointment;

-- Xác nhận: time_end giờ đã có thể NULL
SELECT 
    COLUMN_NAME,
    IS_NULLABLE,
    COLUMN_TYPE,
    COLUMN_DEFAULT
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = 'animalhospital'
AND TABLE_NAME = 'appointment'
AND COLUMN_NAME = 'time_end';

-- =====================================================
-- End of Migration Script
-- =====================================================

