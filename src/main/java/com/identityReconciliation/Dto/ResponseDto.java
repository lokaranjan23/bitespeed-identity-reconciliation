package com.identityReconciliation.Dto;

import java.util.List;

public class ResponseDto {

    private long primaryContactId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Long> secondaryContactIds;

    public long getPrimaryContactId() {
        return primaryContactId;
    }

    public void setPrimaryContactId(long primaryContactId) {
        this.primaryContactId = primaryContactId;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Long> getSecondaryContactIds() {
        return secondaryContactIds;
    }

    public void setSecondaryContactIds(List<Long> secondaryContactIds) {
        this.secondaryContactIds = secondaryContactIds;
    }
}
