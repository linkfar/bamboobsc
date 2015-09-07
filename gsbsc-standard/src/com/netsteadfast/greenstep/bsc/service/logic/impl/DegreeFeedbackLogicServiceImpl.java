/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackAssignService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackItemService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackProjectService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackScoreService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.logic.IDegreeFeedbackLogicService;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackAssign;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackItem;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackScore;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.vo.DegreeFeedbackAssignVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.DegreeFeedbackLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DegreeFeedbackLogicServiceImpl extends BaseLogicService implements IDegreeFeedbackLogicService {
	protected Logger logger=Logger.getLogger(DegreeFeedbackLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_OR_MEMO_LENGTH = 500;
	private IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService;
	private IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService;
	private IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService;
	private IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService;
	private IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	public DegreeFeedbackLogicServiceImpl() {
		super();
	}

	public IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> getDegreeFeedbackProjectService() {
		return degreeFeedbackProjectService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackProjectService")
	@Required			
	public void setDegreeFeedbackProjectService(
			IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService) {
		this.degreeFeedbackProjectService = degreeFeedbackProjectService;
	}

	public IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> getDegreeFeedbackItemService() {
		return degreeFeedbackItemService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackItemService")
	@Required				
	public void setDegreeFeedbackItemService(
			IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService) {
		this.degreeFeedbackItemService = degreeFeedbackItemService;
	}

	public IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> getDegreeFeedbackLevelService() {
		return degreeFeedbackLevelService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackLevelService")
	@Required		
	public void setDegreeFeedbackLevelService(
			IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService) {
		this.degreeFeedbackLevelService = degreeFeedbackLevelService;
	}
		
	public IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> getDegreeFeedbackAssignService() {
		return degreeFeedbackAssignService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackAssignService")
	@Required		
	public void setDegreeFeedbackAssignService(
			IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService) {
		this.degreeFeedbackAssignService = degreeFeedbackAssignService;
	}
	
	public IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> getDegreeFeedbackScoreService() {
		return degreeFeedbackScoreService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackScoreService")
	@Required			
	public void setDegreeFeedbackScoreService(
			IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService) {
		this.degreeFeedbackScoreService = degreeFeedbackScoreService;
	}

	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")
	@Required		
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<DegreeFeedbackProjectVO> createProject(
			DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items,
			List<DegreeFeedbackLevelVO> levels, 
			List<String> ownerEmplOids, List<String> raterEmplOids) throws ServiceException, Exception {
		if (project == null || levels == null || levels.size()<1 || items == null || items.size()<1 
				|| ownerEmplOids == null || ownerEmplOids.size()<1 || raterEmplOids == null || raterEmplOids.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (levels.size()>BscConstants.MAX_DEGREE_FEEDBACK_LEVEL_SIZE || items.size()>BscConstants.MAX_DEGREE_FEEDBACK_ITEM_SIZE) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}
		super.setStringValueMaxLength(project, "description", MAX_DESCRIPTION_OR_MEMO_LENGTH);
		project.setPublishFlag( YesNo.NO );
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackProjectService.saveObject(project);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		project = result.getValue();
		this.createLevels(project, levels);
		this.createItems(project, items);
		this.createAssign(project, ownerEmplOids, raterEmplOids);
		return result;
	}
	
	private void createLevels(DegreeFeedbackProjectVO project, List<DegreeFeedbackLevelVO> levels) throws ServiceException, Exception {
		for (DegreeFeedbackLevelVO level : levels) {
			if ( super.isBlank(level.getName()) ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
			}
			if (level.getValue()<1) { // 等級評分分數設定不能小於1
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
			}
			level.setProjectOid(project.getOid());
			this.degreeFeedbackLevelService.saveObject(level);
		}
	}
	
	private void createItems(DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items) throws ServiceException, Exception {
		for (DegreeFeedbackItemVO item : items) {
			if ( super.isBlank(item.getName()) ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
			}
			item.setProjectOid(project.getOid());
			super.setStringValueMaxLength(item, "description", MAX_DESCRIPTION_OR_MEMO_LENGTH);
			this.degreeFeedbackItemService.saveObject(item);
		}
	}
	
	private void createAssign(DegreeFeedbackProjectVO project, 
			List<String> ownerEmplOids, List<String> raterEmplOids) throws ServiceException, Exception {
		Map<String, EmployeeVO> tmpBag = new HashMap<String, EmployeeVO>();
		for (String ownerOid : ownerEmplOids) {
			EmployeeVO owner = this.fetchEmployee(ownerOid, tmpBag);
			for (String raterOid : raterEmplOids) {
				EmployeeVO rater = this.fetchEmployee(raterOid, tmpBag);
				DegreeFeedbackAssignVO assign = new DegreeFeedbackAssignVO();
				assign.setProjectOid(project.getOid());
				assign.setOwnerId(owner.getEmpId());
				assign.setRaterId(rater.getEmpId());
				this.degreeFeedbackAssignService.saveObject(assign);
			}			
		}
		tmpBag.clear();
		tmpBag = null;
	}
	
	private EmployeeVO fetchEmployee(String oid, Map<String, EmployeeVO> tmpBag) throws ServiceException, Exception {
		if (tmpBag.get(oid)!=null) {
			return tmpBag.get(oid);
		}
		EmployeeVO employee = new EmployeeVO();
		employee.setOid(oid);
		DefaultResult<EmployeeVO> result = this.employeeService.findObjectByOid(employee);
		if (result.getValue() == null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		tmpBag.put(oid, result.getValue());
		return result.getValue();
	}

}
