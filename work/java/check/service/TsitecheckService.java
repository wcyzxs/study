/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.qa.entity.Questionanswer;
import org.wxjs.les.modules.base.service.SignatureService;
import org.wxjs.les.modules.check.dao.TsitecheckDao;

/**
 * 现场踏勘Service
 * @author 千里目
 * @version 2018-07-10
 */
@Service
@Transactional(readOnly = true)
public class TsitecheckService extends CrudService<TsitecheckDao, Tsitecheck> {

	
	public Tsitecheck get(String id) {
		return super.get(id);
	}
	
	public List<Tsitecheck> findList(Tsitecheck tsitecheck) {
		return super.findList(tsitecheck);
	}
	
	public Page<Tsitecheck> findPage(Page<Tsitecheck> page, Tsitecheck tsitecheck) {
		return super.findPage(page, tsitecheck);
	}
	
	
	@Transactional(readOnly = false)
	public void save(Tsitecheck tsitecheck) {
		super.save(tsitecheck);
	}
	
	@Transactional(readOnly = false)
	public void saveInfo(Tsitecheck tsitecheck) {
		this.save(tsitecheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tsitecheck tsitecheck) {
		super.delete(tsitecheck);
	}
	
}