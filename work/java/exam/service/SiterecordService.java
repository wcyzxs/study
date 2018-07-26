/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.exam.entity.Siterecord;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.exam.dao.SiterecordDao;

/**
 * 现场检查笔录Service
 * @author 千里目
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class SiterecordService extends CrudService<SiterecordDao, Siterecord> {

	public Siterecord get(String id) {
		return super.get(id);
	}
	
	public List<Siterecord> findList(Siterecord tsiterecord) {
		return super.findList(tsiterecord);
	}
	
	public Page<Siterecord> findPage(Page<Siterecord> page, Siterecord tsiterecord) {
		return super.findPage(page, tsiterecord);
	}
	
	@Transactional(readOnly = false)
	public void save(Siterecord tsiterecord) {
		super.save(tsiterecord);
	}
	
	@Transactional(readOnly = false)
	public void saveInfo(Siterecord siterecord) {
		this.save(siterecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(Siterecord tsiterecord) {
		super.delete(tsiterecord);
	}
	
}