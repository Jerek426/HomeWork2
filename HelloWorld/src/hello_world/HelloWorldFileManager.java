package hello_world;

import static hello_world.HelloWorldSettings.*;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import world_data.WorldDataManager;

/**
 * This class provides all the file servicing for the application. This
 * means it directs all operations regarding creating new, opening, loading, and
 * saving files, Note that it employs use of WorldIO for the actual file work,
 * this class manages when to actually read and write from/to files, prompting
 * the user when necessary for file names and validation on actions.
 *
 * @author  Richard McKenna 
 *          Debugging Enterprises
 * @version 1.0
 */
public class HelloWorldFileManager
{
    // THIS IS THE APP THAT THIS FILE MANAGER NEEDS TO USE
    private HelloWorld app;

    // WE'LL STORE THE FILE CURRENTLY BEING WORKED ON
    // AND THE NAME OF THE FILE
    private File currentFile;
    private String currentFileName;
    
    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;
    
    /**
     * This default constructor starts the program without a world file being
     * edited.
     * 
     * @param initApp The application this file manager will be providing
     * file services to.
     */
    public HelloWorldFileManager(HelloWorld initApp)
    {
        // KEEP THE APP FOR LATER
        app = initApp;

        // NOTHING YET
        currentFile = null;
        currentFileName = null;
        saved = true;
    }

    /**
     * This method starts the process of editing a new world. If a world is
     * already being edited, it will prompt the user to save it first.
     */
    public void processNewWorldRequest()
    {
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToMakeNew = true;
        if (!saved)
        {
            // THE USER CAN OPT OUT HERE WITH A CANCEL
            continueToMakeNew = promptToSave();
        }

        // IF THE USER REALLY WANTS TO MAKE A NEW WORLD
        if (continueToMakeNew)
        {
            // GO AHEAD AND PROCEED MAKING A NEW WORLD
            continueToMakeNew = promptForNew(true);
         }
    }

    /**
     * This method lets the user open a world saved to a file. It will also make
     * sure data for the current world is not lost.
     */
    public void processOpenWorldRequest()
    {
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToOpen = true;
        if (!saved)
        {
            // THE USER CAN OPT OUT HERE WITH A CANCEL
            continueToOpen = promptToSave();
        }

        // IF THE USER REALLY WANTS TO OPEN A WORLD
        if (continueToOpen)
        {
            // GO AHEAD AND PROCEED MAKING A NEW WORLD
            continueToOpen = promptToOpen();
        }
    }

    /**
     * This method will save the current world to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     */
    public void processSaveWorldRequest()
    {
        // GET HTE DATA MANAGER
        WorldDataManager dataManager = app.getWorldDataManager();

        // DON'T ASK, JUST SAVE
        boolean savedSuccessfully = dataManager.save(currentFile);
        if (savedSuccessfully)
        {
            // MARK IT AS SAVED
            saved = true;

            // AND REFRESH THE GUI
            app.enableSaveButton();
        }
    }

    /**
     * This method will save the current world as a named file provided by the
     * user.
     */
    public void processSaveAsWorldRequest()
    {
        // ASK THE USER FOR A FILE NAME
        promptForNew(false);
    }

    /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     */
    public void processExitRequest()
    {
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToExit = true;
        if (!saved)
        {
            // THE USER CAN OPT OUT HERE
            continueToExit = promptToSave();
        }

        // IF THE USER REALLY WANTS TO EXIT THE APP
        if (continueToExit)
        {
            // EXIT THE APPLICATION
            System.exit(0);
        }
    }

