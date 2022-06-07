package vn.techmaster.demobank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import vn.techmaster.demobank.model.Account;
import vn.techmaster.demobank.model.User;
import vn.techmaster.demobank.repository.AccountRepo;
import vn.techmaster.demobank.repository.UserRepo;

@Service
public class AccountUserService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccountRepo accountRepo;

    @Transactional
    public void generateAccountUser() {
        User user1 = new User("Bob", "123",0350350350);
        User user2 = new User("Alice","123",0360360360);
        User user3 = new User("Tom", "123",0370370370);
        User user4 = new User("Sara","123",0330330330);

        user1.addAccount(new Account("Bob_tiet_kiem", 1000));
        user1.addAccount(new Account("Bob_sinh_hoat", 5000));

        user2.addAccount(new Account("Alice_tiet_kiem", 5000));
        user2.addAccount(new Account("Alice_du_lich", 1000));

        user3.addAccount(new Account("Tom_tiet_kiem", 5000));
        user3.addAccount(new Account("Tom_quy_den", 100));

        user4.addAccount(new Account("Sara_tiet_kiem", 5000));
        user4.addAccount(new Account("Sara_dau_tu", 10000));


     em.persist(user1);
     em.persist(user2);
     em.persist(user3);
     em.persist(user4);
     em.flush();

    }
    // tat ca user
    public List<User> getUserAll() {
        List<User> users = userRepo.findAll();
        return users;
    }
    // goi user theo id
    public Optional<User> getUserById(Long id) {
       return userRepo.findById(id);
    }
    // list account
    public List<Account> getAccountAll() {   
        return accountRepo.findAll();
    }
    //account theo id
    public Optional<Account> getAccountById(Long id) {
       return accountRepo.findById(id);
    }
    //account theo user
    public List<Account> getAccountByUser(User user){
        List<Account> accounts = new ArrayList<>();
        accounts = userRepo.findById(user.getId()).get().getAccounts();
        return accounts;
    }

    //chuyen tien tu account a sang acount b
    public List<Account> getTransfer(Long send_id,Long receive_id,Integer money){
            Optional<Account> send = accountRepo.findById(send_id);
            if(send.isEmpty()){
                throw new NotFoundException("Account có id "+send_id+" không tồn tại");
            }
            Optional<Account> receive = accountRepo.findById(receive_id);
            if(receive.isEmpty()){
                throw new NotFoundException("Account có id "+receive_id+" không tồn tại");
            }
            if((send.get().getAmount()-money) < 10 && (send.get().getAmount()-money) >=0){
                throw new NotFoundException("Số dư tối thiểu phải lớn hơn 10 đô");
            }
            if (send.get().getAmount() - money < 0){
                throw new NotFoundException("Số dư của bạn không đủ thực hiện được giao dịch này");
            }

            String send_string = "chuyển thành công "+money+" đô cho TK "+receive.get().getName();
            String receive_string = "nhận thành công "+money+ " đô từ TK "+send.get().getName();
            List<String> history_send_update = new ArrayList<>(send.get().getHistory());
            List<String> history_receive_update = new ArrayList<>(receive.get().getHistory());
            
            history_send_update.add(send_string);
            history_receive_update.add(receive_string);
            send.get().setHistory(history_send_update);
            receive.get().setHistory(history_receive_update);
            receive.get().setAmount(receive.get().getAmount()+money);
            send.get().setAmount(send.get().getAmount()-money);

            accountRepo.save(send.get());
            accountRepo.save(receive.get());
        return accountRepo.findAll();
    }
}