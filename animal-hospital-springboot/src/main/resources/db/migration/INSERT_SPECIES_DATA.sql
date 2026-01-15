-- =====================================================
-- Script insert dữ liệu vào bảng Species
-- =====================================================

-- Xóa dữ liệu cũ (nếu cần)
-- DELETE FROM species;

-- Insert dữ liệu Species
INSERT INTO species (name, description, is_active) VALUES
('Chó', 'Chó là loài thú cưng phổ biến nhất, trung thành và thân thiện. Chó có nhiều giống khác nhau với đặc điểm và tính cách riêng biệt.', TRUE),
('Mèo', 'Mèo là loài thú cưng độc lập và thông minh. Mèo thích chơi đùa, săn bắt và có tính cách độc đáo.', TRUE)
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    is_active = VALUES(is_active);

-- Kiểm tra dữ liệu đã insert
SELECT * FROM species;
