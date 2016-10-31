/**
 *TODO
 *
 *
 */
package com.matao.bean;

import java.io.Serializable;

/**
 * @author: ZhouYang
 * @time:2015-5-27 上午10:58:55
 * @Description:表情实体
 */

public class BQ implements Serializable {
	private int id;
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "BQ [id=" + id + ", text=" + text + "]";
	}

}
