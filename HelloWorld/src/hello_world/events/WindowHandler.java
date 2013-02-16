package hello_world.events;

import hello_world.HelloWorldFileManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This handler responds to interactions with the window. Should
 * the user click on the Window's 'X' button, this will respond.
 * 
 * @author  Richard McKenna 
 *          Debugging Enterprises
 * @version 1.0
 */
public class WindowHandler implements WindowListener
{
    // THE HANDLER WILL NEED TO RELAY THE RESPONSE
    // REQUEST TO THE FILE MANAGER
    private HelloWorldFileManager fileManager;
    
    /**
     * Constructor, it will store the file manager for later.
     * 
     * @param initFileManager This file manager will be needed
     * to respond to the window interactions.
     */
    public WindowHandler(HelloWorldFileManager initFileManager)
    {
        // KEEP IT FOR LATER
        fileManager = initFileManager;
    }

    /**
     * This method is called in response to the user trying to
     * close the window by clicking on the title bar's "X".
     * 
     * @param we Event object contains information about the
     * window interaction.
     */    
    @Override
    public void windowClosing(WindowEvent we)
    {
        fileManager.processExitRequest();
    }
    
    // WE WON'T BE USING THE REST OF THESE METHODS
    // BUT MUST PROVIDE THEM TO MAKE THE COMPILER HAPPY
    
    @Override
    public void windowOpened(WindowEvent e)         {}

    @Override
    public void windowClosed(WindowEvent e)         {}

    @Override
    public void windowIconified(WindowEvent e)      {}

    @Override
    public void windowDeiconified(WindowEvent e)    {}

    @Override
    public void windowActivated(WindowEvent e)      {}

    @Override
    public void windowDeactivated(WindowEvent e)    {}
}