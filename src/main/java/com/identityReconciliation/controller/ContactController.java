package com.identityReconciliation.controller;


import com.identityReconciliation.Dto.ApiResponse;
import com.identityReconciliation.Dto.ContactDto;
import com.identityReconciliation.Dto.ResponseDto;
import com.identityReconciliation.repository.ContactRepository;
import com.identityReconciliation.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ContactController {

    private final ContactRepository contactRepository;
    private final ContactService contactService;
    public ContactController(ContactRepository contactRepository, ContactService contactService) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
    }
    @PostMapping("/identify")
    public ResponseEntity<ApiResponse<ResponseDto>> saveCustomer(@RequestBody ContactDto contactDto){
        ResponseDto responseDto= contactService.saveCustomer(contactDto);
        ApiResponse response=new ApiResponse();
        response.setContact(responseDto);
        return ResponseEntity.ok(response);
    }

}
