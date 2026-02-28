package com.identityReconciliation.Dto;

public class ApiResponse<ResponseDto> {
    private ResponseDto contact;

    public ResponseDto getContact() {
        return contact;
    }

    public void setContact(ResponseDto contact) {
        this.contact = contact;
    }
}
