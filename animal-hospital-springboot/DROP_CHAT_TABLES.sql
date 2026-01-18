-- =====================================================
-- Script xóa bảng Chatbot AI
-- Database: animalhospital
-- =====================================================

USE animalhospital;

-- Xóa foreign key constraints trước
ALTER TABLE chat_message DROP FOREIGN KEY IF EXISTS fk_chat_message_user;
ALTER TABLE chat_message DROP FOREIGN KEY IF EXISTS fk_chat_message_pet;
ALTER TABLE chat_session DROP FOREIGN KEY IF EXISTS fk_chat_session_user;

-- Xóa indexes
DROP INDEX IF EXISTS idx_chat_message_user_id ON chat_message;
DROP INDEX IF EXISTS idx_chat_message_pet_id ON chat_message;
DROP INDEX IF EXISTS idx_chat_message_session_id ON chat_message;
DROP INDEX IF EXISTS idx_chat_message_created_date ON chat_message;
DROP INDEX IF EXISTS idx_chat_message_session_created ON chat_message;

DROP INDEX IF EXISTS idx_chat_session_user_id ON chat_session;
DROP INDEX IF EXISTS idx_chat_session_session_id ON chat_session;
DROP INDEX IF EXISTS idx_chat_session_last_message_date ON chat_session;

-- Xóa tables
DROP TABLE IF EXISTS chat_message;
DROP TABLE IF EXISTS chat_session;

-- Verification
SHOW TABLES LIKE 'chat_%';

-- =====================================================
-- End of Script
-- =====================================================

