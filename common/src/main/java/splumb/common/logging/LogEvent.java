package splumb.common.logging;

import splumb.common.util.Prop;

import java.util.Date;

import static splumb.common.util.Prop.newProp;

//
// TODO - would an interface be a better design here...not sure
// at the moment...
// Perhaps this is better suited to an immutable object with a builder.
//
public class LogEvent {
    public final Prop<Date> timeStamp = newProp(new Date());

    public final Prop<String> fmt = newProp();

    public final Prop<Object[]> args = newProp();

    public final Prop<String> thread = newProp(Thread.currentThread().getName());

    public LogEvent() {
    }

}
