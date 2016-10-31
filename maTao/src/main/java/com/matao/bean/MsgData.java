package com.matao.bean; 

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com  
 * @version: 创建时间：2015-7-13 上午9:52:38 
 * @Description: 
 */
public class MsgData implements Serializable{
 private int Replys;
 private int Likes;
 private int Notices;
 private int Total;
public int getReplys() {
	return Replys;
}
public void setReplys(int replys) {
	Replys = replys;
}
public int getLikes() {
	return Likes;
}
public void setLikes(int likes) {
	Likes = likes;
}
public int getNotices() {
	return Notices;
}
public void setNotices(int notices) {
	Notices = notices;
}
public int getTotal() {
	return Total;
}
public void setTotal(int total) {
	Total = total;
}
@Override
public String toString() {
	return "MsgData [Replys=" + Replys + ", Likes=" + Likes + ", Notices="
			+ Notices + ", Total=" + Total + "]";
}
}
 