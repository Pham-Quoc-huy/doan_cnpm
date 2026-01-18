-- =====================================================
-- Script INSERT 3 câu hỏi về chăm sóc, tiêm phòng và tâm lý chó
-- =====================================================

USE animalhospital;

-- Insert 3 câu hỏi về chăm sóc thú cưng
INSERT INTO disease_dog (title, keywords, content, severity_level, is_active, created_date) VALUES

-- 1. Cuộc trò chuyện về chăm sóc thú cưng - Dinh dưỡng
('Dinh dưỡng và chế độ ăn cho chó khỏe mạnh',
 'dinh dưỡng,chế độ ăn,thức ăn,ăn gì,khỏe mạnh,protein,chất béo,vitamin,thức ăn chế biến sẵn,thức ăn tự chế biến',
 'Chó cần một chế độ ăn cân bằng với protein, chất béo, carbs, và các vitamin thiết yếu. Bạn có thể cho chó ăn thức ăn chế biến sẵn từ các thương hiệu uy tín, hoặc nếu muốn tự chế biến, hãy đảm bảo rằng bữa ăn cung cấp đủ dinh dưỡng cho chó của bạn. Nếu bạn có bất kỳ thắc mắc nào về loại thức ăn cụ thể, bác sĩ thú y có thể giúp bạn chọn lựa thức ăn phù hợp.',
 'GENERAL',
 TRUE,
 NOW()),

-- 2. Cuộc trò chuyện về việc tiêm phòng cho chó
('Lịch tiêm phòng và các loại vaccine cần thiết cho chó',
 'tiêm phòng,vaccine,vắc xin,tiêm chủng,bệnh dại,parvo,viêm gan,bệnh ho cũi,tiêm nhắc lại,lịch tiêm phòng',
 'Chó cần được tiêm phòng một số bệnh như bệnh dại, parvo, viêm gan, và bệnh ho cũi. Chó con thường được tiêm các mũi vắc xin trong những tháng đầu đời (6-8 tuần tuổi), và sau đó cần tiêm nhắc lại hàng năm. Bạn nên đưa chó của mình đến bác sĩ thú y để được tiêm phòng đúng lịch và kịp thời. Việc tiêm phòng đầy đủ giúp bảo vệ chó khỏi các bệnh truyền nhiễm nguy hiểm.',
 'GENERAL',
 TRUE,
 NOW()),

-- 3. Cuộc trò chuyện về tâm lý chó - Lo âu chia ly
('Chứng lo âu chia ly và cách xử lý khi chó lo lắng khi chủ đi vắng',
 'lo lắng,lo âu,chia ly,đi vắng,stress,tâm lý,lo âu chia ly,đồ chơi,không gian an toàn,giảm lo âu',
 'Chó có thể cảm thấy lo lắng khi chủ đi vắng, và điều này gọi là "chứng lo âu chia ly". Bạn có thể giúp chó cảm thấy thoải mái hơn bằng cách tạo một không gian an toàn cho nó khi bạn vắng mặt, cho nó một số đồ chơi giải trí, hoặc thậm chí thử những sản phẩm hỗ trợ giảm lo âu cho thú cưng. Nếu tình trạng kéo dài và nghiêm trọng, bạn có thể tham khảo ý kiến bác sĩ thú y về phương pháp điều trị. Một số biện pháp khác bao gồm tập luyện dần dần để chó quen với việc ở một mình, hoặc sử dụng pheromone giúp chó thư giãn.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_dog ORDER BY created_date DESC LIMIT 3;
