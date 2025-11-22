-- =====================================================
-- Script tạo bảng Assistant
-- Database: animalhospital
-- =====================================================

USE animalhospital;

-- =====================================================
-- Assistant Table
-- =====================================================

CREATE TABLE IF NOT EXISTS assistant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    CONSTRAINT fk_assistant__user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id) ON DELETE SET NULL,
    CONSTRAINT uk_assistant_user_id UNIQUE (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for assistant
CREATE INDEX idx_assistant_user_id ON assistant(user_id);

