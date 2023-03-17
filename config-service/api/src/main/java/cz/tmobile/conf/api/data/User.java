package cz.tmobile.conf.api.data;

import java.time.LocalDateTime;

public class User extends UserInfo {
	private long age;
	private LocalDateTime validFrom;
	private Department department;

	public User(long personalId) {
		super(personalId);
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public LocalDateTime getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
