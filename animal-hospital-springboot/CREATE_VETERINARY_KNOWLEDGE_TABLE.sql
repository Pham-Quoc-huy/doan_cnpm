-- =====================================================
-- Seed dữ liệu mẫu cho bảng veterinary_knowledge
-- =====================================================

-- Bệnh nôn ở chó
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh nôn ở chó', 'DISEASE', 'nôn,ói,chó,dạ dày,viêm dạ dày,ăn quá nhanh', 'Chó bị nôn có thể do nhiều nguyên nhân:\n1. Ăn quá nhanh hoặc quá nhiều\n2. Viêm dạ dày ruột\n3. Ngộ độc thức ăn\n4. Ký sinh trùng đường ruột\n5. Bệnh gan, thận\n\nTriệu chứng kèm theo cần chú ý:\n- Nôn liên tục > 3 lần/ngày\n- Có máu trong chất nôn\n- Bỏ ăn, mệt mỏi\n- Tiêu chảy kèm theo\n\nXử lý tại nhà:\n- Ngừng cho ăn 12-24 giờ\n- Cho uống nước từng chút một\n- Quan sát tình trạng\n\nKhi nào cần đến bác sĩ:\n- Nôn liên tục không dừng\n- Có máu trong chất nôn\n- Chó mệt mỏi, bỏ ăn > 24 giờ\n- Có triệu chứng khác kèm theo', 'Chó', 'MODERATE', NOW());

-- Tiêu chảy ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Tiêu chảy ở mèo', 'DISEASE', 'tiêu chảy,mèo,đường ruột,phân lỏng', 'Mèo bị tiêu chảy có thể do:\n1. Thay đổi thức ăn đột ngột\n2. Nhiễm khuẩn đường ruột\n3. Ký sinh trùng\n4. Dị ứng thức ăn\n5. Bệnh viêm ruột\n\nTriệu chứng:\n- Phân lỏng, có thể có máu hoặc nhầy\n- Đi vệ sinh nhiều lần\n- Mất nước\n\nXử lý:\n- Cho mèo nhịn ăn 12 giờ (vẫn cho uống nước)\n- Cho ăn thức ăn nhẹ (cơm trắng + thịt gà luộc)\n- Bổ sung nước\n\nCần đến bác sĩ khi:\n- Tiêu chảy > 24 giờ\n- Có máu trong phân\n- Mèo mệt mỏi, bỏ ăn\n- Mất nước nghiêm trọng', 'Mèo', 'MODERATE', NOW());

-- Bỏ ăn ở thú cưng
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Thú cưng bỏ ăn', 'DISEASE', 'bỏ ăn,không ăn,chán ăn,chó,mèo', 'Thú cưng bỏ ăn có thể do:\n1. Bệnh lý (sốt, đau, viêm nhiễm)\n2. Stress, lo âu\n3. Thay đổi môi trường\n4. Vấn đề răng miệng\n5. Bệnh đường tiêu hóa\n\nĐánh giá mức độ:\n- Nhẹ: Bỏ ăn < 24 giờ, vẫn uống nước, hoạt động bình thường\n- Trung bình: Bỏ ăn 24-48 giờ, uống ít nước, hơi mệt\n- Nghiêm trọng: Bỏ ăn > 48 giờ, không uống nước, mệt mỏi nhiều\n\nCần đến bác sĩ ngay khi:\n- Bỏ ăn > 24 giờ kèm các triệu chứng khác\n- Không uống nước\n- Mệt mỏi, nằm liệt\n- Có triệu chứng nôn, tiêu chảy, sốt', 'Tất cả', 'SEVERE', NOW());

-- Sơ cứu khi thú cưng bị ngộ độc
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Sơ cứu ngộ độc ở thú cưng', 'EMERGENCY', 'ngộ độc,độc,thuốc,thức ăn độc,khẩn cấp', 'Khi nghi ngờ thú cưng bị ngộ độc:\n\nTriệu chứng:\n- Nôn, tiêu chảy\n- Co giật\n- Khó thở\n- Bất tỉnh\n- Chảy nước dãi nhiều\n\nHành động ngay lập tức:\n1. Đưa thú cưng đến bác sĩ thú y NGAY LẬP TỨC\n2. Không tự ý cho uống thuốc hoặc gây nôn\n3. Nếu có thể, mang theo bao bì/thuốc mà thú cưng đã ăn\n4. Gọi điện báo trước cho phòng khám\n\nCác chất độc thường gặp:\n- Sô cô la\n- Hành, tỏi\n- Nho, nho khô\n- Xylitol (kẹo cao su không đường)\n- Thuốc của người\n- Hóa chất tẩy rửa\n\nPHÒNG NGỪA:\n- Để thuốc và hóa chất xa tầm với\n- Không cho thú cưng ăn thức ăn của người\n- Kiểm tra danh sách thực phẩm độc hại', 'Tất cả', 'CRITICAL', NOW());

