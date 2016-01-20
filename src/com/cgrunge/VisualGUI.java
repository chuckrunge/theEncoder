package com.cgrunge;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class VisualGUI extends JFrame {

	static final long serialVersionUID = -0L;

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void iMain(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualGUI frame = new VisualGUI();
					frame.setTitle("ENCODER");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void iVisualGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(7, 7, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));

		JLabel lblNew1 = new JLabel("Input File(s) to Process");
		contentPane.add(lblNew1, "2, 2");

		JLabel lblNew2 = new JLabel("Output File to Create");
		contentPane.add(lblNew2, "4, 2");

		// textField = new JTextField();
		// contentPane.add(textField, "6, 2, fill, default");
		// textField.setColumns(10);

		JTextArea leftEditBox = new JTextArea();
		leftEditBox.setText("files to process listed here");
		leftEditBox.setColumns(20);
		leftEditBox.setLineWrap(true);
		leftEditBox.setRows(5);
		leftEditBox.setWrapStyleWord(true);
		leftEditBox.setEditable(false);
		// JScrollPane jScrollPane1 = new JScrollPane(leftEditBox);
		contentPane.add(leftEditBox, "2, 4, fill, default");

		JTextArea rightEditBox = new JTextArea();
		rightEditBox.setText("files to process listed here");
		rightEditBox.setColumns(20);
		rightEditBox.setLineWrap(true);
		rightEditBox.setRows(5);
		rightEditBox.setWrapStyleWord(true);
		rightEditBox.setEditable(false);
		// JScrollPane jScrollPane1 = new JScrollPane(leftEditBox);
		contentPane.add(rightEditBox, "4, 4, fill, default");

		// JComboBox comboBox = new JComboBox();
		// contentPane.add(comboBox, "2, 4, fill, default");
		// table = new JTable();
		// contentPane.add(table, "2, 6, fill, fill");
		
		JButton btnInput = new JButton("Add Input");
		contentPane.add(btnInput, "2, 6");
		JButton btnOutput = new JButton("Output File");
		contentPane.add(btnOutput, "4, 6");
		JButton btnProcess = new JButton("Process");
		contentPane.add(btnProcess, "6, 6"); // col, row
	}

}
