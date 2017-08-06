package cn.itcast.bos.service.bc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.bc.RegionDao;
import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.redis.utils.RedisCRUD;
import cn.itcast.bos.service.bc.RegionService;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionDao regionDao;

	@Autowired
	private RedisCRUD redisCRUDImpl;

	@Override
	public void save(List<Region> list) {
		regionDao.save(list);
	}

	@Override
	public Page<Region> pageQuery(PageRequest pagerequest) {
		return regionDao.findAll(pagerequest);
	}

	public String pageQueryByRedis(PageRequest pageRequest) {
		int pageNumber = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		String keyId = pageNumber + "_" + pageSize;
		String jsonvalue = redisCRUDImpl.GetJSONStringFromRedis(keyId);
		if (StringUtils.isBlank(jsonvalue)) {
			// 第一次查询数据库,将数据库数据序列化Json格式的数据存储到redis 服务器上
			Page<Region> pageData = regionDao.findAll(pageRequest);// 查询数据库
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pageData.getTotalElements());
			map.put("rows", pageData.getContent());
			jsonvalue = JSON.toJSONString(map);// 满足easyui需要分页数据的json格式
			redisCRUDImpl.writeJSONStringToRedis(keyId, jsonvalue);// 将分页的json格式数据存储到redis服务器上
		}
		return jsonvalue;
	}

	@Override
	public Region findById(String id) {
		return regionDao.findById(id);

	}

	@Override
	public List<Region> findAll() {

		return regionDao.findAll();
	}

	@Override
	public Region findByPostcode(String postcode) {

		return regionDao.findByPostcode(postcode);
	}

	@Override
	public void save(Region model) {
		regionDao.save(model);
	}

	@Override
	public Page<Region> pageQuery(PageRequest pagerequest,
			Specification<Region> spec) {
		return regionDao.findAll(spec, pagerequest);
	}

	@Override
	public List<Region> findAllByPcd(String param) {
		if (StringUtils.isNotBlank(param)) {
			return regionDao.findAllByPcd("%" + param + "%");
		} else {
			return findAll();
		}

	}

	@Override
	public Region importpcd(String rid) {
		return regionDao.importpcd(rid);
	}

	@Override
	public String pageFromRedis(PageRequest pagerequest,
			Specification<Region> spec) {
		int pageNumber = pagerequest.getPageNumber();
		int pageSize = pagerequest.getPageSize();
		String key = pageNumber + "_" + pageSize;
		String jsonvalue = redisCRUDImpl.GetJSONStringFromRedis(key);
		if (StringUtils.isBlank(jsonvalue)) {
			Page<Region> pageData = regionDao.findAll(pagerequest);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pageData.getTotalElements());
			map.put("rows", pageData.getContent());
			jsonvalue = JSON.toJSONString(map);
			redisCRUDImpl.writeJSONStringToRedis(key, jsonvalue);
		}
		return jsonvalue;
	}

}
