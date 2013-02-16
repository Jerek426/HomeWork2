package hello_world.events;

import hello_world.HelloWorldFileManager;
import static hello_world.HelloWorldSettings.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This event handler class responds to button clicks on the 
 * file controls. Note that this handler is used for all of the
 * file control buttons.
 * 
 * @author  Richard McKenna 
 *          Debugging Enterprises
 * @version 1.0
 */
public class FileButtonsHandler implements ActionListener
{
    // THE HANDLER WILL NEED TO RELAY THE RESPONSE
    // REQUEST TO THE FILE MANAGER
    private HelloWorldFileManager fileManager;
    
    /**
     * Constructor, it will store the file manager for later.
     * 
     * @param initFileManager This file manager will be needed
     * to respond to the file interactions.
     */
    public FileButtonsHandler(HelloWorldFileManager initFileManager)
    {
        // KEEP IT FOR LATER
        fileManager = initFileManager;
    }

    /**
     * This method is called in response to the user clicking on
     * one of the file buttons.
     * 
     * @param ae Event object that contains information about
     * which button was clicked.
     */
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        // EACH BUTTON STORES A COMMAND, WHICH REPRESENTS
        // WHICH BUTTON WAS CLICKED
        String command = ae.getActionCommand();
        
        // WHICH BUTTON WAS CLICKED?
        switch (command)
        {
            // THE NEW BUTTON?
            case NEW_TOOLTIP:
                fileManager.processNewWorldRequest();
                break;
            // THE OPEN BUTTON?
            case OPEN_TOOLTIP:
                fileManager.processOpenWorldRequest();
                break;
            // THE SAVE BUTTON?
            case SAVE_TOOLTIP:
                fileManager.processSaveWorldRequest();
                break;
            // THE SAVE AS BUTTON?
            case SAVE_AS_TOOLTIP:
                fileManager.processSaveAsWorldRequest();
                break;
            // THE EXIT BUTTON?
            case EXIT_TOOLTIP:
                fileManager.processExitRequest();
                break;
        }
    }
}