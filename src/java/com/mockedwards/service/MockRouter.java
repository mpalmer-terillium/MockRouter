package com.mockedwards.service;

import com.mockedwards.dto.MockRouterRequest;
import com.mockedwards.dto.MockRouterResponse;
import com.mockedwards.eventloop.RouterEventLoop;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public class MockRouter {
    
    private final int PASSCODE = 1234;
    private int RUNNING = 0;
    
    @WebMethod(operationName = "initializeRouter")
    public MockRouterResponse initRouter(@WebParam(name = "request") MockRouterRequest request) {
        
        if(RUNNING != 0) {
            
            return new MockRouterResponse("Router already running");
            
        } else if(request.getPasscode() == PASSCODE) {
            
            RUNNING = 1;
            
            Thread t = new Thread(new RouterEventLoop());
            t.start();

            return new MockRouterResponse("Router initialized!");
            
        } else {
            
            return new MockRouterResponse("Unable to initialize router");
        }
    }
}
