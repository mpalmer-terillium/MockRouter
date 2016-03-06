package com.mockedwards.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MockRouterRequest {
    
    private int passcode;

    public int getPasscode() {
        return passcode;
    }

    public void setPasscode(int passcode) {
        this.passcode = passcode;
    }
}
