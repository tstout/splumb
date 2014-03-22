package splumb.messaging;

import splumb.core.db.SplumbDB;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.*;

class DbBrokerConfig implements BrokerConfig {
    private List<BrokerLocation> brokers = newArrayList();

    @Inject
    DbBrokerConfig(SplumbDB db) {

//        DBCommand cmd = db.createCommand();
//        cmd.select(db.MsgBrokers.HOST, db.MsgBrokers.PORT);
//
//        DBReader rdr = new DBReader();
//        rdr.open(cmd, db.getConnection());
//
//        try {
//            while (rdr.moveNext()) {
//                brokers.add(new BrokerLocation(rdr.getString(db.MsgBrokers.HOST),
//                        rdr.getInt(db.MsgBrokers.PORT)));
//            }
//        }
//        finally {
//            rdr.close();
//        }
    }

    @Override
    public List<BrokerLocation> brokers() {
        return Collections.unmodifiableList(brokers);
    }
}
