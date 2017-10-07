package com.alienlab.common;
public enum DataType {
	DATA("DATA","DATA"),STATUS ("STATUS","STATUS"),MSG("MSG","MSG");
	   
    public String key;
	
	public String value;
	
	private DataType(String key,String value){
		
		this.key   = key;
		this.value = value;
	}
	

	private static String getCloumn(final String key){		
		for(DataType value :DataType.values()){
			if(value.key.equals(key)){
				return value.value;
			}
		}
		String v="";
		for(DataType value :DataType.values()){			
			v=value.value;
		}
		return v;
	}
}


