package xjx;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;

class ShadePanel extends JPanel {
    
    private static final long serialVersionUID = -2644424271663261406L;
    
    public ShadePanel() {
        super();
        setLayout(null);
    }
    
    @Override
    protected void paintComponent(Graphics g1) {// ��д����������
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);// ִ�г��෽��
        int width = getWidth();// ��ȡ�����С
        int height = getHeight();
        // �������ģʽ����
        GradientPaint paint = new GradientPaint(0, 0, Color.CYAN, 0, height,Color.MAGENTA);//ʵ����ɫ����
        //GradientPaint paint = new GradientPaint(0, 0, Color.red, 0, height,Color.blue);
        g.setPaint(paint);// ���û�ͼ��������ģʽ
        g.fillRect(0, 0, width, height);// ���ƾ������ؼ�����
    }
}
 
public class About extends JDialog {
    private static final long serialVersionUID = 4693799019369193520L;
    private JPanel contentPane;
    private Font f1 = new Font("΢���ź�",Font.PLAIN,15);
	private Font f2 = new Font("΢���ź�",Font.PLAIN,20);
	
    public About() {
        setTitle("���ڴ���Ϸ");//���ô������
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");//����ͼ��
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//����Ϊģ̬����
        setSize(410,360);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// �����������
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();// �������䱳�����
        contentPane.add(shadePanel, BorderLayout.CENTER);// �����嵽�����������
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("�汾��2017_07_17_3.0.0\n�����ߣ�XJX\n�������ԣ�Java\n"
    			+ "Email: thexjx@outlook.com\n\n��ӭ�����ҵ���ҳ:");
        J1.setFocusable(false);
    	J1.setFont(f2);
    	J1.setEditable(false);
    	J1.setOpaque(false);//����͸��
    	shadePanel.add(J1);
    	J1.setBounds(10, 10, 400, 180);
    	
    	JLabel MyGithub_Label = new JLabel("Github:");
    	MyGithub_Label.setFont(f2);
    	final JLabel MyGithub = new JLabel("https://github.com/JiaxinTse");
    	MyGithub.setFont(f2);
    	MyGithub.setBackground(Color.white);
    	MyGithub.addMouseListener(new InternetMonitor());
    	JLabel MyCnBlog_Label = new JLabel("����԰:");
    	MyCnBlog_Label.setFont(f2);
    	final JLabel MyCnBlog = new JLabel("http://www.cnblogs.com/journal-of-xjx/");
    	MyCnBlog.setFont(f2);
    	MyCnBlog.addMouseListener(new InternetMonitor());
    	JTextArea Copyright = new JTextArea("     	Copyright @XJX2017.\n   	  All rights reserved.");
    	Copyright.setFocusable(false);
    	Copyright.setOpaque(false);
    	Copyright.setFont(f1);
    	Copyright.setEditable(false);
    	
    	shadePanel.add(MyGithub_Label);
    	MyGithub_Label.setBounds(10, 180, 400, 20);
    	shadePanel.add(MyGithub);
    	MyGithub.setBounds(10, 200, 400, 30);
    	shadePanel.add(MyCnBlog_Label);
    	MyCnBlog_Label.setBounds(10, 230, 400, 20);
    	shadePanel.add(MyCnBlog);
    	MyCnBlog.setBounds(10, 250, 400, 30);
    	shadePanel.add(Copyright);
    	Copyright.setBounds(10, 280, 400, 50);
       
    	setVisible(true);
    }
    
//    public static void main(String[] args) {
//		new About();
//	}
}

class InternetMonitor extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		String J_temp = JLabel_temp.getText();
		System.out.println(J_temp);
		URI uri ;
			try {
				uri = new URI(J_temp);
				Desktop desk=Desktop.getDesktop();
				if(Desktop.isDesktopSupported() && desk.isSupported(Desktop.Action.BROWSE)){
					try {
						desk.browse(uri);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
	}
	public void mouseEntered(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.red);
	}
	public void mouseExited(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.blue);
	}
}
