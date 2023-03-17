package cz.tmobile.conf.api.data;

public class Department {

	private Long depId;
	private String name;
	
	protected Department() {
		super();
	}

	public Department(long depId) {
		super();
		this.depId = depId;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
