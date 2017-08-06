package cn.itcast.bos.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class RedisTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void test() {
		redisTemplate.opsForValue().set("address", "合肥", 300, TimeUnit.SECONDS);
		System.out.println("set ok");
	}

	@Test
	public void test1() {
		redisTemplate.opsForHash().put("my", "name", "范海伦");
		redisTemplate.opsForHash().put("my", "age", 24);
		System.out.println("set-ok");
	}

	@Test
	public void test2() {
		String name = (String) redisTemplate.opsForHash().get("my", "name");
		System.out.println(name);

	}
}
