package splumb.core.host;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.google.common.collect.Lists.newArrayList;

class ShutdownActions implements Runnable {

    private List<Service> services = newArrayList();

    public ShutdownActions add(Runnable r, String name) {
        actions.add(new Action(r, name));
        return this;
    }

    public ShutdownActions add(Service service) {
        services.add(service);
        return this;
    }

    public ShutdownActions() {
    }


//    public ShutdownActions(List<Service> services) {
//        this.services = services;
//    }

//    @Override
//    public void run() {
//        Console con = System.console();
//
//        for (Action r : actions) {
//            r.action.run();
//
//            if (con != null) {
//                con.printf("Shutdown of %s complete\n", r.name);
//            }
//        }
//
//        term.countDown();
//    }

    @Override
    public void run() {
        Console con = System.console();

        for (Service service : services) {
            service.stop();

//            if (con != null) {
//                con.printf("Shutdown of %s complete\n", r.name);
//            }
        }

        term.countDown();
    }

    public ShutdownActions install() {
        Runtime.getRuntime().addShutdownHook(new Thread(this));

        for (Service service : services) {
            service.start();
        }

        return this;
    }

    public void waitForTermination() {
        try {
            term.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    class Action {
        public String name;
        public Runnable action;

        public Action(Runnable r, String name) {
            this.name = name;
            this.action = r;
        }
    }

    private List<Action> actions = new ArrayList<Action>();
    private CountDownLatch term = new CountDownLatch(1);
}
