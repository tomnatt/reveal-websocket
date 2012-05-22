package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import java.util.*;

import models.*;

public class MyWebSocket extends WebSocketController {

    public static void hello(String name) {
        System.out.println("Hello " + name);
        outbound.send("Hello %s!", name);
    }
    
    public static void echo() {
        while (inbound.isOpen()) {
            WebSocketEvent e = await(inbound.nextEvent());
            if (e instanceof WebSocketFrame) {
                WebSocketFrame frame = (WebSocketFrame)e;
                if (!frame.isBinary) {
                    if(frame.textData.equals("quit")) {
                    	System.out.println("quit");
                        outbound.send("Bye!");
                        disconnect();
                    } else {
                    	System.out.println("not quit");
                        outbound.send("Echo: %s", frame.textData);
                    }
                }
            }
            if (e instanceof WebSocketClose) {
                System.out.println("closing");
                Logger.info("Socket closed!");
            }
            System.out.println("howdy");
        }
    }
    
}
