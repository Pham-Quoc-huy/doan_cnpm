-- =====================================================
-- Script INSERT các keyword triệu chứng chung vào bảng disease_dog
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng chung với keywords
INSERT INTO disease_dog (title, keywords, content, severity_level, is_active, created_date) VALUES
-- 1. Mệt / Lờ đờ / Yếu
('Triệu chứng mệt mỏi, lờ đờ, yếu ở chó', 
 'mệt,mệt mỏi,lờ đờ,yếu,kiệt sức,không hoạt động,không chạy nhảy',
 'Mệt mỏi, lờ đờ, yếu là các triệu chứng phổ biến ở chó có thể do nhiều nguyên nhân như bệnh tật, thiếu dinh dưỡng, hoặc vấn đề về tim mạch. Cần theo dõi và đưa chó đến bác sĩ thú y nếu triệu chứng kéo dài hoặc kèm theo các dấu hiệu khác.',
 'GENERAL',
 TRUE,
 NOW()),

-- 2. Bỏ ăn / Biếng ăn / Ăn ít
('Triệu chứng bỏ ăn, biếng ăn, ăn ít ở chó',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém',
 'Bỏ ăn, biếng ăn, ăn ít là dấu hiệu cảnh báo sức khỏe của chó. Có thể do bệnh răng miệng, vấn đề tiêu hóa, stress, hoặc bệnh nghiêm trọng khác. Nếu chó bỏ ăn quá 24 giờ, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Sụt cân / Gầy nhanh
('Triệu chứng sụt cân, gầy nhanh ở chó',
 'sụt cân,gầy,gầy nhanh,giảm cân,ốm yếu,thiếu cân',
 'Sụt cân hoặc gầy nhanh không rõ nguyên nhân là dấu hiệu nghiêm trọng. Có thể do bệnh mãn tính, ký sinh trùng, vấn đề tiêu hóa, hoặc bệnh ung thư. Cần kiểm tra sức khỏe toàn diện tại phòng khám thú y.',
 'SEVERE',
 TRUE,
 NOW()),

-- 4. Sốt / Nóng / Run
('Triệu chứng sốt, nóng, run ở chó',
 'sốt,nóng,run,rùng mình,thân nhiệt cao,phát sốt',
 'Sốt là phản ứng của cơ thể chống lại nhiễm trùng hoặc bệnh tật. Nhiệt độ bình thường của chó là 38-39°C. Nếu nhiệt độ trên 39.5°C kèm run rẩy, cần đưa chó đến bác sĩ thú y ngay để điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 5. Khát nhiều / Uống nhiều
('Triệu chứng khát nhiều, uống nhiều ở chó',
 'khát nhiều,uống nhiều,khát nước,uống liên tục,đa khát',
 'Khát nhiều và uống nhiều nước bất thường có thể là dấu hiệu của bệnh tiểu đường, bệnh thận, hoặc các vấn đề nội tiết khác. Cần kiểm tra máu và nước tiểu để chẩn đoán chính xác.',
 'MODERATE',
 TRUE,
 NOW()),

-- 6. Tiểu nhiều / Tiểu ít
('Triệu chứng tiểu nhiều hoặc tiểu ít ở chó',
 'tiểu nhiều,tiểu ít,đi tiểu nhiều,đi tiểu ít,không tiểu được,tiểu khó',
 'Thay đổi tần suất đi tiểu có thể do nhiễm trùng đường tiết niệu, bệnh thận, tiểu đường, hoặc tắc nghẽn. Tiểu ít hoặc không tiểu được là trường hợp khẩn cấp cần can thiệp ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 7. Đau / Rên / Không cho sờ
('Triệu chứng đau, rên, không cho sờ ở chó',
 'đau,rên,không cho sờ,đau đớn,kêu rên,không chịu sờ,nhạy cảm',
 'Đau đớn, rên rỉ, hoặc không cho sờ vào một vùng nào đó là dấu hiệu của chấn thương, viêm nhiễm, hoặc bệnh nghiêm trọng. Cần kiểm tra cẩn thận và đưa đến bác sĩ thú y để xác định nguyên nhân.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG TIÊU HÓA (RẤT HAY GẶP)
-- =====================================================

-- 8. Nôn / Ói / Ói ra bọt / Ói vàng
('Triệu chứng nôn, ói, ói ra bọt, ói vàng ở chó',
 'nôn,ói,ói ra bọt,ói vàng,buồn nôn,nôn mửa,ói khan',
 'Nôn và ói là triệu chứng rất phổ biến ở chó, có thể do nhiều nguyên nhân như ăn quá nhanh, nuốt dị vật, ngộ độc, viêm dạ dày, hoặc bệnh nghiêm trọng khác. Ói ra bọt vàng thường là dịch mật, có thể do dạ dày trống. Nếu nôn kéo dài hoặc kèm máu, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Tiêu chảy / Đi phân lỏng
('Triệu chứng tiêu chảy, đi phân lỏng ở chó',
 'tiêu chảy,đi phân lỏng,đi ngoài lỏng,phân lỏng,đi cầu lỏng,ỉa chảy',
 'Tiêu chảy là triệu chứng phổ biến có thể do thay đổi thức ăn, nhiễm khuẩn, ký sinh trùng, hoặc bệnh đường ruột. Nếu tiêu chảy kéo dài quá 24-48 giờ, có máu, hoặc kèm các triệu chứng khác như nôn, sốt, cần đưa chó đến bác sĩ thú y để điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 10. Phân có máu / Phân đen
('Triệu chứng phân có máu, phân đen ở chó',
 'phân có máu,phân đen,đi ngoài ra máu,máu trong phân,phân màu đen,phân đen như nhựa đường',
 'Phân có máu hoặc phân đen là dấu hiệu nghiêm trọng. Phân đen (melena) thường do xuất huyết đường tiêu hóa trên, máu đỏ tươi thường do xuất huyết đường tiêu hóa dưới hoặc trực tràng. Đây là trường hợp khẩn cấp cần đưa chó đến bác sĩ thú y ngay lập tức.',
 'SEVERE',
 TRUE,
 NOW()),

-- 11. Táo bón / Rặn
('Triệu chứng táo bón, rặn ở chó',
 'táo bón,rặn,khó đi ngoài,không đi ngoài được,đi ngoài khó,phân cứng',
 'Táo bón là tình trạng chó khó đi ngoài hoặc không đi ngoài được. Có thể do thiếu nước, thiếu chất xơ, nuốt dị vật, hoặc tắc nghẽn đường ruột. Nếu chó rặn nhiều nhưng không đi được, hoặc không đi ngoài quá 2-3 ngày, cần đưa đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 12. Bụng chướng / Bụng phình
('Triệu chứng bụng chướng, bụng phình ở chó',
 'bụng chướng,bụng phình,bụng to,bụng căng,chướng bụng,phình bụng',
 'Bụng chướng hoặc phình to bất thường có thể do tích tụ khí, dịch, hoặc xoắn dạ dày (GDV) - một tình trạng khẩn cấp đe dọa tính mạng. Nếu bụng chướng kèm nôn, không đi ngoài được, hoặc chó đau đớn, cần đưa đến bác sĩ thú y ngay lập tức.',
 'SEVERE',
 TRUE,
 NOW()),

-- 13. Xì hơi nhiều
('Triệu chứng xì hơi nhiều ở chó',
 'xì hơi nhiều,đánh rắm nhiều,trung tiện nhiều,đầy hơi',
 'Xì hơi nhiều có thể do chế độ ăn không phù hợp, nuốt không khí khi ăn quá nhanh, hoặc vấn đề tiêu hóa. Nếu kèm theo các triệu chứng khác như nôn, tiêu chảy, hoặc đau bụng, cần kiểm tra tại phòng khám thú y.',
 'MILD',
 TRUE,
 NOW()),

-- 14. Ăn cỏ / Liếm nền / Liếm tường (dấu buồn nôn/đau bụng)
('Triệu chứng ăn cỏ, liếm nền, liếm tường ở chó (dấu hiệu buồn nôn/đau bụng)',
 'ăn cỏ,liếm nền,liếm tường,ăn cỏ nhiều,liếm sàn,liếm đất,buồn nôn,đau bụng',
 'Chó ăn cỏ, liếm nền, hoặc liếm tường thường là dấu hiệu của buồn nôn, đau bụng, hoặc khó chịu đường tiêu hóa. Đây có thể là cách chó tự làm dịu cảm giác khó chịu. Nếu hành vi này kèm theo nôn, tiêu chảy, hoặc các triệu chứng khác, cần đưa đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG HÔ HẤP & TIM MẠCH
-- =====================================================

-- 15. Ho / Ho khan / Ho về đêm
('Triệu chứng ho, ho khan, ho về đêm ở chó',
 'ho,ho khan,ho về đêm,ho liên tục,ho nhiều,ho kéo dài',
 'Ho ở chó có thể do nhiều nguyên nhân như viêm phế quản, viêm phổi, bệnh tim, hoặc nhiễm trùng đường hô hấp. Ho về đêm thường nghiêm trọng hơn và có thể liên quan đến bệnh tim hoặc phổi. Nếu ho kéo dài quá vài ngày hoặc kèm khó thở, cần đưa đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 16. Khò khè / Khó thở / Thở gấp
('Triệu chứng khò khè, khó thở, thở gấp ở chó',
 'khò khè,khó thở,thở gấp,thở nhanh,thở khó,thở nặng nhọc,thở hổn hển',
 'Khò khè, khó thở, hoặc thở gấp là dấu hiệu nghiêm trọng của vấn đề hô hấp hoặc tim mạch. Có thể do viêm phổi, suy tim, dị vật đường thở, hoặc các bệnh nghiêm trọng khác. Đây là trường hợp khẩn cấp, cần đưa chó đến bác sĩ thú y ngay lập tức.',
 'SEVERE',
 TRUE,
 NOW()),

-- 17. Thở há miệng (đặc biệt khi không nóng)
('Triệu chứng thở há miệng ở chó (đặc biệt khi không nóng)',
 'thở há miệng,thở bằng miệng,há miệng thở,thở há miệng khi không nóng',
 'Chó thở há miệng khi không nóng hoặc không vận động là dấu hiệu bất thường. Có thể do khó thở, đau, stress, hoặc vấn đề về tim mạch. Nếu kèm theo các triệu chứng khác như tím lưỡi, ho, hoặc mệt mỏi, cần đưa đến bác sĩ thú y để kiểm tra ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 18. Tím lưỡi / Tím lợi
('Triệu chứng tím lưỡi, tím lợi ở chó',
 'tím lưỡi,tím lợi,lưỡi tím,lợi tím,niêm mạc tím,thiếu oxy',
 'Tím lưỡi hoặc tím lợi là dấu hiệu nghiêm trọng của thiếu oxy trong máu (hypoxia). Có thể do suy tim, viêm phổi nặng, tắc nghẽn đường thở, hoặc các vấn đề tim mạch nghiêm trọng. Đây là trường hợp khẩn cấp đe dọa tính mạng, cần đưa chó đến bác sĩ thú y ngay lập tức.',
 'CRITICAL',
 TRUE,
 NOW()),

-- 19. Ngất / Xỉu
('Triệu chứng ngất, xỉu ở chó',
 'ngất,xỉu,ngất xỉu,bất tỉnh,mất ý thức,ngã quỵ,choáng',
 'Ngất hoặc xỉu là tình trạng mất ý thức tạm thời, thường do thiếu máu lên não. Có thể do bệnh tim, huyết áp thấp, thiếu máu, hoặc các vấn đề nghiêm trọng khác. Đây là trường hợp khẩn cấp, cần đưa chó đến bác sĩ thú y ngay để chẩn đoán và điều trị.',
 'CRITICAL',
 TRUE,
 NOW()),

-- 20. Tim đập nhanh
('Triệu chứng tim đập nhanh ở chó',
 'tim đập nhanh,mạch nhanh,nhịp tim nhanh,đánh trống ngực',
 'Tim đập nhanh bất thường có thể do lo lắng, đau đớn, sốt, mất nước, hoặc bệnh tim. Nếu kèm theo các triệu chứng khác như khó thở, tím lưỡi, hoặc ngất, cần đưa đến bác sĩ thú y ngay. Cần đo nhịp tim và kiểm tra tim mạch để xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG DA LÔNG (KEYWORD CỰC PHỔ BIẾN)
-- =====================================================

-- 21. Ngứa / Gãi nhiều
('Triệu chứng ngứa, gãi nhiều ở chó',
 'ngứa,gãi nhiều,gãi liên tục,ngứa ngáy,ngứa da,chó gãi nhiều',
 'Ngứa và gãi nhiều là triệu chứng rất phổ biến ở chó, có thể do dị ứng, ký sinh trùng (rận, bọ chét, ve), viêm da, hoặc các bệnh da liễu khác. Nếu gãi quá nhiều gây trầy xước, nhiễm trùng, hoặc rụng lông, cần đưa đến bác sĩ thú y để chẩn đoán và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 22. Rụng lông / Hói
('Triệu chứng rụng lông, hói ở chó',
 'rụng lông,hói,hói đầu,rụng lông nhiều,lông rụng,lông thưa,trụi lông',
 'Rụng lông hoặc hói có thể do nhiều nguyên nhân như dị ứng, ký sinh trùng, nấm, rối loạn nội tiết, hoặc stress. Rụng lông từng mảng hoặc rụng đối xứng thường là dấu hiệu bệnh lý. Cần kiểm tra tại phòng khám thú y để xác định nguyên nhân và điều trị phù hợp.',
 'MODERATE',
 TRUE,
 NOW()),

-- 23. Da đỏ / Viêm da
('Triệu chứng da đỏ, viêm da ở chó',
 'da đỏ,viêm da,da bị đỏ,da sưng đỏ,viêm da dị ứng,da kích ứng',
 'Da đỏ hoặc viêm da có thể do dị ứng, nhiễm trùng, ký sinh trùng, hoặc các bệnh da liễu khác. Viêm da thường kèm theo ngứa, gãi nhiều, và có thể dẫn đến nhiễm trùng thứ phát. Cần đưa đến bác sĩ thú y để chẩn đoán và điều trị, có thể cần dùng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- 24. Vảy / Gàu
('Triệu chứng vảy, gàu ở chó',
 'vảy,gàu,gàu nhiều,da khô,vảy da,tróc vảy,da bong tróc',
 'Vảy hoặc gàu có thể do da khô, viêm da, nhiễm nấm, hoặc các vấn đề về dinh dưỡng. Gàu nhiều kèm theo ngứa hoặc rụng lông thường là dấu hiệu bệnh lý. Cần kiểm tra tại phòng khám thú y để xác định nguyên nhân và có biện pháp điều trị phù hợp.',
 'MILD',
 TRUE,
 NOW()),

-- 25. Mùi hôi
('Triệu chứng mùi hôi ở chó',
 'mùi hôi,hôi,hôi thối,mùi khó chịu,mùi lạ,mùi bất thường',
 'Mùi hôi ở chó có thể do nhiều nguyên nhân như nhiễm trùng da, viêm tai, bệnh răng miệng, hoặc các vấn đề về tuyến hậu môn. Mùi hôi kèm theo các triệu chứng khác như ngứa, gãi nhiều, hoặc chảy dịch cần được kiểm tra tại phòng khám thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 26. Nấm / Ghẻ
('Triệu chứng nấm, ghẻ ở chó',
 'nấm,ghẻ,nấm da,ghẻ chó,hắc lào,nấm lông,ghẻ sarcoptic',
 'Nấm và ghẻ là các bệnh da liễu phổ biến ở chó. Nấm thường gây rụng lông từng mảng, ngứa, và có thể lây sang người. Ghẻ sarcoptic gây ngứa dữ dội, da đỏ, và có thể lây lan nhanh. Cả hai đều cần điều trị bằng thuốc đặc hiệu theo chỉ định của bác sĩ thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 27. Mụn / Mụn mủ
('Triệu chứng mụn, mụn mủ ở chó',
 'mụn,mụn mủ,mụn nhọt,mụn viêm,mụn có mủ,áp xe da',
 'Mụn hoặc mụn mủ ở chó có thể do nhiễm trùng da, viêm nang lông, hoặc các vấn đề về tuyến bã nhờn. Mụn mủ thường là dấu hiệu nhiễm trùng và có thể cần điều trị bằng kháng sinh. Nếu mụn nhiều, lớn, hoặc kèm theo các triệu chứng khác, cần đưa đến bác sĩ thú y để điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 28. Rận / Bọ chét / Ve
('Triệu chứng rận, bọ chét, ve ở chó',
 'rận,bọ chét,ve,chấy rận,bọ chó,ve chó,ký sinh trùng da',
 'Rận, bọ chét, và ve là các ký sinh trùng ngoài da phổ biến ở chó. Chúng gây ngứa, gãi nhiều, rụng lông, và có thể truyền bệnh. Cần điều trị bằng thuốc diệt ký sinh trùng và vệ sinh môi trường sống. Nên tham khảo bác sĩ thú y để chọn loại thuốc phù hợp và an toàn.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG TAI
-- =====================================================

-- 29. Hôi tai
('Triệu chứng hôi tai ở chó',
 'hôi tai,tai hôi,mùi hôi tai,mùi khó chịu ở tai',
 'Hôi tai là dấu hiệu phổ biến của nhiễm trùng tai, viêm tai, hoặc tích tụ ráy tai. Có thể do vi khuẩn, nấm, hoặc ký sinh trùng. Nếu kèm theo các triệu chứng khác như gãi tai, lắc đầu, hoặc chảy dịch, cần đưa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 30. Ráy tai đen / Ráy tai nhiều
('Triệu chứng ráy tai đen, ráy tai nhiều ở chó',
 'ráy tai đen,ráy tai nhiều,ráy tai,ráy tai màu đen,ráy tai dày',
 'Ráy tai đen hoặc ráy tai nhiều bất thường thường là dấu hiệu của nhiễm trùng tai, viêm tai, hoặc nhiễm ve tai (ear mites). Ráy tai bình thường có màu vàng nhạt. Ráy tai đen, dày, hoặc có mùi hôi cần được kiểm tra và điều trị tại phòng khám thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 31. Lắc đầu / Nghiêng đầu
('Triệu chứng lắc đầu, nghiêng đầu ở chó',
 'lắc đầu,nghiêng đầu,lắc đầu nhiều,nghiêng đầu một bên,đầu nghiêng',
 'Lắc đầu hoặc nghiêng đầu thường là dấu hiệu của vấn đề về tai như viêm tai, nhiễm trùng, dị vật trong tai, hoặc ve tai. Nghiêng đầu một bên kéo dài có thể là dấu hiệu của bệnh nghiêm trọng hơn như viêm tai giữa hoặc các vấn đề thần kinh. Cần đưa đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 32. Gãi tai
('Triệu chứng gãi tai ở chó',
 'gãi tai,gãi tai nhiều,gãi tai liên tục,ngứa tai',
 'Gãi tai là dấu hiệu phổ biến của ngứa tai, có thể do viêm tai, nhiễm trùng, ve tai, hoặc dị vật. Nếu gãi quá nhiều có thể gây trầy xước, chảy máu, hoặc nhiễm trùng thứ phát. Cần kiểm tra tai và điều trị tại phòng khám thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 33. Tai đỏ / Tai sưng
('Triệu chứng tai đỏ, tai sưng ở chó',
 'tai đỏ,tai sưng,tai bị đỏ,tai sưng to,viêm tai,đỏ tai',
 'Tai đỏ hoặc tai sưng là dấu hiệu của viêm tai, nhiễm trùng, hoặc dị ứng. Viêm tai có thể do vi khuẩn, nấm, hoặc ký sinh trùng. Nếu kèm theo các triệu chứng khác như chảy dịch, hôi tai, hoặc đau, cần đưa đến bác sĩ thú y ngay để điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- 34. Chảy dịch tai
('Triệu chứng chảy dịch tai ở chó',
 'chảy dịch tai,dịch tai,chảy mủ tai,mủ tai,dịch từ tai,chảy nước tai',
 'Chảy dịch tai có thể là dấu hiệu của nhiễm trùng tai, viêm tai, hoặc thủng màng nhĩ. Dịch có thể trong, vàng, nâu, hoặc có mùi hôi. Đây là dấu hiệu nghiêm trọng cần được điều trị ngay tại phòng khám thú y. Không nên tự ý nhỏ thuốc vào tai mà chưa có chẩn đoán.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG MẮT
-- =====================================================

-- 35. Chảy ghèn / Chảy nước mắt
('Triệu chứng chảy ghèn, chảy nước mắt ở chó',
 'chảy ghèn,chảy nước mắt,ghèn mắt,nước mắt nhiều,chảy nước mắt liên tục,ghèn mắt nhiều',
 'Chảy ghèn hoặc chảy nước mắt nhiều có thể là dấu hiệu của viêm kết mạc, nhiễm trùng mắt, dị vật trong mắt, hoặc tắc ống lệ. Ghèn có thể trong, vàng, xanh, hoặc đặc. Nếu kèm theo mắt đỏ, sưng, hoặc đau, cần đưa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 36. Mắt đỏ
('Triệu chứng mắt đỏ ở chó',
 'mắt đỏ,mắt bị đỏ,đỏ mắt,viêm mắt,đỏ kết mạc',
 'Mắt đỏ là dấu hiệu của viêm kết mạc, nhiễm trùng mắt, dị ứng, hoặc chấn thương mắt. Có thể kèm theo chảy nước mắt, ghèn, hoặc đau. Mắt đỏ kéo dài hoặc kèm theo các triệu chứng khác cần được kiểm tra tại phòng khám thú y để xác định nguyên nhân và điều trị phù hợp.',
 'MODERATE',
 TRUE,
 NOW()),

-- 37. Mắt đục / Trắng đục
('Triệu chứng mắt đục, trắng đục ở chó',
 'mắt đục,trắng đục,mắt bị đục,mắt trắng đục,đục thủy tinh thể,đục giác mạc',
 'Mắt đục hoặc trắng đục có thể là dấu hiệu của đục thủy tinh thể, đục giác mạc, viêm màng bồ đào, hoặc các bệnh về mắt nghiêm trọng khác. Đục thủy tinh thể thường gặp ở chó già. Cần đưa đến bác sĩ thú y chuyên khoa mắt để chẩn đoán và điều trị. Một số trường hợp có thể cần phẫu thuật.',
 'SEVERE',
 TRUE,
 NOW()),

-- 38. Nháy mắt / Nheo mắt
('Triệu chứng nháy mắt, nheo mắt ở chó',
 'nháy mắt,nheo mắt,nháy mắt nhiều,nheo mắt liên tục,chớp mắt nhiều',
 'Nháy mắt hoặc nheo mắt nhiều thường là dấu hiệu của đau mắt, khó chịu, dị vật trong mắt, hoặc viêm mắt. Có thể kèm theo chảy nước mắt, mắt đỏ, hoặc sợ ánh sáng. Cần kiểm tra mắt và điều trị tại phòng khám thú y. Nếu có dị vật, không nên tự ý lấy ra mà cần bác sĩ xử lý.',
 'MODERATE',
 TRUE,
 NOW()),

-- 39. Sợ sáng
('Triệu chứng sợ sáng ở chó',
 'sợ sáng,sợ ánh sáng,nhạy cảm với ánh sáng,tránh ánh sáng,nhắm mắt khi ra nắng',
 'Sợ sáng (photophobia) là dấu hiệu của viêm mắt, đau mắt, viêm giác mạc, hoặc các bệnh về mắt nghiêm trọng. Chó sẽ tránh ánh sáng, nhắm mắt, hoặc nheo mắt khi ở nơi sáng. Cần đưa đến bác sĩ thú y để kiểm tra và điều trị nguyên nhân gây ra tình trạng này.',
 'MODERATE',
 TRUE,
 NOW()),

-- 40. Dụi mắt
('Triệu chứng dụi mắt ở chó',
 'dụi mắt,dụi mắt nhiều,cọ mắt,chà mắt,gãi mắt',
 'Dụi mắt là dấu hiệu của ngứa mắt, đau mắt, hoặc khó chịu ở mắt. Có thể do dị vật, viêm mắt, dị ứng, hoặc nhiễm trùng. Dụi mắt quá nhiều có thể gây trầy xước giác mạc hoặc nhiễm trùng thứ phát. Cần kiểm tra mắt và điều trị tại phòng khám thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 41. Mắt sưng
('Triệu chứng mắt sưng ở chó',
 'mắt sưng,mắt bị sưng,sưng mắt,phù mắt,mí mắt sưng',
 'Mắt sưng có thể là dấu hiệu của viêm mắt, nhiễm trùng, dị ứng, chấn thương, hoặc áp xe. Có thể kèm theo mắt đỏ, chảy nước mắt, hoặc đau. Mắt sưng nghiêm trọng hoặc kèm theo các triệu chứng khác cần được điều trị ngay tại phòng khám thú y. Một số trường hợp có thể cần dùng kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG MIỆNG – RĂNG
-- =====================================================

-- 42. Hôi miệng
('Triệu chứng hôi miệng ở chó',
 'hôi miệng,miệng hôi,mùi hôi miệng,mùi khó chịu ở miệng',
 'Hôi miệng ở chó thường do vấn đề về răng miệng như cao răng, viêm nướu, nhiễm trùng răng, hoặc bệnh nướu răng. Cũng có thể do các vấn đề tiêu hóa hoặc bệnh thận. Cần kiểm tra răng miệng và làm sạch răng định kỳ. Nếu kèm theo các triệu chứng khác như chảy máu lợi hoặc khó nhai, cần đưa đến bác sĩ thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 43. Cao răng
('Triệu chứng cao răng ở chó',
 'cao răng,vôi răng,mảng bám răng,răng vàng,răng bẩn',
 'Cao răng (vôi răng) là mảng bám cứng tích tụ trên răng, gây viêm nướu, hôi miệng, và có thể dẫn đến mất răng. Cần làm sạch răng định kỳ tại phòng khám thú y. Chăm sóc răng miệng hàng ngày bằng cách đánh răng hoặc dùng các sản phẩm vệ sinh răng miệng cho chó có thể giúp ngăn ngừa tích tụ cao răng.',
 'MILD',
 TRUE,
 NOW()),

-- 44. Chảy máu lợi
('Triệu chứng chảy máu lợi ở chó',
 'chảy máu lợi,chảy máu nướu,lợi chảy máu,nướu chảy máu,máu ở lợi',
 'Chảy máu lợi thường là dấu hiệu của viêm nướu, bệnh nướu răng, hoặc chấn thương. Có thể do cao răng, nhiễm trùng, hoặc các vấn đề về răng miệng khác. Nếu kèm theo hôi miệng, đau, hoặc khó nhai, cần đưa đến bác sĩ thú y để kiểm tra và điều trị. Làm sạch răng và điều trị viêm nướu có thể cần thiết.',
 'MODERATE',
 TRUE,
 NOW()),

-- 45. Loét miệng
('Triệu chứng loét miệng ở chó',
 'loét miệng,loét trong miệng,vết loét miệng,loét lưỡi,loét nướu',
 'Loét miệng có thể do nhiễm trùng, viêm, chấn thương, hoặc các bệnh tự miễn. Có thể gây đau, khó nhai, chảy nước dãi, hoặc bỏ ăn. Cần đưa đến bác sĩ thú y để xác định nguyên nhân và điều trị. Một số trường hợp có thể cần dùng thuốc kháng sinh, kháng viêm, hoặc điều trị bệnh nền.',
 'MODERATE',
 TRUE,
 NOW()),

-- 46. Khó nhai / Nhai một bên
('Triệu chứng khó nhai, nhai một bên ở chó',
 'khó nhai,nhai một bên,nhai khó,nhai lệch,nhai không được',
 'Khó nhai hoặc nhai một bên thường do đau răng, viêm nướu, gãy răng, dị vật trong miệng, hoặc các vấn đề về hàm. Có thể kèm theo chảy nước dãi, bỏ ăn, hoặc đau. Cần kiểm tra răng miệng tại phòng khám thú y để xác định nguyên nhân và điều trị. Một số trường hợp có thể cần nhổ răng hoặc điều trị nha khoa.',
 'MODERATE',
 TRUE,
 NOW()),

-- 47. Chảy nước dãi
('Triệu chứng chảy nước dãi ở chó',
 'chảy nước dãi,nước dãi nhiều,chảy dãi,tiết nước bọt nhiều',
 'Chảy nước dãi nhiều có thể do đau miệng, khó nuốt, dị vật trong miệng, nhiễm độc, hoặc các vấn đề về thần kinh. Nếu kèm theo các triệu chứng khác như khó nhai, loét miệng, hoặc bỏ ăn, cần đưa đến bác sĩ thú y ngay. Nếu nghi ngờ nhiễm độc, cần cấp cứu ngay lập tức.',
 'MODERATE',
 TRUE,
 NOW()),

-- 48. Ho kiểu mắc nghẹn
('Triệu chứng ho kiểu mắc nghẹn ở chó',
 'ho kiểu mắc nghẹn,ho như mắc nghẹn,ho nghẹn,ho như hóc xương',
 'Ho kiểu mắc nghẹn có thể do dị vật trong cổ họng, viêm thanh quản, hoặc các vấn đề về đường hô hấp. Nếu chó có dấu hiệu nghẹt thở, tím lưỡi, hoặc không thể thở được, cần cấp cứu ngay. Nếu ho kéo dài hoặc kèm theo các triệu chứng khác, cần đưa đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG TIẾT NIỆU – SINH DỤC
-- =====================================================

-- 49. Tiểu ra máu
('Triệu chứng tiểu ra máu ở chó',
 'tiểu ra máu,nước tiểu có máu,tiểu máu,máu trong nước tiểu,đái ra máu',
 'Tiểu ra máu là dấu hiệu nghiêm trọng có thể do nhiễm trùng đường tiết niệu, sỏi thận, sỏi bàng quang, khối u, hoặc chấn thương. Cần đưa đến bác sĩ thú y ngay để kiểm tra và điều trị. Có thể cần xét nghiệm nước tiểu, siêu âm, hoặc các xét nghiệm khác để xác định nguyên nhân.',
 'SEVERE',
 TRUE,
 NOW()),

-- 50. Tiểu rắt / Đi tiểu nhiều lần ít nước
('Triệu chứng tiểu rắt, đi tiểu nhiều lần ít nước ở chó',
 'tiểu rắt,đi tiểu nhiều lần ít nước,tiểu nhiều lần,tiểu ít,tiểu không hết',
 'Tiểu rắt hoặc đi tiểu nhiều lần nhưng ít nước thường là dấu hiệu của nhiễm trùng đường tiết niệu, viêm bàng quang, sỏi bàng quang, hoặc các vấn đề về đường tiết niệu khác. Có thể kèm theo đau khi tiểu, tiểu ra máu, hoặc liếm vùng kín nhiều. Cần đưa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 51. Khó tiểu
('Triệu chứng khó tiểu ở chó',
 'khó tiểu,tiểu khó,không tiểu được,tiểu không ra,không đi tiểu được',
 'Khó tiểu là dấu hiệu nghiêm trọng có thể do tắc nghẽn đường tiết niệu, sỏi, khối u, hoặc các vấn đề về thần kinh. Đặc biệt nguy hiểm ở chó đực do có thể gây tắc nghẽn hoàn toàn. Cần cấp cứu ngay nếu chó không thể tiểu được. Có thể cần thông tiểu hoặc phẫu thuật.',
 'SEVERE',
 TRUE,
 NOW()),

-- 52. Liếm vùng kín nhiều
('Triệu chứng liếm vùng kín nhiều ở chó',
 'liếm vùng kín nhiều,liếm bộ phận sinh dục,liếm âm hộ,liếm dương vật',
 'Liếm vùng kín nhiều có thể do ngứa, đau, nhiễm trùng đường tiết niệu, viêm, hoặc các vấn đề về sinh dục. Nếu kèm theo các triệu chứng khác như tiểu ra máu, tiểu rắt, hoặc chảy dịch, cần đưa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 53. Nước tiểu hôi
('Triệu chứng nước tiểu hôi ở chó',
 'nước tiểu hôi,mùi hôi nước tiểu,nước tiểu có mùi khó chịu',
 'Nước tiểu hôi có thể do nhiễm trùng đường tiết niệu, viêm bàng quang, hoặc các vấn đề về thận. Nếu kèm theo các triệu chứng khác như tiểu rắt, tiểu ra máu, hoặc khó tiểu, cần đưa đến bác sĩ thú y để kiểm tra. Có thể cần xét nghiệm nước tiểu để xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- 54. Chảy dịch âm hộ / Dương vật
('Triệu chứng chảy dịch âm hộ, dương vật ở chó',
 'chảy dịch âm hộ,chảy dịch dương vật,dịch từ âm hộ,dịch từ dương vật,chảy mủ sinh dục',
 'Chảy dịch từ âm hộ hoặc dương vật có thể do nhiễm trùng, viêm, hoặc các bệnh về sinh dục. Ở chó cái, có thể liên quan đến viêm tử cung (pyometra) - một tình trạng nghiêm trọng cần điều trị ngay. Cần đưa đến bác sĩ thú y để kiểm tra và điều trị. Một số trường hợp có thể cần phẫu thuật.',
 'SEVERE',
 TRUE,
 NOW()),

-- 55. Bụng dưới đau
('Triệu chứng bụng dưới đau ở chó',
 'bụng dưới đau,đau bụng dưới,đau vùng bụng dưới,đau bụng',
 'Đau bụng dưới có thể do nhiều nguyên nhân như viêm bàng quang, sỏi bàng quang, viêm tử cung (pyometra), hoặc các vấn đề về đường tiết niệu và sinh dục khác. Có thể kèm theo các triệu chứng như tiểu rắt, tiểu ra máu, hoặc chảy dịch. Cần đưa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG THẦN KINH
-- =====================================================

-- 56. Co giật
('Triệu chứng co giật ở chó',
 'co giật,động kinh,lên cơn co giật,co giật toàn thân',
 'Co giật là dấu hiệu nghiêm trọng có thể do động kinh, nhiễm độc, chấn thương đầu, khối u não, hoặc các bệnh về thần kinh khác. Trong cơn co giật, cần giữ chó an toàn, không đưa tay vào miệng, và ghi lại thời gian và mô tả cơn co giật. Cần đưa đến bác sĩ thú y ngay sau cơn co giật để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 57. Run
('Triệu chứng run ở chó',
 'run,run rẩy,run lẩy bẩy,run toàn thân',
 'Run có thể do lạnh, sợ hãi, đau, sốt, nhiễm độc, hoặc các vấn đề về thần kinh. Nếu kèm theo các triệu chứng khác như co giật, mất thăng bằng, hoặc yếu, cần đưa đến bác sĩ thú y ngay. Nếu nghi ngờ nhiễm độc, cần cấp cứu ngay lập tức.',
 'MODERATE',
 TRUE,
 NOW()),

-- 58. Đi loạng choạng
('Triệu chứng đi loạng choạng ở chó',
 'đi loạng choạng,đi không vững,đi xiêu vẹo,đi không đều',
 'Đi loạng choạng có thể do các vấn đề về thần kinh, chấn thương cột sống, đau chân, hoặc các vấn đề về tai trong (ảnh hưởng đến thăng bằng). Cần đưa đến bác sĩ thú y để kiểm tra và xác định nguyên nhân. Một số trường hợp có thể cần chụp X-quang, MRI, hoặc các xét nghiệm khác.',
 'MODERATE',
 TRUE,
 NOW()),

-- 59. Mất thăng bằng
('Triệu chứng mất thăng bằng ở chó',
 'mất thăng bằng,không giữ được thăng bằng,ngã,đứng không vững',
 'Mất thăng bằng có thể do các vấn đề về tai trong (viêm tai giữa), chấn thương đầu, khối u não, hoặc các vấn đề về thần kinh khác. Có thể kèm theo nghiêng đầu, quay vòng vòng, hoặc nôn. Cần đưa đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 60. Quay vòng vòng
('Triệu chứng quay vòng vòng ở chó',
 'quay vòng vòng,quay tròn,quay một chỗ,xoay vòng',
 'Quay vòng vòng thường là dấu hiệu của các vấn đề về tai trong, chấn thương đầu, khối u não, hoặc các bệnh về thần kinh. Có thể kèm theo nghiêng đầu, mất thăng bằng, hoặc nôn. Cần đưa đến bác sĩ thú y ngay để kiểm tra. Có thể cần chụp MRI hoặc các xét nghiệm khác để xác định nguyên nhân.',
 'SEVERE',
 TRUE,
 NOW()),

-- 61. Ngã
('Triệu chứng ngã ở chó',
 'ngã,ngã xuống,ngã đột ngột,ngã không rõ nguyên nhân',
 'Ngã có thể do nhiều nguyên nhân như mất thăng bằng, yếu chân, co giật, đau, hoặc các vấn đề về thần kinh. Nếu ngã thường xuyên hoặc kèm theo các triệu chứng khác như co giật, mất thăng bằng, hoặc yếu, cần đưa đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- 62. Liệt / Yếu chân sau
('Triệu chứng liệt, yếu chân sau ở chó',
 'liệt chân sau,yếu chân sau,chân sau không đi được,liệt hai chân sau',
 'Liệt hoặc yếu chân sau có thể do chấn thương cột sống, đĩa đệm thoát vị, khối u, hoặc các vấn đề về thần kinh khác. Đây là tình trạng nghiêm trọng cần được điều trị ngay. Cần đưa đến bác sĩ thú y ngay để kiểm tra. Có thể cần chụp X-quang, MRI, hoặc phẫu thuật.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- TRIỆU CHỨNG XƯƠNG KHỚP
-- =====================================================

-- 63. Khập khiễng
('Triệu chứng khập khiễng ở chó',
 'khập khiễng,đi khập khiễng,đi cà nhắc,đi không đều chân',
 'Khập khiễng có thể do chấn thương, đau chân, viêm khớp, gãy xương, hoặc các vấn đề về xương khớp khác. Cần kiểm tra chân để tìm vết thương, sưng, hoặc đau. Nếu khập khiễng kéo dài hoặc kèm theo sưng, đau, cần đưa đến bác sĩ thú y để kiểm tra. Có thể cần chụp X-quang để xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- 64. Đau chân
('Triệu chứng đau chân ở chó',
 'đau chân,chân đau,đau khi đi,đau khi chạm vào chân',
 'Đau chân có thể do chấn thương, viêm khớp, gãy xương, dị vật, hoặc các vấn đề về xương khớp khác. Chó có thể không chịu đặt chân xuống, liếm chân nhiều, hoặc kêu khi chạm vào. Cần kiểm tra chân và đưa đến bác sĩ thú y nếu đau kéo dài hoặc nghiêm trọng. Có thể cần chụp X-quang để xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- 65. Không nhảy được
('Triệu chứng không nhảy được ở chó',
 'không nhảy được,không thể nhảy,nhảy khó,nhảy đau',
 'Không nhảy được có thể do đau chân, viêm khớp, chấn thương, hoặc các vấn đề về xương khớp. Đặc biệt phổ biến ở chó lớn tuổi do viêm khớp. Cần đưa đến bác sĩ thú y để kiểm tra và điều trị. Có thể cần dùng thuốc giảm đau, kháng viêm, hoặc vật lý trị liệu.',
 'MODERATE',
 TRUE,
 NOW()),

-- 66. Đứng lên khó
('Triệu chứng đứng lên khó ở chó',
 'đứng lên khó,khó đứng lên,đứng dậy khó,không đứng được',
 'Đứng lên khó có thể do đau khớp, viêm khớp, yếu cơ, hoặc các vấn đề về xương khớp. Đặc biệt phổ biến ở chó lớn tuổi. Có thể kèm theo khập khiễng hoặc không nhảy được. Cần đưa đến bác sĩ thú y để kiểm tra và điều trị. Có thể cần dùng thuốc giảm đau, kháng viêm, hoặc vật lý trị liệu.',
 'MODERATE',
 TRUE,
 NOW()),

-- 67. Sưng khớp
('Triệu chứng sưng khớp ở chó',
 'sưng khớp,khớp sưng,khớp bị sưng,sưng ở khớp',
 'Sưng khớp có thể do viêm khớp, chấn thương, nhiễm trùng, hoặc các vấn đề về xương khớp khác. Có thể kèm theo đau, khập khiễng, hoặc không chịu vận động. Cần đưa đến bác sĩ thú y để kiểm tra. Có thể cần chụp X-quang, xét nghiệm dịch khớp, hoặc các xét nghiệm khác để xác định nguyên nhân và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 68. Kêu đau khi bế
('Triệu chứng kêu đau khi bế ở chó',
 'kêu đau khi bế,đau khi bế,kêu khi bế,không cho bế',
 'Kêu đau khi bế có thể do đau lưng, đau khớp, chấn thương cột sống, hoặc các vấn đề về xương khớp khác. Cần đưa đến bác sĩ thú y để kiểm tra và xác định nguyên nhân. Có thể cần chụp X-quang để kiểm tra cột sống và khớp. Tránh bế chó nếu chúng có dấu hiệu đau.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_dog ORDER BY created_date DESC LIMIT 68;
