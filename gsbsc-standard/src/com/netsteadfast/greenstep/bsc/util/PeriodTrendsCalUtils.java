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
package com.netsteadfast.greenstep.bsc.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.model.PeriodTrendsData;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@SuppressWarnings("unchecked")
public class PeriodTrendsCalUtils {
	private static IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private static IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	static {
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");
	}
	
	private static Context getContext(String visionOid, String startDate, String endDate, 
			String startYearDate, String endYearDate, String frequency, String dataFor, String orgId, String empId,
			String measureDataOrganizationOid, String measureDataEmployeeOid) throws ServiceException, Exception {
		Context context = new ContextBase();
		context.put("visionOid", visionOid);
		context.put("startDate", startDate);
		context.put("endDate", endDate);		
		context.put("startYearDate", startYearDate);
		context.put("endYearDate", endYearDate);		
		context.put("frequency", frequency);
		context.put("dataFor", dataFor);
		context.put("orgId", BscConstants.MEASURE_DATA_ORGANIZATION_FULL);
		context.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL);
		context.put("account", "");
		if (!Constants.HTML_SELECT_NO_SELECT_ID.equals(measureDataOrganizationOid) && !StringUtils.isBlank(measureDataOrganizationOid)) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid( measureDataOrganizationOid );
			DefaultResult<OrganizationVO> result = organizationService.findObjectByOid(organization);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			organization = result.getValue();
			context.put("orgId", organization.getOrgId() );
		}
		if (!Constants.HTML_SELECT_NO_SELECT_ID.equals(measureDataEmployeeOid) && !StringUtils.isBlank(measureDataEmployeeOid)) {
			EmployeeVO employee = new EmployeeVO();
			employee.setOid( measureDataEmployeeOid );
			DefaultResult<EmployeeVO> result = employeeService.findObjectByOid(employee);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			employee = result.getValue();
			context.put("empId", employee.getEmpId() );
			context.put("account", employee.getAccount() );
		}				
		return context;
	}
	
	private static void fillKpiPeriodTrends(List<PeriodTrendsData<KpiVO>> result, 
			ChainResultObj result1, ChainResultObj result2) throws ServiceException, Exception {
				
		VisionVO visionCV = ( (BscStructTreeObj)result1.getValue() ).getVisions().get(0);
		VisionVO visionPV = ( (BscStructTreeObj)result2.getValue() ).getVisions().get(0);
		for (PerspectiveVO perspective : visionCV.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				for (KpiVO kpi : objective.getKpis()) {
					PeriodTrendsData<KpiVO> periodData = new PeriodTrendsData<KpiVO>();
					periodData.setCurrent(kpi);
					result.add(periodData);
				}
			}
		}
		for (PeriodTrendsData<KpiVO> periodData : result) {
			for (PerspectiveVO perspective : visionPV.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (KpiVO kpi : objective.getKpis()) {
						if (periodData.getCurrent().getId().equals(kpi.getId())) {
							periodData.setPrevious(kpi);
						}
					}
				}
			}	
			if (periodData.getPrevious() == null) { // 沒有對應的 KPI
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			
			/**
			 * periodData.getCurrent().getTrendsFormula() 與 periodData.getPrevious().getTrendsFormula() 一定是相同的 
			 */
			Object ans = BscFormulaUtils.parseKPIPeroidScoreChangeValue(
					periodData.getCurrent().getTrendsFormula(), 
					periodData.getCurrent().getScore(), 
					periodData.getPrevious().getScore());
			String change = String.valueOf(ans);
			if ( NumberUtils.isNumber( String.valueOf(change) ) ) {
				periodData.setChange( NumberUtils.toFloat(change)  );
			}
			
		}
		
	}	
	
	public static List<PeriodTrendsData<KpiVO>> getKpiScoreChange(String visionOid1, String startDate1, String endDate1, 
			String startYearDate1, String endYearDate1, String frequency1, String dataFor1, String orgId1, String empId1,
			String measureDataOrganizationOid1, String measureDataEmployeeOid1,
			String visionOid2, String startDate2, String endDate2, 
			String startYearDate2, String endYearDate2, String frequency2, String dataFor2, String orgId2, String empId2,
			String measureDataOrganizationOid2, String measureDataEmployeeOid2) throws ServiceException, Exception {
		
		List<PeriodTrendsData<KpiVO>> result = new ArrayList<PeriodTrendsData<KpiVO>>();
		Context context1 = getContext(visionOid1, startDate1, endDate1, startYearDate1, endYearDate1, frequency1, dataFor1, orgId1, empId1, 
				measureDataOrganizationOid1, measureDataEmployeeOid1);
		Context context2 = getContext(visionOid2, startDate2, endDate2, startYearDate2, endYearDate2, frequency2, dataFor2, orgId2, empId2, 
				measureDataOrganizationOid2, measureDataEmployeeOid2);
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj1 = chain.getResultFromResource("performanceScoreChain", context1);
		ChainResultObj resultObj2 = chain.getResultFromResource("performanceScoreChain", context2);
		fillKpiPeriodTrends(result, resultObj1, resultObj2);		
		return result;
	}

}
