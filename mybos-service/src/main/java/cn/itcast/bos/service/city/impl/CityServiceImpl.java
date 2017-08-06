package cn.itcast.bos.service.city.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.city.CityDao;
import cn.itcast.bos.domain.city.City;
import cn.itcast.bos.redis.utils.RedisCRUD;
import cn.itcast.bos.service.city.CityService;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class CityServiceImpl implements CityService {
	@Autowired
	private CityDao cityDao;
	@Autowired
	private RedisCRUD redisCRUDImpl;

	@Override
	public String loadCity(int pid) {
		String jsonvalue = redisCRUDImpl.GetJSONStringFromRedis(String
				.valueOf(pid));
		if (StringUtils.isBlank(jsonvalue)) {
			List<City> list = findAll(pid);
			jsonvalue = JSON.toJSONString(list);
			redisCRUDImpl
					.writeJSONStringToRedis(String.valueOf(pid), jsonvalue);
		}
		return jsonvalue;
	}

	private List<City> findAll(int pid) {

		return cityDao.findAll(pid);
	}

}
