package alltomator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum ConfigurationGroup {
	
	MAVEN_BUILD(1, true, "Maven Build", false),
	TOMCAT(2, false, "Apache Tomcat", true);
	
	private int order;
	private boolean needWait;
	private boolean needCleanBuildBefore;
	private String name;
	
	private ConfigurationGroup(int order, boolean needWait, String name, boolean needCleanBuildBefore){
		this.order = order;
		this.needWait = needWait;
		this.name = name;
		this.needCleanBuildBefore = needCleanBuildBefore;
	}
	
	public int getOrder() {
		return order;
	}
	
	public boolean isNeedWait() {
		return needWait;
	}
	
	public boolean isNeedCleanBuildBefore() {
		return needCleanBuildBefore;
	}
	
	public String getName() {
		return name;
	}
	
	public static List<ConfigurationGroup> getOrdered(){
		List<ConfigurationGroup> orderedList = new ArrayList<ConfigurationGroup>();
		orderedList.addAll(Arrays.asList(ConfigurationGroup.values()));
		Collections.sort(orderedList, new Comparator<ConfigurationGroup>() {

			@Override
			public int compare(ConfigurationGroup o1, ConfigurationGroup o2) {
				return Integer.valueOf(o1.order).compareTo(Integer.valueOf(o2.order));
			}
		});
		return orderedList;
	}

}