-- Dinh dưỡng cho chó
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Dinh dưỡng cơ bản cho chó', 'NUTRITION', 'dinh dưỡng,thức ăn,chó,ăn uống', 'Dinh dưỡng cho chó:\n\nThức ăn phù hợp:\n- Thức ăn khô chuyên dụng cho chó (theo độ tuổi)\n- Thịt nạc (gà, bò, heo) đã nấu chín\n- Rau củ (cà rốt, bí đỏ) đã nấu chín\n- Trứng gà đã nấu chín\n\nKhông nên cho ăn:\n- Xương nấu chín (dễ gãy, nguy hiểm)\n- Sô cô la, cà phê\n- Hành, tỏi\n- Nho, nho khô\n- Thức ăn quá mặn, quá ngọt\n\nLượng thức ăn:\n- Chó con: 3-4 bữa/ngày\n- Chó trưởng thành: 2 bữa/ngày\n- Chó già: 2-3 bữa/ngày, thức ăn dễ tiêu\n\nLuôn đảm bảo có nước sạch đầy đủ.', 'Chó', 'GENERAL', NOW());

-- Chăm sóc mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Chăm sóc cơ bản cho mèo', 'CARE', 'chăm sóc,mèo,vệ sinh,tắm,chải lông', 'Chăm sóc mèo:\n\nVệ sinh:\n- Mèo tự làm sạch, không cần tắm thường xuyên\n- Chải lông 2-3 lần/tuần (mèo lông dài: hàng ngày)\n- Vệ sinh tai, mắt khi cần\n- Cắt móng 2-3 tuần/lần\n\nMôi trường:\n- Khay vệ sinh sạch sẽ, đặt nơi yên tĩnh\n- Nước sạch đầy đủ (mèo thích nước chảy)\n- Nơi nghỉ ngơi yên tĩnh, cao ráo\n- Đồ chơi để mèo vận động\n\nDinh dưỡng:\n- Thức ăn khô hoặc ướt chuyên dụng\n- Không cho ăn thức ăn của người\n- Chia nhỏ bữa ăn (mèo ăn nhiều bữa nhỏ)\n\nSức khỏe:\n- Tiêm phòng đầy đủ\n- Tẩy giun định kỳ\n- Khám sức khỏe định kỳ', 'Mèo', 'GENERAL', NOW());

-- Tiêm phòng cho thú cưng
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Lịch tiêm phòng cho thú cưng', 'VACCINATION', 'tiêm phòng,vaccine,vắc xin,chó,mèo', 'Lịch tiêm phòng:\n\nCHO CHÓ:\n- 6-8 tuần: Mũi 1 (DHPPi - 5 bệnh)\n- 10-12 tuần: Mũi 2 (DHPPi + Dại)\n- 14-16 tuần: Mũi 3 (DHPPi + Dại)\n- Sau đó: Nhắc lại hàng năm\n\nCHO MÈO:\n- 6-8 tuần: Mũi 1 (FVRCP - 3 bệnh)\n- 10-12 tuần: Mũi 2 (FVRCP + Dại)\n- 14-16 tuần: Mũi 3 (FVRCP + Dại)\n- Sau đó: Nhắc lại hàng năm\n\nCác bệnh được phòng:\n- Chó: Dại, Parvo, Care, Viêm gan, Ho cũi chó\n- Mèo: Dại, FVR (cúm mèo), Calicivirus, Panleukopenia\n\nLưu ý:\n- Thú cưng phải khỏe mạnh khi tiêm\n- Theo dõi phản ứng sau tiêm\n- Giữ sổ tiêm phòng để theo dõi', 'Tất cả', 'GENERAL', NOW());

-- =====================================================
-- CÁC BỆNH PHỔ BIẾN Ở MÈO
-- =====================================================

