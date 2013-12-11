package org.janux.util.loadtest.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import org.janux.util.loadtest.LoadRunner;
import org.janux.util.loadtest.SimpleLoadRunner;
import org.springframework.beans.factory.InitializingBean;

/**
 ***********************************************************************
 * Main screen for application.  Shows status of all currently running
 * threads on a grid.  As well as global information about the amount
 * of memory usage, and number of client connections
 ***********************************************************************
*/
public class FrameMain extends JFrame implements InitializingBean
{
 private static final long serialVersionUID = 5218096026978037245L;
 private List<SimpleLoadRunner> loadRunnerList;
 private final static String NAME_HEADER         = "Thread ID";
// private final static String BOOKERROR_HEADER    = "# Book Errors";
// private final static String AVAILERROR_HEADER   = "# Avail Errors";
 private final static String NUM_REQ_HEADER      = "# Requests";
// private final static String SEARCHERROR_HEADER  = "# Search Errors";
 //private final static String BOOKTIME_HEADER    = "Avg Book Time";
 private final static String AVAILTIME_HEADER   = "Avg Avail Time";
// private final static String SEARCHTIME_HEADER  = "Avg Search Time";
 private final static String DESCR_HEADER  = "Request";
 //private final static String RESPONSE_HEADER = "Response";

  // menu stuff
  JMenuBar jMenuBar1;
  JMenu mnuFile;
  JMenuItem mnuClose;

  // layout managers

  // labels
  JLabel lblNumConnections;
  JLabel lblTotalMemory;
  JLabel lblFreeMemory;
  JLabel lblUsedMemory;

  // thread stuff
  //ThreadGroup tGroup;
  private Timer threadTimer;
  private Timer memoryTimer;
  JPanel pnlLabels = new JPanel();

  JTable grdTAList;
  private long lastClickTime = 0;
  JSplitPane jSplitPane1;
  BorderLayout borderLayout2 = new BorderLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

/**
 ***********************************************************************
 * Creates a new main screen.
 * @see java.lang.ThreadGroup
 ***********************************************************************
*/
  public FrameMain()
    {
 //   tGroup = aGroup;

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);

    // set up components
    try
      {
      jbInit();
      }
    catch(Exception e)
      {
      e.printStackTrace();
      }

    // set the title bar
    this.setTitle("Loading configuration....");

    // draw the window
    this.pack();
    this.setSize(500,400);
    jSplitPane1.setDividerLocation(200);

    m_CenterWindow(this);
    }

