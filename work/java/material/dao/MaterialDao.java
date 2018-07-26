/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.material.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.material.entity.Material;

/**
 * 视听资料证据DAO接口
 * @author 千里目
 * @version 2018-07-23
 */
@MyBatisDao
public interface MaterialDao extends CrudDao<Material> {
	
}