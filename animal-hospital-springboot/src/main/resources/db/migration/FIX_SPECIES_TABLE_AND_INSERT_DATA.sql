-- =====================================================
-- Script sửa table Species và insert dữ liệu
-- =====================================================

-- Chọn database
USE animalhospital;

-- Xóa table cũ nếu tồn tại (để tạo lại với schema đúng)
DROP TABLE IF EXISTS species;

-- Tạo lại table với schema đúng (không có description, có DEFAULT cho created_date)
CREATE TABLE species (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE COMMENT 'Tên loài: Chó, Mèo',
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_name (name),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert dữ liệu Species (created_date sẽ tự động được set bởi DEFAULT CURRENT_TIMESTAMP)
INSERT INTO species (name, is_active) VALUES
('Chó', TRUE),
('Mèo', TRUE);

-- Kiểm tra dữ liệu đã insert
SELECT * FROM species;
