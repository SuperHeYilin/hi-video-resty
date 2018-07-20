package cn.diffpi.core.redis;

import java.util.Set;

public class test
{

    public static void main(String[] args)
    {
        Set<String> sb = RedisManager.get("_query::default::module_lottery::keys");
        for (String string : sb)
        {
         System.out.println(string);   
        }
    }

}
