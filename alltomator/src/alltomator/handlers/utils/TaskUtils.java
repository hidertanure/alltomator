package alltomator.handlers.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;

import alltomator.model.AlltomatorTask;
import alltomator.model.ConfigurationGroup;

public class TaskUtils {

	private static final String PREFIXO = "AT_";

	public static List<AlltomatorTask> getTasks() {

		List<AlltomatorTask> tasks = new ArrayList<AlltomatorTask>();
		try {
			
			long id = 1;

			ILaunchManager manager = DebugPlugin.getDefault()
					.getLaunchManager();
			
			for(ConfigurationGroup group : ConfigurationGroup.getOrdered()){
				
				id = buildGroupTask(tasks, id, manager, group);
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tasks;

	}

	private static long buildGroupTask(List<AlltomatorTask> tasks, long id,
			ILaunchManager manager, ConfigurationGroup group)
			throws CoreException {
		
		List<ILaunchConfiguration> configurations = new ArrayList<ILaunchConfiguration>();
		
		for (ILaunchConfigurationType type : manager
				.getLaunchConfigurationTypes()) {
			if (group.getName().equals(type.getName())) {
				for (ILaunchConfiguration configuration : manager
						.getLaunchConfigurations(type)) {
					if (configuration.getName().startsWith(PREFIXO)) {
						configurations.add(configuration);
					}
				}
			}
		}
		
		if(!configurations.isEmpty()){
			
			Collections.sort(configurations, new Comparator<ILaunchConfiguration>() {

				@Override
				public int compare(ILaunchConfiguration o1, ILaunchConfiguration o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			
			for(ILaunchConfiguration configuration : configurations){
				
				tasks.add(new AlltomatorTask(configuration, id, group));
				
				id++;
			}
			
		}
		return id;
	}
	
}
