package hello_world;

import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * This class lists all of the constants used for running the
 * application. The point of doing this is that String Literals,
 * i.e. text in quotes in a program, are not buried somewhere deep
 * in the middle of a program, instead, they're all here, easy
 * to find and change if need be. The same goes for other constants
 * that are used for setting up the application colors, fonts, etc.
 * 
 * Note that this is not an ideal approach. A better approach still
 * would be loading all of this data from an external file, like an
 * XML file, but for now this will do.
 * 
 * @author  Richard McKenna
 *          Debugging Enterprises
 * @version 1.0
 */
public class HelloWorldSettings 
{
    // PATH SETTINGS
    public static final String  DATA_PATH       = "data/";
    public static final String  FLAGS_PATH      = DATA_PATH + "flags/";
    public static final String  WORLDS_PATH     = DATA_PATH + "worlds/";
    public static final String  BUTTONS_PATH    = DATA_PATH + "buttons/";    
    
    // WINDOW SETTINGS
    public static final int     WINDOW_WIDTH        = 900;
    public static final int     WINDOW_HEIGHT       = 600;
    public static final boolean WINDOW_IS_RESIZABLE = false;
    
    // THIS IS THE SCHEMA WE'LL USE
    public static final String  WORLD_SCHEMA_FILE = WORLDS_PATH + "WorldRegions.xsd";
    
    // TREE SETTINGS
    public static final String  INIT_WORLD_ROOT = "World";
    
    // FILE BUTTON IMAGES
    public static final String  NEW_BUTTON_FILE     = BUTTONS_PATH + "New.png";
    public static final String  OPEN_BUTTON_FILE    = BUTTONS_PATH + "Open.png";
    public static final String  SAVE_BUTTON_FILE    = BUTTONS_PATH + "Save.png";
    public static final String  SAVE_AS_BUTTON_FILE = BUTTONS_PATH + "SaveAs.png";
    public static final String  EXIT_BUTTON_FILE    = BUTTONS_PATH + "Exit.png";
    
    // REGION EDITOR BUTTON IMAGES
    public static final String ADD_REGION_BUTTON_FILE       = BUTTONS_PATH + "AddRegion.png";
    public static final String REMOVE_REGION_BUTTON_FILE    = BUTTONS_PATH + "RemoveRegion.png";
    public static final String EDIT_REGION_BUTTON_FILE      = BUTTONS_PATH + "EditRegion.png";
    
    // REGION UPDATE/CANCEL BUTTON IMAGES
    public static final String UPDATE_BUTTON_FILE    = BUTTONS_PATH + "Update.png";
    public static final String CANCEL_BUTTON_FILE    = BUTTONS_PATH + "Cancel.png";
    
    // FILE BUTTON MOUSE OVER TEXT
    public static final String NEW_TOOLTIP      = "New World";
    public static final String OPEN_TOOLTIP     = "Open World";
    public static final String SAVE_TOOLTIP     = "Save World";
    public static final String SAVE_AS_TOOLTIP  = "Save As World";
    public static final String EXIT_TOOLTIP     = "Exit";
    
    // REGION EDITOR MOUSE OVER TEXT
    public static final String ADD_REGION_TOOLTIP       = "Add Region";
    public static final String REMOVE_REGION_TOOLTIP    = "Remove Region";
    public static final String EDIT_REGION_TOOLTIP      = "Edit Region";
    
    // REGION UPDATE MOUSE OVER TEXT
    public static final String UPDATE_REGION_TOOLTIP    = "Update Region";
    public static final String CANCEL_UPDATE_TOOLTIP    = "Cancel Update";    
    
    // REGION EDITOR HEADER AND LABEL TEXT
    public static final String  REGION_EDITOR_HEADER    = "REGION EDITOR";
    public static final String  ID_LABEL_TEXT           = "Id: ";
    public static final String  NAME_LABEL_TEXT         = "Name: ";
    public static final String  TYPE_LABEL_TEXT         = "Type: ";
    public static final String  CAPITAL_LABEL_TEXT      = "Capital: ";

