package cn.itcast.bos.redis.utils;

import com.alibaba.fastjson.serializer.SerializeFilter;

public interface RedisCRUD {

	// 将json字符串存到redis中
	public void writeJSONStringToRedis(String key, String jsonstring);

	// 将对象序列化,存到redis
	public void writeObjectToRedisByFastjson(String key, Object obj);

	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj,
			SerializeFilter filter);

	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj,
			String... inproperties);

	public String GetJSONStringFromRedis(String key);

	public void deleteJSONStringFromRedisByKey(String key);
}
