package org.fanti.uploader.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 * Copyright: Copyright(c) 2009-2018 税友集团
 * Company: 税友软件集团有限公司
 *
 * @author ftk
 * @date 2018/8/27
 */

public class FileLockUtil {
    private static Map<String, Lock> LOCKS = new HashMap<String, Lock>();

    public static synchronized Lock getLock(String key){
        if(LOCKS.containsKey(key)){
            return LOCKS.get(key);
        }else{
            Lock one = new ReentrantLock();
            LOCKS.put(key, one);
            return one;
        }
    }

    public static synchronized void removeLock(String key){
        LOCKS.remove(key);
    }
}
