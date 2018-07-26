/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.exam.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.exam.entity.Siterecord;

/**
 * 现场检查笔录DAO接口
 * @author 千里目
 * @version 
 */
@MyBatisDao
public interface SiterecordDao extends CrudDao<Siterecord> {
	
}