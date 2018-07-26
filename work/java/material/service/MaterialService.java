/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.material.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.material.entity.Material;
import org.wxjs.les.modules.exam.entity.Siterecord;
import org.wxjs.les.modules.material.dao.MaterialDao;

/**
 * 视听资料证据Service
 * @author 千里目
 * @version 2018-07-23
 */
@Service
@Transactional(readOnly = true)
public class MaterialService extends CrudService<MaterialDao, Material> {

	public Material get(String id) {
		return super.get(id);
	}
	
	public List<Material> findList(Material material) {
		return super.findList(material);
	}
	
	public Page<Material> findPage(Page<Material> page, Material material) {
		return super.findPage(page, material);
	}
	
	@Transactional(readOnly = false)
	public void save(Material material) {
		super.save(material);
	}
	
	@Transactional(readOnly = false)
	public void saveInfo(Material material) {
		this.save(material);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(Material material) {
		super.delete(material);
	}
	
}