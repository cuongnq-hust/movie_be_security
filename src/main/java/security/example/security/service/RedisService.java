package security.example.security.service;

import java.util.List;

public interface RedisService<T> {
    List<T> searchList(String key) throws Exception;

    void saveList(String key, List<T> list) throws Exception;
    void deleteKeysStartingWith(String prefix) throws Exception;
}
