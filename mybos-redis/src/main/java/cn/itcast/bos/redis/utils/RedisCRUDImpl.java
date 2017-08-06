package cn.itcast.bos.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

@Service
@Transactional
public class RedisCRUDImpl implements RedisCRUD {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void writeJSONStringToRedis(String key, String jsonstring) {
		redisTemplate.opsForValue().set(key, jsonstring);
	}

	@Override
	public void writeObjectToRedisByFastjson(String key, Object obj) {
		String jsonString = JSON.toJSONString(obj);
		redisTemplate.opsForValue().set(key, jsonString);
	}

	@Override
	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj,
			SerializeFilter filter) {
		String jsonString = JSON.toJSONString(obj, filter);
		redisTemplate.opsForValue().set(key, jsonString);
	}

	@Override
	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj,
			String... inproperties) {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(
				inproperties);
		String jsonString = JSON.toJSONString(obj, filter);
		redisTemplate.opsForValue().set(key, jsonString);
	}

	@Override
	public String GetJSONStringFromRedis(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void deleteJSONStringFromRedisByKey(String key) {
		redisTemplate.delete(key);
	}

}
