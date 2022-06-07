## **Xây dựng ứng dụng mô phỏng ngân hàng**

# Yêu cầu chung
1. Một User sở hữu nhiều Account. Quan hệ 1:Many Bidirection, Cascading All

2. Tạo 4 User: Bob, Alice, Tom, Sara. Mỗi người có từ 1 đến vài Account.

3. Tạo TransferException để xử lý các ngoại lệ khi chuyển tiền. TransferException 
cần ghi lại chi tiết nội dung chuyển tiền.

4. Tạo REST API gồm có:
+ userById
+ accountById
+ accountsByUserId
+ transfer(accountA, accountB, amount)
+ Cấu hình Log4J2 để ghi log ra file transfer_error.log khi có TransferException được ném ra.

### Để sử dụng OpenAPI truy cập [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#)
![alt](images/1.jpg)
- userById OpenAPI
![alt](images/2.jpg)

- accountById OpenAPI
![alt](images/3.jpg)
-  accountsByUserId OpenAPI
![alt](images/4.jpg)
- transfer(accountA, accountB, amount) OpenAPI
![alt](images/5.jpg)
- nội dung file transfer_error.log
![alt](images/6.jpg)
