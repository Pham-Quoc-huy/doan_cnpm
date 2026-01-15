-- =====================================================
-- Script tạo 3 tables mới: species, disease_dog, disease_cat
-- =====================================================

-- 1. Table: species (Loài thú cưng)
CREATE TABLE IF NOT EXISTS species (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE COMMENT 'Tên loài: Chó, Mèo',
    description VARCHAR(1000) COMMENT 'Mô tả về loài',
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_name (name),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Table: disease_dog (Bệnh của Chó)
CREATE TABLE IF NOT EXISTS disease_dog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở chó',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,chó',
    content TEXT NOT NULL COMMENT 'Nội dung chi tiết về bệnh',
    severity_level VARCHAR(50) COMMENT 'Mức độ: MILD, MODERATE, SEVERE, CRITICAL, GENERAL',
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_title (title(255)),
    INDEX idx_keywords (keywords(255)),
    INDEX idx_is_active (is_active),
    FULLTEXT INDEX ft_search (title, keywords, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Table: disease_cat (Bệnh của Mèo)
CREATE TABLE IF NOT EXISTS disease_cat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở mèo',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,mèo',
    content TEXT NOT NULL COMMENT 'Nội dung chi tiết về bệnh',
    severity_level VARCHAR(50) COMMENT 'Mức độ: MILD, MODERATE, SEVERE, CRITICAL, GENERAL',
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_title (title(255)),
    INDEX idx_keywords (keywords(255)),
    INDEX idx_is_active (is_active),
    FULLTEXT INDEX ft_search (title, keywords, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Insert dữ liệu mẫu
-- =====================================================

-- Insert species
INSERT INTO species (name, description, is_active) VALUES
('Chó', 'Chó là loài thú cưng phổ biến nhất, trung thành và thân thiện. Chó có nhiều giống khác nhau với đặc điểm và tính cách riêng biệt.', TRUE),
('Mèo', 'Mèo là loài thú cưng độc lập và thông minh. Mèo thích chơi đùa, săn bắt và có tính cách độc đáo.', TRUE)
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    is_active = VALUES(is_active);

-- Insert disease_dog (Bệnh của Chó)
INSERT INTO disease_dog (title, keywords, content, severity_level, is_active) VALUES
('Bệnh nôn ở chó', 'nôn,chó,ói,buồn nôn', 
'Bệnh nôn ở chó có thể do nhiều nguyên nhân:
- Ăn quá nhanh hoặc quá nhiều
- Thay đổi thức ăn đột ngột
- Ngộ độc thực phẩm
- Bệnh dạ dày, viêm ruột
- Ký sinh trùng

Triệu chứng:
- Nôn ra thức ăn hoặc dịch vàng/xanh
- Bỏ ăn, mệt mỏi
- Đau bụng

Cách xử lý:
- Ngừng cho ăn 12-24 giờ
- Cho uống nước từng ít một
- Nếu nôn liên tục hoặc có máu, đưa đến bác sĩ ngay', 'MODERATE', TRUE),

('Tiêu chảy ở chó', 'tiêu chảy,chó,đi ngoài,phân lỏng',
'Tiêu chảy ở chó là tình trạng phổ biến:
- Do thức ăn không phù hợp
- Nhiễm khuẩn, virus
- Ký sinh trùng đường ruột
- Stress, thay đổi môi trường

Triệu chứng:
- Đi ngoài nhiều lần, phân lỏng
- Có thể có máu hoặc nhầy
- Mất nước, mệt mỏi

Cách xử lý:
- Cho uống nhiều nước
- Nhịn ăn 12-24 giờ
- Cho ăn thức ăn nhẹ (cơm trắng, thịt gà luộc)
- Nếu kéo dài > 2 ngày hoặc có máu, đến bác sĩ', 'MODERATE', TRUE)

ON DUPLICATE KEY UPDATE title=title;

-- Insert disease_cat (Bệnh của Mèo)
INSERT INTO disease_cat (title, keywords, content, severity_level, is_active) VALUES
('Bệnh nôn ở mèo', 'nôn,mèo,ói,buồn nôn',
'Bệnh nôn ở mèo thường do:
- Nuốt lông (hairball) - phổ biến nhất
- Ăn quá nhanh
- Thay đổi thức ăn
- Bệnh dạ dày, thận, gan

Triệu chứng:
- Nôn ra lông hoặc thức ăn
- Nôn dịch vàng/xanh
- Bỏ ăn, mệt mỏi

Cách xử lý:
- Chải lông thường xuyên để giảm nuốt lông
- Cho ăn thức ăn chống hairball
- Nếu nôn liên tục hoặc có máu, đến bác sĩ ngay', 'MODERATE', TRUE),

('Tiêu chảy ở mèo', 'tiêu chảy,mèo,đi ngoài,phân lỏng',
'Tiêu chảy ở mèo có thể do:
- Thay đổi thức ăn đột ngột
- Nhiễm khuẩn, virus (FIP, FIV)
- Ký sinh trùng
- Stress

Triệu chứng:
- Đi ngoài nhiều lần, phân lỏng
- Có thể có máu
- Mất nước, mệt mỏi

Cách xử lý:
- Cho uống nhiều nước
- Nhịn ăn 12 giờ
- Cho ăn thức ăn nhẹ
- Nếu kéo dài hoặc có máu, đến bác sĩ ngay', 'MODERATE', TRUE)

ON DUPLICATE KEY UPDATE title=title;
