-- Script để tạo database cho Animal Hospital
-- Chạy script này trong MySQL Workbench hoặc MySQL CLI

CREATE DATABASE IF NOT EXISTS animalhospital 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Kiểm tra database đã tạo
SHOW DATABASES;

-- Sử dụng database
USE animalhospital;

-- Sau khi chạy script này, chạy tiếp file CREATE_ALL_TABLES.sql để tạo tất cả các bảng

