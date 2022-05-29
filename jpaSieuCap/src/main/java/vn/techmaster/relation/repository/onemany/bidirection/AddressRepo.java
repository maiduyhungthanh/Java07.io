package vn.techmaster.relation.repository.onemany.bidirection;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.relation.model.onemany.bidirection.Address;


@Repository
public interface AddressRepo extends JpaRepository<Address, Long>{
  
}
