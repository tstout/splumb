package splumb.core.host;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class ShutdownActions implements Runnable
{
    public ShutdownActions add(Runnable r, String name)
    {
        actions.add(new Action(r, name));
        return this;
    }

    @Override
    public void run()
    {
        Console con = System.console();

        for (Action r : actions)
        {
            r.action.run();

            if (con != null)
            {
                con.printf("Shutdown of %s complete\n", r.name);
            }
        }

        term.countDown();
    }

    public ShutdownActions install()
    {
        Runtime
                .getRuntime()
                .addShutdownHook(new Thread(this));

        return this;
    }

    public void waitForTermination()
    {
        try
        {
            term.await();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    class Action
    {
        public String name;
        public Runnable action;

        public Action(Runnable r, String name)
        {
            this.name = name;
            this.action = r;
        }
    }

    private List<Action> actions = new ArrayList<Action>();
    private CountDownLatch term = new CountDownLatch(1);
}