/**
 ***********************************************************************
 * Component initialization
 ***********************************************************************
*/
  private void jbInit() throws Exception
  {
    jSplitPane1 = new JSplitPane();

    this.setEnabled(true);
    this.getContentPane().setLayout(borderLayout2);

    // add this listener so that the main window gets redrawn after showing config form
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
      public void windowActivated(WindowEvent e)
        {
        this_windowActivated(e);
        }
      });


    //  ********* MENU STUFF **************

    // File menu item
    mnuFile = new JMenu("File");

    // close program menu item
    mnuClose = new JMenuItem("Close");
    mnuClose.addActionListener( new mnuCloseListener() );



    // create menu
    jMenuBar1 = new JMenuBar();
    jMenuBar1.setOpaque(false);

    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setBottomComponent(pnlLabels);
    jMenuBar1.add(mnuFile);
    mnuFile.add(mnuClose);
    this.setJMenuBar(jMenuBar1);


    // ***************** GRID STUFF *******************
    grdTAList = new JTable();
    final TableModel tm = grdTAList.getModel();
    if ( tm instanceof DefaultTableModel )
      {
      final DefaultTableModel dm = (DefaultTableModel )tm;

      dm.addColumn(NAME_HEADER);
      dm.addColumn(DESCR_HEADER);
      dm.addColumn(NUM_REQ_HEADER);
      dm.addColumn(AVAILTIME_HEADER);
   //   dm.addColumn(BOOKTIME_HEADER);
   //   dm.addColumn(SEARCHTIME_HEADER);
   //   dm.addColumn(AVAILERROR_HEADER);
    //  dm.addColumn(BOOKERROR_HEADER);
   //   dm.addColumn(SEARCHERROR_HEADER);
   //   dm.addColumn(RESPONSE_HEADER);
      }

    grdTAList.getColumn(NAME_HEADER).setPreferredWidth(100);
 //   grdTAList.getColumn(BOOKERROR_HEADER).setPreferredWidth(100);
 //   grdTAList.getColumn(AVAILERROR_HEADER).setPreferredWidth(100);
    grdTAList.getColumn(NUM_REQ_HEADER).setPreferredWidth(100);
  //  grdTAList.getColumn(SEARCHERROR_HEADER).setPreferredWidth(100);
    grdTAList.getColumn(DESCR_HEADER).setPreferredWidth(400);
  //  grdTAList.getColumn(RESPONSE_HEADER).setPreferredWidth(200);
    grdTAList.getColumn(AVAILTIME_HEADER).setPreferredWidth(100);
  //  grdTAList.getColumn(BOOKTIME_HEADER).setPreferredWidth(100);
  //  grdTAList.getColumn(SEARCHTIME_HEADER).setPreferredWidth(100);
    

    grdTAList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    grdTAList.setRowSelectionAllowed(false);
    grdTAList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    grdTAList.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseReleased(MouseEvent e)
      {
        grdTAList_mouseReleased(e);
      }
    });

    // set the layout to accept three panels
    pnlLabels.setLayout(gridBagLayout1);

    // Labels
    lblNumConnections = new JLabel("Number of Connections:");
    lblTotalMemory    = new JLabel("Total Memory: ");
    lblUsedMemory     = new JLabel("Used Memory: ");
    lblFreeMemory     = new JLabel("Free Memory: ");

    this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
    pnlLabels.add(lblNumConnections, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    pnlLabels.add(lblUsedMemory, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    pnlLabels.add(lblFreeMemory, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    pnlLabels.add(lblTotalMemory, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));


    lblNumConnections.setHorizontalAlignment(SwingConstants.LEFT);
    lblUsedMemory.setHorizontalAlignment(SwingConstants.LEFT);
    lblFreeMemory.setHorizontalAlignment(SwingConstants.LEFT);
    lblTotalMemory.setHorizontalAlignment(SwingConstants.LEFT);

    JScrollPane scrollPane = new JScrollPane(grdTAList);
    jSplitPane1.add(scrollPane, JSplitPane.TOP);
    jSplitPane1.add(pnlLabels, JSplitPane.BOTTOM);

    jSplitPane1.setDividerLocation(300);


    {
    final int THREAD_CHECK_TIMEOUT = 2 * 1000;    // check thread counts every 2 seconds
    final CountThreads listener = new CountThreads(this);
    threadTimer = new Timer(THREAD_CHECK_TIMEOUT,listener);
    threadTimer.start();
    }

    {
    final int MEM_CHECK_TIMEOUT     = 10 * 1000;   // check mem usage every 10 seconds (also runs garbage collection)
    final int MEM_CHECK_MAX_PERCENT = 5;           // log any memory jumps larger than this percentage
    final CountMemUsage listener = new CountMemUsage(this,MEM_CHECK_MAX_PERCENT);
    memoryTimer = new Timer(MEM_CHECK_TIMEOUT,listener);
    memoryTimer.start();
    }

  }

/**
 ***********************************************************************
 * This procedure centers a window frame on the screen
 * @param wndw Window that needs to be centered
 * @see java.awt.Window
 ***********************************************************************
*/
  public static void m_CenterWindow(final Window wndw)
    {
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final Dimension frameSize  = wndw.getSize();

    if ( frameSize.height > screenSize.height )
      frameSize.height = screenSize.height;
    if ( frameSize.width > screenSize.width )
      frameSize.width = screenSize.width;

    wndw.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    wndw.setVisible(true);
    }

/**
 ***********************************************************************
 * This procedure is used to redraw the main window after the
 * configuration window closes.  Otherwise, the main windows' menu
 * gets screwed up
 ***********************************************************************
*/
  private void this_windowActivated(WindowEvent e)
    {
     final Dimension frameSize = getSize();
     setSize(frameSize.width + 1,frameSize.height + 1);
     m_CenterWindow(this);
    }

/**
 ***********************************************************************
 * Make sure exit is called if window closes
 ***********************************************************************
*/
  protected void processWindowEvent(WindowEvent e)
    {
    if ( e.getID() == WindowEvent.WINDOW_CLOSING )
      {
      System.exit(0);
      }
    }

  
  
public List<SimpleLoadRunner> getLoadRunnerList()
{
	return loadRunnerList;
}

public void setLoadRunnerList(List<SimpleLoadRunner> loadRunnerList)
{
	this.loadRunnerList = loadRunnerList;
}

/**
 ***********************************************************************
 * Display the given request on screen.  This procedure will display the
 * request for a given thread in the grid.
 * @param aThreadID The thread that handled the request
 * @param aRequest The request text
 ***********************************************************************
*/
  public void logRequestDescription(final String aThreadID, final String aRequest)
    {
    m_UpdateCellValue(aThreadID,DESCR_HEADER,aRequest);
    }

/**
 ***********************************************************************
 * Display the given request response on screen.  This procedure will display the
 * request response for a given thread in the grid.
 * @param aThreadID The thread that handled the request
 * @param aResponse The response text
 ***********************************************************************
*/
  /*
  public void logResponse(final String aThreadID, final String aResponse)
    {
    m_UpdateCellValue(aThreadID,RESPONSE_HEADER,aResponse);
    }
    */

/**
 ***********************************************************************
 * Display the Client IP address on screen.
 * @param aThreadID The thread that handled the request
 * @param aClientIP The IP address of the thread client
 ***********************************************************************
*/
  /*
  public void logBookError(final String aThreadID, final int aNumBookErrors)
    {
    m_UpdateCellValue(aThreadID,BOOKERROR_HEADER,aNumBookErrors);
    }
*/
  
  /**
   ***********************************************************************
   * Display the Client IP address on screen.
   * @param aThreadID The thread that handled the request
   * @param aAvailTime ??
   ***********************************************************************
  */
    public void logAvailTime(final String aThreadID, final long aAvailTime)
      {
      m_UpdateCellValue(aThreadID,AVAILTIME_HEADER,aAvailTime);
      }

  
  /**
   ***********************************************************************
   * Display the Client IP address on screen.
   * @param aThreadID The thread that handled the request
   * @param aClientIP The IP address of the thread client
   ***********************************************************************
  */
    /*
    public void logBookTime(final String aThreadID, final long aBookTime)
      {
      m_UpdateCellValue(aThreadID,BOOKTIME_HEADER,aBookTime);
      }
*/
  
    /**
     ***********************************************************************
     * Display the Client IP address on screen.
     * @param aThreadID The thread that handled the request
     * @param aClientIP The IP address of the thread client
     ***********************************************************************
    */
    /*
      public void logSearchTime(final String aThreadID, final long aSearchTime)
        {
        m_UpdateCellValue(aThreadID,SEARCHTIME_HEADER,aSearchTime);
        }
*/
    
/**
 ***********************************************************************
 * Display the session ID on screen.
 * @param aThreadID The thread that handled the request
 * @param aSessionID The Session ID
 ***********************************************************************
*/
      /*
  public void logAvailError(final String aThreadID, final int aNumErrors)
    {
    m_UpdateCellValue(aThreadID,AVAILERROR_HEADER,aNumErrors);
    }
*/
      
/**
 ***********************************************************************
 * Display the current number of client requests on screen.
 * @param aThreadID The thread that handled the request
 * @param aNumRequests The current number of requests this client has handled
 ***********************************************************************
*/
  public void logNumRequests(final String aThreadID, final int aNumRequests)
    {
    m_UpdateCellValue(aThreadID,NUM_REQ_HEADER,aNumRequests);
    }

/**
 ***********************************************************************
 * Display a timestamp on screen.
 * @param aThreadID The thread to be timestampped
 * @param aTime Time value to use for timestamp
 ***********************************************************************
*/
  /*
  public void logSearchError(final String aThreadID, final int aNumSearchErrors)
    {
    m_UpdateCellValue(aThreadID,SEARCHERROR_HEADER,aNumSearchErrors);
    }
    */

/**
 ***********************************************************************
 * This procedure is called whenever a logging event happens (2 variations)
 ***********************************************************************
*/
  private void m_UpdateCellValue(final String aRowHeader, final String aColHeader, final int aCellValue)
    {
    final String sValue = Integer.toString(aCellValue);
    m_UpdateCellValue(aRowHeader,aColHeader,sValue);
    }

  private void m_UpdateCellValue(final String aRowHeader, final String aColHeader, final long aCellValue)
  {
  final String sValue = Long.toString(aCellValue);
  m_UpdateCellValue(aRowHeader,aColHeader,sValue);
  }

  private void m_UpdateCellValue(final String aRowHeader, final String aColHeader, final String aCellValue)
    {
    try
    {
        final int iRow    = m_GetRowIndex(aRowHeader);
        final int iColumn = m_GetColumnIndex(aColHeader);

        if ( (iRow >= 0) && (iColumn >= 0) && (aCellValue instanceof String) )
            grdTAList.setValueAt(aCellValue,iRow,iColumn);
    }
    catch (Exception e)
    {
    }
    }

/**
 ***********************************************************************
 * This procedure returns the row number for the given thread
 ***********************************************************************
*/
  private int m_GetRowIndex(final String aThreadID)
    {
    // find the name column
    final int iNameColumn = m_GetColumnIndex(NAME_HEADER);

    // find the row that matches the TA name
    if ( iNameColumn >= 0 )
      {
      Object CellObject;
      String sCellValue;
      for ( int iRowNum = 0; iRowNum < grdTAList.getRowCount(); iRowNum++ )
        {
        CellObject = grdTAList.getValueAt(iRowNum,iNameColumn);
        if ( CellObject instanceof String )
          {
          sCellValue = ((String )CellObject).trim();
          if ( sCellValue.equals(aThreadID) )
            return(iRowNum);
          }
        }
      }

    return(-1);
    }

/**
 ***********************************************************************
 * This procedure returns the column number for the given caption
 ***********************************************************************
*/
  private int m_GetColumnIndex(final String aCaption)
    {
    for ( int iCol = 0; iCol < grdTAList.getColumnCount(); iCol++ )
      {
      if ( grdTAList.getColumnName(iCol).equals(aCaption) )
        return(iCol);
      }

    return(-1);
    }

/**
 ***********************************************************************
 * This procedure returns the name of the thread in the given row
 ***********************************************************************
*/
  /*
  private String m_GetThreadNameFromRow(final int aRowNum)
    {
    // find the name column
    final int iNameColumn = m_GetColumnIndex(NAME_HEADER);

    // find the row that matches the TA name
    if ( iNameColumn >= 0 )
      {
      final Object CellObject = grdTAList.getValueAt(aRowNum,iNameColumn);
      if ( CellObject instanceof String )
        {
        final String sCellValue = (String )CellObject;
        return(sCellValue);
        }
      }

    return("");
    }
 */
  
/**
 ***********************************************************************
 * This procedure adds a new row for the given thread at the bottom of the grid
 * @param aThreadName Name of thread to add
 ***********************************************************************
*/
  public void addThreadRow(final String aThreadName)
    {
    final TableModel dm = grdTAList.getModel();
    if ( dm instanceof DefaultTableModel )
      ((DefaultTableModel )dm).addRow(new Vector());

    // find the name column
    final int iNameColumn = m_GetColumnIndex(NAME_HEADER);
    final int iNameRow    = grdTAList.getRowCount() - 1;

    grdTAList.setValueAt(aThreadName,iNameRow,iNameColumn);
    }

/**
 ***********************************************************************
 * This procedure removes the row for the given thread
 * @param aThreadName Name of thread to remove
 ***********************************************************************
*/
  public void removeThreadRow(final String aThreadName)
    {
    final int iNameRow = m_GetRowIndex(aThreadName);
    if ( iNameRow >= 0 )
      {
      final DefaultTableModel dm = (DefaultTableModel )grdTAList.getModel();
      if ( dm instanceof DefaultTableModel )
        dm.removeRow(iNameRow);
      }

    }

/**
 ***********************************************************************
 * This section handles double clicking on the grid
 ***********************************************************************
*/
  void grdTAList_mouseReleased(MouseEvent e)
    {
    final long MAX_CLICK_TIME = 300;     // allow up to 300 milliseconds between clicks

    // figure the time interval from the previous click
    final long thisClickTime = e.getWhen();
    final long timeDiff      = Math.abs(thisClickTime - lastClickTime);

    // check if this is a double click
    if ( timeDiff < MAX_CLICK_TIME )
      {
       final int iSelectedRow = grdTAList.getSelectedRow();
    //   final int iSelectedCol = grdTAList.getSelectedColumn();
       final int iNameCol = this.m_GetColumnIndex(NAME_HEADER);      
       
       final String sThreadName = (String )grdTAList.getValueAt(iSelectedRow,iNameCol);
       final Displayable thread = findThread(sThreadName);
       final Map<String, String> stats = thread.getStatistics();
       
       final StringBuffer sBuf = new StringBuffer();
       sBuf.append("Current Statistics for " + sThreadName + "\n\n");
       for (String sKey : stats.keySet())
       {
    	   final String sValue = stats.get(sKey);
    	   sBuf.append(sKey + ": " + sValue + "\n");
       }
       
       final String sValue = sBuf.toString();
     //  final String sValue = (String )grdTAList.getValueAt(iSelectedRow,iSelectedCol);

       final dlgShowGridCell frmDebug = new dlgShowGridCell(this,sValue);
       m_CenterWindow(frmDebug);
      }

    // save the click time
    lastClickTime = thisClickTime;
    }

  
  public Displayable findThread(final String aThreadName)
  {
	  for (SimpleLoadRunner loadRunner : this.getLoadRunnerList())
	  {
		  for (Runnable runnable : loadRunner.getRunnableList())
		  {
			  if (runnable instanceof Displayable)
			  {
				  final Displayable displayable = (Displayable)runnable;
				  if (displayable.getThreadName().equalsIgnoreCase(aThreadName))
				  {
					  return displayable;
				  }
			  }
		  }
	  }
	  
	  
	  return null;
  }
  
@SuppressWarnings("unchecked")
public void afterPropertiesSet() throws Exception
{
	this.createThreadRows();
}



public void createThreadRows()
{
	// get a list of all threads in all loadRunners
	final List<Runnable> allThreads = new ArrayList<Runnable>();
	for (LoadRunner loadRunner : this.getLoadRunnerList())
	{
		allThreads.addAll(loadRunner.getRunnableList());
	}
	
	
	// show current stats on all threads
	for (Runnable runnable : allThreads)
	{
	    if (runnable instanceof Displayable)
	    {
	    	final Displayable displayable = (Displayable) runnable;
	    	
	    	final String sName = displayable.getThreadName();
	    	this.addThreadRow(sName);
	    }
	}
	
}


public void updateThreadRows()
{
	// get a list of all threads in all loadRunners
	final List<Runnable> allThreads = new ArrayList<Runnable>();
	for (LoadRunner loadRunner : this.getLoadRunnerList())
	{
		allThreads.addAll(loadRunner.getRunnableList());
	}
	
	
	// show current stats on all threads
	for (Runnable runnable : allThreads)
	{
	    if (runnable instanceof Displayable)
	    {
	    	final Displayable displayable = (Displayable) runnable;
	    	
	    	final String sName = displayable.getThreadName();
	    	logRequestDescription(sName, displayable.getCurrentTaskDescription());
	    	logNumRequests(sName, displayable.getNumTasksCompleted());
	    	logAvailTime(sName, displayable.getAverageTaskTime());
	    }
	}
	
}

}

