package com.hanium.AguideR;


public class Comment_Item {
	private String content, nickname;
	public Comment_Item(){
		this.content = null;
		this.nickname = null;
	}
	public void setContent(String c){
		this.content = c;
	}
	public String getContent(){
		return this.content;
	}
	public void setNickname(String c){
		this.nickname = c;
	}
	public String getNickname(){
		return this.nickname;
	}
	
}
