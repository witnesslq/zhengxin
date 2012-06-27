/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package org.xpup.hafmis.syscollection.pickupmng.pickup.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.xpup.common.util.BSUtils;
import org.xpup.common.util.Pagination;
import org.xpup.common.util.PaginationUtils;
import org.xpup.hafmis.common.util.BusiTools;
import org.xpup.hafmis.orgstrct.dto.OfficeDto;
import org.xpup.hafmis.orgstrct.dto.SecurityInfo;
import org.xpup.hafmis.syscollection.common.domain.entity.PickHead;
import org.xpup.hafmis.syscollection.common.domain.entity.PickTail;
import org.xpup.hafmis.syscollection.pickupmng.pickup.bsinterface.IPickupBS;
import org.xpup.hafmis.syscollection.pickupmng.pickup.dto.ApplyBookDTO;
import org.xpup.hafmis.syscollection.pickupmng.pickup.dto.Convert;
import org.xpup.hafmis.syscollection.pickupmng.pickup.form.PickupGetCompanyIdAF;

/**
 * MyEclipse Struts Creation date: 07-20-2007 XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class VindicatePickEmployeeListAction extends Action {
  public static final String EMPLOYEELIST = "org.xpup.hafmis.syscollection.pickupmng.pickup.action.VindicatePickEmployeeListAction";

  public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) {
    String url = "mx";
    try {
      IPickupBS pbs = (IPickupBS) BSUtils.getBusinessService("pickupBS", this,
          mapping.getModuleConfig());
      Pagination pag = null;
      PickupGetCompanyIdAF result = new PickupGetCompanyIdAF();
      String headId = request.getParameter("id");
      if (headId == null) {
        headId = (String) request.getSession().getAttribute("headId");
      }
      if (headId != null && headId.length() == 10
          && new Integer(headId).toString().length() == 9) {
        headId = (String) request.getSession().getAttribute("headId");
        request.getSession().setAttribute("headId", headId);
      } else {
        request.getSession().setAttribute("headId", headId);
      }
      PickHead head = new PickHead();
      String pickstatus = "";
      if (headId != null) {
        url = request.getParameter("url");
      }
      if (headId != null) {
        String sun = (String) request.getParameter("sun");
        if (sun != null && sun.equals("sun")) {
          request.getSession().removeAttribute(EMPLOYEELIST);
        }
        request.getSession().removeAttribute("orgList");
        pag = getPagination(EMPLOYEELIST, request, headId);
        head = pbs.queryById(new Integer(headId));
        pickstatus = head.getPickSatatus().toString();
        String orgName = head.getOrg().getOrgInfo().getName();
        String docNumber = request.getParameter("docNumber");
        String noteNumber = request.getParameter("noteNumber");
        pag.getQueryCriterions().put("orgName", orgName);
        pag.getQueryCriterions().put("docNumber", docNumber);
        pag.getQueryCriterions().put("noteNumber", noteNumber);
        pag.getQueryCriterions().put("orgid", head.getOrg().getId());
        request.getSession().removeAttribute("orgidCollnbank");
        request.getSession().setAttribute("orgidCollnbank",
            head.getOrg().getId().toString());
        request.getSession().setAttribute(EMPLOYEELIST, pag);
      } else {
        pag = (Pagination) request.getSession().getAttribute(EMPLOYEELIST);
      }
      PaginationUtils.updatePagination(pag, request);
      List list = pbs.findTheOrgAllEmployee(pag);// ��ò�ѯ��������
      List print = pbs.getPrintAllEmployeeList((String) pag
          .getQueryCriterions().get("idValue"));// ���ȫ��������..������ȫ��������������
      List list_yg = pbs.getPrintAllEmployeeList_yg((String) pag
          .getQueryCriterions().get("idValue"));
      String s = pbs.getPrintAllEmployeeList_yga((String) pag
          .getQueryCriterions().get("idValue"));
      PickTail pickTail_yg = (PickTail) list_yg.get(0);
      result.setSumBalance(pickTail_yg.getSumBalance());
      result.setSumInterese(pickTail_yg.getSumInterest());
      result.setSumTotal(pickTail_yg.getSumBalance().add(
          pickTail_yg.getSumInterest()));
      result.setSomePickupNumber(Integer.parseInt(s));
      result.setDistoryPickupNumber(pickTail_yg.getSumPerson()-Integer.parseInt(s));
      result.setSumPerson(pickTail_yg.getSumPerson());
      PickTail tail = (PickTail) print.get(0);
      String fOrgbank = "";
      String forgbankid = "";
      String office = "";
      String collbankname = "";
      if (tail.getPickHead().getOrg().getOrgInfo().getPayBank() != null) {
        fOrgbank = tail.getPickHead().getOrg().getOrgInfo().getPayBank()
            .getName();
        forgbankid = tail.getPickHead().getOrg().getOrgInfo().getPayBank()
            .getAccountNum();
      }
      SecurityInfo securityInfo = (SecurityInfo) request.getSession()
          .getAttribute("SecurityInfo");
      List officlist = securityInfo.getAllCenterList();
      String collBankid = tail.getPickHead().getOrg().getOrgInfo()
          .getCollectionBankId();
      if (officlist != null) {
        OfficeDto dto = (OfficeDto) officlist.get(0);
        office = dto.getOfficeName();
      }
      if (collBankid != null) {
        collbankname = pbs.findCollBank(collBankid);
      }
      if (pickstatus.equals("1")) {
        result.setButtonState("1");
      } else {
        result.setButtonState("2");
      }
      String orgId = pag.getQueryCriterions().get("orgid") + "";
      if(orgId != null)
        orgId = BusiTools.convertTenNumber(orgId);
      result.setId(orgId);
      result.setName((String) pag.getQueryCriterions().get("orgName"));
      result.setDocNumber((String) pag.getQueryCriterions().get("docNumber"));
      result.setNoteNumber((String) pag.getQueryCriterions().get("noteNumber"));
      result.setList(list);
      String str[] = new String[2];
      str = pbs.queryOfficeBankNames(head.getOrg().getId().toString(), "2",
          headId, "D", securityInfo);
      if (str[0] != null) {
        office = str[0];
      }
      if (str[1] != null) {
        collbankname = str[1];
      }
      ApplyBookDTO dto = new ApplyBookDTO();
      dto.setHeadid(headId);
      dto.setPickBalance(result.getSumBalance());
      dto.setSOrgName(result.getName());
      dto.setSOrgBank(fOrgbank);
      dto.setSOrgNumber(forgbankid);
      dto.setFOrgBank(collbankname);
      dto.setFOrgName(office);
      dto.setOrgid(result.getId());
      dto.setDocnum(result.getDocNumber());
      dto.setPickstatus(pickstatus);
      dto.setOrgid(head.getOrg().getId().toString());
      request.getSession().setAttribute("applys", dto);
      request.getSession().setAttribute("orgList", result);
      request.getSession().setAttribute("employees", list);
    } catch (Exception s) {
      s.printStackTrace();
    }
    if (url != null && !url.equals("mx")) {
      return new ActionForward("/printEmployeeAC.do");
    } else {
      return new ActionForward("/orgPickInfo.jsp");
    }
  }

  private Pagination getPagination(String paginationKey,
      HttpServletRequest request, String headId) {
    Pagination pagination = (Pagination) request.getSession().getAttribute(
        paginationKey);
    if (pagination == null) {// ��һ�ν�����ʱ��...
      pagination = new Pagination(0, 10, 1, "emp.id", "desc", new HashMap(0));
      pagination.getQueryCriterions().put("idValue", headId);
      request.getSession().setAttribute(paginationKey, pagination);
    }
    return pagination;
  }

  private void count(List list, PickupGetCompanyIdAF result) {
    // System.out.println("��λԱ���ļ���..."+list.size());
    int sumPerson = 0;
    double sumTotal = 0.00;
    double sumBalance = 0.00;
    double sumInterest = 0.00;
    int sumDistroyPick = 0;
    int sumSomePick = 0;
    if (list != null && !list.isEmpty()) {
      Iterator it = list.iterator();
      while (it.hasNext()) {// ѭ���ۼӸ��ֱ���...�����ݻ���
        PickTail t = (PickTail) it.next();
        sumBalance = sumBalance + t.getPickCurBalance().doubleValue()
            + t.getPickPreBalance().doubleValue();
        sumInterest = sumInterest + t.getPickCurInterest().doubleValue()
            + t.getPickPreInterest().doubleValue();
        if (t.getPickType().intValue() == 1)// ������ȡ
          sumSomePick++;
        if (t.getPickType().intValue() == 2)// ������ȡ
          sumDistroyPick++;

      }
      sumPerson = sumSomePick + sumDistroyPick;
      sumTotal = sumBalance + sumInterest;
      // �ѻ��ܵ����ݷ���Form��������...������ʾ����
      result.setSomePickupNumber(sumSomePick);
      result.setDistoryPickupNumber(sumDistroyPick);
      result.setSumPerson(sumPerson);
      String balance = new Double(sumBalance).toString();
      String interest = new Double(sumInterest).toString();
      String sum = new Double(sumTotal).toString();
      result.setSumBalance(new BigDecimal(Convert.getTwoDouble(balance)));
      result.setSumInterese(new BigDecimal(Convert.getTwoDouble(interest)));
      result.setSumTotal(new BigDecimal(Convert.getTwoDouble(sum)));
    }
  }
}