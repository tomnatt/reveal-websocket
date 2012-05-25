package models;

import java.util.*;

import play.libs.*;
import play.libs.F.*;

public class CommandModel {
    
    final ArchivedEventStream<CommandModel.Event> commandEvents = new ArchivedEventStream<CommandModel.Event>(100);
    
    /**
     * New device joins channel
     */
    public EventStream<CommandModel.Event> join() {
        return commandEvents.eventStream();
    }
    
    /**
     * A command comes in
     */
    public void order(String text) {
        if(text == null || text.trim().equals("")) {
            return;
        }
        commandEvents.publish(new Command(text));
    }
    
    
    // ~~~~~~~~~ CommandModel events

    public static abstract class Event {
        
        final public String type;
        final public Long timestamp;
        
        public Event(String type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }
        
    }
    
    public static class Command extends Event {
        
        final public String text;
        
        public Command(String text) {
            super("command");
            this.text = text;
        }
        
    }
    
    // ~~~~~~~~~ CommandModel factory

    static CommandModel instance = null;
    public static CommandModel get() {
        if(instance == null) {
            instance = new CommandModel();
        }
        return instance;
    }
    
}
