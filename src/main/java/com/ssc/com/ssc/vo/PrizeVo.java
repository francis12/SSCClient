package com.ssc.com.ssc.vo;

import java.io.Serializable;

public class PrizeVo implements Serializable{

	private String no;
	private String prize;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private String time;


}
