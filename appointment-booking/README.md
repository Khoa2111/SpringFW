# Appointment Booking (Clinic) — Spring Boot (Maven) + MySQL

Dự án này tập trung vào bài toán **đặt lịch theo thời gian** (time-based constraints) — khác với CRUD thuần.

## 1) Bức tranh tổng thể
Hệ thống phòng khám tối giản có 3 thực thể chính:

- **Doctor**: bác sĩ
- **Patient**: bệnh nhân
- **Appointment**: lịch hẹn giữa doctor và patient trong một khoảng thời gian

Vấn đề cốt lõi mà hệ thống phải giải quyết:
1. **Không cho trùng lịch (time overlap)** cho cùng 1 doctor.
2. Quản lý **vòng đời (state machine)** của lịch hẹn: đặt → đang khám → hoàn thành / hủy / no-show.
3. Tránh lỗi **timezone**: lưu thời gian theo **UTC** (dùng `Instant`).

## 2) Luồng nghiệp vụ quan trọng (step-by-step)

### 2.1 Tạo lịch hẹn (Create Appointment)
Input: `doctorId`, `patientId`, `startTime`, `endTime` (ISO-8601, UTC)

1. Validate: `endTime` phải > `startTime`
2. Mở `@Transactional`
3. Lock doctor (hoặc lock theo doctorId) để tránh 2 request tạo trùng slot đồng thời
4. Query kiểm tra overlap với lịch đã tồn tại (chỉ tính các lịch chưa bị CANCELLED):

**Overlap rule (logic)**
- `newStart < existingEnd` **AND** `newEnd > existingStart`

Nếu có overlap → throw exception → rollback
Nếu không → insert appointment với trạng thái `SCHEDULED`

### 2.2 Chuyển trạng thái (State transitions)
- `SCHEDULED -> IN_PROGRESS`
- `IN_PROGRESS -> COMPLETED`
- `SCHEDULED -> CANCELLED`
- `SCHEDULED -> NO_SHOW`

Nếu chuyển sai (vd `COMPLETED -> IN_PROGRESS`) → báo lỗi.

## 3) Cách chạy nhanh (MySQL)
1. Tạo database:
```sql
CREATE DATABASE appointment_booking CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
2. Sửa user/pass trong `src/main/resources/application.yml`
3. Run:
```bash
mvn spring-boot:run
```

## 4) API mẫu
### 4.1 Tạo doctor
POST `http://localhost:8081/api/doctors`
```json
{ "name": "Dr. House", "specialty": "Diagnostics" }
```

### 4.2 Tạo patient
POST `http://localhost:8081/api/patients`
```json
{ "name": "Nguyen Van A", "phone": "0900000000" }
```

### 4.3 Đặt lịch
POST `http://localhost:8081/api/appointments`
```json
{
  "doctorId": 1,
  "patientId": 1,
  "startTime": "2026-05-10T09:00:00Z",
  "endTime": "2026-05-10T09:30:00Z"
}
```

### 4.4 Xem lịch của doctor trong khoảng thời gian
GET `http://localhost:8081/api/appointments?doctorId=1&from=2026-05-10T00:00:00Z&to=2026-05-11T00:00:00Z`

## 5) Checklist kiến thức bạn sẽ thấy trong code
- Instant/UTC và parse ISO-8601
- JPQL query kiểm tra overlap
- `@Transactional` + pessimistic locking để tránh double-booking
- State machine đơn giản (validate chuyển trạng thái)

## 6) Quy tắc (pattern) rút ra
- Bài toán lịch hẹn = **ràng buộc theo thời gian**. Đừng cố giải bằng CRUD thuần.
- Lưu time trong DB theo UTC (Instant). Convert timezone ở client/UI.
- Không có locking/transaction → rất dễ double-booking khi concurrent.
