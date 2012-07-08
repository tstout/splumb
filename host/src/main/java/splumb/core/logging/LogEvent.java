package splumb.core.logging;

import splumb.core.util.Prop;

import java.util.Date;

import static splumb.core.util.Prop.newProp;

//
// TODO - would an interface be a better design here...not sure
// at the moment...
//
public class LogEvent {
    public final Prop<Date> timeStamp = newProp(new Date());

    public final Prop<String> fmt = newProp();

    public final Prop<Object[]> args = newProp();

    public LogEvent() {
    }

}
