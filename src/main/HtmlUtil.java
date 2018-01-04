package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;

public class HtmlUtil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea inputTextArea;
	private JTextArea outPutTextArea;
	private JButton transButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HtmlUtil frame = new HtmlUtil();
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
	public HtmlUtil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		transButton = new JButton("transform");
		transButton.setBackground(Color.BLUE);
		transButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outPutTextArea.setText(getTransStr());
			}
		});
		transButton.setBounds(20, 243, 410, 29);
		contentPane.add(transButton);
		
		outPutTextArea = new JTextArea();
		outPutTextArea.setBounds(20, 141, 410, 96);
		contentPane.add(outPutTextArea);
		
		inputTextArea = new JTextArea();
		inputTextArea.setBounds(20, 21, 410, 89);
		contentPane.add(inputTextArea);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(20, 4, 61, 16);
		contentPane.add(lblInput);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(20, 124, 61, 16);
		contentPane.add(lblOutput);
	}
	
	/**
	 * 转换逻辑
	 */
	private String getTransStr(){
		String inputStr = inputTextArea.getText().trim().replaceAll("\n", "");
		List<Integer> leftArray = new ArrayList<Integer>();
		List<Integer> rightArray = new ArrayList<Integer>();

		int leftArrowIndex = 0;
		int rightArrowIndex = 0;
		while(leftArrowIndex!=-1 && rightArrowIndex!=-1){
			int leftArrowRet = inputStr.indexOf("<", leftArrowIndex);
			int rightArrowRet = inputStr.indexOf(">", rightArrowIndex);
			
			if(leftArrowRet!=-1){
				leftArray.add(leftArrowRet);
				leftArrowIndex = leftArrowRet+1;
			}else{
				leftArrowIndex = -1;
			}
			
			if(rightArrowRet!=-1){
				rightArray.add(rightArrowRet);
				rightArrowIndex = rightArrowRet+1;
			}else{
				rightArrowIndex = -1;
			}
		}	

		if(leftArray.size() == 0 || rightArray.size() == 0){
			return "not a html string.";
		}
		
		if(leftArray.size() != rightArray.size()){
			return "Eorror size.";
		}
		
		List<HtmlSection> sectionArray = new ArrayList<HtmlSection>();
		int lastSectionEndIndex = rightArray.get(0);
		for(int i=0;i<leftArray.size();i++){
			int start = leftArray.get(i);
			int end = rightArray.get(i);
			
			if(lastSectionEndIndex+1 < start)	{
				HtmlSection contentSection = new HtmlSection(lastSectionEndIndex+1,start-1);
				sectionArray.add(contentSection);
			}	
			lastSectionEndIndex = end;
			
			HtmlSection section = new HtmlSection(start,end);
			sectionArray.add(section);
		}
		
		String ret = "";
		int length = sectionArray.size();
		for(int i=0;i<length;i++){
			ret += sectionArray.get(i).getTransString(inputStr);
			
			if(i!=(length-1)){
				ret += "+";
			}
		}
		return ret;
	}
}
