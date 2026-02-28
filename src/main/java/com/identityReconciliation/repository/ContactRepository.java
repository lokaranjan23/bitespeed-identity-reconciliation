package com.identityReconciliation.repository;

import com.identityReconciliation.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Long> {

    //@Query("select c from Contact c where (:email id not null and c.email=:email)" +
          //  " or (:phoneNumber is not null and c.phoneNumber=:phoneNumber)")
    List<Contact> findByEmailOrPhoneNumber(@Param("email") String email, @Param("phoneNumber") String phoneNumber);
    List<Contact> findByLinkedId(Long linkedId);

}
