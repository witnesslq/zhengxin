package org.xpup.hafmis.sysloan.querystatistics.loanaccountquery.principalgl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xpup.common.util.BSUtils;
import org.xpup.hafmis.orgstrct.dto.SecurityInfo;
import org.xpup.hafmis.sysloan.dataready.loanpara.bsinterface.ILoanDocNumDesignBS;

/**
 * @author yuqf 2007-10-19
 */
public class PrincipalglTaPrintAC extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    try {
      request.setAttribute("URL", "principalglTaShowAC.do");
      SecurityInfo securityInfo = (SecurityInfo) request.getSession()
          .getAttribute("SecurityInfo");
      //String userName = securityInfo.getRealName();
      ILoanDocNumDesignBS loanDocNumDesignBS = (ILoanDocNumDesignBS) BSUtils
      .getBusinessService("sysloanloanDocNumDesignBS", this, mapping.getModuleConfig());
      String userName="";
      try {
        String name = loanDocNumDesignBS.getNamePara();

        if (name.equals("1")) {
          userName = securityInfo.getUserName();
        } else {
          userName = securityInfo.getRealName();
        }

      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      String plbizDate = securityInfo.getUserInfo().getPlbizDate();
      request.setAttribute("userName", userName);
      request.setAttribute("plbizDate", plbizDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mapping.findForward("to_principalglTa_cell");
  }

}
