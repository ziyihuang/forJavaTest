package pangxiong;


import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author linch
 */
//@Entity
//@Table(name = "db_odds_config")
public class OddsConfig   {

	private static final long serialVersionUID = 1L;
	/*档次类型*/
//	@NotNull	 
	private Integer gradeType;
	/*中奖金额*/
//	@NotNull	 
	private BigDecimal money;
	/*中奖概率*/
//	@NotNull	 
	private Integer odds;
	/*创建时间*/
	private Date createTime;

	public Integer getGradeType(){
		return gradeType;
	}
	
	public void setGradeType(Integer gradeType){
		this.gradeType=gradeType;
	}
	public BigDecimal getMoney(){
		return money;
	}
	
	public void setMoney(BigDecimal money){
		this.money=money;
	}
	
 	public Integer getOdds() {
		return odds;
	}

	public void setOdds(Integer odds) {
		this.odds = odds;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
}
