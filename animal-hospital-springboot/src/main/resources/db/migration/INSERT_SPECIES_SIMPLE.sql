-- =====================================================
-- Script INSERT đơn giản - Chỉ insert dữ liệu
-- Chạy script này sau khi đã chạy CREATE_SPECIES_AND_DISEASE_TABLES.sql
-- =====================================================

USE animalhospital;

-- Insert dữ liệu Species (created_date sẽ tự động được set bởi DEFAULT CURRENT_TIMESTAMP)
INSERT INTO species (name, is_active, created_date) VALUES
('Chó', TRUE, NOW()),
('Mèo', TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    is_active = VALUES(is_active);

SELECT * FROM species;
