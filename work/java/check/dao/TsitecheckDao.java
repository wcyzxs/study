/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.dao;

import java.util.List;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.check.entity.Tsitecheck;

/**
 * 现场踏勘DAO接口
 * @author 千里目
 * @version 2018-07-10
 */
@MyBatisDao
public interface TsitecheckDao extends CrudDao<Tsitecheck> {

}