package org.janux.util.loadtest.ui;

import java.io.File;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 ***********************************************************************
 * This class implements a Dialog box that the user can use to show the
 * contents of a grid cell onscreen.  This is useful for long strings
 * that cannot be displayed entirely in a grid cell.
 ***********************************************************************
*/
public class dlgShowGridCell extends JDialog
{
  private static final long serialVersionUID = 935306360744545526L;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTextArea memDetails = new JTextArea();
  JPanel jPanel1 = new JPanel();
  JButton btnSave = new JButton();
  JButton btnClose = new JButton();
  private String sCellInfo;
  FlowLayout flowLayout1 = new FlowLayout();

/**
 ***********************************************************************
 * Construct a new frame that shows the given string
 * @param frame The owner frame of this dialog box
 * @param aCellInfo Text to display on the screen
 ***********************************************************************
*/
  public dlgShowGridCell(Frame frame, final String aCellInfo)
  {
    super(frame, "Details", false);
    try
    {
      jbInit();
      sCellInfo = aCellInfo;
      memDetails.setText(sCellInfo);
      memDetails.setLineWrap(true);
      pack();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    panel1.setLayout(borderLayout1);
    jPanel1.setPreferredSize(new Dimension(40, 40));
    jPanel1.setLayout(flowLayout1);
    btnSave.setText("Save");
    btnSave.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        btnSave_actionPerformed(e);
      }
    });
    btnClose.setText("Close");
    btnClose.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        btnClose_actionPerformed(e);
      }
    });
    panel1.setPreferredSize(new Dimension(500, 300));
    getContentPane().add(panel1);
    JScrollPane scrollPane = new JScrollPane(memDetails);
    panel1.add(scrollPane, BorderLayout.CENTER);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnSave, null);
    jPanel1.add(btnClose, null);
  }

  private void btnClose_actionPerformed(ActionEvent e)
  {
    this.setVisible(false);
  }

  private void btnSave_actionPerformed(ActionEvent e)
  {
  final JFileChooser dlgSave = new JFileChooser();
 // dlgSave.setFileSelectionMode( dlgSave.FILES_ONLY );
  dlgSave.setDialogTitle("Save as");
  final int retval = dlgSave.showSaveDialog(null);
  if ( retval == JFileChooser.APPROVE_OPTION )
    {
    final File outFile = dlgSave.getSelectedFile();
    try
      {
    	outFile.hashCode();
   //   FileStore.Write(outFile,sCellInfo);
      }
    catch (Exception ex)
      {
      System.out.println(ex.toString());
      }
    }
  }

}

