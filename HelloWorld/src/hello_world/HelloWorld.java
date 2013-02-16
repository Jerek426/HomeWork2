package hello_world;

import static hello_world.HelloWorldSettings.*;
import hello_world.events.FileButtonsHandler;
import hello_world.events.WindowHandler;
import hello_world.events.WorldTreeHandler;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import world_data.Region;
import world_data.RegionType;
import world_data.WorldDataManager;
import world_io.WorldIO;

/**
 * This HelloWorld application allows for the creating, viewing, and editing
 * of XML documents that validate against the WorldRegions.xsd schema. These
 * documents store hierarchical data about continents, nations, states, and
 * counties to be used by an application or game.
 * 
 * @author  Richard McKenna 
 *          Debugging Enterprises
 * @version 1.0
 */
public class HelloWorld
{
    // HERE'S OUR DATA
    private WorldDataManager worldDataManager;
    
    // THIS WILL MANAGE I/O
    private HelloWorldFileManager fileManager;
    
    // NOW FOR THE GUI

    // HERE'S OUR APP'S WINDOW
    private JFrame window;

    // THIS WILL CONTAIN THE TOOLBARS AND WILL GO IN THE NORTH
    private JPanel northPanel;

    // HERE'S A TOOLBAR TO GO IN THE NORTH WITH FILE CONTROLS
    private JPanel  fileToolbar;
    private JButton newButton;
    private JButton openButton;
    private JButton saveButton;
    private JButton saveAsButton;
    private JButton exitButton;
    private JButton updateButton;
    private JButton cancelButton;

    // THIS WILL SEPARATE THE CENTER OF OUR GUI INTO TWO HALVES (LEFT & RIGHT)
    private JSplitPane splitPane;
    
    // HERE'S WHERE WE'LL PRESENT THE HIERARCHICAL 
    // REPRESENTATION OF OUR DATA
    private JScrollPane         worldTreeScrollPane;
    private JTree               worldTree;
    private DefaultTreeModel    worldTreeModel;

    // HERE WE'LL DISPLAY AND EDIT REGION INFO
    private JPanel      regionEditorPanel;
    private JLabel      headerLabel;
    private JLabel      idLabel;
    private JTextField  idTextField;
    private JLabel      nameLabel;
    private JTextField  nameTextField;
    private JLabel      typeLabel;
    private JComboBox   typeComboBox;
    private JLabel      capitalLabel;
    private JTextField  capitalTextField;

    /**
     * Default constructor, note that it does not initialize anything. All
     * initialization is done via the init method.
     */
    public HelloWorld()   
    {
    
    }

    // ACCESSOR METHODS
    
    /**
     * Accessor method for getting the application's top-level window,
     * within which all GUI components will be contained.
     * 
     * @return The top-level window of the application.
     */
    public JFrame getWindow()
    {
        return window;
    }

    /**
     * Accessor method for getting the application's world data manager,
     * which manages all the geographic information, but has no knowledge
     * of its presentation in this application.
     * 
     * @return The application's geographic data manager.
     */
    public WorldDataManager getWorldDataManager()
    {
        return worldDataManager;
    }

    /**
     * Accessor method for getting the application's file manager, which
     * manages all reading and writing to and from files, as well as the
     * creation of new files.
     * 
     * @return The application's file manager.
     */
    public HelloWorldFileManager getFileManager()
    {
        return fileManager;
    }

    /**
     * Accessor method for getting the text inside the id text field,
     * which the user may or may not have changed.
     * 
     * @return The text found inside the id text field.
     */
    public String getInputId()
    {
        return idTextField.getText();
    }
    
    /**
     * Accessor method for getting the text inside the name text field,
     * which the user may or may not have changed.
     * 
     * @return The text found inside the name text field.
     */
    public String getInputName()
    {
        return nameTextField.getText();
    }
    
    /**
     * Accessor method for getting the RegionType selected inside the 
     * type combo box, which the user may or may not have changed.
     * 
     * @return The RegionType found inside the type combo box.
     */    
    public RegionType getInputType()
    {
        return (RegionType)typeComboBox.getSelectedItem();
    }
    
