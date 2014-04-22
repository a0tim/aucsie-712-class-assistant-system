import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.omg.CORBA.IdentifierHelper;

public class seat712 extends JFrame implements ActionListener{
	static ImageIcon icon;
	static List<HashMap<String,String>> Data;
	static List<JButton> BArr;
	static List<JTextField> TArr1,TArr2;
	static List<JPanel> PArr;
	static String WeekDate[] = new String[18];
	static Boolean hasData[] = new Boolean[18];
	static char Seat[] = {'A','B','C','D','E','F','G'};
	static Boolean Direction = true;
	static String FileType = "null";//file,seat,new,null
	static int ThisWeek = 0,NowWeek = 0;
	
	static JMenuBar JMB = new JMenuBar();
	static JMenu JM_file,JM_edit,JM_seat,JM_score,JM_week,JM_other;
	static JLabel YEAR = new JLabel("OOO�Ǧ~��_��O�Ǵ�");
	static JLabel TEACHER = new JLabel("XXX�Ѯv");
	static JLabel WEEKDATE = new JLabel("00/00");
	static JLabel CLASSNAME = new JLabel("�ҵ{�W��");
	static JLabel WEEK = new JLabel("�ثe�˵�:00�g    ���g:00�g");
	static JLabel CLOCK = new JLabel();
	static JTextArea STATUS = new JTextArea("�w��ϥ�712�Ұ󻲧U�t��,�зs�W�ζפJ�y���H�~��");
	static JButton EDIT = new JButton("�s�觹��");
	
	public static void main(String[] args) {
		new seat712();
	}
	