    // APP FONTS
    public static final Font    REGION_EDITOR_HEADER_FONT   = new Font("Serif", Font.BOLD, 32);
    public static final Font    REGION_EDITOR_LABEL_FONT    = new Font("Serif", Font.BOLD, 20);
    public static final Font    REGION_EDITOR_INPUT_FONT    = new Font("Console", Font.PLAIN, 20);
  
    // SIZE AND POSITIONING SETTINGS FOR CONTROLS
    public static final int     REGION_EDITOR_CONTROLS_IPADX    = 5;
    public static final int     REGION_EDITOR_CONTROLS_IPADY    = 10;
    public static final Insets  REGION_CONTROLS_INSETS          = new Insets(5, 15, 5, 15);
    public static final Insets  REGION_TEXT_FIELD_MARGINS       = new Insets(0, 5, 0, 5);
    public static final int     SPLIT_PANE_LEFT_LOCATION        = 200;
    public static final int     REGION_TEXT_FIELD_COLUMNS       = 15;
    public static final int     BUTTON_INSET                    = 1;
    public static final Border  TOOLBAR_BORDER                  = BorderFactory.createEtchedBorder();
 
    // WE'LL NEED THESE TO DYNAMICALLY BUILD TEXT
    public static final String EMPTY_TEXT                   = "";
    public static final String WORLD_FILE_EXTENSION         = ".xml";
    public static final String APP_NAME                     = "Hello World";
    public static final String APP_NAME_FILE_NAME_SEPARATOR = " - ";
    public static final String PNG_FORMAT_NAME              = "png";
    public static final String PNG_FILE_EXTENSION           = "." + PNG_FORMAT_NAME;
       
   /***** DIALOG MESSAGES AND TITLES *****/
    // DIALOG BOX MESSAGES TO GIVE FEEDBACK BACK TO THE USER
    public static final String WORLD_NAME_REQUEST_TEXT              = "What do you want to name your world?";
    public static final String WORLD_NAME_REQUEST_TITLE_TEXT        = "Enter World File Name";
    public static final String OVERWRITE_FILE_REQUEST_TEXT_A        = "There is already a file called \n";
    public static final String OVERWRITE_FILE_REQUEST_TEXT_B        = "\nWould you like to overwrite it?";
    public static final String OVERWRITE_FILE_REQUEST_TITLE_TEXT    = "Overwrite File?";
    public static final String NO_FILE_SELECTED_TEXT                = "No File was Selected to Open";
    public static final String NO_FILE_SELECTED_TITLE_TEXT          = "No File Selected";
    public static final String WORLD_LOADED_TEXT                    = "World File has been Loaded";
    public static final String WORLD_LOADED_TITLE_TEXT              = "World File Loaded";
    public static final String WORLD_LOADING_ERROR_TEXT             = "An Error Occured While Loading the World";
    public static final String WORLD_LOADING_ERROR_TITLE_TEXT       = "World Loading Error";
    public static final String WORLD_SAVED_TEXT                     = "World File has been Saved";
    public static final String WORLD_SAVED_TITLE_TEXT               = "World File Saved";
    public static final String WORLD_SAVING_ERROR_TEXT              = "An Error Occured While Saving the World";
    public static final String WORLD_SAVING_ERROR_TITLE_TEXT        = "World Saving Error";    
    public static final String PROMPT_TO_SAVE_TEXT                  = "Would you like to save your World?";
    public static final String PROMPT_TO_SAVE_TITLE_TEXT            = "Save your world?";
    public static final String NO_REGION_NAME_PROVIDED_TEXT         = "Error - No Region Name Provided";
    public static final String NO_REGION_NAME_PROVIDED_TITLE_TEXT   = "No Region Name Provided";
    public static final String DUPLICATE_ID_TEXT                    = "Illegal Change - Region Ids Must Be Unique";
    public static final String DUPLICATE_ID_TITLE_TEXT              = "Duplicate Region Id Error";
    public static final String ENTER_NEW_REGION_ID_TEXT             = "What is the Id for your new Region?";
    public static final String ENTER_NEW_REGION_ID_TITLE_TEXT       = "Enter Region Id";
    public static final String INVALID_ID_TEXT                      = "Invalid Region Id Provided";
    public static final String INVALID_ID_TITLE_TEXT                = "Invalid Id";
}