     /**
     * Accessor method for getting the text inside the capital text field,
     * which the user may or may not have changed.
     * 
     * @return The text found inside the capital text field.
     */
    public String getInputCapital()
    {
        return capitalTextField.getText();
    }

    // init METHOD AND ITS HELPERS
    
    /**
     * Called at startup, this method initializes all GUI file controls, laying out
     * the necessary components and hooking up their event handlers, and then
     * opens the application window, sending it into event handling mode. Note
     * that the tree and region editing file controls will only be added to the
     * application once the first world is either created or loaded. Note that
     * when complete, the GUI is constructed and the window is opened and visible.
     */
    public void init()
    {
        // INIT THE MANAGERS, THESE CONTROL THE BEHAVIOR
        // OF THE APPLICATION
        initAppManagers();

        // INIT THE TOP-LEVEL WINDOW
        initWindow();

        // THEN INIT THE GUI
        initGUI();

        // AND GET THE APP ROLLING, OPENING THE WINDOW AND
        // MOVING AHEAD INTO EVENT HANDLING MODE
        window.setVisible(true);
    }

    // PRIVATE HELPER INIT METHODS

    /**
     * Called at startup, this helper method initializes all
     * the manager classes, which provide the app behaviour.
     */
    private void initAppManagers()
    {
        // THE FILE MANAGER
        fileManager = new HelloWorldFileManager(this);

        // AND THE DATA MANAGER
        worldDataManager = new WorldDataManager();
        
        // AND OUR IMPORTER/EXPORTER
        File schemaFile = new File(WORLD_SCHEMA_FILE);
        WorldIO worldIO = new WorldIO(schemaFile);
        worldDataManager.setWorldImporterExporter(worldIO);
     }

    /**
     * Called at startup, this method sets up the window for use. 
     */
    private void initWindow()
    {
        window = new JFrame();
        
        window.setTitle(APP_NAME);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setResizable(WINDOW_IS_RESIZABLE);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        centerWindow();
        
        WindowHandler wh = new WindowHandler(fileManager);
        window.addWindowListener(wh);
    }
    
    /**
     * Initializes all the GUI components excluding the world tree
     * and region editor controls.
     */
    private void initGUI()
    {
        // WE'LL USE THIS TO HELP LOAD OUR IMAGES
        MediaTracker tracker = new MediaTracker(window);

        // THIS WILL HANDLE EVENTS FOR OUR FILE BUTTONS
        FileButtonsHandler handler = new FileButtonsHandler(fileManager);

        // WE'LL PUT ALL THE TOOLBARS IN THE NORTH PANEL
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // THE FILE TOOLBAR CONTROLS
        fileToolbar = new JPanel();
        fileToolbar.setBorder(TOOLBAR_BORDER);
        newButton       = initToolbarButton(NEW_BUTTON_FILE,       fileToolbar, tracker, 0, NEW_TOOLTIP,      handler);
        openButton      = initToolbarButton(OPEN_BUTTON_FILE,      fileToolbar, tracker, 1, OPEN_TOOLTIP,     handler);
        saveButton      = initToolbarButton(SAVE_BUTTON_FILE,      fileToolbar, tracker, 2, SAVE_TOOLTIP,     handler);
        saveAsButton    = initToolbarButton(SAVE_AS_BUTTON_FILE,   fileToolbar, tracker, 3, SAVE_AS_TOOLTIP,  handler);
        exitButton      = initToolbarButton(EXIT_BUTTON_FILE,      fileToolbar, tracker, 4, EXIT_TOOLTIP,     handler);
        northPanel.add(fileToolbar);
        
        // WAIT FOR ALL THE IMAGES TO FINISH LOADING
        this.waitForMediaTracker(tracker);

        // AND THE NORTH PANEL GOES IN THE WINDOW
        window.add(northPanel, BorderLayout.NORTH);
        
        // MAKE SURE THE BUTTONS START IN THE CORRECT ENABLED/DISABLED STATES
        saveButton.setEnabled(false);
    }
    