	private seat712(){
		setSize(900,685);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setTitle("�u�z�j�Ǹ�T�u�{�Ǩt_712�Ы�_�Ұ󻲧U�t��");
		icon = new ImageIcon(this.getClass().getResource("/icon.png"));
		setIconImage(icon.getImage());
		
		YEAR.setBounds(10, 5, 135, 20);
		add(YEAR);
		TEACHER.setBounds(10, 25, 135, 20);
		add(TEACHER);
		CLASSNAME.setFont(new Font(null, Font.BOLD, 35));
		CLASSNAME.setBounds(310, 5, 400, 40);
		add(CLASSNAME);
		WEEKDATE.setFont(new Font(null, Font.BOLD, 35));
		WEEKDATE.setBounds(180, 5, 135, 40);
		add(WEEKDATE);
		WEEK.setBounds(705, 5, 180, 20);
		WEEK.setFont(new Font(null, Font.BOLD, 14));
		add(WEEK);
		CLOCK.setBounds(705, 25, 180, 20);
		CLOCK.setFont(new Font(null, Font.BOLD, 14));
		CLOCK.setBackground(new Color(170,220,255));
		CLOCK.setOpaque(true);
		add(CLOCK);
		new Thread(new Clock(CLOCK)).start();
		STATUS.setFont(new Font(null, 0, 13));
		STATUS.setEditable(false);
		STATUS.setBackground(new Color(200,255,255));
		JScrollPane JSP = new JScrollPane(STATUS);
		JSP.setBounds(10, 607, 875, 25);
		JSP.setAutoscrolls(true);
		add(JSP);
		EDIT.setBounds(150, 555, 135, 20);
		EDIT.setVisible(false);
		EDIT.setBackground(Color.CYAN);
		EDIT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JM_seat.getItem(1).setEnabled(true);
				EDIT.setVisible(false);
				DoneEdit();
			}
		});
		add(EDIT);		
		
		GridLayout GL = new GridLayout(3,1);
		GL.setVgap(5);
		BArr = new ArrayList<JButton>();
		TArr1 = new ArrayList<JTextField>();
		TArr2 = new ArrayList<JTextField>();
		PArr = new ArrayList<JPanel>();
		for(int i=0;i<7;i++){
			for(int j=0;j<12;j++){
				JPanel JP = new JPanel();
				JP.setLayout(GL);
				JP.setBounds(j<4?(j*70)+10:j<8?(j*70)+30:(j*70)+50,(i*80)+50, 65, 75);
				JButton JB = new JButton((String.valueOf(Seat[i])+(j+1)));
				JTextField JT1 = new JTextField();
				JTextField JT2 = new JTextField();
				JB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton B = (JButton)e.getSource();
						if(B.getBackground() == Color.RED){
							B.setForeground(Color.BLACK);
							B.setBackground(Color.GREEN);
						}else if(B.getBackground() == Color.BLUE){
							B.setForeground(Color.WHITE);
							B.setBackground(Color.RED);
						}else if(B.getBackground() == Color.GREEN){
							B.setForeground(Color.WHITE);
							B.setBackground(Color.BLUE);
						}
					}
				});
				JB.setEnabled(false);
				JT1.setHorizontalAlignment(JTextField.CENTER);
				JT1.setEnabled(false);
				JT1.setEditable(false);
				JT2.setHorizontalAlignment(JTextField.CENTER);
				JT2.setEnabled(false);
				JT2.setEditable(false);
				
				JP.add(JB);JP.add(JT1);JP.add(JT2);
				BArr.add(JB);TArr1.add(JT1);TArr2.add(JT2);PArr.add(JP);
				if(i==6 && j<4)
					JP.setVisible(false);
				add(JP);
			}
		}
		JM_file = new JMenu("�ɮ�");
		JM_file.add(new JMenuItem("Ū���Ұ���")).addActionListener(this);
		JM_file.add(new JMenuItem("�x�s�Ұ���")).addActionListener(this);
		JM_file.getItem(1).setEnabled(false);
		JM_file.add(new JMenuItem("���}")).addActionListener(this);
		
		JM_edit = new JMenu("�s��");
		JM_edit.add(new JMenuItem("���g�L�ҵ{���")).addActionListener(this);
		JM_edit.add(new JMenuItem("�s��L�����")).addActionListener(this);
		JM_edit.add(new JMenuItem("��糧�g�W�Ҥ��")).addActionListener(this);
		JM_edit.setEnabled(false);
		
		JM_seat = new JMenu("�y���");
		JM_seat.add(new JMenuItem("�s�W")).addActionListener(this);
		JM_seat.add(new JMenuItem("�ק�")).addActionListener(this);
		JM_seat.getItem(1).setEnabled(false);
		JM_seat.add(new JMenuItem("�פJ")).addActionListener(this);
		JM_seat.add(new JMenuItem("�ץX(���]�t�ҵ{���)")).addActionListener(this);
		JM_seat.getItem(3).setEnabled(false);
		
		JM_score = new JMenu("�Ұ����");
		JM_score.add(new JMenuItem("�s�襻�g���Z")).addActionListener(this);
		JM_score.setEnabled(false);
		
		JM_week = new JMenu("�g��(0)");
		ButtonGroup BG = new ButtonGroup();
		JRadioButtonMenuItem JRB = null;
		for(int i=0;i<19;i++){
			JRB = new JRadioButtonMenuItem((i>0?"��"+i+"�g":"�`��"));
			JRB.addActionListener(this);
			JRB.setName(String.valueOf(i));
			BG.add(JRB);
			JM_week.add(JRB);
		}
		JM_week.setEnabled(false);
		
		JM_other = new JMenu("��L");
		JM_other.add(new JMenuItem("�y�����")).addActionListener(this);
		JM_other.add(new JMenuItem("����")).addActionListener(this);
		JM_other.add(new JMenuItem("����")).addActionListener(this);
		
		JMB.add(JM_file);
		JMB.add(JM_edit);
		JMB.add(JM_seat);
		JMB.add(JM_score);
		JMB.add(JM_week);
		JMB.add(JM_other);
		setJMenuBar(JMB);
		setVisible(true);
		
		JLabel JL = new JLabel(icon);
		JTextArea JTA = new JTextArea("\n\n�Ѯv�z�n\n�w��ϥ�712�Ұ󻲧U�t��\n"
				+ "�����ϥνзs�W�ζפJ�y���\n��L�Բӻ������I�� -��L-");
		JTA.setEditable(false);
		JTA.setBackground(new Color(240,240,240));
		JPanel JP = new JPanel();
		JP.setLayout(new GridLayout(1,2));
		JP.add(JL);JP.add(JTA);
		JOptionPane.showMessageDialog(this,JP,"Welcome",JOptionPane.PLAIN_MESSAGE);
	}
	
	private void ChangeDirection(){
		if(!Direction){
			for(int i=0;i<BArr.size();i++){
				JButton JB = BArr.get(i);
				JTextField JT1 = TArr1.get(i);
				JTextField JT2 = TArr2.get(i);
				JPanel JP = PArr.get(i);
				JP.removeAll();
				JP.setVisible(true);
				JP.add(JB);JP.add(JT1);JP.add(JT2);
				if(JB.getText().equals("G1") || JB.getText().equals("G2")
						|| JB.getText().equals("G3") || JB.getText().equals("G4")){
					JP.setVisible(false);
				}
			}
			EDIT.setLocation(150, 555);
			Direction = true;
		}else{
			for(int i=0;i<BArr.size();i++){
				JButton JB = BArr.get(i);
				JTextField JT1 = TArr1.get(i);
				JTextField JT2 = TArr2.get(i);
				JPanel JP = PArr.get(BArr.size()-1-i);
				JP.removeAll();
				JP.setVisible(true);
				JP.add(JB);JP.add(JT1);JP.add(JT2);
				if(JB.getText().equals("G1") || JB.getText().equals("G2")
						|| JB.getText().equals("G3") || JB.getText().equals("G4")){
					JP.setVisible(false);
				}
			}
			EDIT.setLocation(610, 75);
			Direction = false;
		}
		SetStatus(0,"�ܧ�y���V,�ثe��"+(Direction?"�ǥͨ����˵�":"�Юv�����˵�"));
	}
	
	private void UpdateInfo(String y,String t,String c) {
		YEAR.setText(y);
		TEACHER.setText(t);
		CLASSNAME.setText(c);
	}
	
	private void UpdateAttend(){
		for(HashMap<String,String> HM:Data){
			for(JButton JB:BArr){
				if(HM.get("seat").equals(JB.getText())){
					if(JB.getBackground().equals(Color.GREEN)){
						HM.put(NowWeek+"-attend","�X�u");
					}else if(JB.getBackground().equals(Color.BLUE)){
						HM.put(NowWeek+"-attend","���");
					}else if(JB.getBackground().equals(Color.RED)){
						HM.put(NowWeek+"-attend","�m��");
					}
				}
			}
		}
	}
	
	private void UpdateSeat(){
		WEEK.setText("�ثe�˵�:"+NowWeek+"�g    ���g:"+ThisWeek+"�g");
		JM_week.setText("�g��("+NowWeek+")");
		JM_week.getItem(NowWeek).setSelected(true);
		for(int i=0;i<19;i++){
			if(i==0){
				JM_week.getItem(i).setEnabled(true);
			}else if(i<=ThisWeek && hasData[i-1]){
				JM_week.getItem(i).setEnabled(true);
			}else{
				JM_week.getItem(i).setEnabled(false);
			}
		}
		if(ThisWeek==1){
			JM_edit.getItem(1).setEnabled(false);
		}else{
			JM_edit.getItem(1).setEnabled(true);
		}
		if(NowWeek == ThisWeek){
			JM_edit.getItem(0).setEnabled(true);
			JM_edit.getItem(2).setEnabled(true);
			JM_seat.getItem(1).setEnabled(true);
			JM_score.setEnabled(true);
		}else{
			JM_edit.getItem(0).setEnabled(false);
			JM_edit.getItem(2).setEnabled(false);
			JM_seat.getItem(1).setEnabled(false);
			JM_score.setEnabled(false);
		}
		for(HashMap<String,String> HM:Data){
			int index = 0;
			for(JButton JB:BArr){
				if(JB.getText().equals(HM.get("seat"))){
					index = BArr.indexOf(JB);
					break;
				}
			}
			TArr1.get(index).setText(HM.get("id"));
			TArr2.get(index).setText(HM.get("name"));
			if(HM.get("id").length()==8){
				if(HM.get(NowWeek+"-attend").equals("�X�u")){
					BArr.get(index).setBackground(Color.GREEN);
					BArr.get(index).setForeground(Color.BLACK);
				}else if(HM.get(NowWeek+"-attend").equals("���")){
					BArr.get(index).setBackground(Color.BLUE);
					BArr.get(index).setForeground(Color.WHITE);
				}else if(HM.get(NowWeek+"-attend").equals("�m��")){
					BArr.get(index).setBackground(Color.RED);
					BArr.get(index).setForeground(Color.WHITE);
				}
				BArr.get(index).setEnabled(NowWeek == ThisWeek?true:false);
				TArr1.get(index).setEnabled(true);
				TArr2.get(index).setEnabled(true);
			}else{
				BArr.get(index).setBackground(Color.LIGHT_GRAY);
				BArr.get(index).setForeground(Color.BLACK);
				BArr.get(index).setEnabled(false);
				TArr1.get(index).setEnabled(false);
				TArr2.get(index).setEnabled(false);
			}			
		}
		WEEKDATE.setText(WeekDate[NowWeek-1]);
		SetStatus(0, "�ثe��ܪ��O ��"+NowWeek+"�g �I�W����");
	}
	
	//after NewSeat or ReadSeat
	private void InitialData(){
		Data = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<7*12;i++){
			HashMap<String,String> HM = new HashMap<String,String>();
			HM.put("seat", BArr.get(i).getText());
			HM.put("id", TArr1.get(i).getText());
			HM.put("name", TArr2.get(i).getText());
			for(int j=1;j<19;j++){
				HM.put(j+"-attend","-");
				HM.put(j+"-score","-");
			}
			Data.add(HM);
		}
		for(int i=0;i<18;i++)
			hasData[i] = false;
		JM_file.getItem(0).setEnabled(false);
		JM_file.getItem(1).setEnabled(true);
		JM_seat.getItem(0).setEnabled(false);
		JM_seat.getItem(2).setEnabled(false);
		JM_seat.getItem(3).setEnabled(true);
	}
	
	private void NewSeat(){
		JTextField t[] = new JTextField[8];
		t[0] = new JTextField("�Ǧ~��:(ex:102)");
        t[0].setEditable(false);
        t[1] = new JTextField();
        t[2] = new JTextField("�Ǵ�:(�W or �U)");
        t[2].setEditable(false);
        t[3] = new JTextField();
        t[4] = new JTextField("�Юv:");
        t[4].setEditable(false);
        t[5] = new JTextField();
        t[6] = new JTextField("�ҵ{�W��:(�̦h10�r)");
        t[6].setEditable(false);
        t[7] = new JTextField();
        String but[] = new String[] {"�T�w","����"};
		do{
			int result = JOptionPane.showOptionDialog(null, t, "��J�ҵ{��T",
	                      JOptionPane.YES_NO_OPTION,
	                      JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
			t[1].setBackground(Color.WHITE);
			t[3].setBackground(Color.WHITE);
			t[5].setBackground(Color.WHITE);
			t[7].setBackground(Color.WHITE);
	        if (result == 0) {
	        	if(t[1].getText().length()==3){
	        		if(t[3].getText().length()==1){
	        			if(t[5].getText().length()>0){
		        			if(t[7].getText().length()>0 && t[7].getText().length()<11){
		        				UpdateInfo(t[1].getText()+"�Ǧ~��_"+t[3].getText()+"�Ǵ�",
		    	        				t[5].getText()+"�Ѯv",t[7].getText());
		    	        		TArr1.get(0).setText("�Ǹ�");
		    	        		TArr2.get(0).setText("�m�W");
		    					EDIT.setVisible(true);
		    					FileType = "new";
		    					ThisWeek = 1;
		    					NowWeek = 1;
		    					WeekDate[ThisWeek-1] = new SimpleDateFormat("MM/dd").format(new Date());
		    					InitialData();
		    					hasData[0] = true;
		    					UpdateSeat();
		    					StartEdit();
		    					break;
		        			}else{
		        				t[7].setBackground(Color.YELLOW);
		        			}
		        		}else{
		        			t[5].setBackground(Color.YELLOW);
		        		}
	        		}else{
	        			t[3].setBackground(Color.YELLOW);
	        		}
	        	}else{
	        		t[1].setBackground(Color.YELLOW);
	        	}
	        }else{
	        	SetStatus(1, "�ж�J����ҵ{��T�H�s�W�y���!!!");
	        	break;
	        }
		}while(true);
	}
	
	private void StartEdit(){
		for(int i=0;i<BArr.size();i++){
			BArr.get(i).setEnabled(false);
			BArr.get(i).setBackground(Color.LIGHT_GRAY);
			TArr1.get(i).setEditable(true);
			TArr1.get(i).setEnabled(true);
			TArr2.get(i).setEditable(true);
			TArr2.get(i).setEnabled(true);
		}
		JM_file.setEnabled(false);
		JM_edit.setEnabled(false);
		JM_seat.setEnabled(false);
		JM_score.setEnabled(false);
		JM_week.setEnabled(false);
		EDIT.setVisible(true);
		SetStatus(0, "�y���ק粒�����I��-�s�觹��-");
	}
	
	private void DoneEdit(){
		for(int i=0;i<BArr.size();i++){
			TArr1.get(i).setEditable(false);
			TArr2.get(i).setEditable(false);
			for(HashMap<String,String> HM:Data){
				if(HM.get("seat").equals(BArr.get(i).getText())){
					if(TArr1.get(i).getText().length()==8){
						HM.put("id",TArr1.get(i).getText());
						HM.put("name",TArr2.get(i).getText());
						HM.put(NowWeek+"-attend","�X�u");
					}else{
						HM.put("id","-");
						HM.put("name","-");
						HM.put(NowWeek+"-attend","-");
					}
					break;
				}
			}
		}
		if(FileType.equals("new")){
			SaveSeat();
			FileType = "seat";
		}
		JM_file.setEnabled(true);
		JM_edit.setEnabled(true);
		JM_seat.setEnabled(true);
		JM_seat.getItem(1).setEnabled(true);
		JM_score.setEnabled(true);
		JM_week.setEnabled(true);
		UpdateSeat();
		SetStatus(0, "�ק�y�즨�\!!!");
	}
	
	private void ReadSeat() {
		JFileChooser JFC = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
		JFC.setFileFilter(filter);
		int returnVal = JFC.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File F = JFC.getSelectedFile();
			try {
				BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(F)));
				String info = BR.readLine();
				StringTokenizer STinfo = new StringTokenizer(info,",");
				if(!info.substring(3,6).equals("�Ǧ~��")){
					JOptionPane.showConfirmDialog(this, "�ɮ׮榡���~", "ĵ�i", JOptionPane.CLOSED_OPTION
							,JOptionPane.ERROR_MESSAGE);
					return;
				}
				UpdateInfo(STinfo.nextToken(),STinfo.nextToken(),STinfo.nextToken());
				InitialData();
				hasData[0] = true;
				String S = "";
				while((S = BR.readLine())!=null){
					StringTokenizer ST = new StringTokenizer(S,", -");
					String seat = ST.nextToken();
					for(HashMap<String,String> HM:Data){
						if(HM.get("seat").equals(seat)){
							HM.put("1-attend",ST.hasMoreTokens()?"�X�u":"-");
							HM.put("id",ST.hasMoreTokens()?ST.nextToken():"-");
							HM.put("name",ST.hasMoreTokens()?ST.nextToken():"-");
							break;
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JM_file.getItem(0).setEnabled(false);
			JM_file.getItem(1).setEnabled(true);
			JM_edit.setEnabled(true);
			JM_seat.getItem(0).setEnabled(false);
			JM_seat.getItem(1).setEnabled(true);
			JM_seat.getItem(2).setEnabled(false);
			JM_seat.getItem(3).setEnabled(true);
			JM_score.setEnabled(true);
			JM_week.setEnabled(true);
			
			FileType = "seat";
			ThisWeek = 1;
			NowWeek = ThisWeek;
			WeekDate[ThisWeek-1] = new SimpleDateFormat("MM/dd").format(new Date());
			hasData[ThisWeek-1] = true;
			UpdateSeat();
			SetStatus(0, "�y���פJ���\,�z�i�H�}�l�I�W,�I���y����s���X�u���p,�I�W�����п��-�ɮ�-�x�s�Ұ���-");
		}
	}

	private void SaveSeat(){
		JFileChooser JFC =new JFileChooser();
		JFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		JFC.setApproveButtonText("�x�s");
		JFC.setDialogTitle("�x�s���|���ɦW");
		JFC.setSelectedFile(new File(YEAR.getText()+"_"+CLASSNAME.getText()+"_�y���.csv"));
		do{
			int returnVal = JFC.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				String Path = JFC.getSelectedFile().getAbsolutePath();
				File F = new File(Path+(Path.endsWith(".csv")?"":".csv"));
				String S = YEAR.getText()+","+TEACHER.getText()+","+CLASSNAME.getText()+"\r\n";
				for(int i=0;i<7;i++){
					for(int j=0;j<12;j++){
						S += BArr.get((i*12)+j).getText()+",";
						S += TArr1.get((i*12)+j).getText()+",";
						S += TArr2.get((i*12)+j).getText()+"\r\n";
					}
				}
				try {
					F.createNewFile();
					BufferedWriter B = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(F)));
					B.write(S);B.flush();
					B.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				SetStatus(0, "�y���ץX���\,�U���I�W�i�����פJ�y���");
				break;
			}else if(!FileType.equals("new")){
				break;
			}
		}while(true);
	}
	
	private void ReadFile(){
		JFileChooser JFC = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
		JFC.setFileFilter(filter);
		int returnVal = JFC.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File F = JFC.getSelectedFile();
			try {
				BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(F)));
				String info = BR.readLine();
				StringTokenizer STinfo = new StringTokenizer(info,",");
				if(!info.substring(3,6).equals("�Ǧ~��")){
					JOptionPane.showConfirmDialog(this, "�ɮ׮榡���~", "ĵ�i", JOptionPane.CLOSED_OPTION
							,JOptionPane.ERROR_MESSAGE);
					return;
				}
				UpdateInfo(STinfo.nextToken(),STinfo.nextToken(),STinfo.nextToken());
				ThisWeek = Integer.parseInt(STinfo.nextToken())+1;
				InitialData();
				info = BR.readLine();
				StringTokenizer Winfo = new StringTokenizer(info,",");
				Winfo.nextToken();Winfo.nextToken();Winfo.nextToken();
				for(int i=0;i<18;i++){
					WeekDate[i] = Winfo.nextToken();
					hasData[i] = Winfo.nextToken().equals("true")?true:false;
				}
				String S = "";
				while((S = BR.readLine())!=null){
					StringTokenizer ST = new StringTokenizer(S,", ");
					String seat = ST.nextToken();
					for(HashMap<String,String> HM:Data){
						if(HM.get("seat").equals(seat)){
							HM.put("id",ST.nextToken());
							HM.put("name",ST.nextToken());
							for(int i=0;i<18;i++){
								if(HM.get("id").equals("-")){
									HM.put((i+1)+"-attend",ST.nextToken());
								}else{
									HM.put((i+1)+"-attend",(i+1)==ThisWeek?"�X�u":ST.nextToken());
								}								
								HM.put((i+1)+"-score",ST.nextToken());
							}
							break;
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JM_file.getItem(0).setEnabled(false);
			JM_edit.setEnabled(true);
			JM_seat.getItem(0).setEnabled(false);
			JM_seat.getItem(1).setEnabled(true);
			JM_seat.getItem(2).setEnabled(false);
			JM_score.setEnabled(true);
			JM_week.setEnabled(true);
			FileType = "file";
			NowWeek = ThisWeek;
			WeekDate[ThisWeek-1] = new SimpleDateFormat("MM/dd").format(new Date());
			hasData[ThisWeek-1] = true;
			UpdateSeat();
			SetStatus(0, "�ҵ{��ƶפJ���\,�z�i�H�}�l�I�W,�I���y����s���X�u���p,�I�W�����п��-�ɮ�-�x�s�Ұ���-");
		}
	}
	
	private void SaveFile(){
		UpdateAttend();
		JFileChooser JFC = new JFileChooser();
		JFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		JFC.setApproveButtonText("�x�s");
		JFC.setDialogTitle("�x�s���|���ɦW");
		JFC.setSelectedFile(new File(YEAR.getText()+"_"+CLASSNAME.getText()+"_�ҵ{���.csv"));
		int returnVal = JFC.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			String Path = JFC.getSelectedFile().getAbsolutePath();
			File F = new File(Path+(Path.endsWith(".csv")?"":".csv"));
			
			String S = YEAR.getText()+","+TEACHER.getText()+","+CLASSNAME.getText()+","
					+ThisWeek+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\r\n";
			S += "�y��,�Ǹ�,�m�W";
			for(int i=0;i<18;i++){
				S += ","+WeekDate[i]+","+hasData[i];
			}
			S += "\r\n";
			for(HashMap<String,String> HM:Data){
				S += HM.get("seat")+","+HM.get("id")+","+HM.get("name");
				for(int i=0;i<18;i++){
					S += ","+HM.get((i+1)+"-attend")+","+HM.get((i+1)+"-score");
				}
				S += "\r\n";
			}
			try {
				F.createNewFile();
				BufferedWriter B = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(F)));
				B.write(S);B.flush();
				B.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void ViewInfo(){
		String info = "�u�z�j�Ǹ�T�u�{�Ǩt_712�Ы�_�Ұ󻲧U�t��\n\n"+
				"�������ϥνзs�W�y����J�ǥ͸��\n�é�s�觹�����x�s�y���\n\n"+
				"���Ĥ@�g�W�ҳz�L�פJ�y���}�l�s��Ұ���\n���t�Υi�O���C�g�W�Ҹ�T(�I�W����,�Ұ��Z)\n\n"+
				"������C�g�W�ҥi�Q�Ϋe�����x�s���ҵ{��ƶi��s�@�g���Ұ�";
		JOptionPane.showMessageDialog(this,info);
	}
	
	private void ViewAbout(){
		JLabel JL = new JLabel();
		JL.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				String cmd = "rundll32 "+"url.dll,FileProtocolHandler "
						+"http://fb.com/yaohailong";
		        try {
					Process p = Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		JL.setText("<html>�u�z�j�Ǹ�T�u�{�Ǩt_712�Ы�_�Ұ󻲧U�t��<br>"
				+"Build: v 1.0-20140421<br>�s�@: �u�z��u  �����f<br><br>"
				+"<a href=\"http://fb.com/yaohailong\">�u�z��u �f���s</a></html>");
		JOptionPane.showMessageDialog(this,JL,
				"����",JOptionPane.CLOSED_OPTION,
				new ImageIcon(this.getClass().getResource("/lute.png")));
	}
	
	private void ViewData(){
		String c[] = new String[39];
		c[0] = "�y��";c[1] = "�Ǹ�";c[2] = "�m�W";
		for(int i=3;i<39;i+=2){
			c[i] = ((i-1)/2)+"-�X�u";
			c[i+1] = ((i-1)/2)+"-���Z";
		}
		String t[][] = new String[84][39];
		int i=0;
		for(HashMap<String,String> HM:Data){
			t[i][0] = HM.get("seat");
			t[i][1] = HM.get("id");
			t[i][2] = HM.get("name");
			for(int j=3;j<39;j+=2){
				t[i][j] = HM.get(((j-1)/2)+"-attend");
				t[i][j+1] = HM.get(((j-1)/2)+"-score");
			}
			i++;
		}
		JTable JT = new JTable(t,c);
		JT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JT.setShowHorizontalLines(false);
		JT.setSelectionMode(0);
		JT.setRowSorter(new TableRowSorter<TableModel>(JT.getModel()));
		for(int x=0;x<JT.getColumnCount();x++){
			int width = 0;
			int Row = JT.getRowCount();
			for (int ii=0;ii<Row;ii++) {
				TableCellRenderer renderer = JT.getCellRenderer(ii, x);
				Component comp = renderer.getTableCellRendererComponent(
						JT, JT.getValueAt(ii, x),false, false, ii, x);
				int thisWidth = comp.getPreferredSize().width;
				if (thisWidth > width)
					width = thisWidth;
			}
			if(x>2 && width<40)
				width = 40;
			JT.getColumnModel().getColumn(x).setPreferredWidth(width+10);
			JT.getColumnModel().getColumn(x).setResizable(false);
		}
		JScrollPane JSP = new JScrollPane(JT);
		JSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(this,JSP,"�I�����W�٥i�H�Ƨ�",JOptionPane.PLAIN_MESSAGE);
	}
	
	private void NoData(){
		for(HashMap<String,String> HM:Data){
			HM.put(NowWeek+"-attend","-");
		}
		for(JButton JB:BArr){
			if(TArr1.get(BArr.indexOf(JB)).getText().length()==8){
				JB.setBackground(Color.GREEN);
				JB.setForeground(Color.BLACK);
			}
		}
		hasData[NowWeek-1] = false;
		hasData[NowWeek] = true;
		ThisWeek++;
		NowWeek = ThisWeek;
		WeekDate[ThisWeek-1] = new SimpleDateFormat("MM/dd").format(new Date());
		UpdateSeat();
	}
	
	private void EditScore(){
		String c[] = {"�y��","�Ǹ�","�m�W","���g���Z"};
		String t[][] = new String[84][4];
		int i=0;
		for(HashMap<String,String> HM:Data){
			t[i][0] = HM.get("seat");
			t[i][1] = HM.get("id");
			t[i][2] = HM.get("name");
			t[i][3] = HM.get(NowWeek+"-score");
			i++;
		}
		JTable JT = new JTable(t,c);
		JT.setShowHorizontalLines(false);
		JT.setSelectionMode(0);
		JT.setRowSorter(new TableRowSorter<TableModel>(JT.getModel()));
		JT.putClientProperty("terminateEditOnFocusLost", true);
		JScrollPane JSP = new JScrollPane(JT);
		JSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		int cho = JOptionPane.showConfirmDialog(this,JSP,"�������Z���H�s��",
				JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(cho==0){
			for(i=0;i<84;i++){
				for(HashMap<String,String> HM:Data){
					if(HM.get("seat").equals(JT.getValueAt(i,0))){
						HM.put(NowWeek+"-score",String.valueOf(JT.getValueAt(i,3)));
						break;
					}
				}
			}
			SetStatus(0,"���Z�s�觹��,�ҵ{��Ƥw��s!!!");
		}else{
			SetStatus(1,"�����s�覨�Z!!!");
		}
	}
	
	private void ChangeWeekDate(){
		String d = JOptionPane.showInputDialog(this,"�п�J���:(mm/dd)");
		if(d.matches("[0-1][0-9]/[0-3][0-9]")){
			WeekDate[NowWeek-1] = d;
			UpdateSeat();
		}else{
			SetStatus(1, "����榡���~!!!");
		}
	}
	
	private void SetStatus(int i,String S){
		switch(i){
		case 0:
			STATUS.setForeground(Color.BLACK);
			STATUS.setText(STATUS.getText()+"\n"+S);
			break;
		case 1:
			STATUS.setForeground(Color.RED);
			STATUS.setText(STATUS.getText()+"\n"+S);
			break;
		}
	}
	
	private class Clock implements Runnable{
		JLabel CLOCK;
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy/MM/dd    aaa hh:mm:ss");
		
		public Clock(JLabel CLOCK) {
			this.CLOCK = CLOCK;
		}
		@Override
		public void run() {
			while(true){
				CLOCK.setText(fomat.format(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem M = (JMenuItem)e.getSource();
		if(M.getText().equals("Ū���Ұ���")){
			ReadFile();
		}else if(M.getText().equals("�x�s�Ұ���")){
			SaveFile();
		}else if(M.getText().equals("���}")){
			System.exit(0);
		}else if(M.getText().equals("���g�L�ҵ{���")){
			NoData();
		}else if(M.getText().equals("�s��L�����")){
			JOptionPane.showMessageDialog(this,"�����\�ॼ�}��");
		}else if(M.getText().equals("��糧�g�W�Ҥ��")){
			ChangeWeekDate();
		}else if(M.getText().equals("�s�W")){
			NewSeat();
		}else if(M.getText().equals("�ק�")){
			if(JOptionPane.showConfirmDialog(this,"�ק�y��N����I�W����\n�T�{�ק�?","�T�{�ק�",
					JOptionPane.OK_CANCEL_OPTION) == 0){
				StartEdit();
			}else{
				SetStatus(0,"�����ק�!!!");
			}
		}else if(M.getText().equals("�פJ")){
			ReadSeat();
		}else if(M.getText().equals("�ץX(���]�t�ҵ{���)")){
			SaveSeat();
		}else if(M.getText().equals("�s�襻�g���Z")){
			EditScore();
		}else if(M.getText().equals("�y�����")){
			ChangeDirection();
		}else if(M.getText().equals("����")){
			ViewInfo();
		}else if(M.getText().equals("����")){
			ViewAbout();
		}else if(M.getText().equals("�`��")){
			UpdateAttend();
			ViewData();
		}else{
			UpdateAttend();
			NowWeek = Integer.parseInt(M.getName());
			UpdateSeat();
		}
	}
}