/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package org.xpup.hafmis.sysloan.querystatistics.querystatistics.loandeskaccquery.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xpup.common.util.Pagination;
import org.xpup.hafmis.sysloan.querystatistics.querystatistics.loandeskaccquery.form.LoanDeskaccQueryTbAF;

/** 
 * MyEclipse Struts
 * Creation date: 10-22-2007
 * 
 * XDoclet definition:
 * @struts.action path="/loanDeskaccQueryTbFindAC" name="loanDeskaccQueryTbAF" scope="request" validate="true"
 */
public class LoanDeskaccQueryTbFindAC extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
    LoanDeskaccQueryTbAF loanDeskaccQueryTbAF=(LoanDeskaccQueryTbAF)form;
    HashMap criterions = makeCriterionsMap(loanDeskaccQueryTbAF);
    Pagination pagination = new Pagination(0, 10, 1, " p202.biz_date ", "ASC",criterions);
    String paginationKey = LoanDeskaccQueryTbShowAC.PAGINATION_KEY;
    request.getSession().setAttribute(paginationKey, pagination);
    return mapping.findForward("loanDeskaccQueryTbShowAC");
  }

  protected HashMap makeCriterionsMap(LoanDeskaccQueryTbAF loanDeskaccQueryTbAF) {
  HashMap map = new HashMap();
  String docnum =loanDeskaccQueryTbAF.getDocnum().trim();
  if (!(docnum == null || "".equals(docnum.trim()))) {
    map.put("docnum",docnum );
  }
  String biztype =loanDeskaccQueryTbAF.getBiztype().trim();
  if (!(biztype == null || "".equals(biztype.trim()))) {
    map.put("biztype",biztype );
  }
  String bizdateB =loanDeskaccQueryTbAF.getBizdateB().trim();
  if (!(bizdateB == null || "".equals(bizdateB.trim()))) {
    map.put("bizdateB",bizdateB );
  }
  String bizdateE =loanDeskaccQueryTbAF.getBizdateE().trim();
  if (!( bizdateE== null || "".equals(bizdateE.trim()))) {
    map.put("bizdateE",bizdateE );
  }  
 
  return map;
  }
}