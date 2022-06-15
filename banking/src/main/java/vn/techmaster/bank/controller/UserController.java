package vn.techmaster.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.bank.service.BankService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired BankService bankService;

  @GetMapping("/user")
  public ResponseEntity<?> UserAll() {
    return ResponseEntity.ok(bankService.getAllUsers());
  } 
}
