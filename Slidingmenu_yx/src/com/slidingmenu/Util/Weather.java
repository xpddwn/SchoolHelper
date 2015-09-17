package com.slidingmenu.Util;

public class Weather {
	private String statedetail;
	private String lowTem;
	private String highTem;
	
	public void setstatedetail(String statedetail){
		this.statedetail = statedetail;
	}
	
	public String getstatedetail(){
		return statedetail;
	}
	
	public void setlowTem(String lowTem){
		this.lowTem = lowTem;
	}
	
	public String getlowTem()
	{
		return lowTem;
	}
	
	public void sethighTem(String highTem){
		this.highTem = highTem;
	}
	
	public String gethighTem(){
		return highTem;
	}

}
