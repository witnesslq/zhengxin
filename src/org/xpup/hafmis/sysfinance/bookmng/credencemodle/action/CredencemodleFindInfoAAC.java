package org.xpup.hafmis.sysfinance.bookmng.credencemodle.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xpup.common.exception.BusinessException;
import org.xpup.common.util.BSUtils;
import org.xpup.hafmis.orgstrct.dto.SecurityInfo;
import org.xpup.hafmis.sysfinance.bookmng.credencemodle.bsinterface.ICredencemodleBS;
import org.xpup.hafmis.sysfinance.bookmng.credencemodle.dto.CredencemodleDTO;

public class CredencemodleFindInfoAAC extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    try {
      response.setContentType("text/html;charset=UTF-8");
      response.setHeader("Cache-Control", "no-cache");
      SecurityInfo securityInfo = (SecurityInfo) request.getSession()
          .getAttribute("SecurityInfo");
      String subjectCode = (String) request.getParameter("subjectCode");
      if (subjectCode != null && !"".equals(subjectCode.trim())) {
        subjectCode = (String) request.getParameter("subjectCode");
        request.getSession().setAttribute("subjectCode", subjectCode);
      }
      String text = null;
      CredencemodleDTO credencemodleDTO = new CredencemodleDTO();
      ICredencemodleBS credencemodleBS = (ICredencemodleBS) BSUtils
          .getBusinessService("credencemodleBS", this, mapping
              .getModuleConfig());
      credencemodleBS.findCredencemodleExist(subjectCode, securityInfo);
      credencemodleDTO = credencemodleBS.findCredencemodleInfo(subjectCode,
          securityInfo);
      text = "displays1('" + credencemodleDTO.getSubjectCode() + "','"
          + credencemodleDTO.getSubjectName() + "')";
      request.getSession().setAttribute("subjectCode",
          credencemodleDTO.getSubjectCode());
      request.getSession().setAttribute("subjectName",
          credencemodleDTO.getSubjectName());
      response.getWriter().write(text);
      response.getWriter().close();
    } catch (BusinessException e) {
      e.printStackTrace();
      String text = "backErrors('" + e.getLocalizedMessage() + "')";
      response.getWriter().write(text);
      response.getWriter().close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
