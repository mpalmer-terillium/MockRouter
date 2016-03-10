package com.mockedwards.eventloop;

import com.mockedwards.dto.MockIntegrationRequest;
import com.mockedwards.dto.MockIntegrationResponse;
import com.mockedwards.factory.IntegrationFactory;
import com.mockedwards.integration.CCIntegration;
import com.mockedwards.integration.IntegrationType;
import com.mockedwards.integration.OSCIntegration;
import com.mockqueue.service.Message;
import com.mockqueue.service.MockQueueService;
import com.mockqueue.service.MockQueueServiceService;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RouterEventLoop implements Runnable {
    
    private static final Logger logger = Logger.getLogger("MockEdwardsClient");
    private static final MockQueueService queue = new MockQueueServiceService().getMockQueueServicePort();
    
    @Override
    public void run() {
        while (true) {
           try {
                Thread.sleep(4000);
                Message message = queue.getNextMessage();

                if (message.getId() != -1) {
                    routeMessage(message);
                }
           } catch (Exception e) {
               logger.log(Level.SEVERE, e.toString());
           }
       }
    }
    
    private static void routeMessage(Message m) {
        switch (m.getType()) {

            case PAY_TRACE:
                MockIntegrationResponse ccResponse  = doCCIntegration(new MockIntegrationRequest(
                        IntegrationType.PayTrace, "100.00", "Authorization"));
                logger.log(Level.INFO, "Response: {0}", ccResponse.getResponse());
                logger.info("PayTrace Integration Call");
                break;

            case SALES_CLOUD:
                MockIntegrationResponse oscResponse = doOSCIntegration(new MockIntegrationRequest(
                        IntegrationType.SalesCloud, "TEST OP WS", "READ"));
                logger.log(Level.INFO, "Response: {0}", oscResponse.getResponse());
                logger.info("SalesCloud Integration Call");
                break;

            default:
                logger.log(Level.INFO, "Unable to route unknown message type: {0}", m.getType());
                break;
        }
    }

    private static MockIntegrationResponse doCCIntegration(MockIntegrationRequest mockRequest) {
        CCIntegration ccInt = (CCIntegration) IntegrationFactory.getIntegration(mockRequest.getIntegrationType());
        return ccInt.processIntegration(mockRequest);
    }

    private static MockIntegrationResponse doOSCIntegration(MockIntegrationRequest mockRequest) {
        OSCIntegration oscInt = (OSCIntegration) IntegrationFactory.getIntegration(mockRequest.getIntegrationType());
        return oscInt.processIntegration(mockRequest);
    }
}