    /**
     * GUI setup methods can be quite lengthy and repetitive so it helps to
     * create helper methods that can do a bunch of things at once. This method
     * creates a button with a bunch of pre-made values.
     *
     * @param imageFile The image to use for the button.
     *
     * @param parent The container inside which to put the button. Note that
     * for this GUI, we'll always assume FlowLayout.
     *
     * @param tracker This makes sure our button fully loads.
     *
     * @param id A unique id for the button so the tracker knows it's there.
     *
     * @param tooltip The mouse-over text for the button.
     * 
     * @param eventHandler The listener that will respond to presses on
     * this button.
     *
     * @return A fully constructed and initialized button with all the data
     * provided to it as arguments.
     */
    private JButton initToolbarButton(  String          imageFile,
                                        Container       parent,
                                        MediaTracker    tracker,
                                        int             id,
                                        String          tooltip,
                                        ActionListener  eventHandler)
    {
        // LET'S GET A GENERIC BUTTON
        JButton button = initGenericButton(imageFile, tracker, id, tooltip, eventHandler);

        // AND THEN PUT IT IN THE TOOLBAR
        parent.add(button);
        
        // AND RETURN IT
        return button;
    }

    /**
     * Sets up everything a button needs except how it is laid out, since we'll
     * have buttons put in toolbars that use FlowLayout and our regions editor,
     * which uses GridBagLatout.
     * 
     * @param imageFile The image to use for the button.
     *
     * @param parent The container inside which to put the button. Note that
     * for this GUI, we'll always assume FlowLayout.
     *
     * @param tracker This makes sure our button fully loads.
     *
     * @param id A unique id for the button so the tracker knows it's there.
     *
     * @param tooltip The mouse-over text for the button.
     * 
     * @param eventHandler The listener that will respond to presses on
     * this button.
     *
     * @return A fully constructed and initialized button with all the data
     * provided to it as arguments. Note that it has not been laid out
     * inside a container yet, as that will be a custom operation.
     */
    private JButton initGenericButton(  String          imageFile,
                                        MediaTracker    tracker,
                                        int             id,
                                        String          tooltip,
                                        ActionListener  eventHandler)
    {
        // LOAD THE IMAGE AND MAKE AN ICON
        Image img = batchLoadImage(imageFile, tracker, id);
        ImageIcon ii = new ImageIcon(img);

        // MAKE THE BUTTON THAT WE'LL END UP RETURNING
        JButton createdButton = new JButton();

        // NOW SETUP OUR BUTTON FOR USE
        
        // GIVE IT THE ICON
        createdButton.setIcon(ii);
        
        // GIVE IT THE MOUSE-OVER TEXT
        createdButton.setToolTipText(tooltip);
        
        // INSETS ARE SPACING INSIDE THE BUTTON,
        // TOP LEFT RIGHT BOTTOM
        Insets buttonMargin = new Insets(
                BUTTON_INSET, BUTTON_INSET, BUTTON_INSET, BUTTON_INSET);
        createdButton.setMargin(buttonMargin);

        // INIT THE EVENT HANDLERS - NOTE WE'RE USING THE TOOLTIP
        // AS THE ACTION COMMAND SINCE THEY ARE UNIQUE FOR EACH BUTTON
        createdButton.setActionCommand(tooltip);
        createdButton.addActionListener(eventHandler);

        // AND RETURN THE CREATED BUTTON
        return createdButton;        
    }

