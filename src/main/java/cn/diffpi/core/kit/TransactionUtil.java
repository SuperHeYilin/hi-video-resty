package cn.diffpi.core.kit;

import cn.dreampie.orm.DataSourceMeta;
import cn.dreampie.orm.Metadata;

public class TransactionUtil
{
    private DataSourceMeta dataSourceMeta;    
    public static TransactionUtil init(){
        return new TransactionUtil();
    }
    public TransactionUtil(){
        dataSourceMeta=Metadata.getDataSourceMeta("default");
        dataSourceMeta.initTransaction(false, 2);
    }
    public void begin(){
        dataSourceMeta.beginTransaction();
    }
    public void commit(){
        dataSourceMeta.commitTransaction();
        dataSourceMeta.endTranasaction();
    }
    public void rollback(){
        dataSourceMeta.rollbackTransaction();
        dataSourceMeta.endTranasaction();
    }
    public void close(){
        dataSourceMeta.close();
    }
}

