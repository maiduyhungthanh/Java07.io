## Java07-HomeWork  
#### 1. Trong quá trình tạo dự án Spring Boot chúng ta phải khai báo những tham số sau đây : groupID, artifactID. Ý nghĩa các tham số này là gì?
- groupID : Tên tổ chức / công ty / cá nhân của dự án VD: vn.techmaster
- artifactID: Tên của packge, dự án
#### 2. Tại sao phải đảo ngược tên miền trong <groupId>vn.techmaster</groupId>?
- vì lúc inport thì hệ thống sẽ nhóm cái tên miền giống nhau vào 1 chỗ -->code đẹp, dễ quan sát, dễ kiểm tra
#### 3. SpringBoot có 2 cơ chế để quản lý thư viện. Hãy kể tên chúng?
- Maven & Gradle
#### 4. File pom.xml có tác dụng gì?
- pom.xml là nơi chứa các config cho project và chứa các dependencies của project.
#### 5. Trong file pom.xml có các thẻ dependency. Ý nghĩa của chúng là gì?
```
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
```
- 
#### 6.Ý nghĩa của @Controllerlà gì?
- Controller trong ứng dụng Spring Boot là nơi tiếp nhận request và trả về response cho client. Có thể hiểu controller chính là lớp trung gian giữa server của bạn và bên ngoài.
@Controller có thể trả về View qua một String hoặc JSON data trong response body (nếu được chỉ định). Thích hợp cho các controller có routing, chuyển trang các kiểu.
#### 7. Ý nghĩa của @RequestMapping là gì? Nó có những tham số gì ngoài value?
- @RequestMapping là ánh xạ một yêu cầu HTTP tới một phương thức sử dụng một số tiêu chí cơ bản
- value trong @RequestMapping là đuôi của đường dẫn trên url
#### 8. Ý nghĩa của @RequestResponse khi đặt trong hàm hứng request để làm gì?

#### 9. Hãy trả lời khi nào thì dùng @PathVariable và khi nào nên dùng @RequestParam

#### 10. Thứ tự các thành phần đường dẫn @PathVariable có thể hoán đổi được không?

#### 11. @GetMapping khác gì so với @PostMapping?
#### 12. Trong các annotation @RequestMapping, @GetMapping, @PostMapping… có tham số produces = MediaType.XXXX ý nghĩa tham số này là gì?

#### 13. Giải thích ý nghĩa của @RequestBody trong đoạn code dưới đây
```
@PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public Message echoMessage(@RequestBody Message message){
    return message;
}
```
#### 14.Cổng mặc định ứng dụng SpringBoot là 8080. Hãy google cách để thay đổi cổng lắng nghe mặc định
- VD: muốn đổi sang server 8081 thì trong file application.properties trong src/main/resources thêm dòng lệnh
```
server.port = 8081
```