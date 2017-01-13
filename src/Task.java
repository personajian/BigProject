import java.util.ArrayList;

/**
 * ��Ҫ�������ֲ�ͬ��ͬ�ŵ�����
 * @author Guanglei Pan
 * @version 2.0
 * 
 */
public class Task {
	private String startTime;  //��������
	private String orderNo;   // ��ͬ��
	private String salesMan;  //����Ա
	private String client; //�ͻ�
	private String endDate;//��������
	private ArrayList<Product> require;//�ú�ͬ���£����еĲ�ͬ�ͺš�����Product
	
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