    /**
     * This method helps us load a bunch of images and ensure they are fully
     * loaded when we want to use them.
     *
     * @param imageFile The path and name of the image file to load.
     *
     * @param tracker This will help ensure all the images are loaded.
     *
     * @param id A unique identifier for each image in the tracker. It will only
     * wait for ids it knows about.
     *
     * @return A constructed image that has been registered with the tracker.
     * Note that the image's data has not necessarily been fully loaded when
     * this method ends.
     */
    private Image batchLoadImage(String imageFile, MediaTracker tracker, int id)
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.getImage(imageFile);
        tracker.addImage(img, id);
        return img;
    }
    
    /**
     * Waits of all the media given to the media tracker to fully
     * load so that they can be used by the program. Note that if
     * we don't do this we may end up with missing images for
     * our GUI icons.
     * 
     * @param tracker The tracker that already has images it is
     * keeping an eye on.
     */
    public void waitForMediaTracker(MediaTracker tracker)
    {
        // NOW THE MEDIA TRACKER WILL MAKE SURE ALL THE IMAGES
        // ARE DONE LOADING BEFORE WE GO ON
        try
        {
            tracker.waitForAll();
        } 
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }        
    }
     
    // initRegionControls AND ITS HELPERS
     
    /**
     * Initializes the rest of the controls, including the world tree
     * and the region editing. Note that this is called after either a
     * new world is created or an existing one is loaded from a file.
     */
    public void initRegionControls()
    {
        // ONLY DO THIS IF IT HASN'T BEEN DONE ALREADY
        if (headerLabel != null)
        {
            return;
        }
        
        // WE'LL NEED THIS TO LOAD IMAGES
        MediaTracker tracker = new MediaTracker(window);
        
        // WE'LL DISPLAY ALL THE REGIONS HERE
        initTree();        

        // AND OUR REGION DISPLAY
        regionEditorPanel = new JPanel();

        // INITIALIZE OUR HEADER
        headerLabel = new JLabel(REGION_EDITOR_HEADER);
        headerLabel.setFont(REGION_EDITOR_HEADER_FONT);
        
        // NOW THE INPUT LABELS
        idLabel         = initRegionEditorTextFieldLabel(ID_LABEL_TEXT);
        nameLabel       = initRegionEditorTextFieldLabel(NAME_LABEL_TEXT);
        typeLabel       = initRegionEditorTextFieldLabel(TYPE_LABEL_TEXT);
        capitalLabel    = initRegionEditorTextFieldLabel(CAPITAL_LABEL_TEXT);

        // THE TEXT FIELDS
        idTextField         = initRegionEditorTextField();
        nameTextField       = initRegionEditorTextField();
        capitalTextField    = initRegionEditorTextField();
        
        // OUR COMBO BOX
        typeComboBox    = new JComboBox(RegionType.values());
        typeComboBox.setFont(REGION_EDITOR_INPUT_FONT);
        
        // NOW LAY EVERYTHING OUT
        regionEditorPanel.setLayout(new GridBagLayout());
        addComponentToRegionEditorPanel(headerLabel,        0, 0, 2, 1);
        addComponentToRegionEditorPanel(idLabel,            0, 1, 1, 1);
        addComponentToRegionEditorPanel(idTextField,        1, 1, 1, 1);
        addComponentToRegionEditorPanel(nameLabel,          0, 2, 1, 1);
        addComponentToRegionEditorPanel(nameTextField,      1, 2, 1, 1);
        addComponentToRegionEditorPanel(typeLabel,          0, 3, 1, 1);
        addComponentToRegionEditorPanel(typeComboBox,       1, 3, 1, 1);
        addComponentToRegionEditorPanel(capitalLabel,       0, 4, 1, 1);
        addComponentToRegionEditorPanel(capitalTextField,   1, 4, 1, 1);

        // WAIT FOR THE IMAGES TO FULLY LOAD
        waitForMediaTracker(tracker);

        // AND FINALLY SETUP THE APP'S WORKBENCH, WHICH IS SPLIT
        // INTO TWO HALVES, THE WORLD TREE ON THE LEFT AND THE 
        // REGION VIEWING/EDITING ON THE RIGHT
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                worldTreeScrollPane,
                regionEditorPanel);
        splitPane.setDividerLocation(SPLIT_PANE_LEFT_LOCATION);

        // NOW PUT THE WORKBENCH INSIDE THE WINDOW
        window.add(splitPane, BorderLayout.CENTER);
        
        // AND FORCE THE WINDOW TO REFRESH EVERYTHING
        window.validate();        
    }
 
    /**
     * Initializes the world tree, loading it with the geographic data currently
     * found inside the data manager. Note that this method is only called 
     * once, after either a new world is created or loaded.
     */
    private void initTree()
    {
        // INIT THE TREE
        worldTree = new JTree();
        
        // MAKE IT SCROLLABLE
        worldTreeScrollPane = new JScrollPane(worldTree);
        
        // AND HOOK UP THE TREE LISTENER
        WorldTreeHandler treeHandler = new WorldTreeHandler(this);
        worldTree.addTreeSelectionListener(treeHandler);
    }

    /**
     * Helper method for initRegionControls that creates and returns
     * a new label with custom text and a standard font.
     * 
     * @param text The text to appear inside the label.
     * 
     * @return A constructed label displaying the text argument.
     */
    private JLabel initRegionEditorTextFieldLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setFont(REGION_EDITOR_LABEL_FONT);
        return label;
    }
  
    /**
     * Helper method for initRegionControls that creates and returns
     * a new text field with a standard number of columns and font. 
     * 
     * @return A constructed text field.
     */
    private JTextField initRegionEditorTextField()
    {
        JTextField textField = new JTextField(REGION_TEXT_FIELD_COLUMNS);
        textField.setFont(REGION_EDITOR_INPUT_FONT);
        textField.setMargin(REGION_TEXT_FIELD_MARGINS);
        return textField;
    }
    
    /**
     * Helper method used for organizing components inside the region
     * editor panel, which uses GridBagLayout.
     * 
     * @param componentToAdd Component we're putting inside the panel
     * 
     * @param col Column location to put the component.
     * 
     * @param row Row location to put the component.
     * 
     * @param colSpan Number of columns the component will span.
     * 
     * @param rowSpan Number of rows the component will span.
     */
    private void addComponentToRegionEditorPanel(Component componentToAdd,
            int col, int row, int colSpan, int rowSpan)
    {
        // EMPLOY GRID BAG LAYOUT TO PUT IT IN THE PANEL
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = colSpan;
        gbc.gridheight = rowSpan;
        gbc.ipadx = REGION_EDITOR_CONTROLS_IPADX;
        gbc.ipady = REGION_EDITOR_CONTROLS_IPADY;
        gbc.insets = REGION_CONTROLS_INSETS;
        gbc.fill = GridBagConstraints.BOTH;
        regionEditorPanel.add(componentToAdd, gbc);
    }

    /**
     * Centers the window on the screen, no matter the screen resolution. Note
     * that the window must already be sized when this method is called.
     */
    public void centerWindow()
    {
        // GET THE SCREEN SIZE FROM THE TOOLKIT AND USE THOSE DIMENSIONS
        // TO CALCULATE HOW TO CENTER IT ON THE SCREEN
        Toolkit singletonToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = singletonToolkit.getScreenSize();
        int halfWayX = screenSize.width / 2;
        int halfWayY = screenSize.height / 2;
        int halfWindowWidth = window.getWidth() / 2;
        int halfWindowHeight = window.getHeight() / 2;
        int windowX = halfWayX - halfWindowWidth;
        int windowY = halfWayY - halfWindowHeight;
        window.setLocation(windowX, windowY);
    }

    /**
     * This method reloads all the data inside the world data manager
     * into the tree and makes the selectedRegion argument the selected
     * node, which it opens up the tree to.
     * 
     * @param selectedRegion Node to be selected and displayed after
     * the tree is reloaded.
     */
    public void refreshWorldTree(Region selectedRegion)
    {
        LinkedList<Region> selectionPath = worldDataManager.getPathFromRoot(selectedRegion);
        Region world = worldDataManager.getWorld();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(world);
        if (worldTreeModel == null)
        {
            worldTreeModel = new DefaultTreeModel(root);
            worldTree.setModel(worldTreeModel);
        }
        else
        {
            worldTreeModel.setRoot(root);
        }
        addSubRegionsToTree(world, root);
        this.setSelectedRegion(selectionPath, selectedRegion);
    }

    /**
     * Initializes the tree with the proper data, including setting
     * up the tree's data model.
     */
    public void initWorldTree()
    {
        Region world = worldDataManager.getWorld();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(world);
        
        // IF FIRST TIME, MAKE MODEL
        if (worldTreeModel == null)
        {
            worldTreeModel = new DefaultTreeModel(root);
            worldTree.setModel(worldTreeModel);
        }
        else
        {
            worldTreeModel.setRoot(root);
        }
        
        // BUILD THE REST OF THE TREE
        addSubRegionsToTree(world, root);
        selectRootNode();
    }
    
    /**
     * This method opens all the child nodes of the root. We use
     * this when the application first starts.
     */    
    public void openFirstRowOfWorldTree()
    {
        worldTree.expandRow(0);
    }
    

    /**
     * This helper method adds children to parents and cascades this
     * on down through tree to build it out.
     * 
     * @param regionWithSubRegionsToAdd The region with children to add.
     * 
     * @param parentNode The parent node that we'll add the child nodes to.
     */
    private void addSubRegionsToTree(Region regionWithSubRegionsToAdd, DefaultMutableTreeNode parentNode)
    {
        // WE'LL NEED TO ARRANGE THEM IN SORTED ORDER BY NAME
        TreeMap<String, Region> subRegions = new TreeMap();
        Iterator<Region> subRegionsIterator = regionWithSubRegionsToAdd.getSubRegions();
        while (subRegionsIterator.hasNext())
        {
            Region subRegion = subRegionsIterator.next();
            subRegions.put(subRegion.getId(), subRegion);
        }

        // ADD ALL THE CHILDREN
        Iterator<String> keysIterator = subRegions.keySet().iterator();
        while (keysIterator.hasNext())
        {
            String subRegionName = keysIterator.next();
            Region subRegion = subRegions.get(subRegionName);
            DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subRegion);
            worldTreeModel.insertNodeInto(subNode, parentNode, parentNode.getChildCount());
            
            // HERE IS OUR RECURSIVE CALL TO CASCADE IT ON DOWN
            addSubRegionsToTree(subRegion, subNode);
        }
    }

    /**
     * Forces the data from the regionToDisplay argument into the
     * region editing controls.
     * 
     * @param regionToDisplay The region the user wishes to view.
     */
    public void displayRegion(Region regionToDisplay)
    {
        // GET ALL THIS REGION'S DATA AND DISPLAY IT
        idTextField.setText(regionToDisplay.getId());
        nameTextField.setText(regionToDisplay.getName());
        typeComboBox.setSelectedItem(regionToDisplay.getType());
    }

    /**
     * Forces the world tree to open all nodes necessary to display the 
     * node that is currently selected.
     */
    public void openSelectedRegion()
    {
        // FIND THE NODE BY WALKING THE TREE
        DefaultMutableTreeNode walker = (DefaultMutableTreeNode)worldTreeModel.getRoot();
        
        // GET THE PATH TO THE NODE WE WANT OPENED
        TreeNode[] path = walker.getPath();
        int count = 0;
        for(int i=0; i < path.length; i++)
        {
            DefaultMutableTreeNode testNode = (DefaultMutableTreeNode)path[i];
            count = count + walker.getIndex(testNode) + 1;
            
            // EXPAND EACH NODE'S ROW AS WE GO
            worldTree.expandRow(count);
            walker = testNode;
        }
    }

    /**
     * Gets the region that corresponds to the node that's currently
     * selected.
     * 
     * @return The region selected in the world tree.
     */
    public Region getSelectedRegion()
    {
        // GET THE PATH TO THE NODE
        TreePath selectedPath = worldTree.getSelectionPath();
        if (selectedPath != null)
        {
            // AND GET THE LAST ONE, WHICH IS THE SELECTED ONE
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            Region selectedRegion = (Region) selectedNode.getUserObject();
            return selectedRegion;
        }
        return null;
    }

    /**
     * Gets and returns the tree node that contains the nodeRegion argument.
     * 
     * @param nodeRegion The Region that corresponds to the node we're looking for.
     * 
     * @return The node that contains the nodeRegion as its userObject (i.e. tree node data).
     */
    public DefaultMutableTreeNode getRegionNode(Region nodeRegion)
    {
        // START AT THE ROOT
        DefaultMutableTreeNode walker = (DefaultMutableTreeNode)worldTreeModel.getRoot();
        Region testRegion = (Region)walker.getUserObject();
        if (testRegion.getId().equals(nodeRegion.getId()))
        {
            return walker;
        }
        else
        {
            // THIS WILL BE A RECURSIVE SEARCH METHOD
            return findDescendantNode(walker, nodeRegion);
        }
    }

    /**
     * This recursive helper method cascades the search for a node
     * that stores the region argument as data on down the tree.
     * 
     * @param node The node we examine in looking for region. Note we'll cascade
     * this search recursively on down through node's children.
     * 
     * @param region The region we're searching for.
     * 
     * @return The node that contains region. If not found, null is returned.
     */
    private DefaultMutableTreeNode findDescendantNode(DefaultMutableTreeNode node, Region region)
    {
        // GO THROUGH ALL OF node's CHILDREN
        for (int i = 0; i < node.getChildCount(); i++)
        {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)node.getChildAt(i);
            Region testRegion = (Region)childNode.getUserObject();
            if (testRegion.getId().equals(region.getId()))
            {
                return childNode;
            }
            else if (!childNode.isLeaf())
            {
                // RECURSIVE CALL HERE
                DefaultMutableTreeNode foundNode = findDescendantNode(childNode, region);
                if (foundNode != null)
                {
                    return foundNode;
                }
            }
        }        
        return null;
    }

    /**
     * Forces the selectedRegion to be selected in the tree.
     * 
     * @param path List of nodes, starting from the root, needed to find
     * the selectedRegion in the tree.
     * 
     * @param selectedRegion Region whose corresponding node is to
     * be selected.
     */
    public void setSelectedRegion(LinkedList<Region> path, Region selectedRegion)
    {
        DefaultMutableTreeNode newNode = getRegionNode(selectedRegion);
        TreeNode[] pathNodes = newNode.getPath();
        TreePath treePath = new TreePath(pathNodes);
        worldTree.setSelectionPath(treePath);
    }

    /**
     * Gets and returns the parent region of the selected region.
     * 
     * @return The parent region of the selected region.
     */
    public Region getSelectedRegionParent()
    {
        TreePath selectedPath = worldTree.getSelectionPath();
        if (selectedPath != null)
        {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
            if (parentNode != null)
            {
                Region parentRegion = (Region) parentNode.getUserObject();
                return parentRegion;
            }
        }
        return null;
    }
    
    /**
     * Resets the id text field to the selected region's data.
     */
    public void resetIdInput()
    {
        Region region = getSelectedRegion();
        idTextField.setText(region.getId());
    }

    /**
     * Resets the name text field to the selected region's data.
     */
    public void resetNameInput()
    {
        Region region = getSelectedRegion();
        nameTextField.setText(region.getName());
    }

    /**
     * Resets the type combo box to the selected region's data.
     */
    public void resetTypeInput()
    {
        Region region = getSelectedRegion();
        typeComboBox.setSelectedItem(region.getType());
    }
    
    /**
     * Resets the name text field to the selected region's data.
     */
    public void resetCapitalInput()
    {
        Region region = getSelectedRegion();
        nameTextField.setText(region.getCapital());
    }

    /**
     * Resets all the region editing text fields to the currently
     * selected region's data.
     */
    public void resetAllInput()
    {
        resetIdInput();
        resetNameInput();
        resetTypeInput();
        resetCapitalInput();
    }
 
    /**
     * Selects the root node in the tree and opens the tree to
     * display its children.
     */    
    public void selectRootNode()
    {
        worldTree.setSelectionRow(0);
        openFirstRowOfWorldTree();
    }
    
    /**
     * Selects the node in the tree that contains the regionToSelect
     * data. Note that this method will search through the tree
     * for that data.
     * 
     * @param regionToSelect Region to select in the tree.
     */
    public void selectRegion(Region regionToSelect)
    {
        // ONLY LOOK FOR IT IF IT EXISTS
        if (worldDataManager.hasRegion(regionToSelect))
        {
            LinkedList<Region> pathToRegion = worldDataManager.getPathFromRoot(regionToSelect);
            DefaultMutableTreeNode walker = (DefaultMutableTreeNode)worldTreeModel.getRoot();
            boolean regionFound = false;
            int pathIndex = 0;
            while (!regionFound)
            {
                Region testRegion = (Region)walker.getUserObject();
                if (testRegion.getId().equals(regionToSelect.getId()))
                {
                    // SELECT THIS NODE
                    TreePath pathToWalker = new TreePath(walker.getPath());
                    worldTree.setSelectionPath(pathToWalker);
                    regionFound = true;
                }
                // LOOK THROUGH THE CHILD NODES FOR THE NEXT ONE
                else
                {
                    DefaultMutableTreeNode testNode;
                    boolean childFound = false;
                    for (int i = 0; i < walker.getChildCount() && !childFound; i++ )
                    {
                        testNode = (DefaultMutableTreeNode)walker.getChildAt(i);
                        testRegion = (Region)testNode.getUserObject();
                        Region testChild = pathToRegion.get(pathIndex + 1);
                        if (testRegion.getId().equals(testChild.getId()))
                        {
                            childFound = true;
                            walker = testNode;
                        }
                    }
                    pathIndex++;
                }
            }
            TreeNode[] pathToRoot = worldTreeModel.getPathToRoot(walker);
            TreePath path = new TreePath(pathToRoot);
            worldTree.setSelectionPath(path);  
        }
    }

    /**
     * Used for enabling and disabling the save button. Note
     * that it determines its own state by checking to see if
     * the world has been saved or not since the last edit.
     */
    public void enableSaveButton()
    {
        boolean shouldBeEnabled = !fileManager.isSaved();
        saveButton.setEnabled(shouldBeEnabled);
    }
   
    /**
     * Used for enabling and disabling the file controls.
     * 
     * @param enabled If true, the file controls are enabled,
     * if false they are disabled. Note that the save button
     * is still dependent upon if the current world has 
     * already been saved or not.
     */
    public void enableFileControls(boolean enabled)
    {
        newButton.setEnabled(enabled);
        openButton.setEnabled(enabled);
        
        if (fileManager.isSaved())
        {
            saveButton.setEnabled(false);
        }
        else
        {
            saveButton.setEnabled(enabled);
        }
    }
    
    /**
     * This method is used for enabling and disable the world tree.
     * 
     * @param enabled If true, the world tree is enabled, allowing
     * the user to interact with it, else it is disabled.
     */
    public void enableWorldTree(boolean enabled)
    {
        worldTree.setEnabled(enabled);
    }
    
    /**
     * This method can enable and disable the controls used 
     * for changing region data.
     * 
     * @param enabled If true, the region input controls
     * are enabled, else they are disabled.
     */
    public void enableRegionEditor(boolean enabled)
    {
        idTextField.setEnabled(enabled);
        nameTextField.setEnabled(enabled);
        typeComboBox.setEnabled(enabled);
        capitalTextField.setEnabled(enabled);
    }
    
    /**
     * This method is for enabling or disabling the controls associated
     * with changing region details, meaning the text fields for entering
     * the id, name, etc.
     * 
     * @param enable If true, the controls are enabled, else they
     * are disabled. Note that the rest of the app's controls
     * will all be disabled when we are editing a region.
     */
    public void enableRegionEditing(boolean enable)
    {
        // ACTIVATE/DEACTIVATE ALL FILE CONTROLS
        enableFileControls(!enable);
        
        // ACTIVATE/DEACTIVATE TREE 
        enableWorldTree(!enable);
        
        // ACTIVATE/DEACTIVATE REGION EDITING CONTROLS
        enableRegionEditor(enable);        
    }
    
    /**
     * Here is where our application starts. It builds the
     * GUI and then initializes it, which starts it in
     * event handling mode.
     * 
     * @param args Command-line arguments, which we won't be using.
     */
    public static void main(String[] args)
    {
        // CONSTRUCT THE APP
        HelloWorld app = new HelloWorld();
        
        // AND THEN START IT UP
        app.init();
    }
}