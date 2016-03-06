package com.mockedwards.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MockRouterResponse {
    
    private String message;
    
    public MockRouterResponse() {}

    public MockRouterResponse(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
