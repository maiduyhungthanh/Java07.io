package vn.techmaster.relation.service.onemany.bidirection;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.relation.model.onemany.bidirection.Address;
import vn.techmaster.relation.model.onemany.bidirection.Customer;
import vn.techmaster.relation.repository.onemany.bidirection.AddressRepo;
import vn.techmaster.relation.repository.onemany.bidirection.CustomerRepository;

@Service
public class AddressService {
  @Autowired private AddressRepo  addressRepo;


  public List<Address> getAll() {
    return addressRepo.findAll();
  }
}