-- Bệnh nôn ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh nôn ở mèo', 'DISEASE', 'nôn,ói,mèo,dạ dày,viêm dạ dày,nuốt lông,hairball', 'Mèo bị nôn có thể do:\n\nNguyên nhân phổ biến:\n1. Nuốt lông (hairball) - RẤT PHỔ BIẾN ở mèo\n2. Ăn quá nhanh\n3. Viêm dạ dày ruột\n4. Dị ứng thức ăn\n5. Bệnh thận, gan\n6. Tắc nghẽn đường ruột (do lông hoặc dị vật)\n\nTriệu chứng:\n- Nôn ra lông (hairball) - bình thường nếu thỉnh thoảng\n- Nôn liên tục > 2-3 lần/ngày - BẤT THƯỜNG\n- Có máu trong chất nôn\n- Bỏ ăn, mệt mỏi\n- Nôn ngay sau khi ăn\n\nXử lý tại nhà:\n- Nếu nôn hairball: Bình thường, cho ăn thức ăn hỗ trợ tiêu hóa lông\n- Chải lông thường xuyên để giảm nuốt lông\n- Cho ăn từng chút một, chia nhỏ bữa\n\nCần đến bác sĩ khi:\n- Nôn liên tục > 3 lần/ngày\n- Có máu trong chất nôn\n- Mèo bỏ ăn, mệt mỏi\n- Nôn ngay sau khi ăn\n- Có triệu chứng khác (tiêu chảy, sốt)', 'Mèo', 'MODERATE', NOW());

-- Bệnh đường tiết niệu ở mèo (FLUTD)
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh đường tiết niệu ở mèo', 'DISEASE', 'tiểu khó,tiểu nhiều,tiểu ra máu,mèo,đường tiết niệu,FLUTD,bàng quang', 'Bệnh đường tiết niệu dưới ở mèo (FLUTD):\n\nTriệu chứng:\n- Đi tiểu nhiều lần, mỗi lần ít\n- Rặn khi đi tiểu, khó tiểu\n- Tiểu ra máu\n- Đi tiểu ngoài khay vệ sinh (bất thường)\n- Liếm vùng sinh dục nhiều\n- Kêu la khi đi tiểu\n- Bỏ ăn, nôn\n\nNguyên nhân:\n- Sỏi bàng quang\n- Nhiễm trùng đường tiết niệu\n- Viêm bàng quang\n- Tắc nghẽn niệu đạo (KHẨN CẤP - mèo đực dễ bị)\n\nKHẨN CẤP - Cần đến bác sĩ NGAY:\n- Mèo không thể đi tiểu (tắc nghẽn)\n- Rặn liên tục nhưng không tiểu được\n- Bụng căng, đau\n- Mèo kêu la, đau đớn\n\nĐây là TÌNH TRẠNG KHẨN CẤP, có thể gây tử vong nếu không xử lý kịp thời!\n\nPhòng ngừa:\n- Cho mèo uống đủ nước\n- Thức ăn ướt giúp tăng lượng nước\n- Giảm stress\n- Giữ khay vệ sinh sạch sẽ', 'Mèo', 'CRITICAL', NOW());

-- Bệnh cúm mèo (FVR)
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh cúm mèo (FVR)', 'DISEASE', 'cúm mèo,ho,hắt hơi,chảy nước mũi,mắt,mèo,FVR,viêm mũi', 'Bệnh cúm mèo (Feline Viral Rhinotracheitis - FVR):\n\nTriệu chứng:\n- Hắt hơi, chảy nước mũi\n- Chảy nước mắt, mắt đỏ\n- Ho\n- Sốt\n- Bỏ ăn\n- Mệt mỏi\n- Loét miệng (trong trường hợp nặng)\n\nNguyên nhân:\n- Virus Herpes (FHV-1)\n- Virus Calicivirus\n- Lây qua tiếp xúc trực tiếp\n\nXử lý:\n- Cách ly mèo bệnh\n- Vệ sinh mắt, mũi bằng nước muối sinh lý\n- Đảm bảo mèo uống đủ nước\n- Cho ăn thức ăn mềm, dễ nuốt\n- Giữ ấm, tránh gió lùa\n\nCần đến bác sĩ khi:\n- Mèo bỏ ăn hoàn toàn\n- Sốt cao\n- Khó thở\n- Mắt đóng kín, có mủ\n- Triệu chứng không cải thiện sau 3-5 ngày\n\nPhòng ngừa:\n- Tiêm phòng đầy đủ (FVRCP)\n- Tránh tiếp xúc với mèo bệnh\n- Giữ môi trường sạch sẽ', 'Mèo', 'MODERATE', NOW());

