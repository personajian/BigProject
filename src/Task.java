import java.util.ArrayList;

/**
 * 主要是来划分不同合同号的任务
 * @author Guanglei Pan
 * @version 2.0
 * 
 */
public class Task {
	private String startTime;  //订单日期
	private String orderNo;   // 合同号
	private String salesMan;  //销售员
	private String client; //客户
	private String endDate;//交货日期
	private ArrayList<Product> require;//该合同号下，所有的不同型号、规格的Product
	
	public ArrayList<Product> getRequire() {
		return require;
	}
	public void setRequire(ArrayList<Product> require) {
		this.require = require;
	}
	public Task() {
		
	}
	public Task(String startTime, String orderNo,String salesMan, String client,String endDate, ArrayList<Product> require) {
		this.startTime = startTime;
		this.orderNo = orderNo;
		this.salesMan = salesMan;
		this.client = client;
		this.endDate = endDate;
		this.require = require;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String time) {
		this.startTime = time;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getSalesMan() {
		return salesMan;
	}
	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}
	
	public String getClient() {
		return client;
	}
	
	public void setClient(String client) {
		this.client = client;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
