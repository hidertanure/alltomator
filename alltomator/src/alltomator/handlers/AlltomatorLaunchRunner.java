package alltomator.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.ServerCore;

import alltomator.handlers.utils.TaskUtils;
import alltomator.model.AlltomatorTask;

public class AlltomatorLaunchRunner extends Job {
	
	private static final String[] MESSAGES = new String[]{
			"Agora vai em!",
			"Opa, tem mais uma...",
			"Rapaz... Quantas tasks?",
			"Da pra tomar um café fi?",
			"Ta doido, trabalha não?",
			"Ta acabando?",
			"Já perdi a paciência..."
	};
	
	private List<AlltomatorTask> tasks = new ArrayList<AlltomatorTask>();
	private List<String> errors = new ArrayList<String>();
	
	public AlltomatorLaunchRunner(){
		super("Alltomator");
	}
	
	public void launch(IProgressMonitor monitor) throws CoreException{
		
		this.tasks = TaskUtils.getTasks();
		monitor.beginTask("Alltomator ta rodando rapá!", tasks.size());
		
		monitor.subTask("Opa, bora lá...  "+"- Dando aquele refresh...");
		this.refreshAll();

		int done = 0;
		String message = MESSAGES[MESSAGES.length-1];
		for(AlltomatorTask task : tasks){
			
			try{
			
				if(task.getGroup().isNeedCleanBuildBefore()){
					if(done > 0){
						monitor.subTask(task.getName()+"...  "+"- Dando aquele refresh novamente...");
						this.refreshAll();
					}
					monitor.subTask(task.getName()+"...  "+"- Rodando um buildizinho maroto antes...");
					this.buildAll();
				}
				
				if(done < MESSAGES.length){
					message = MESSAGES[done];
				}
				monitor.subTask(task.getName()+"...  "+"- "+message);
				
				ILaunch launched = DebugUITools.buildAndLaunch(task.getConfiguration(), ILaunchManager.DEBUG_MODE, new NullProgressMonitor());
				
				if(task.getGroup().isNeedWait()){
					waitLaunched(monitor, launched);
				}
				
				if(checkMonitorCancel(monitor, launched)){
					launched.terminate();
					monitor.done();
					return;
				}
			
			}catch(Exception ex){
				this.errors.add(task.getName() + " - " + ex.getMessage());
			}
			
			monitor.worked(1);
			done++;
		}
		
		if(this.errors.isEmpty()){
			monitor.subTask("Acho que foi em!");
		}else{
			monitor.subTask("Acho que deu ruim em! =/");
		}
		
		this.sleep(10000);
		
		monitor.done();
		
	}
	
	private void refreshAll(){
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IWorkspaceRoot.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
			this.errors.add(e.getMessage());
		}
	}

	private void buildAll() {
		try {
			ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
			for(IServer server : ServerCore.getServers()){
				server.publish(IServer.PUBLISH_CLEAN, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			e.printStackTrace();
			this.errors.add(e.getMessage());
		}
	}

	private void waitLaunched(IProgressMonitor monitor, ILaunch launched)
			throws DebugException {
		while(!launched.isTerminated()){
			this.sleep(10);
			if(checkMonitorCancel(monitor, launched)){
				return;
			}
		}
	}

	private boolean checkMonitorCancel(IProgressMonitor monitor,
			ILaunch launched) throws DebugException {

		if (monitor.isCanceled()) {
			return true;
		}
		
		return false;
	}
	
	private void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IStatus run(IProgressMonitor arg0) {
		
		try {
			this.launch(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			this.errors.add(e.getMessage());
		}
		
		if(!this.errors.isEmpty()){
			
			StringBuilder b = new StringBuilder();
			for(String e : this.errors){
				b.append(e).append("\r\n");
			}
			
			throw new RuntimeException("\r\nDeu ruim nessas paradas ai...\r\n\r\n"+b.toString());
			
		}
		
		return Status.OK_STATUS;
		
	}
	
	public List<String> getErrors() {
		return errors;
	}

}
