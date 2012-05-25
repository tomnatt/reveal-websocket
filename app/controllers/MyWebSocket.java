package controllers;

import play.*;
import play.libs.F.*;
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
            //WebSocketEvent e = await(inbound.nextEvent());
            //WebSocketEvent e = await(StatefulModel.instance.event.nextEvent());
            
            
            //StatefulModel.instance.event.publish
            
            Object event = await(StatefulModel.instance.event.nextEvent());
            System.out.println(event.getClass().toString());
            //outbound.send(event);
            
            /*
            if (e instanceof WebSocketFrame) {
                
                WebSocketFrame frame = (WebSocketFrame)e;
                if (!frame.isBinary) {
                    if(frame.textData.equals("quit")) {
                        outbound.send("Bye!");
                        disconnect();
                    } else {
                        
                        System.out.println(frame.textData);
                        outbound.send("Echo: %s", frame.textData);
                        
                        
                    }
                }
            }
            
            if (e instanceof WebSocketClose) {
                Logger.info("Socket closed!");
            }
            */
            
        }
    }
    
}
