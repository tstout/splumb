package splumb.core.db;

import com.google.inject.AbstractModule;

public class DBDevModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(DBService.class).to(H2Db.class);
    }

}