# Order Management (Mini E-Commerce) — Spring Boot (Maven) + MySQL

Mục tiêu của nhánh này là giúp bạn học Spring Boot theo kiểu **hiểu bản chất**: bắt đầu từ bức tranh tổng thể → chia nhỏ thành module → mô phỏng luồng chạy từng bước → edge cases (race condition, transaction) → tổng kết pattern.

## 1) Bức tranh tổng thể
Đây là “lõi” của một hệ thống bán hàng tối giản:

- **Product**: hàng hóa, có `price`, `stock`.
- **Order**: đơn hàng, có trạng thái.
- **OrderItem**: từng dòng sản phẩm trong đơn.

Điểm khác với “quản lý sinh viên” (CRUD thuần):
- Có **tính toàn vẹn** cần bảo vệ: trừ tồn kho phải đúng.
- Có **giao dịch** (transaction): hoặc thành công toàn bộ, hoặc rollback.
- Có **cạnh tranh dữ liệu** (concurrency): 2 người mua món cuối cùng cùng lúc.

## 2) Luồng nghiệp vụ quan trọng (step-by-step)
### 2.1 Tạo đơn hàng (place order)
1. Client gửi danh sách items: `(productId, quantity)`
2. Service mở `@Transactional`
3. Với mỗi product:
   - Lock hàng product (pessimistic write) để tránh oversell
   - Check `stock >= quantity`
   - Trừ stock
4. Tạo `Order` + các `OrderItem`
5. Tính tổng tiền
6. Commit transaction

Nếu bất kỳ bước nào fail (vd thiếu stock) → throw exception → **rollback**, stock không bị trừ dở.

## 3) Cách chạy nhanh
1. Tạo database MySQL:
```sql
CREATE DATABASE order_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
2. Sửa `src/main/resources/application.yml` (user/pass)
3. Run:
```bash
mvn spring-boot:run
```

## 4) API mẫu
- POST `http://localhost:8080/api/orders`

Body:
```json
{
  "customerId": 1,
  "items": [
    {"productId": 1, "quantity": 2},
    {"productId": 2, "quantity": 1}
  ]
}
```

## 5) Checklist kiến thức bạn sẽ thấy trong code
- Controller mỏng, logic nằm ở Service
- `@Transactional` để đảm bảo atomic
- JPA mapping: OneToMany / ManyToOne
- Locking: `@Lock(PESSIMISTIC_WRITE)` + `SELECT ... FOR UPDATE`
- Lưu `priceAtPurchase` để chống thay đổi giá trong tương lai

## 6) Quy tắc (pattern) rút ra
- **Đụng tới tồn kho/tiền bạc** ⇒ luôn nghĩ đến Transaction + Concurrency.
- Đừng tính toán/ghi DB rải rác nhiều nơi ⇒ gom vào 1 Service transactional.
- Dữ liệu “lịch sử” (invoice) ⇒ snapshot giá/thuế tại thời điểm mua.
