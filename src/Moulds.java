
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 模具资源数量以及模具匹配方法
 * @author Fuchao Chen
 * @version 2.0
 */
public class Moulds {
	private String type;
	private String configure;
	public  int[] A1capcity={40, 20, 8, 4};
	public  int[] C2capcity={10, 4};
	public  int C3capcity = 26;
	public  int[] E1capcity={21, 4};
	public  int[] E2capcity={18, 8};
	public  int[] E3capcity={4, 2};
	public Moulds() {
		
	}
	public Moulds(String type, String configure) {
		this.type=type;
		this.configure=configure;
		
	}
	/**
	 * 
	 * @param number
	 * @return 字符串下标含义：
	 *  0:型号
	 *  1：型号数量
	 *  2：下标
	 */ 
	public ArrayList<Capacity> match(int number){
		String[] s = configure.split(",");
		String s1 = s[0];
		String s2 = s[2];
		
		String[] temp=s1.split("[,/-]");
		int a=Integer.parseInt(temp[0]);
		for(String ss: temp){
			if(a < Integer.parseInt(ss))
				a=Integer.parseInt(ss);
		}
		ArrayList<Capacity> result = new ArrayList<Capacity>();
		if(type.equals("A1")){
			Matcher m1 = Pattern.compile("(\\d+)-(\\d+)").matcher(s2),
					m2 = Pattern.compile("(\\d+)-(\\d+)-(\\d+)").matcher(s2),
					m3 = Pattern.compile("(\\d+)/(\\d+)").matcher(s2);
			boolean b = m1.lookingAt()||m2.lookingAt()||m3.lookingAt();
			if(b){
				if(a >= 5 && a <= 800) {
					if(A1capcity[0] > 0) {
						if(A1capcity[0] >= number) {
							A1capcity[0] -= number;
							Capacity capacity = new Capacity("A1", number, 0);
							result.add(capacity);
							return result;
						} else {
							int t = A1capcity[0];
							A1capcity[0] = 0;
							Capacity capacity = new Capacity("A1", t, 0);
							result.add(capacity);
							return result;
						}
					} else {
						return result;
					}
				} else {
					if(A1capcity[2] > 0) {
						if(A1capcity[2] >= number) {
							A1capcity[2] -= number;
							Capacity capacity = new Capacity("A1", number, 2);
							result.add(capacity);
							return result;
						} else {
							int t = A1capcity[2];
							A1capcity[2] = 0;
							Capacity capacity = new Capacity("A1", t, 2);
							result.add(capacity);
							return result;
						}
					} else {
						return result;
					}
				}
					
			}else{
				if(a >= 5 && a <= 800){
					if(A1capcity[1] > 0) {
						if(A1capcity[1] >= number) {
							A1capcity[1] -= number;
							Capacity capacity = new Capacity("A1", number, 1);
							result.add(capacity);
							return result;
						} else {
							int t = A1capcity[1];
							A1capcity[1] = 0;
							Capacity capacity = new Capacity("A1", t, 1);
							result.add(capacity);
							return result;
						}
					} else {
						return result;
					}
				} else {
					if(A1capcity[3] > 0) {
						if(A1capcity[3] >= number) {
							A1capcity[3] -= number;
							Capacity capacity = new Capacity("A1", number, 3);
							result.add(capacity);
							return result;
						} else {
							int t = A1capcity[3];
							A1capcity[3] = 0;
							Capacity capacity = new Capacity("A1", t, 3);
							result.add(capacity);
							return result;
						}
					} else {
						return result;
					}
				}
			}
		}else if(type.equals("C2")){
			if(a >= 5 && a <= 800){
				if(C2capcity[0]+C2capcity[1] > 0) {
					//C2capcity[0] + C2capcity[1] -= number;
					if(C2capcity[0] + C2capcity[1] < number) {
						Capacity capacity1 = new Capacity("C2", C2capcity[0], 0);
						result.add(capacity1);
						Capacity capacity2 = new Capacity("C2", C2capcity[1], 1);
						result.add(capacity2);
						C2capcity[0] = 0;
						C2capcity[1] = 0;
						return result;
					} else {
						if(C2capcity[0] >= number) {
							C2capcity[0] -= number;
							Capacity capacity = new Capacity("C2", number, 0);
							result.add(capacity);
						} else {						
							C2capcity[1] -= (number - C2capcity[0]);
							Capacity capacity1 = new Capacity("C2", C2capcity[0], 0);
							result.add(capacity1);
							Capacity capacity2 = new Capacity("C2", (number - C2capcity[0]), 1);
							result.add(capacity2);
							C2capcity[0] = 0;
						}
						return result;
					}
				} else {
					return result;
				}
			}else {
				if(C2capcity[1] > 0) {
					if(C2capcity[1] >= number) {
						C2capcity[1] -= number;
						Capacity capacity = new Capacity("C2", number, 1);
						result.add(capacity);
						return result;
					} else {
						int t = C2capcity[1];
						C2capcity[1] = 0;
						Capacity capacity = new Capacity("C2", t, 1);
						result.add(capacity);
						return result;
					}
				} else {
					return result;
				}
			}
		}else if(type.equals("C3")){
			if(C3capcity > 0) {
				if(C3capcity >= number) {
					C3capcity -= number;
					Capacity capacity = new Capacity("C3", number, 0);
					result.add(capacity);
					return result;
				} else {
					int t = C3capcity;
					C3capcity = 0;
					Capacity capacity = new Capacity("C3", t, 0);
					result.add(capacity);
					return result;
				}
			} else {
				return result;
			}
		}else if(type.equals("E1")){
			if(a == 4000){
				if(E1capcity[1] > 0) {
					if(E1capcity[1] >= number) {
						E1capcity[1] -= number;
						Capacity capacity = new Capacity("E1", number, 1);
						result.add(capacity);
						return result;
					} else {
						int t = E1capcity[1];
						E1capcity[1] = 0;
						Capacity capacity = new Capacity("E1", t, 1);
						result.add(capacity);
						return result;
					}
				} else {
					return result;
				}
			}else {
				if(E1capcity[0]+E1capcity[1] > 0) {
					if(E1capcity[0] + E1capcity[1] < number) {			
						Capacity capacity1 = new Capacity("E1", E1capcity[0], 0);
						result.add(capacity1);
						Capacity capacity2 = new Capacity("E1", E1capcity[1], 1);
						result.add(capacity2);
						E1capcity[0] = 0;
						E1capcity[1] = 0;
						return result;
					} else {
						if(E1capcity[0] >= number) {
							E1capcity[0] -= number;
							Capacity capacity = new Capacity("E1", number, 0);
							result.add(capacity);
						} else {						
							E1capcity[1] -= (number - E1capcity[0]);
							Capacity capacity1 = new Capacity("E1", E1capcity[0], 0);
							result.add(capacity1);
							Capacity capacity2 = new Capacity("E1", number - E1capcity[0], 1);
							result.add(capacity2);
							E1capcity[0] = 0;
						}
						return result;
					}
				} else {
					return result;
				}
			}
		}else if(type.equals("E2")){
			if(a == 4000){
				if(E2capcity[1] > 0) {
					if(E2capcity[1] >= number) {
						E2capcity[1] -= number;
						Capacity capacity = new Capacity("E2", number, 1);
						result.add(capacity);
						return result;
					} else {
						int t = E2capcity[1];
						E2capcity[1] = 0;
						Capacity capacity = new Capacity("E2", t, 1);
						result.add(capacity);
						return result;
					}
				} else {
					return result;
				}
			}else {
			
				if(E2capcity[0]+E2capcity[1] > 0) {
					if(E2capcity[0] + E2capcity[1] < number) {
						Capacity capacity1 = new Capacity("E2", E2capcity[0], 0);
						result.add(capacity1);
						Capacity capacity2 = new Capacity("E2", E2capcity[1], 1);
						result.add(capacity2);
						E2capcity[0] = 0;
						E2capcity[1] = 0;
						return result;
					} else {
						if(E2capcity[0] >= number) {
							E2capcity[0] -= number;
							Capacity capacity = new Capacity("E2", number, 0);
							result.add(capacity);
						} else {							
							E2capcity[1] -= (number - E2capcity[0]);
							Capacity capacity1 = new Capacity("E2", E2capcity[0], 0);
							result.add(capacity1);
							Capacity capacity2 = new Capacity("E2", number - E2capcity[0], 1);
							result.add(capacity2);
							E2capcity[0] = 0;
						}
						return result;
					}
				} else {
					return result;
				}
			}
		}else{
			if(a == 4000){
				if(E3capcity[1] > 0) {
					if(E3capcity[1] >= number) {
						E3capcity[1] -= number;
						Capacity capacity = new Capacity("E3", number, 1);
						result.add(capacity);
						return result;
					} else {
						int t = E3capcity[1];
						E3capcity[1] = 0;
						Capacity capacity = new Capacity("E3", t, 1);
						result.add(capacity);
						return result;
					}
				} else {
					return result;
				}
			}else {
				if(E3capcity[0]+E3capcity[1] > 0) {
					//C2capcity[0] + C2capcity[1] -= number;
					if(E3capcity[0] + E3capcity[1] < number) {
						Capacity capacity1 = new Capacity("E3", E3capcity[0], 0);
						result.add(capacity1);
						Capacity capacity2 = new Capacity("E3", E3capcity[1], 1);
						result.add(capacity2);
						E3capcity[0] = 0;
						E3capcity[1] = 0;
						return result;
					} else {
						if(E3capcity[0] >= number) {
							E3capcity[0] -= number;
							Capacity capacity = new Capacity("E3", E3capcity[0], 0);
							result.add(capacity);
						} else {							
							E3capcity[1] -= (number - E3capcity[0]);
							Capacity capacity1 = new Capacity("E3", E3capcity[0], 0);
							result.add(capacity1);
							Capacity capacity2 = new Capacity("E3", number - E3capcity[0], 1);
							result.add(capacity2);
							E3capcity[0] = 0;
						}
						return result;
					}
				} else {
					return result;
				}
			}
		}
		
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setConfigure(String configure) {
		this.configure = configure;
	}

	public void setA1capcity(int[] a1capcity) {
		A1capcity = a1capcity;
	}

	public void setC2capcity(int[] c2capcity) {
		C2capcity = c2capcity;
	}

	public void setC3capcity(int c3capcity) {
		C3capcity = c3capcity;
	}

	public void setE1capcity(int[] e1capcity) {
		E1capcity = e1capcity;
	}

	public void setE2capcity(int[] e2capcity) {
		E2capcity = e2capcity;
	}

	public void setE3capcity(int[] e3capcity) {
		E3capcity = e3capcity;
	}
	
	public int[] getA1capcity() {
		return A1capcity;
	}

	public int[] getC2capcity() {
		return C2capcity;
	}

	public int getC3capcity() {
		return C3capcity;
	}

	public int[] getE1capcity() {
		return E1capcity;
	}

	public int[] getE2capcity() {
		return E2capcity;
	}

	public int[] getE3capcity() {
		return E3capcity;
	}


}
