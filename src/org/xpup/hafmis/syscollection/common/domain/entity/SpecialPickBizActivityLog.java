package org.xpup.hafmis.syscollection.common.domain.entity;

/**
 * 业务活动日志-特殊提取I
 * 
 *@author 李娟
 *2007-6-20
 */
public class SpecialPickBizActivityLog extends BizActivityLog{
  
  private static final long serialVersionUID = -3745806964079944938L;
  
    /** persistent field */
    private String type;
  
    public String getType() {
      return "特殊提取";
    }
}