-- Bệnh giun ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh giun ở mèo', 'DISEASE', 'giun,sán,mèo,ký sinh trùng,tẩy giun', 'Bệnh giun ở mèo:\n\nCác loại giun phổ biến:\n1. Giun đũa (Roundworm)\n2. Giun móc (Hookworm)\n3. Giun tóc (Whipworm)\n4. Sán dây (Tapeworm)\n\nTriệu chứng:\n- Gầy yếu, chậm lớn\n- Bụng to, căng\n- Tiêu chảy, có thể có máu\n- Nôn (có thể nôn ra giun)\n- Thấy giun trong phân\n- Lông xơ xác\n- Thiếu máu (nếu nhiều giun)\n\nTẩy giun:\n- Mèo con: 2, 4, 6, 8 tuần tuổi, sau đó mỗi tháng đến 6 tháng\n- Mèo trưởng thành: 3-4 lần/năm\n- Mèo đi ra ngoài: Tẩy thường xuyên hơn\n\nThuốc tẩy giun:\n- Cần dùng đúng loại thuốc cho mèo\n- Theo chỉ dẫn của bác sĩ\n- Không tự ý dùng thuốc của người\n\nPhòng ngừa:\n- Vệ sinh môi trường sạch sẽ\n- Không cho mèo ăn thịt sống\n- Tránh tiếp xúc với phân mèo khác\n- Tẩy giun định kỳ', 'Mèo', 'MILD', NOW());

-- Bệnh béo phì ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh béo phì ở mèo', 'DISEASE', 'béo phì,thừa cân,mèo,béo,tiểu đường', 'Bệnh béo phì ở mèo:\n\nDấu hiệu:\n- Không sờ thấy xương sườn khi vuốt\n- Không thấy eo thon\n- Mỡ tích tụ ở bụng\n- Khó di chuyển, lười vận động\n\nNguyên nhân:\n- Ăn quá nhiều\n- Ít vận động\n- Mèo trong nhà không có cơ hội vận động\n- Cho ăn thức ăn của người\n\nHậu quả:\n- Tiểu đường\n- Bệnh khớp\n- Bệnh tim\n- Vấn đề hô hấp\n- Giảm tuổi thọ\n\nGiải pháp:\n- Giảm lượng thức ăn từ từ\n- Chia nhỏ bữa ăn\n- Cho ăn thức ăn ít calo\n- Tăng cường vận động (đồ chơi, leo trèo)\n- Không cho ăn thức ăn của người\n\nCần đến bác sĩ:\n- Để được tư vấn chế độ ăn phù hợp\n- Kiểm tra sức khỏe tổng thể\n- Loại trừ các bệnh lý khác', 'Mèo', 'MODERATE', NOW());

-- Bệnh răng miệng ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh răng miệng ở mèo', 'DISEASE', 'răng,lợi,hôi miệng,mèo,viêm lợi,cao răng', 'Bệnh răng miệng ở mèo:\n\nTriệu chứng:\n- Hôi miệng\n- Lợi đỏ, sưng, chảy máu\n- Cao răng tích tụ\n- Khó ăn, nhai một bên\n- Chảy nước dãi\n- Răng lung lay, rụng\n\nNguyên nhân:\n- Vi khuẩn tích tụ thành cao răng\n- Viêm lợi (gingivitis)\n- Bệnh nha chu\n- Răng bị gãy, nhiễm trùng\n\nHậu quả:\n- Đau đớn, khó ăn\n- Nhiễm trùng có thể lan sang các cơ quan khác\n- Ảnh hưởng đến tim, thận\n\nChăm sóc tại nhà:\n- Đánh răng cho mèo (nếu mèo chịu)\n- Thức ăn khô giúp làm sạch răng\n- Đồ chơi nhai giúp làm sạch răng\n- Kiểm tra răng miệng định kỳ\n\nCần đến bác sĩ:\n- Lấy cao răng định kỳ (1-2 lần/năm)\n- Điều trị viêm lợi\n- Nhổ răng nếu cần\n- Khám răng miệng định kỳ', 'Mèo', 'MODERATE', NOW());

-- Bệnh thận ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh thận ở mèo', 'DISEASE', 'thận,mèo,tiểu nhiều,uống nhiều,suy thận', 'Bệnh thận ở mèo (đặc biệt mèo già):\n\nTriệu chứng:\n- Uống nước nhiều\n- Đi tiểu nhiều\n- Gầy yếu\n- Bỏ ăn\n- Nôn\n- Hôi miệng (mùi amoniac)\n- Lông xơ xác\n- Mệt mỏi\n\nNguyên nhân:\n- Tuổi già (mèo > 7 tuổi)\n- Nhiễm trùng thận\n- Sỏi thận\n- Ngộ độc\n- Di truyền\n\nChẩn đoán:\n- Xét nghiệm máu (BUN, Creatinine)\n- Xét nghiệm nước tiểu\n- Siêu âm thận\n\nĐiều trị:\n- Chế độ ăn đặc biệt (thức ăn cho mèo bệnh thận)\n- Thuốc hỗ trợ thận\n- Truyền dịch nếu cần\n- Điều trị theo chỉ định của bác sĩ\n\nPhòng ngừa:\n- Cho mèo uống đủ nước\n- Thức ăn chất lượng tốt\n- Khám sức khỏe định kỳ (đặc biệt mèo già)\n- Tránh thức ăn quá mặn\n\nCần đến bác sĩ NGAY khi có triệu chứng!', 'Mèo', 'SEVERE', NOW());

