package alltomator.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import alltomator.model.ConfigurationGroup;

public class AlltomatorHelpHandler extends AbstractHandler {
	
	private static final String PREFIXO = "AT_";
	
	public AlltomatorHelpHandler(){
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		StringBuilder b = new StringBuilder();
		b.append("Basicamente, roda as paradas abaixo configuradas com prefixo ["+PREFIXO+"1..n]\r\n");
		b.append("\r\n");
		for(ConfigurationGroup group : ConfigurationGroup.getOrdered()){
			b.append("Task do tipo: ").append(group.getName()).append(", ");
			b.append("Sequencial: ").append(group.isNeedWait()).append(", ");
			b.append("Precisa de Clean/Build antes: ").append(group.isNeedCleanBuildBefore()).append(" ");
			b.append("\r\n");
			b.append("\r\n");
		}
		
		b.append("Sacou?");
		
		MessageDialog.openInformation(
				window.getShell(),
				"Alltomator - Ajuda o maluco!",
				b.toString());
		
		return null;
	}

}
