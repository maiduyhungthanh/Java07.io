package vn.techmaster.demobank.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import vn.techmaster.demobank.model.Account;
import vn.techmaster.demobank.model.User;
import vn.techmaster.demobank.service.AccountUserService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")

public class HomeController {

    @Autowired AccountUserService accountUserService;
    @Operation(summary = "tất cả User")
    @GetMapping("/user")
    public ResponseEntity<?> listUser() {
        List<User> listUser = accountUserService.getUserAll();
        return ResponseEntity.ok(listUser);
    }
    @Operation(summary = "Nhận user theo id")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> userById(@Parameter(description = "id của User cần tìm kiếm") @PathVariable Long id) {
        Optional<User> user = accountUserService.getUserById(id);
        if(user.isEmpty()){
            throw new NotFoundException("User với id "+id+" không tồn tại");
        }
        return ResponseEntity.ok(user);
    }
    @Operation(summary = "tất cả Account")
    @GetMapping("/account")
    public ResponseEntity<?> listAccount() {
        List<Account> listAccount = accountUserService.getAccountAll();
        return ResponseEntity.ok(listAccount);
    }
    @Operation(summary = "Nhận account theo id")
    @GetMapping("/account/{id}")
    public ResponseEntity<?> AccountById(@Parameter(description = "id của Account cần tìm kiếm") @PathVariable Long id) {
        Optional<Account> account = accountUserService.getAccountById(id);
        if(account.isEmpty()){
            throw new NotFoundException("Account với id "+id+" không tồn tại");
        }
        return ResponseEntity.ok(account);
    }
    @Operation(summary = "Nhận account theo id của User")
    @GetMapping("/account-user/{id_user}")
    public ResponseEntity<?> AccountByUser(@Parameter(description = "id của User cần tìm kiếm") @PathVariable Long id_user) {
        Optional<User> user = accountUserService.getUserById(id_user);
        if(user.isEmpty()){
            throw new NotFoundException("User với id "+id_user+" không tồn tại");
        }
        List<Account> accounts = accountUserService.getAccountByUser(user.get());
        return ResponseEntity.ok(accounts);
    }
    @Operation(summary = "chuyển khoản từ TK:A sang TK:B")
    @PostMapping(value="account-transfer")
    public ResponseEntity<?> getTransfeAccounts(@RequestParam Long send_id,@RequestParam Long receive_id,@RequestParam Integer money) {
        accountUserService.getTransfer(send_id, receive_id, money);
        return ResponseEntity.ok(accountUserService.getAccountAll().stream().filter(acc->acc.getId()==send_id||acc.getId()==receive_id).collect(Collectors.toList()));
    }
    

}