/**
 ***********************************************************************
 * This class is used to calculate memory usage
 ***********************************************************************
*/
class CountMemUsage implements ActionListener
  {
  private FrameMain owner;
  private static long iLastFreePercent;
  private static long iMaxPercent;

/**
 ***********************************************************************
 * Create a new memory usage instance
 ***********************************************************************
*/
  public CountMemUsage(final FrameMain aOwner, final long aMaxPercent)
    {
    owner = aOwner;
    iMaxPercent = aMaxPercent;
    }

  public void actionPerformed(final ActionEvent e)
    {

    try
      {
      final Runtime rt = Runtime.getRuntime();
      if ( rt instanceof Runtime )
        {
        rt.gc();

        final long FreeMem  = rt.freeMemory();
        final long TotalMem = rt.totalMemory();
        final long UsedMem  = TotalMem - FreeMem;
        final long iFreePercent = (FreeMem * 100)/TotalMem;
        final long iUsedPercent = (UsedMem * 100)/TotalMem;

        final DecimalFormat MemFormat = new DecimalFormat("###,###,###,###");

        if ( owner.lblTotalMemory instanceof JLabel )
          owner.lblTotalMemory.setText("Total Memory:   " + MemFormat.format(TotalMem) );
        if ( owner.lblUsedMemory instanceof JLabel )
          owner.lblUsedMemory.setText( " Used Memory:   " + MemFormat.format(UsedMem) + "   "  + iUsedPercent + "%");
        if ( owner.lblFreeMemory instanceof JLabel )
          owner.lblFreeMemory.setText( " Free Memory:   " + MemFormat.format(FreeMem) + "   "  + iFreePercent + "%");

        if ( Math.abs(iFreePercent - iLastFreePercent) > iMaxPercent )
          {
        //  AppLog.LogInfo("Amount of free memory changed from " + iLastFreePercent + "% to " + iFreePercent + "%");
          iLastFreePercent = iFreePercent;
          }
        }

      }
    catch (Exception ex)
      {}
    }
  }

/**
 ***********************************************************************
 * This class is used to count the number of client threads
 ***********************************************************************
*/
class CountThreads implements ActionListener
  {
	private FrameMain owner;

  public CountThreads(final FrameMain aOwner)
    {
	  owner = aOwner;
    }

  public void actionPerformed(final ActionEvent e)
    {
	 
    try
      {
    	owner.updateThreadRows();
      }
    catch (Exception ex)
      {}
    }
  }

/**
 ***********************************************************************
 * This class is used to close the application
 ***********************************************************************
*/
class mnuCloseListener implements ActionListener
{
 public void actionPerformed(final ActionEvent e)
   {
  // AppLog.LogInfo("Program stopped");
   System.exit(0);
   }
}