    /**
     * This helper method asks the user for a name for the world about to be
     * created. Note that when the world is created, a corresponding .xml file
     * is also created.
     *
     * @return true if the user goes ahead and provides a good name false if
     * they cancel.
     */
    private boolean promptForNew(boolean brandNew)
    {
        // SO NOW ASK THE USER FOR A WORLD NAME
        String worldName = JOptionPane.showInputDialog(
                app.getWindow(),
                WORLD_NAME_REQUEST_TEXT,
                WORLD_NAME_REQUEST_TITLE_TEXT,
                JOptionPane.QUESTION_MESSAGE);

        // GET THE WORLD DATA
        WorldDataManager dataManager = app.getWorldDataManager();

        // IF THE USER CANCELLED, THEN WE'LL GET A fileName
        // OF NULL, SO LET'S MAKE SURE THE USER REALLY
        // WANTS TO DO THIS ACTION BEFORE MOVING ON
        if ((worldName != null)
                && (worldName.length() > 0))
        { 
            // WE ARE STILL IN DANGER OF AN ERROR DURING THE WRITING
            // OF THE INITIAL FILE, SO WE'LL NOT FINALIZE ANYTHING
            // UNTIL WE KNOW WE CAN WRITE TO THE FILE
            String fileNameToTry = worldName + WORLD_FILE_EXTENSION;
            File fileToTry = new File(WORLDS_PATH + fileNameToTry);
            if (fileToTry.isDirectory())
            {
                return false;
            }
            int selection = JOptionPane.OK_OPTION;
            if (fileToTry.exists())
            {
                selection = JOptionPane.showConfirmDialog(app.getWindow(), 
                        OVERWRITE_FILE_REQUEST_TEXT_A + fileNameToTry + OVERWRITE_FILE_REQUEST_TEXT_B, 
                        OVERWRITE_FILE_REQUEST_TITLE_TEXT, 
                        JOptionPane.OK_CANCEL_OPTION, 
                        JOptionPane.QUESTION_MESSAGE);
            }
            if (selection == JOptionPane.OK_OPTION)
            {
                // MAKE OUR NEW WORLD
                if (brandNew)
                {
                    dataManager.reset(worldName);
                }
                
                // INITIALIZE THE GUI CONTROLS IF THIS IS
                // THE FIRST WORLD THIS SESSION
                app.initRegionControls();
        
                
        // NOW FOR THE DATA. THIS RELOADS ALL REGIONS INTO THE TREE
        app.initWorldTree();

                // NOW SAVE OUR NEW WORLD
                dataManager.save(fileToTry);
            
                // NO ERROR, SO WE'RE HAPPY
                saved = true;

                // UPDATE THE FILE NAMES AND FILE
                currentFileName = fileNameToTry;
                currentFile = fileToTry;

                // SELECT THE ROOT NODE, WHICH SHOULD FORCE A
                // TRANSITION INTO THE REGION VIEWING STATE
                // AND PUT THE FILE NAME IN THE TITLE BAR
                app.getWindow().setTitle(APP_NAME + APP_NAME_FILE_NAME_SEPARATOR + currentFileName);
                
                // WE DID IT!
                return true;
            }
        }
        // USER DECIDED AGAINST IT
        return false;
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating a new
     * world, or opening another world, or exiting. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is returned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave()
    {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        int selection = JOptionPane.showOptionDialog(
                app.getWindow(),
                PROMPT_TO_SAVE_TEXT, PROMPT_TO_SAVE_TITLE_TEXT,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection == JOptionPane.YES_OPTION)
        {
            WorldDataManager dataManager = app.getWorldDataManager();
            boolean saveSucceeded = dataManager.save(currentFile);
            if (saveSucceeded)
            {
                // WE MADE IT THIS FAR WITH NO ERRORS
                JOptionPane.showMessageDialog(
                        app.getWindow(),
                        WORLD_SAVED_TEXT,
                        WORLD_SAVED_TITLE_TEXT,
                        JOptionPane.INFORMATION_MESSAGE);
                saved = true;
            } else
            {
                // SOMETHING WENT WRONG WRITING THE XML FILE
                JOptionPane.showMessageDialog(
                        app.getWindow(),
                        WORLD_SAVING_ERROR_TEXT,
                        WORLD_SAVING_ERROR_TITLE_TEXT,
                        JOptionPane.ERROR_MESSAGE);
            }
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection == JOptionPane.CANCEL_OPTION)
        {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private boolean promptToOpen()
    {
        // ASK THE USER FOR THE WORLD TO OPEN
        JFileChooser worldFileChooser = new JFileChooser(WORLDS_PATH);
        int buttonPressed = worldFileChooser.showOpenDialog(app.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (buttonPressed == JFileChooser.APPROVE_OPTION)
        {
            // GET THE FILE THE USER ENTERED
            File testFile = worldFileChooser.getSelectedFile();
            if (testFile == null)
            {
                // TELL THE USER ABOUT THE ERROR
                JOptionPane.showMessageDialog(
                        app.getWindow(),
                        NO_FILE_SELECTED_TEXT,
                        NO_FILE_SELECTED_TITLE_TEXT,
                        JOptionPane.INFORMATION_MESSAGE);

                return false;
            }

            // AND LOAD THE WORLD (XML FORMAT) FILE
                WorldDataManager dataManager = app.getWorldDataManager();
                boolean loadedSuccessfully = dataManager.load(testFile);
             
                if (loadedSuccessfully)
                {
                    app.initRegionControls();
                    
                    // NOW FOR THE DATA. THIS RELOADS ALL REGIONS INTO THE TREE
                    app.initWorldTree();                    
                    currentFile = testFile;
                    currentFileName = currentFile.getName();
                    saved = true;
                    
                    // AND PUT THE FILE NAME IN THE TITLE BAR
                    app.getWindow().setTitle(APP_NAME + APP_NAME_FILE_NAME_SEPARATOR + currentFileName);
                    
                    // TELL THE USER ABOUT OUR SUCCESS
                    JOptionPane.showMessageDialog(
                        app.getWindow(),
                        WORLD_LOADED_TEXT,
                        WORLD_LOADED_TITLE_TEXT,
                        JOptionPane.INFORMATION_MESSAGE);
                
                    return true;
                } 
                else
                {
                // TELL THE USER ABOUT THE ERROR
                    JOptionPane.showMessageDialog(
                        app.getWindow(),
                        WORLD_LOADING_ERROR_TEXT,
                        WORLD_LOADING_ERROR_TITLE_TEXT,
                        JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
        }
        return false;
    }

    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the world is changed in some way.
     */
    public void markFileAsNotSaved()
    {
        saved = false;
    }

    /**
     * Accessor method for checking to see if the current world has been saved
     * since it was last editing. If the current file matches the world data,
     * we'll return true, otherwise false.
     *
     * @return true if the current world is saved to the file, false otherwise.
     */
    public boolean isSaved()
    {
        return saved;
    }
}