package org.docear.plugin.pdfutilities.actions;

import java.awt.event.ActionEvent;
import java.net.URI;

import javax.swing.JFileChooser;

import org.docear.plugin.pdfutilities.util.NodeUtils;
import org.freeplane.core.ui.EnabledAction;
import org.freeplane.core.util.TextUtils;
import org.freeplane.features.link.mindmapmode.MLinkController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;

@EnabledAction( checkOnNodeChange = true )
public class AddMonitoringFolderAction extends AbstractMonitoringAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public AddMonitoringFolderAction(String key) {
		super(key);		
	}

	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle(TextUtils.getText("AddMonitoringFolderAction_dialog_title")); //$NON-NLS-1$
		int result = fileChooser.showOpenDialog(Controller.getCurrentController().getViewController().getJFrame());
        if(result == JFileChooser.APPROVE_OPTION){
        	URI pdfDir = MLinkController.toLinkTypeDependantURI(Controller.getCurrentController().getMap().getFile(), fileChooser.getSelectedFile());
        	fileChooser.setDialogTitle(TextUtils.getText("AddMonitoringFolderAction_dialog_title_mindmaps")); //$NON-NLS-1$
        	result = fileChooser.showOpenDialog(Controller.getCurrentController().getViewController().getJFrame());
        	if(result == JFileChooser.APPROVE_OPTION){
        		URI mindmapDir = MLinkController.toLinkTypeDependantURI(Controller.getCurrentController().getMap().getFile(), fileChooser.getSelectedFile());
        		NodeModel selected = Controller.getCurrentController().getSelection().getSelected();
        		NodeUtils.addMonitoringDir(selected, pdfDir);
        		NodeUtils.addMindmapDir(selected, mindmapDir);
        		this.updateNodesAgainstMonitoringDir(selected, pdfDir, mindmapDir);
        	}   		
        }	
		
	}	

	@Override
	public void setEnabled(){
		NodeModel selected = Controller.getCurrentController().getSelection().getSelected();
		if(selected == null){
			this.setEnabled(false);
		}
		else{
			this.setEnabled(!NodeUtils.isMonitoringNode(selected));
		}
	}

}
