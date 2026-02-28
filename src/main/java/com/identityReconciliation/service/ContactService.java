package com.identityReconciliation.service;


import com.identityReconciliation.Dto.ContactDto;
import com.identityReconciliation.Dto.ResponseDto;
import com.identityReconciliation.entity.Contact;
import com.identityReconciliation.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.identityReconciliation.entity.LinkPrecedence.PRIMARY;
import static com.identityReconciliation.entity.LinkPrecedence.SECONDARY;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public ResponseDto saveCustomer(ContactDto contactDto) {
        List<Contact> matchingContacts = contactRepository.findByEmailOrPhoneNumber
                (contactDto.getEmail(), contactDto.getPhoneNumber());
        Contact contact=new Contact();
        ResponseDto responseDto=new ResponseDto();
        List<String> emails=new ArrayList<>();
        List<String> phoneNumbers=new ArrayList<>();
        List<Long> secondaryContactId=new ArrayList<>();
        if (matchingContacts.isEmpty()) {

            Contact newContact = new Contact();
            newContact.setEmail(contactDto.getEmail());
            newContact.setPhoneNumber(contactDto.getPhoneNumber());
            newContact.setLinkedId(null);
            newContact.setLinkPrecedence(PRIMARY);
            contactRepository.save(newContact);

            responseDto.setPrimaryContactId(newContact.getId());
            if (newContact.getEmail() != null)
                emails.add(newContact.getEmail());
            if (newContact.getPhoneNumber() != null)
                phoneNumbers.add(newContact.getPhoneNumber());
            responseDto.setEmails(emails);
            responseDto.setPhoneNumbers(phoneNumbers);
            responseDto.setSecondaryContactIds(secondaryContactId);

            return responseDto;
        }

        Contact primary = null;

        for (Contact c : matchingContacts) {
            if (c.getLinkPrecedence() == PRIMARY) {

                if (primary == null) {
                    primary = c;
                } else if (c.getCreatedAt().isBefore(primary.getCreatedAt())) {
                    primary = c;
                }
            }
        }

        if (primary == null) {
            Contact root = matchingContacts.get(0);

            while (root.getLinkedId() != null) {
                root = contactRepository.findById(root.getLinkedId()).orElse(null);
            }
            primary = root;
        }
        for (Contact c : matchingContacts) {

            if (c.getLinkPrecedence() == PRIMARY && !c.getId().equals(primary.getId())) {
                c.setLinkPrecedence(SECONDARY);
                c.setLinkedId(primary.getId());
                contactRepository.save(c);
            }
        }
        boolean exactExists = false;

        for (Contact c : matchingContacts) {

            boolean emailMatch = contactDto.getEmail() != null && contactDto.getEmail().equals(c.getEmail());
            boolean phoneMatch = contactDto.getPhoneNumber() != null &&
                            contactDto.getPhoneNumber().equals(c.getPhoneNumber());

            if (emailMatch && phoneMatch) {
                exactExists = true;
                break;
            }
        }

        if (!exactExists) {
            Contact secondaryNew = new Contact();
            secondaryNew.setEmail(contactDto.getEmail());
            secondaryNew.setPhoneNumber(contactDto.getPhoneNumber());
            if(primary.getLinkedId()!=null){
            secondaryNew.setLinkedId(primary.getLinkedId());
            }
            else {
                secondaryNew.setLinkedId(primary.getId());
            }
            secondaryNew.setLinkPrecedence(SECONDARY);
            contactRepository.save(secondaryNew);
        }

        List<Contact> linkedContacts = contactRepository.findByLinkedId(primary.getId());
        responseDto.setPrimaryContactId(primary.getId());

        if (primary.getEmail() != null && !emails.contains(primary.getEmail())) {
            emails.add(primary.getEmail());
        }

        if (primary.getPhoneNumber() != null && !phoneNumbers.contains(primary.getPhoneNumber())) {
            phoneNumbers.add(primary.getPhoneNumber());
        }
        for (Contact sec : linkedContacts) {

            if (sec.getEmail() != null && !emails.contains(sec.getEmail())) {
                emails.add(sec.getEmail());
            }
            if (sec.getPhoneNumber() != null && !phoneNumbers.contains(sec.getPhoneNumber())) {
                phoneNumbers.add(sec.getPhoneNumber());
            }
            secondaryContactId.add(sec.getId());
        }

        responseDto.setEmails(emails);
        responseDto.setPhoneNumbers(phoneNumbers);
        responseDto.setSecondaryContactIds(secondaryContactId);

        return responseDto;
    }

}
/*Primary resolution

Secondary linking

Multi-cluster merging

Root flattening

Exact match prevention

Identity graph consistency*/
