package alltomator.model;

import org.eclipse.debug.core.ILaunchConfiguration;

public class AlltomatorTask {
	
	private final Long id;
	private final ILaunchConfiguration configuration;
	private final ConfigurationGroup group;
	
	public AlltomatorTask(ILaunchConfiguration configuration, Long id, ConfigurationGroup group) {
		super();
		this.configuration = configuration;
		this.id = id;
		this.group = group;
	}
	
	public String getName(){
		return this.configuration.getName();
	}
	
	public Long getId(){
		return this.id;
	}
	
	public ConfigurationGroup getGroup() {
		return group;
	}
	
	public ILaunchConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlltomatorTask other = (AlltomatorTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
