package alltomator.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class AlltomatorHandler extends AbstractHandler {

	public AlltomatorHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		//IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		AlltomatorLaunchRunner runner = new AlltomatorLaunchRunner();

		//window.getWorkbench().getProgressService().showInDialog(window.getShell(), runner);
		
		runner.setUser(true);
		runner.schedule();
		
		return null;
	}
	
}
