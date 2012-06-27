/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package org.xpup.hafmis.syscollection.pickupmng.pickup.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xpup.common.util.BSUtils;
import org.xpup.common.util.Pagination;
import org.xpup.common.util.PaginationUtils;
import org.xpup.hafmis.common.util.BusiConst;
import org.xpup.hafmis.common.util.BusiTools;
import org.xpup.hafmis.orgstrct.dto.SecurityInfo;
import org.xpup.hafmis.syscollection.common.domain.entity.PickHead;
import org.xpup.hafmis.syscollection.common.domain.entity.PickTail;
import org.xpup.hafmis.syscollection.pickupmng.pickup.bsinterface.IPickupBS;
import org.xpup.hafmis.syscollection.pickupmng.pickup.dto.ApplyBookDTO;
import org.xpup.hafmis.syscollection.pickupmng.pickup.dto.OrgSearchConditionDTO;
import org.xpup.hafmis.syscollection.pickupmng.pickup.form.NameAF;
import org.xpup.hafmis.syscollection.pickupmng.pickup.form.VindicateListAF;
import org.xpup.hafmis.syscollection.systemmaintain.loanpara.bsinterface.ILoanDocNumDesignBS;
public class VindicateListShowAC extends Action {
  public static final String VINDICATE = "org.xpup.hafmis.syscollection.pickupmng.pickup.action.VindicateListShowAC";
  public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) {
    String flag="";
    int flag_count=0;
    try{
      VindicateListAF submitForm = (VindicateListAF) form;// TODO Auto-generated method stub
      VindicateListAF result = new VindicateListAF();// TODO Auto-generated method stub
      IPickupBS pbs = (IPickupBS)BSUtils.getBusinessService("pickupBS",this,mapping.getModuleConfig());
      Pagination pa = null;
      if(submitForm.getSearch()!=null){//�����ѯ��ť��ʱ��...������������
        request.getSession().removeAttribute(VINDICATE);//������˲�ѯ��ť..��ô�������²�ѯ..�������Session
        pa = getPagination(VINDICATE, request);//���»��һ��Pagination����...
        pa.getQueryCriterions().put("orgId", submitForm.getDto().getOrgId());
        pa.getQueryCriterions().put("orgName", submitForm.getDto().getOrgName());
        pa.getQueryCriterions().put("noteNumber", submitForm.getDto().getNoteNumber());
        pa.getQueryCriterions().put("docNumber", submitForm.getDto().getDocNumber());
        pa.getQueryCriterions().put("start", submitForm.getDto().getStart());
        pa.getQueryCriterions().put("end", submitForm.getDto().getEnd());
        pa.getQueryCriterions().put("pickDate", submitForm.getDto().getPickDate());
        pa.getQueryCriterions().put("pickDate_end", submitForm.getDto().getPickDate_end());
        pa.getQueryCriterions().put("state", submitForm.getDto().getState());
        pa.getQueryCriterions().put("reason", submitForm.getDto().getReason());
        pa.getQueryCriterions().put("select", "����Ա���˲�ѯ��ť");
      }else{//����ǵ�һ�ν���
        pa = this.getPagination(VINDICATE, request);
      }
      Map busiState = BusiTools.listBusiProperty(BusiConst.BUSINESSSTATE);
      Map reason = BusiTools.listBusiProperty(BusiConst.PICKUPREASON);
      pa = (Pagination)request.getSession().getAttribute(VINDICATE);//������ʲôʱ���������ط�������ֵ��...
      PaginationUtils.updatePagination(pa, request);
      //�������õ�����͡�����
      result.setBusiState(busiState);
      result.setReason(reason);
      SecurityInfo sInfo = (SecurityInfo)request.getSession().getAttribute("SecurityInfo");
      int type=sInfo.getIsOrgEdition();
      request.setAttribute("type", type+"");
      List list =pbs.getVindicatePageData(pa,sInfo);//��ñ�ҳ������ --- ��Ȩ�� ---
      List count =pbs.getVindicateAllDate(pa,sInfo);//��û��ܵļ��� --- ��Ȩ�� ---
      List taillist = new ArrayList();
      List templist = new ArrayList();
      
      if(count!=null && !count.isEmpty()){
        Iterator it = count.iterator();
        while(it.hasNext()){//��ȡΪ����ֵ
          PickHead h = (PickHead)it.next();
          taillist=pbs.querytailbyheadid(h.getId().toString(), sInfo);
           for (int i = 0; i < taillist.size(); i++) {
            PickTail tail = (PickTail)taillist.get(i);
            templist.add(tail);
          }
        }
       }
      count(count,result,templist);
      flag=(String)request.getSession().getAttribute("print_list_tq");
      result.setList(list);
      if(list.isEmpty()){
//        System.out.println("���ݿ�����û�м�¼");
      }else{
        PickHead head = (PickHead)list.get(0);
        if(head.getPickSatatus().toString().equals("1")){
          result.setButtonState("1");
        }
        else if(head.getPickSatatus().toString().equals("3")){
          result.setButtonState("3");
        }
        else{
            result.setButtonState("other");
        }
      }
      //saveDisplayValue(pa, result);������������ò�ѯ��������ʾ���������,����ҵ��Ҫ��Ҫ��ʾ...
      request.getSession().setAttribute("info",result);
      request.getSession().setAttribute("print_list",count);
      
      if(flag!=null&&flag.equals("print_list_tq")){
        request.getSession().removeAttribute("print_list_tq");
        flag_count=1;
        //SecurityInfo securityInfo=(SecurityInfo)request.getSession().getAttribute("securityInfo"); 
        ILoanDocNumDesignBS loanDocNumDesignBS = (ILoanDocNumDesignBS) BSUtils.getBusinessService
        ("loanDocNumDesignBS", this, mapping.getModuleConfig());
        String date = "";
        ApplyBookDTO apply = new ApplyBookDTO();//��ȡƾ֤..
        PickHead pick=null;
        String collbankname="";
        String userName="";
        Date bizdate=BusiTools.stringToUDate(sInfo.getUserInfo().getBizDate(), "yyyyMMdd");
        date = BusiTools.dateToString(bizdate, "yyyy-MM-dd");
        if(count.size()>0){
          pick = (PickHead) count.get(0);
         }
        String str[]=new String [2];
        str= pbs.queryOfficeBankNames(pick.getOrg().getId().toString(), "2",pick.getId().toString(), 
"D", sInfo);

        if(str[1]!=null){
          collbankname=str[1];
        }     
        String name = loanDocNumDesignBS.getNamePara();

        if (name.equals("1")) {
          userName = sInfo.getUserName();
        } else {
          userName = sInfo.getRealName();
        }
      
        apply.setFOrgBank(collbankname);//���λ(���Ĺ鼯����)
//        apply.setPickBalance(pick.getPickBalance().add(pick.getDistroyInterest()).divide
//        (new BigDecimal(1),2,BigDecimal.ROUND_HALF_DOWN));//����ת������λС��
        apply.setBizdate(date);
        apply.setOperater(userName);
        
        request.setAttribute("apply", apply);
       request.setAttribute("date", date);
       request.getSession().setAttribute("list",count);
       request.setAttribute("PRINT", "vindicateListShowAC.do");
        
        return new ActionForward("/print_list_tq.jsp");
       
       }
    }catch(Exception s){
      s.printStackTrace();
    }
    if(flag_count>0){
      return new ActionForward("/print_list_tq.jsp");
    }
    else{
    return new ActionForward("/pickup_vindicatelist.jsp");
    }
  }
  private Pagination getPagination(String paginationKey,
      HttpServletRequest request) {
    Pagination pagination = (Pagination) request.getSession().getAttribute(
        paginationKey);
    if (pagination == null) {//��һ�ν�����ʱ��...
      pagination = new Pagination(0,10,1,"p.id","desc",new HashMap(0));
      request.getSession().setAttribute(paginationKey, pagination);
    }
    return pagination;
  }
  private void saveDisplayValue(Pagination pa,VindicateListAF af){
    String orgId = (String)pa.getQueryCriterions().get("orgId");
    String orgName = (String)pa.getQueryCriterions().get("orgName");
    String noteNumber = (String)pa.getQueryCriterions().get("noteNumber");
    String docNumber = (String)pa.getQueryCriterions().get("docNumber");
    String start = (String)pa.getQueryCriterions().get("start");
    String end= (String)pa.getQueryCriterions().get("end");
    String pickDate = (String)pa.getQueryCriterions().get("pickDate");
    String state = (String)pa.getQueryCriterions().get("state");
    String pickType = (String) pa.getQueryCriterions().get("pickType");
    String select = (String)pa.getQueryCriterions().get("select");
    String orderBy = (String)pa.getOrderBy();
    String order = (String)pa.getOrderother();
    OrgSearchConditionDTO search = af.getDto();
    search.setSelect(select);
    search.setOrgId(orgId);
    search.setOrgName(orgName);
    search.setNoteNumber(noteNumber);
    search.setDocNumber(docNumber);
    search.setStart(start);
    search.setEnd(end);
    search.setPickDate(pickDate);
    search.setState(state);
    search.setPickDate(pickDate);
  }
  private void count(List list,VindicateListAF result,List taillist){
  
    int sumPerson = 0;
    int countmon = 0;
    int countcls = 0;
    int countrest = 0;
    BigDecimal sumTotal = new BigDecimal(0.00);
    BigDecimal sumBalance = new BigDecimal(0.00);
    BigDecimal sumInterest = new BigDecimal(0.00);
    BigDecimal moneymon = new BigDecimal(0.00);
    BigDecimal moneycls = new BigDecimal(0.00);
    BigDecimal moneyrest = new BigDecimal(0.00);
    
    int count_xh=0;
    BigDecimal sum_xh = new BigDecimal(0.00);
    
    if(list!=null && !list.isEmpty()){
      Iterator it = list.iterator();
      while(it.hasNext()){//ѭ���ۼӸ��ֱ���...�����ݻ���
        PickHead h = (PickHead)it.next();
        sumPerson = sumPerson + h.getPickPersonCount().intValue();
        sumTotal = sumTotal.add(h.getPickMoneyCount());
        sumBalance = sumBalance.add(h.getPickBalance());
        sumInterest = sumInterest.add(h.getDistroyInterest());
      }
    if(taillist!=null&& !taillist.isEmpty()){
      Iterator it1 = taillist.iterator();
      while(it1.hasNext()){
        PickTail t=(PickTail)it1.next();
        if(t.getPickReason()!=null){
        if(t.getPickReason().equals(new BigDecimal(2))){
          countmon++;
          moneymon = moneymon.add(t.getTotal());
        }else{
          if(t.getPickReason().equals(new BigDecimal(3))){
            countcls++;
            moneycls = moneycls.add(t.getTotal());
          }else{
            if(t.getPickReason().equals(new BigDecimal(1))
                ||t.getPickReason().equals(new BigDecimal(4))
                ||t.getPickReason().equals(new BigDecimal(5))
                ||t.getPickReason().equals(new BigDecimal(6))){
              countrest++;
              moneyrest = moneyrest.add(t.getTotal());
            }
            else{
              //������ȡ�ϼ�:
              count_xh++;
              sum_xh=sum_xh.add(t.getPickCurBalance()).add(t.getPickPreBalance());
            }
          }
        }
       }
      }
    }
    
      //�ѻ��ܵ����ݷ���Form��������...������ʾ����
      result.setSumPerson(sumPerson);
      result.setSumBalance(sumBalance);
      result.setSumInterest(sumInterest);
      result.setSumTotal(sumTotal);
      
      result.setCountmon(countmon);
      result.setMoneymon(moneymon);
      
      result.setMoneycls(moneycls);
      result.setCountcls(countcls);
      
      result.setCountrest(countrest);
      result.setMoneyrest(moneyrest);
      
      
      result.setSumPerson(result.getSumPerson()-result.getCountmon()-result.getCountcls());
      BigDecimal money=result.getMoneycls().add(result.getMoneymon());
      result.setSumBalance(result.getSumBalance().subtract(money));
      result.setSumTotal(result.getSumTotal().subtract(money));
      result.setCount_xh(count_xh);
      result.setSum_xh(sum_xh);
    }
  }
}