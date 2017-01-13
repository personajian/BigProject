
public class Capacity {
	private String model;
	private int modelNumber;
	private int index;
	public Capacity() {
		
	}
	public Capacity(String model, int modelNumber, int index) {
		this.model = model;
		this.modelNumber = modelNumber;
		this.index = index;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(int modelNumber) {
		this.modelNumber = modelNumber;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
