package com.md.demo.server.bean.entry;

public class UserAccount {

	private int id;

	private int userId;

	private double avaliableBalance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getAvaliableBalance() {
		return avaliableBalance;
	}

	public void setAvaliableBalance(double avaliableBalance) {
		this.avaliableBalance = avaliableBalance;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", userId=" + userId
				+ ", avaliableBalance=" + avaliableBalance + "]";
	}

}
