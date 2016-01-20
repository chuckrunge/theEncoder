package com.cgrunge;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.cgrunge.EncoderUtil;

public class MainWindow implements ActionListener {

	private JFrame frame;
	private JButton btnLeftButton = new JButton("Select Input");
	private JButton btnClear = new JButton("Clear All");
	private JButton btnDecode = new JButton("Decode!");
	private JButton btnEncode = new JButton("Encode!");
	private JTextArea txtrLeft = new JTextArea();
	private JTextArea txtrRight = new JTextArea();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("ENCODER");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		txtrLeft.setBounds(10, 45, 414, 53);
		frame.getContentPane().add(txtrLeft);
		txtrRight.setBounds(10, 184, 414, 32);
		txtrRight.setText("Encoded");
		frame.getContentPane().add(txtrRight);
		
		JLabel lblLeft = new JLabel("Input File(s) to Process");
		lblLeft.setBounds(10, 20, 138, 14);
		frame.getContentPane().add(lblLeft);

		btnLeftButton.addActionListener(this);
		btnLeftButton.setBounds(10, 109, 138, 23);
		frame.getContentPane().add(btnLeftButton);
		
		btnClear.addActionListener(this);
		btnClear.setBounds(10, 227, 138, 23);
		frame.getContentPane().add(btnClear);
		
		btnEncode.addActionListener(this);
		btnEncode.setBounds(239, 227, 89, 23);
		frame.getContentPane().add(btnEncode);
		
		btnDecode.addActionListener(this);
		btnDecode.setBounds(335, 227, 89, 23);
		frame.getContentPane().add(btnDecode);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		lblEnterPassword.setBounds(10, 159, 117, 14);
		frame.getContentPane().add(lblEnterPassword);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnLeftButton) {

            JFileChooser openFile = new JFileChooser();
            openFile.showOpenDialog(null);
            File inFile = openFile.getSelectedFile();
            if(inFile!=null) {
            	System.out.println(inFile.getAbsolutePath());
            	if(txtrLeft.getText().length()==0) {
            		txtrLeft.setText(inFile.getAbsolutePath());
            	} else {
            		txtrLeft.setText(txtrLeft.getText()+"\r\n"+inFile.getAbsolutePath());
            	}
            }
		}
		if (evt.getSource() == btnClear) {

            txtrRight.setText("");
            txtrLeft.setText("");
		}

		btnEncode:
		if (evt.getSource() == btnEncode) {

            if(txtrRight.getText().isEmpty()) {
            	infoBox("Password is required!", "Password Missing");
            	break btnEncode;
            }
            if(txtrLeft.getText().isEmpty()) {
            	infoBox("Filename is required!", "Filename Missing");
            	break btnEncode;
            }
            //parse for multiple input files
            int i=0;
            Scanner scanner = new Scanner(txtrLeft.getText());
            while (scanner.hasNextLine()) {
            	String szLine = scanner.nextLine();
            	i++;
            	EncoderUtil util = new EncoderUtil("encode", szLine, txtrRight.getText());
            	try{
            		int result = util.execute();
            		File oFile = new File("./encoder.out");
            		File newName = new File("./encoder"+i+".out");
            		if(newName.exists()) {
            			newName.delete();
            		}
            		oFile.renameTo( newName);
            		System.out.println("result is in file "+newName.getPath());
            		infoBox("Result is in file "+newName.getPath(), "Encoding Complete");
            	} catch(Exception e) {
            		System.out.println("encoding failed!");
            		infoBox("Encoding could not be completed!", "Encoding Failed");
            	}
            }
            scanner.close();				
		}
		
		btnDecode:
		if (evt.getSource() == btnDecode) {

            if(txtrRight.getText().isEmpty()) {
            	infoBox("Password is required!", "Password Missing");
            	break btnDecode;
            }
            if(txtrLeft.getText().isEmpty()) {
            	infoBox("Filename is required!", "Filename Missing");
            	break btnDecode;
            }
            //parse for multiple input files
            int i=0;
            Scanner scanner = new Scanner(txtrLeft.getText());
            while (scanner.hasNextLine()) {
            	String szLine = scanner.nextLine();
            	i++;
            	EncoderUtil util = new EncoderUtil("decode", szLine, txtrRight.getText());
            	try{
            		int result = util.execute();
            		File oFile = new File("./encoder.out");
            		File newName = new File("./encoder"+i+".out");
            		if(newName.exists()) {
            			newName.delete();
            		}
            		oFile.renameTo( newName);
            		System.out.println("result is in file "+newName.getPath());
            		infoBox("Result is in file "+newName.getPath(), "Encoding Complete");
            	} catch(Exception e) {
            		System.out.println("encoding failed!");
            		infoBox("Encoding could not be completed!", "Encoding Failed");
            	}
            }
            scanner.close();				
		}

	}//end method
	
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}//end class