-- Bệnh tiểu đường ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh tiểu đường ở mèo', 'DISEASE', 'tiểu đường,mèo,đường huyết,insulin', 'Bệnh tiểu đường ở mèo:\n\nTriệu chứng:\n- Uống nước rất nhiều\n- Đi tiểu rất nhiều\n- Ăn nhiều nhưng gầy\n- Mệt mỏi, yếu sức\n- Nôn\n- Hơi thở có mùi acetone (trong trường hợp nặng)\n\nNguyên nhân:\n- Béo phì\n- Ít vận động\n- Tuổi già\n- Di truyền\n- Bệnh tuyến tụy\n\nChẩn đoán:\n- Xét nghiệm đường huyết\n- Xét nghiệm nước tiểu\n\nĐiều trị:\n- Tiêm insulin (theo chỉ định bác sĩ)\n- Chế độ ăn đặc biệt (ít carbohydrate)\n- Giảm cân nếu béo phì\n- Theo dõi đường huyết định kỳ\n\nPhòng ngừa:\n- Duy trì cân nặng hợp lý\n- Cho mèo vận động\n- Thức ăn chất lượng, không quá nhiều carbohydrate\n- Khám sức khỏe định kỳ\n\nCần đến bác sĩ để được chẩn đoán và điều trị!', 'Mèo', 'SEVERE', NOW());

-- Bệnh nấm da ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh nấm da ở mèo', 'DISEASE', 'nấm da,mèo,ngứa,rụng lông,ringworm', 'Bệnh nấm da ở mèo (Ringworm):\n\nTriệu chứng:\n- Rụng lông từng mảng\n- Da đỏ, có vảy\n- Ngứa (không phải lúc nào cũng có)\n- Vùng da tròn, có viền rõ ràng\n- Thường ở đầu, tai, chân\n\nNguyên nhân:\n- Nấm Microsporum canis\n- Lây từ mèo khác\n- Lây sang người (zoonotic)\n\nLây lan:\n- Tiếp xúc trực tiếp\n- Qua đồ dùng (lược, khăn, giường)\n- Môi trường (bào tử nấm sống lâu)\n\nĐiều trị:\n- Thuốc kháng nấm (theo chỉ định bác sĩ)\n- Tắm bằng dầu gội đặc biệt\n- Vệ sinh môi trường (làm sạch đồ dùng, giường)\n- Cách ly mèo bệnh\n\nPhòng ngừa:\n- Tránh tiếp xúc với mèo bệnh\n- Vệ sinh môi trường sạch sẽ\n- Khám sức khỏe định kỳ\n\nLưu ý: Có thể lây sang người, cần cẩn thận!', 'Mèo', 'MILD', NOW());

-- Bệnh viêm tai ở mèo
INSERT INTO veterinary_knowledge (title, category, keywords, content, species, severity_level, created_date) VALUES
('Bệnh viêm tai ở mèo', 'DISEASE', 'viêm tai,mèo,tai,ngứa tai,chảy mủ tai', 'Bệnh viêm tai ở mèo:\n\nTriệu chứng:\n- Gãi tai nhiều\n- Lắc đầu\n- Nghiêng đầu về một bên\n- Tai đỏ, sưng\n- Có mùi hôi từ tai\n- Chảy mủ, dịch từ tai\n- Mèo đau khi chạm vào tai\n\nNguyên nhân:\n- Nhiễm ký sinh trùng (ve tai)\n- Nhiễm nấm, vi khuẩn\n- Dị ứng\n- Dị vật trong tai\n- U nang, khối u\n\nXử lý:\n- Vệ sinh tai bằng dung dịch vệ sinh tai\n- Không dùng tăm bông đẩy sâu vào tai\n- Điều trị theo nguyên nhân (thuốc nhỏ tai)\n\nCần đến bác sĩ khi:\n- Tai sưng, đỏ nhiều\n- Chảy mủ, có mùi hôi\n- Mèo đau đớn nhiều\n- Không cải thiện sau vài ngày\n\nPhòng ngừa:\n- Vệ sinh tai định kỳ (nếu cần)\n- Kiểm tra tai thường xuyên\n- Tránh để nước vào tai khi tắm', 'Mèo', 'MODERATE', NOW());

