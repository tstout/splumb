package splumb.core.logging;

import org.apache.empire.db.DBRecord;

// TOOD - DBRecord parent not necessary to pass unit test.  Investigate the real utility of DBRecord.
public class LogRecord extends DBRecord {
    private int level;
    private String msg;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}