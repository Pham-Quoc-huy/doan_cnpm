-- =====================================================
-- Script INSERT 3 câu hỏi về bệnh lý, giun sán và hành vi mèo
-- =====================================================

USE animalhospital;

-- Insert 3 câu hỏi về mèo
INSERT INTO disease_cat (title, keywords, content, severity_level, is_active, created_date) VALUES

-- 1. Cuộc trò chuyện về bệnh lý - Mèo bị sốt và chảy nước mắt (cúm)
('Cảm cúm mèo - Triệu chứng sốt và chảy nước mắt',
 'sốt,chảy nước mắt,cúm,cảm cúm,feline herpesvirus,fhv,viêm mắt,ghèn mắt,ho,khó thở',
 'Mèo có thể bị cảm cúm (Feline Herpesvirus) khi có triệu chứng như sốt và chảy nước mắt. Feline Herpesvirus là một trong những nguyên nhân phổ biến gây bệnh đường hô hấp trên ở mèo. Triệu chứng thường gặp bao gồm: sốt, chảy nước mắt, chảy nước mũi, hắt hơi, ho, khó thở, bỏ ăn. Tuy nhiên, để chắc chắn, bạn nên đưa mèo đến bác sĩ thú y để được xét nghiệm và điều trị đúng cách. Bệnh này có thể lây lan giữa các mèo, nên cần cách ly mèo bệnh.',
 'MODERATE',
 TRUE,
 NOW()),

-- 2. Cuộc trò chuyện về bệnh giun sán
('Bệnh giun sán ở mèo - Triệu chứng nôn và sụt cân',
 'giun sán,giun,ký sinh trùng,nôn,sụt cân,tiêu chảy,bụng chướng,thiếu máu,tẩy giun',
 'Có thể mèo của bạn bị giun sán, vì giun có thể gây nôn, tiêu chảy và sụt cân. Các triệu chứng khác của bệnh giun sán ở mèo bao gồm: bụng chướng, thiếu máu, bỏ ăn, mệt mỏi, có thể thấy giun trong phân hoặc nôn ra. Bạn nên đưa mèo đi khám bác sĩ thú y để làm xét nghiệm phân và điều trị giun sán nếu cần. Nên tẩy giun định kỳ cho mèo (mèo con: 2 tuần/lần đến 3 tháng tuổi, sau đó 3 tháng/lần) để phòng ngừa.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Cuộc trò chuyện về hành vi mèo - Liếm lông quá mức
('Hành vi liếm lông quá mức và cắn móng ở mèo',
 'liếm lông,cắn móng,hành vi,stress,căng thẳng,vấn đề về da,viêm da,dị ứng,lo âu',
 'Liếm lông là hành vi tự làm sạch bình thường của mèo. Tuy nhiên, nếu mèo của bạn liếm quá mức hoặc cắn móng tay liên tục, có thể do căng thẳng hoặc vấn đề về da. Các nguyên nhân có thể bao gồm: stress, lo âu, dị ứng, viêm da, ký sinh trùng ngoài da (bọ chét, ve), đau đớn, hoặc thói quen. Bạn nên theo dõi và nếu hành vi này kéo dài hoặc kèm theo các triệu chứng khác như rụng lông, vết thương, hoặc thay đổi hành vi, hãy đưa mèo đến bác sĩ thú y để kiểm tra. Bác sĩ có thể giúp xác định nguyên nhân và đưa ra phương pháp điều trị phù hợp.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_cat ORDER BY created_date DESC LIMIT 3;
