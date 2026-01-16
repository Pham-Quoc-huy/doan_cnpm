-- =====================================================
-- Script INSERT các keyword triệu chứng chung vào bảng disease_cat
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng chung với keywords
INSERT INTO disease_cat (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. TRIỆU CHỨNG CHUNG
-- =====================================================

-- 1. Mệt mỏi / Lờ đờ / Yếu
('Triệu chứng mệt mỏi, lờ đờ, yếu ở mèo',
 'mệt mỏi,lờ đờ,yếu,kiệt sức,không hoạt động,không chạy nhảy',
 'Mệt mỏi, lờ đờ, yếu là các triệu chứng phổ biến ở mèo có thể do nhiều nguyên nhân. Nghi ngờ: Sốt / nhiễm trùng, Bệnh gan / thận, Thiếu máu, Cảm cúm mèo (Feline Herpesvirus, FHV), Giun sán / ký sinh trùng, Bệnh tiểu đường, Bệnh tim (suy tim, bệnh cơ tim), Ngộ độc, Bệnh hô hấp (viêm phổi, viêm phế quản). Follow-up: Có nôn / tiêu chảy không? Có chảy nước mắt / ghèn mắt không? Có ho / khó thở không? Có mùi lạ từ miệng hay cơ thể không? Cần đưa mèo đến bác sĩ thú y nếu triệu chứng kéo dài hoặc kèm theo các dấu hiệu khác.',
 'GENERAL',
 TRUE,
 NOW()),

-- 2. Bỏ ăn / Biếng ăn / Ăn ít
('Triệu chứng bỏ ăn, biếng ăn, ăn ít ở mèo',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém',
 'Bỏ ăn, biếng ăn, ăn ít là dấu hiệu cảnh báo sức khỏe của mèo. Nghi ngờ: Sốt / nhiễm trùng, Bệnh gan / thận, Thiếu máu, Cảm cúm mèo (Feline Herpesvirus, FHV), Giun sán / ký sinh trùng, Bệnh tiểu đường, Bệnh tim, Ngộ độc. Follow-up: Có nôn / tiêu chảy không? Có chảy nước mắt / ghèn mắt không? Có ho / khó thở không? Có mùi lạ từ miệng hay cơ thể không? Nếu mèo bỏ ăn quá 24 giờ, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Sụt cân / Gầy nhanh
('Triệu chứng sụt cân, gầy nhanh ở mèo',
 'sụt cân,gầy nhanh,gầy,gầy đi,giảm cân nhanh,ốm yếu',
 'Sụt cân hoặc gầy nhanh là dấu hiệu nghiêm trọng ở mèo. Nghi ngờ: Bệnh gan / thận, Bệnh tiểu đường, Giun sán / ký sinh trùng, Bệnh tim, Bệnh đường ruột (IBD), U bướu. Follow-up: Có bỏ ăn không? Có nôn / tiêu chảy không? Có khát nhiều / uống nhiều không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- 4. Sốt / Nóng / Run
('Triệu chứng sốt, nóng, run ở mèo',
 'sốt,nóng,run,run rẩy,thân nhiệt cao,người nóng',
 'Sốt, nóng, run là dấu hiệu của nhiễm trùng hoặc bệnh tật. Nghi ngờ: Sốt / nhiễm trùng, Cảm cúm mèo (Feline Herpesvirus, FHV), Bệnh hô hấp (viêm phổi, viêm phế quản), Ngộ độc. Follow-up: Có nôn / tiêu chảy không? Có ho / khó thở không? Có chảy nước mắt / ghèn mắt không? Cần đo nhiệt độ và đưa mèo đến bác sĩ thú y nếu sốt cao hoặc kéo dài.',
 'MODERATE',
 TRUE,
 NOW()),

-- 5. Khát nhiều / Uống nhiều
('Triệu chứng khát nhiều, uống nhiều ở mèo',
 'khát nhiều,uống nhiều,uống nước nhiều,khát nước liên tục',
 'Khát nhiều, uống nhiều có thể là dấu hiệu của bệnh nghiêm trọng. Nghi ngờ: Bệnh tiểu đường, Bệnh thận, Bệnh gan, Bệnh cường giáp. Follow-up: Có tiểu nhiều không? Có sụt cân không? Có bỏ ăn không? Cần đưa mèo đến bác sĩ thú y để kiểm tra đường huyết và chức năng thận.',
 'MODERATE',
 TRUE,
 NOW()),

-- 6. Tiểu nhiều / Tiểu ít
('Triệu chứng tiểu nhiều, tiểu ít ở mèo',
 'tiểu nhiều,tiểu ít,đi tiểu nhiều,đi tiểu ít,tiểu không được',
 'Tiểu nhiều hoặc tiểu ít bất thường là dấu hiệu cần chú ý. Nghi ngờ: Bệnh tiểu đường, Bệnh thận, Bệnh tiết niệu (UTI, sỏi thận), Viêm tử cung. Follow-up: Có tiểu ra máu không? Có khó tiểu không? Có liếm vùng kín nhiều không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 7. Đau / Rên / Không cho sờ
('Triệu chứng đau, rên, không cho sờ ở mèo',
 'đau,rên,không cho sờ,đau khi chạm,kêu đau,đau đớn',
 'Đau, rên, không cho sờ là dấu hiệu của đau đớn hoặc khó chịu. Nghi ngờ: Chấn thương, Viêm khớp, Bệnh đường ruột, Bệnh tiết niệu, Ngộ độc. Follow-up: Đau ở vị trí nào? Có sưng không? Có bỏ ăn không? Cần đưa mèo đến bác sĩ thú y để xác định nguyên nhân và điều trị giảm đau.',
 'MODERATE',
 TRUE,
 NOW()),

-- 8. Khó thở / Thở gấp
('Triệu chứng khó thở, thở gấp ở mèo',
 'khó thở,thở gấp,thở nhanh,thở khó,thở há miệng',
 'Khó thở, thở gấp là dấu hiệu nghiêm trọng cần cấp cứu. Nghi ngờ: Bệnh tim (suy tim, bệnh cơ tim), Bệnh hô hấp (viêm phổi, viêm phế quản), Hen suyễn mèo, Bệnh truyền nhiễm (Feline Calicivirus, Feline Herpesvirus). Follow-up: Có ho không? Có tím lưỡi / lợi không? Có khó thở khi vận động không? Cần cấp cứu ngay nếu mèo khó thở nghiêm trọng.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. TIÊU HÓA
-- =====================================================

-- 9. Nôn / Ói
('Triệu chứng nôn, ói ở mèo',
 'nôn,ói,ói ra bọt,ói vàng,ói liên tục',
 'Nôn, ói là triệu chứng phổ biến ở mèo. Nghi ngờ: Bệnh viêm dạ dày ruột, Viêm tụy (Pancreatitis), Ngộ độc thức ăn, Bệnh giun sán, Bệnh đường ruột (IBD - Inflammatory Bowel Disease), Viêm gan / thận, U bướu đường tiêu hóa. Follow-up: Có nôn liên tục không? Có tiêu chảy không? Ăn cỏ có phải để nôn? Nếu nôn kéo dài hoặc kèm theo các triệu chứng khác, cần đưa đến bác sĩ thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 10. Tiêu chảy / Phân lỏng
('Triệu chứng tiêu chảy, phân lỏng ở mèo',
 'tiêu chảy,phân lỏng,đi phân lỏng,đi ngoài lỏng,phân không thành khuôn',
 'Tiêu chảy, phân lỏng có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh viêm dạ dày ruột, Ngộ độc thức ăn, Bệnh giun sán, Bệnh đường ruột (IBD), Viêm gan / thận. Follow-up: Có nôn không? Phân có máu không? Có bỏ ăn không? Nếu tiêu chảy kéo dài hoặc kèm theo máu, cần đưa đến bác sĩ thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 11. Phân có máu / Phân đen
('Triệu chứng phân có máu, phân đen ở mèo',
 'phân có máu,phân đen,phân máu,đi ngoài ra máu,phân màu đen',
 'Phân có máu hoặc phân đen là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh viêm dạ dày ruột, U bướu đường tiêu hóa, Bệnh đường ruột (IBD), Chảy máu đường tiêu hóa. Follow-up: Có nôn không? Có đau bụng không? Cần đưa mèo đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 12. Táo bón / Không đi ngoài
('Triệu chứng táo bón, không đi ngoài ở mèo',
 'táo bón,không đi ngoài,rặn,đi ngoài khó,phân cứng',
 'Táo bón, không đi ngoài có thể do nhiều nguyên nhân. Nghi ngờ: Tắc nghẽn đường ruột, Bệnh đường ruột, Thiếu nước, Vấn đề về chế độ ăn. Follow-up: Có nôn không? Có đau bụng không? Có bỏ ăn không? Nếu táo bón kéo dài, cần đưa đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 13. Ăn cỏ / Liếm nền
('Triệu chứng ăn cỏ, liếm nền ở mèo',
 'ăn cỏ,liếm nền,liếm tường,ăn cỏ để nôn',
 'Ăn cỏ, liếm nền thường là dấu hiệu của buồn nôn hoặc đau bụng. Nghi ngờ: Bệnh viêm dạ dày ruột, Viêm tụy, Bệnh đường ruột, Có dị vật trong dạ dày. Follow-up: Có nôn sau khi ăn cỏ không? Có tiêu chảy không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. HÔ HẤP & TIM MẠCH
-- =====================================================

-- 14. Ho / Ho khan / Ho có đờm
('Triệu chứng ho, ho khan, ho có đờm ở mèo',
 'ho,ho khan,ho có đờm,ho liên tục,ho về đêm',
 'Ho, ho khan, ho có đờm có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh hô hấp (viêm phổi, viêm phế quản), Hen suyễn mèo, Bệnh truyền nhiễm (Feline Calicivirus, Feline Herpesvirus), Viêm xoang / viêm mũi. Follow-up: Có khó thở khi vận động không? Ho khan / ho đờm có kèm theo sốt không? Có chảy nước mắt / ghèn mắt không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 15. Khó thở / Thở gấp / Thở há miệng
('Triệu chứng khó thở, thở gấp, thở há miệng ở mèo',
 'khó thở,thở gấp,thở há miệng,thở nhanh,thở khó',
 'Khó thở, thở gấp, thở há miệng là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh tim (suy tim, bệnh cơ tim), Bệnh hô hấp (viêm phổi, viêm phế quản), Hen suyễn mèo, Bệnh truyền nhiễm. Follow-up: Có ho không? Có tím lưỡi / lợi không? Có khó thở khi vận động không? Cần cấp cứu ngay nếu mèo khó thở nghiêm trọng.',
 'SEVERE',
 TRUE,
 NOW()),

-- 16. Run / Lắc đầu
('Triệu chứng run, lắc đầu ở mèo',
 'run,run rẩy,lắc đầu,lắc đầu nhiều,run toàn thân',
 'Run, lắc đầu có thể do nhiều nguyên nhân. Nghi ngờ: Sốt / nhiễm trùng, Bệnh thần kinh, Ngộ độc, Vấn đề về tai. Follow-up: Có sốt không? Có mất thăng bằng không? Có nghiêng đầu không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 17. Mắt đỏ / Chảy nước mắt
('Triệu chứng mắt đỏ, chảy nước mắt ở mèo',
 'mắt đỏ,chảy nước mắt,đỏ mắt,viêm mắt,chảy ghèn mắt',
 'Mắt đỏ, chảy nước mắt có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc (pink eye), Nhiễm virus (FHV - Feline Herpesvirus), Bệnh truyền nhiễm (Feline Calicivirus, Feline Herpesvirus). Follow-up: Có mắt chảy ghèn liên tục không? Mắt có đỏ / viêm không? Có ho / khó thở không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 18. Tím lưỡi / Lợi
('Triệu chứng tím lưỡi, tím lợi ở mèo',
 'tím lưỡi,tím lợi,lưỡi tím,lợi tím,thiếu oxy',
 'Tím lưỡi, tím lợi là dấu hiệu nghiêm trọng của thiếu oxy. Nghi ngờ: Bệnh tim (suy tim, bệnh cơ tim), Bệnh hô hấp (viêm phổi, viêm phế quản), Hen suyễn mèo. Follow-up: Có khó thở không? Có ho không? Cần cấp cứu ngay lập tức nếu mèo có dấu hiệu tím lưỡi / lợi.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. DA VÀ LÔNG
-- =====================================================

-- 19. Rụng lông / Hói / Vảy
('Triệu chứng rụng lông, hói, vảy ở mèo',
 'rụng lông,hói,vảy,gàu,rụng lông theo mảng,lông rụng nhiều',
 'Rụng lông, hói, vảy có thể do nhiều nguyên nhân. Nghi ngờ: Viêm da dị ứng (allergy), Nấm da (ringworm), Bệnh da do ký sinh trùng (ve, rận, bọ chét), Rối loạn nội tiết (cường giáp, tiểu đường). Follow-up: Lông có bị rụng theo mảng không? Có chảy mủ từ da không? Có sưng ở da không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 20. Da đỏ / Viêm da
('Triệu chứng da đỏ, viêm da ở mèo',
 'da đỏ,viêm da,da bị đỏ,viêm da mủ,da sưng đỏ',
 'Da đỏ, viêm da có thể do nhiều nguyên nhân. Nghi ngờ: Viêm da dị ứng (allergy), Bệnh viêm da mủ (pyoderma), Viêm da do vi khuẩn, Nấm da (ringworm). Follow-up: Có chảy mủ từ da không? Có sưng ở da không? Có ngứa / gãi nhiều không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 21. Ngứa / Gãi nhiều
('Triệu chứng ngứa, gãi nhiều ở mèo',
 'ngứa,gãi nhiều,gãi liên tục,ngứa da,ngứa ngáy',
 'Ngứa, gãi nhiều có thể do nhiều nguyên nhân. Nghi ngờ: Viêm da dị ứng (allergy), Bệnh da do ký sinh trùng (ve, rận, bọ chét), Nấm da (ringworm), Viêm da do vi khuẩn. Follow-up: Có rụng lông không? Có da đỏ / viêm da không? Có mụn / ghẻ không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 22. Mùi hôi lạ
('Triệu chứng mùi hôi lạ ở mèo',
 'mùi hôi lạ,mùi hôi,mùi khó chịu,mùi bất thường',
 'Mùi hôi lạ có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh viêm da mủ (pyoderma), Viêm da do vi khuẩn, Vấn đề về răng miệng, Bệnh tiết niệu. Follow-up: Mùi hôi từ đâu? Từ da, miệng, hay vùng kín? Có các triệu chứng khác không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 23. Mụn / Ghẻ
('Triệu chứng mụn, ghẻ ở mèo',
 'mụn,ghẻ,mụn mủ,mụn trên da,ghẻ lở',
 'Mụn, ghẻ có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh viêm da mủ (pyoderma), Viêm da do vi khuẩn, Nấm da (ringworm), Bệnh da do ký sinh trùng. Follow-up: Có chảy mủ từ mụn không? Có sưng không? Có ngứa / gãi nhiều không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 24. Ve / Rận / Bọ chét
('Triệu chứng ve, rận, bọ chét ở mèo',
 've,rận,bọ chét,chấy rận,ký sinh trùng da,bọ chét mèo',
 'Ve, rận, bọ chét là các ký sinh trùng ngoài da phổ biến ở mèo. Nghi ngờ: Bệnh da do ký sinh trùng (ve, rận, bọ chét). Follow-up: Có ngứa / gãi nhiều không? Có rụng lông không? Có da đỏ / viêm da không? Cần điều trị bằng thuốc diệt ký sinh trùng và vệ sinh môi trường sống. Nên tham khảo bác sĩ thú y để chọn loại thuốc phù hợp và an toàn.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. MẮT
-- =====================================================

-- 25. Chảy nước mắt / Ghèn mắt
('Triệu chứng chảy nước mắt, ghèn mắt ở mèo',
 'chảy nước mắt,ghèn mắt,ghèn mắt nhiều,nước mắt nhiều,chảy ghèn',
 'Chảy nước mắt, ghèn mắt có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc (pink eye), Nhiễm virus (FHV - Feline Herpesvirus), Bệnh truyền nhiễm (Feline Calicivirus, Feline Herpesvirus). Follow-up: Có mắt chảy ghèn liên tục không? Mắt có đỏ / viêm không? Có ho / khó thở không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 26. Mắt đỏ / Viêm kết mạc
('Triệu chứng mắt đỏ, viêm kết mạc ở mèo',
 'mắt đỏ,viêm kết mạc,đỏ mắt,viêm mắt,đỏ kết mạc',
 'Mắt đỏ, viêm kết mạc có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc (pink eye), Nhiễm virus (FHV - Feline Herpesvirus), Bệnh truyền nhiễm (Feline Calicivirus, Feline Herpesvirus). Follow-up: Có mắt chảy ghèn liên tục không? Mắt có đỏ / viêm không? Có sợ sáng / nheo mắt không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 27. Mắt mờ / Đục
('Triệu chứng mắt mờ, đục ở mèo',
 'mắt mờ,mắt đục,trắng đục,mắt bị đục,đục thủy tinh thể',
 'Mắt mờ, đục có thể do nhiều nguyên nhân. Nghi ngờ: Đục thủy tinh thể, Glaucoma / tăng nhãn áp, Bệnh mắt do ký sinh trùng. Follow-up: Có mắt đỏ không? Có sợ sáng / nheo mắt không? Cần đưa mèo đến bác sĩ thú y chuyên khoa mắt để chẩn đoán và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 28. Sợ sáng / Nheo mắt
('Triệu chứng sợ sáng, nheo mắt ở mèo',
 'sợ sáng,nheo mắt,nhạy cảm với ánh sáng,tránh ánh sáng,nhắm mắt khi ra nắng',
 'Sợ sáng, nheo mắt có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc, Glaucoma / tăng nhãn áp, Đục thủy tinh thể, Bệnh mắt do ký sinh trùng. Follow-up: Có mắt đỏ không? Có chảy nước mắt / ghèn mắt không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. TAI
-- =====================================================

-- 29. Hôi tai
('Triệu chứng hôi tai ở mèo',
 'hôi tai,tai hôi,mùi hôi tai,mùi khó chịu ở tai',
 'Hôi tai là dấu hiệu phổ biến của nhiễm trùng tai, viêm tai, hoặc tích tụ ráy tai. Nghi ngờ: Viêm tai giữa, Nhiễm trùng tai (do vi khuẩn hoặc nấm), Ve tai, Ký sinh trùng (otoacariasis). Follow-up: Có mùi hôi từ tai không? Tai có chảy dịch không? Có gãi tai / lắc đầu không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 30. Tai đỏ / Sưng
('Triệu chứng tai đỏ, sưng ở mèo',
 'tai đỏ,tai sưng,tai bị đỏ,tai sưng to,viêm tai',
 'Tai đỏ, sưng là dấu hiệu của viêm tai, nhiễm trùng, hoặc dị ứng. Nghi ngờ: Viêm tai giữa, Nhiễm trùng tai (do vi khuẩn hoặc nấm), Ve tai, Ký sinh trùng (otoacariasis). Follow-up: Có mùi hôi từ tai không? Tai có chảy dịch không? Có gãi tai / lắc đầu không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- 31. Ráy tai nhiều / Đen
('Triệu chứng ráy tai nhiều, đen ở mèo',
 'ráy tai nhiều,ráy tai đen,ráy tai,ráy tai màu đen,ráy tai dày',
 'Ráy tai nhiều hoặc ráy tai đen bất thường thường là dấu hiệu của nhiễm trùng tai, viêm tai, hoặc nhiễm ve tai (ear mites). Nghi ngờ: Viêm tai giữa, Nhiễm trùng tai, Ve tai, Ký sinh trùng (otoacariasis). Follow-up: Có mùi hôi từ tai không? Tai có chảy dịch không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 32. Lắc đầu / Nghiêng đầu
('Triệu chứng lắc đầu, nghiêng đầu ở mèo',
 'lắc đầu,nghiêng đầu,lắc đầu nhiều,nghiêng đầu một bên,đầu nghiêng',
 'Lắc đầu hoặc nghiêng đầu thường là dấu hiệu của vấn đề về tai. Nghi ngờ: Viêm tai giữa, Nhiễm trùng tai, Ve tai, Ký sinh trùng (otoacariasis), Polyp tai. Follow-up: Có mùi hôi từ tai không? Tai có chảy dịch không? Có gãi tai không? Cần đưa mèo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 33. Gãi tai
('Triệu chứng gãi tai ở mèo',
 'gãi tai,gãi tai nhiều,gãi tai liên tục,ngứa tai',
 'Gãi tai là dấu hiệu phổ biến của ngứa tai, có thể do viêm tai, nhiễm trùng, ve tai, hoặc dị vật. Nghi ngờ: Viêm tai giữa, Nhiễm trùng tai, Ve tai, Ký sinh trùng (otoacariasis). Follow-up: Có mùi hôi từ tai không? Tai có chảy dịch không? Có lắc đầu / nghiêng đầu không? Cần kiểm tra tai và điều trị tại phòng khám thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 7. MIỆNG & RĂNG
-- =====================================================

-- 34. Hôi miệng
('Triệu chứng hôi miệng ở mèo',
 'hôi miệng,miệng hôi,mùi hôi miệng,mùi khó chịu ở miệng',
 'Hôi miệng ở mèo thường do vấn đề về răng miệng hoặc bệnh tật. Nghi ngờ: Viêm lợi / viêm nướu, Nhiễm trùng miệng, Bệnh gan (liver disease), Nhiễm trùng do vi khuẩn, Cao răng. Follow-up: Có chảy máu lợi không? Miệng có mùi hôi lạ không? Có khó ăn / không ăn không? Cần kiểm tra răng miệng và làm sạch răng định kỳ.',
 'MODERATE',
 TRUE,
 NOW()),

-- 35. Loét miệng / Vết loét
('Triệu chứng loét miệng, vết loét ở mèo',
 'loét miệng,vết loét,loét trong miệng,loét lưỡi,loét nướu',
 'Loét miệng, vết loét có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng miệng, Bệnh truyền nhiễm (Feline Calicivirus, Feline Herpesvirus), Viêm lợi / viêm nướu. Follow-up: Có chảy máu lợi không? Có khó ăn / không ăn không? Cần đưa mèo đến bác sĩ thú y để xác định nguyên nhân và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 36. Chảy máu lợi / Viêm lợi
('Triệu chứng chảy máu lợi, viêm lợi ở mèo',
 'chảy máu lợi,viêm lợi,chảy máu nướu,lợi chảy máu,viêm nướu',
 'Chảy máu lợi, viêm lợi thường là dấu hiệu của bệnh nướu răng. Nghi ngờ: Viêm lợi / viêm nướu, Nhiễm trùng miệng, Cao răng. Follow-up: Có hôi miệng không? Có khó ăn / không ăn không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị. Làm sạch răng và điều trị viêm nướu có thể cần thiết.',
 'MODERATE',
 TRUE,
 NOW()),

-- 37. Khó ăn / Không ăn
('Triệu chứng khó ăn, không ăn ở mèo',
 'khó ăn,không ăn,ăn khó,ăn đau,không chịu ăn',
 'Khó ăn, không ăn có thể do nhiều nguyên nhân. Nghi ngờ: Viêm lợi / viêm nướu, Nhiễm trùng miệng, Loét miệng, Bệnh gan, Vấn đề về răng. Follow-up: Có hôi miệng không? Có chảy máu lợi không? Có loét miệng không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 8. THẦN KINH
-- =====================================================

-- 38. Co giật / Động kinh
('Triệu chứng co giật, động kinh ở mèo',
 'co giật,động kinh,lên cơn co giật,co giật toàn thân',
 'Co giật, động kinh là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh thần kinh (chấn thương, viêm não), Động kinh / co giật, U não, Ngộ độc, Bệnh cột sống. Follow-up: Có co giật hay ngất xỉu không? Có mất thăng bằng khi di chuyển không? Trong cơn co giật, cần giữ mèo an toàn và đưa đến bác sĩ thú y ngay sau cơn co giật.',
 'SEVERE',
 TRUE,
 NOW()),

-- 39. Run / Tê liệt
('Triệu chứng run, tê liệt ở mèo',
 'run,run rẩy,tê liệt,liệt,liệt chân,yếu chân',
 'Run, tê liệt có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh thần kinh, Chấn thương cột sống, U não, Ngộ độc, Bệnh cột sống. Follow-up: Có mất thăng bằng không? Có quay vòng / đi loạng choạng không? Cần đưa mèo đến bác sĩ thú y ngay để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- 40. Mất thăng bằng
('Triệu chứng mất thăng bằng ở mèo',
 'mất thăng bằng,không giữ được thăng bằng,ngã,đứng không vững',
 'Mất thăng bằng có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh thần kinh (chấn thương, viêm não), U não, Vấn đề về tai trong, Bệnh cột sống. Follow-up: Có quay vòng / đi loạng choạng không? Có nghiêng đầu không? Cần đưa mèo đến bác sĩ thú y ngay để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- 41. Quay vòng / Đi loạng choạng
('Triệu chứng quay vòng, đi loạng choạng ở mèo',
 'quay vòng,quay tròn,đi loạng choạng,đi không vững,đi xiêu vẹo',
 'Quay vòng, đi loạng choạng thường là dấu hiệu của các vấn đề về thần kinh hoặc tai trong. Nghi ngờ: Bệnh thần kinh, U não, Vấn đề về tai trong, Bệnh cột sống. Follow-up: Có mất thăng bằng không? Có nghiêng đầu không? Cần đưa mèo đến bác sĩ thú y ngay để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- 42. Mắt trợn ngược
('Triệu chứng mắt trợn ngược ở mèo',
 'mắt trợn ngược,trợn mắt,mắt lộn ngược',
 'Mắt trợn ngược là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh thần kinh, Co giật / động kinh, U não, Ngộ độc. Follow-up: Có co giật không? Có mất thăng bằng không? Cần cấp cứu ngay lập tức nếu mèo có dấu hiệu mắt trợn ngược.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 9. TIẾT NIỆU & SINH DỤC
-- =====================================================

-- 43. Tiểu ra máu
('Triệu chứng tiểu ra máu ở mèo',
 'tiểu ra máu,nước tiểu có máu,tiểu máu,máu trong nước tiểu,đái ra máu',
 'Tiểu ra máu là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh tiết niệu (UTI, sỏi thận), Nhiễm trùng đường tiểu, Ung thư đường tiết niệu, Bệnh thận. Follow-up: Có chảy dịch từ vùng sinh dục không? Có liếm liên tục không? Cần đưa mèo đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 44. Khó tiểu
('Triệu chứng khó tiểu ở mèo',
 'khó tiểu,tiểu khó,không tiểu được,tiểu không ra,không đi tiểu được',
 'Khó tiểu là dấu hiệu nghiêm trọng có thể do tắc nghẽn đường tiết niệu, sỏi, khối u, hoặc các vấn đề về thần kinh. Nghi ngờ: Bệnh tiết niệu (UTI, sỏi thận), Nhiễm trùng đường tiểu, Ung thư đường tiết niệu. Follow-up: Có tiểu ra máu không? Có liếm vùng kín nhiều không? Cần cấp cứu ngay nếu mèo không thể tiểu được.',
 'SEVERE',
 TRUE,
 NOW()),

-- 45. Liếm vùng kín nhiều
('Triệu chứng liếm vùng kín nhiều ở mèo',
 'liếm vùng kín nhiều,liếm bộ phận sinh dục,liếm âm hộ,liếm dương vật',
 'Liếm vùng kín nhiều có thể do ngứa, đau, nhiễm trùng đường tiết niệu, viêm, hoặc các vấn đề về sinh dục. Nghi ngờ: Bệnh tiết niệu (UTI, sỏi thận), Nhiễm trùng đường tiểu, Viêm tử cung (chó cái). Follow-up: Có tiểu ra máu không? Có chảy dịch từ vùng sinh dục không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 46. Chảy dịch âm đạo
('Triệu chứng chảy dịch âm đạo ở mèo',
 'chảy dịch âm đạo,chảy dịch âm hộ,dịch từ âm hộ,chảy mủ sinh dục',
 'Chảy dịch từ âm đạo có thể do nhiễm trùng, viêm, hoặc các bệnh về sinh dục. Nghi ngờ: Viêm tử cung (chó cái), Nhiễm trùng đường tiểu, Bệnh tiết niệu. Follow-up: Có tiểu ra máu không? Có liếm liên tục không? Cần đưa mèo đến bác sĩ thú y để kiểm tra và điều trị. Một số trường hợp có thể cần phẫu thuật.',
 'SEVERE',
 TRUE,
 NOW()),

-- 47. Nước tiểu có mùi lạ
('Triệu chứng nước tiểu có mùi lạ ở mèo',
 'nước tiểu có mùi lạ,nước tiểu hôi,mùi hôi nước tiểu',
 'Nước tiểu có mùi lạ có thể do nhiễm trùng đường tiết niệu, viêm bàng quang, hoặc các vấn đề về thận. Nghi ngờ: Bệnh tiết niệu (UTI, sỏi thận), Nhiễm trùng đường tiểu, Bệnh thận. Follow-up: Có tiểu ra máu không? Có khó tiểu không? Cần đưa mèo đến bác sĩ thú y để kiểm tra. Có thể cần xét nghiệm nước tiểu để xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 10. BỆNH TRUYỀN NHIỄM THƯỜNG GẶP
-- =====================================================

-- 48. Feline Calicivirus (FCV)
('Triệu chứng Feline Calicivirus (FCV) ở mèo',
 'feline calicivirus,fcv,calicivirus,viêm miệng mèo',
 'Feline Calicivirus (FCV) là bệnh truyền nhiễm phổ biến ở mèo. Triệu chứng: Loét miệng, Chảy nước dãi, Ho, Hắt hơi, Chảy nước mắt, Sốt, Bỏ ăn. Nghi ngờ: Nhiễm virus, Bệnh đường hô hấp cấp (cúm mèo). Cần đưa mèo đến bác sĩ thú y để điều trị và cách ly để tránh lây lan.',
 'SEVERE',
 TRUE,
 NOW()),

-- 49. Feline Herpesvirus (FHV)
('Triệu chứng Feline Herpesvirus (FHV) ở mèo',
 'feline herpesvirus,fhv,herpesvirus mèo,cảm cúm mèo',
 'Feline Herpesvirus (FHV) là bệnh truyền nhiễm phổ biến ở mèo. Triệu chứng: Chảy nước mắt / ghèn mắt, Mắt đỏ, Hắt hơi, Ho, Sốt, Bỏ ăn, Mệt mỏi. Nghi ngờ: Nhiễm virus, Bệnh đường hô hấp cấp (cúm mèo). Cần đưa mèo đến bác sĩ thú y để điều trị và cách ly để tránh lây lan.',
 'SEVERE',
 TRUE,
 NOW()),

-- 50. Feline Immunodeficiency Virus (FIV)
('Triệu chứng Feline Immunodeficiency Virus (FIV) ở mèo',
 'feline immunodeficiency virus,fiv,immunodeficiency virus,suy giảm miễn dịch mèo',
 'Feline Immunodeficiency Virus (FIV) là bệnh suy giảm miễn dịch ở mèo. Triệu chứng: Mệt mỏi, Sụt cân, Sốt, Nhiễm trùng tái phát, Bệnh răng miệng. Nghi ngờ: Nhiễm virus. Cần đưa mèo đến bác sĩ thú y để xét nghiệm và điều trị. Mèo bị FIV cần được chăm sóc đặc biệt và cách ly.',
 'SEVERE',
 TRUE,
 NOW()),

-- 51. Feline Leukemia Virus (FeLV)
('Triệu chứng Feline Leukemia Virus (FeLV) ở mèo',
 'feline leukemia virus,felv,leukemia virus,ung thư máu mèo',
 'Feline Leukemia Virus (FeLV) là bệnh nghiêm trọng ở mèo. Triệu chứng: Mệt mỏi, Sụt cân, Sốt, Thiếu máu, Nhiễm trùng tái phát, U bướu. Nghi ngờ: Nhiễm virus. Cần đưa mèo đến bác sĩ thú y để xét nghiệm và điều trị. Mèo bị FeLV cần được chăm sóc đặc biệt và cách ly.',
 'SEVERE',
 TRUE,
 NOW()),

-- 52. Bệnh giun sán
('Triệu chứng bệnh giun sán ở mèo',
 'giun sán,ký sinh trùng,giun,giun đũa,giun móc,giun tóc',
 'Bệnh giun sán là bệnh phổ biến ở mèo. Triệu chứng: Sụt cân, Bỏ ăn, Tiêu chảy, Nôn, Bụng chướng, Thiếu máu. Nghi ngờ: Giun sán / ký sinh trùng, Nhiễm ký sinh trùng. Cần đưa mèo đến bác sĩ thú y để xét nghiệm phân và điều trị bằng thuốc tẩy giun. Nên tẩy giun định kỳ cho mèo.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_cat ORDER BY created_date DESC LIMIT 52;
