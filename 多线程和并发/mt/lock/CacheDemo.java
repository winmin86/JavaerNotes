package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: lock.CacheDemo
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/13 16:44
 * @Version: 1.0
 */
public class CacheDemo {

    Map<String, Object> cache = new HashMap<String, Object>();

    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    public Object get(String key) {
        rwl.readLock().lock();
        Object value = null;
        try {
            value = cache.get(key);
        } catch (Exception e) {

        } finally {
            rwl.readLock().unlock();
        }
        return value;
    }

    public boolean set(String key, Object value) {
        boolean rs = false;
        try {
            rwl.writeLock().lock();
            cache.put(key, value);
        } catch (Exception e) {

        } finally {
            rwl.writeLock().unlock();
        }
        return rs;
    }
}
