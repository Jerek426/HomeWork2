package hello_world.events;

import hello_world.HelloWorld;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import world_data.Region;

/**
 * This handler responds to interactions with the world tree. Should
 * the user select a tree node, which represents a region, the region
 * should be loaded into the region editor.
 * 
 * @author  Richard McKenna 
 *          Debugging Enterprises
 * @version 1.0
 */
public class WorldTreeHandler implements TreeSelectionListener
{
    // THE HANDLER WILL NEED TO CHANGE THE
    // GUI IN ITS RESPONSE
    private HelloWorld app;
    
    /**
     * Constructor, it will store the app's gui for later.
     * 
     * @param initApp This app will be needed
     * to respond to the tree interactions.
     */
    public WorldTreeHandler(HelloWorld initApp)
    {
        // KEEP IT FOR LATER
        app = initApp;
    }

    /**
     * This method is called in response to the user clicking on
     * one of the regions in the world tree.
     * 
     * @param tse Event object that contains information about
     * which tree node (and thus region) was selected by the user.
     */    
    @Override
    public void valueChanged(TreeSelectionEvent tse) 
    {
        // GET THE SELECTED NODE
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
        
        // AND GET THAT NODE'S REGION
        Region selectedRegion = (Region)selectedNode.getUserObject();
        
        // DISPLAY THE REGION'S DETAILS IN THE REGION ENTRY CONTROLS
        app.displayRegion(selectedRegion);
        
        // AND MAKE SURE IT STARTS OFF NOT IN EDITING MODE
        app.enableRegionEditor(false);
    }    
}