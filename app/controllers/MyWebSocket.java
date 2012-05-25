package controllers;

import play.*;
import play.libs.F.*;
import static play.libs.F.Matcher.*;
import static play.mvc.Http.WebSocketEvent.*;
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
            
            CommandModel comm = CommandModel.get();
            
            // Socket connected, get the command stream
            EventStream<CommandModel.Event> commandMessagesStream = comm.join();
         
            // Loop while the socket is open
            while(inbound.isOpen()) {
                
                // Wait for an event (either something coming on the inbound socket channel, or ChatRoom messages)
                Either<WebSocketEvent,CommandModel.Event> e = await(Promise.waitEither(
                    inbound.nextEvent(), 
                    commandMessagesStream.nextEvent()
                ));
                
                // Case: TextEvent received on the socket
                for(String message: TextFrame.match(e._1)) {
                    comm.order(message);
                }
                
                // Case: New command
                for(CommandModel.Command command: ClassOf(CommandModel.Command.class).match(e._2)) {
                    outbound.send(command.text);
                }
                
                // Case: The socket has been closed
                for(WebSocketClose closed: SocketClosed.match(e._1)) {
                    disconnect();
                } 
                
            }    
            
        }
    }
    
}
