package org.xpup.hafmis.syscollection.querystatistics.chgbiz.chgpay.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.xpup.common.util.Pagination;



//单位的查询
public class ChgpayTbFindAAC extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    response.setContentType("text/html;charset=UTF-8");
    response.setHeader("Cache-Control", "no-cache");
    try {
//    获得单位编号

      String orgId=request.getParameter("orgId");
      String empId=request.getParameter("empId");
      String empName=request.getParameter("empName");
      empName = new String(empName.getBytes("ISO-8859-1"),"gb2312");
      
      String orgName=request.getParameter("orgName");
      orgName = new String(orgName.getBytes("ISO-8859-1"),"gb2312");
      String startChgMonth=request.getParameter("startChgMonth");
      String endChgMonth=request.getParameter("endChgMonth");
      
      String startBizDate=request.getParameter("startBizDate");
      String endBizDate=request.getParameter("endBizDate");
      String type=request.getParameter("type");
      
      
      String paginationKey = getPaginationKey();
//      Pagination pagination= (Pagination) request.getSession().getAttribute(paginationKey);
//      String temp_type = (String) pagination.getQueryCriterions().get("temp_type");
      // 付云峰修改：如果从弹出框进行查询，则从新分配pagination
//      if (temp_type!=null) {
      Pagination pagination = new Pagination(0, 10, 1, " chgPaymentTail.chgPaymentHead.org.id DESC, chgPaymentTail.empId DESC, chgPaymentTail.id ",
            "ASC", new HashMap(0));
        
//      }
      
      pagination.getQueryCriterions().put("orgId", orgId);  
      pagination.getQueryCriterions().put("empId", empId);  
      pagination.getQueryCriterions().put("empName", empName);  
      
      pagination.getQueryCriterions().put("orgName", orgName);  
      pagination.getQueryCriterions().put("startChgMonth", startChgMonth);  
      pagination.getQueryCriterions().put("endChgMonth", endChgMonth);  
      
      pagination.getQueryCriterions().put("startBizDate", startBizDate);  
      pagination.getQueryCriterions().put("endBizDate", endBizDate);  
      pagination.getQueryCriterions().put("type", type); 
      request.getSession().setAttribute(paginationKey, pagination);

      String text=null;
      
     
      text="showlist()";
      response.getWriter().write(text);
      response.getWriter().close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  protected String getForword() {
    return "chgpayTbShowAC.do";
  }
  protected String getPaginationKey() {
    return ChgpayTbShowAC.PAGINATION_KEY;
  }
}
