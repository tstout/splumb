package splumb.messaging.commands;

// how about putting behaviour here to handle command impl instead of
// a map within the borker (similar to framing and nio impl)?
//
public enum BrokerCommands {
    ADD_QUEUE,
    ADD_TOPIC;
}
