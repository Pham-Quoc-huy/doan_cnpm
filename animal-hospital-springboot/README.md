# Animal Hospital Management System

Hệ thống quản lý bệnh viện thú y - Spring Boot (không dùng JHipster)

## Cấu trúc Project

```
animal-hospital-springboot/
├── src/
│   ├── main/
│   │   ├── java/com/docpet/animalhospital/
│   │   │   ├── domain/          # Entities
│   │   │   ├── repository/      # JPA Repositories
│   │   │   ├── service/         # Business Logic
│   │   │   │   ├── dto/        # Data Transfer Objects
│   │   │   │   └── mapper/     # MapStruct Mappers
│   │   │   ├── web/rest/         # REST Controllers
│   │   │   ├── security/        # Security Configuration
│   │   │   └── config/         # Configuration Classes
│   │   └── resources/
│   │       └── application.yml
│   └── test/
└── pom.xml
```

## Cài đặt và Chạy

### Yêu cầu
- Java 17
- Maven 3.6+
- MySQL 8.0+

### Cấu hình Database

1. Tạo database trong MySQL:
```sql
CREATE DATABASE animalhospital CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Cấu hình trong `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/animalhospital
    username: root
    password: 1234
```

### Chạy ứng dụng

```bash
mvn spring-boot:run
```

Hoặc:
```bash
mvn clean install
java -jar target/animal-hospital-springboot-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### Authentication
- `POST /api/authenticate` - Login
- `GET /api/authenticate` - Check authentication

### Account
- `POST /api/register` - Register Owner
- `POST /api/register-vet` - Register Vet
- `GET /api/account` - Get current user
- `POST /api/account` - Update account

### Appointments
- `POST /api/appointments` - Create appointment
- `GET /api/appointments` - Get all for current owner
- `GET /api/appointments/{id}` - Get by id
- `PATCH /api/appointments/{id}/status` - Update status

## Entities

- **User** - Người dùng hệ thống
- **Authority** - Vai trò (ROLE_ADMIN, ROLE_USER, ROLE_DOCTOR, ROLE_ASSISTANT)
- **Owner** - Chủ thú cưng
- **Pet** - Thú cưng
- **Vet** - Bác sĩ thú y
- **Appointment** - Lịch hẹn
- **AppointmentAction** - Hành động trên appointment
- **LabTest** - Xét nghiệm

## Database Schema

Database sẽ được tạo tự động bởi Hibernate với `ddl-auto: update` hoặc bạn có thể tạo schema thủ công trong MySQL.

## Port

- Application: `8080`
- MySQL: `3306`

## Lưu ý

- Project này không sử dụng JHipster
- Sử dụng Spring Boot thuần với JWT Authentication
- Database sẽ được tạo tự động khi chạy lần đầu (hoặc tạo thủ công)

