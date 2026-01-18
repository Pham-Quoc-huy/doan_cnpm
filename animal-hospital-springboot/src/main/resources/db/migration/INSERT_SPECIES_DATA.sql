-- =====================================================
-- Script INSERT dữ liệu vào bảng Species
-- =====================================================

USE animalhospital;

-- Insert dữ liệu Species (created_date sẽ tự động được set bởi DEFAULT CURRENT_TIMESTAMP)
INSERT INTO species (name, is_active, created_date) VALUES
('Chó', TRUE, NOW()),
('Mèo', TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    is_active = VALUES(is_active);

SELECT * FROM species;
