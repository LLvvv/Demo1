package xjx;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1877974685325498861L;
	private Font f = new Font("΢���ź�",Font.PLAIN,15);
	private Font f2 = new Font("΢���ź�",Font.PLAIN,12);
	private JRadioButtonMenuItem speedItems[];
	private ButtonGroup speedGroup;
	private JRadioButtonMenuItem headItems[];
	private ButtonGroup headGroup;
	private JRadioButtonMenuItem bodyItems[];
	private ButtonGroup bodyGroup;
	private JRadioButtonMenuItem sizeItems[];
	private ButtonGroup sizeGroup;
	private ImageIcon backgroundImage;
	private JLabel label;
	JPanel imagePanel;
	public  int sizeW=1250;
	public int sizeH=675;
	
	public MainWindow(){
		Image img = Toolkit.getDefaultToolkit().getImage("title.png");//����ͼ��
		setIconImage(img);
	    setTitle("Snake By XJX");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    setSize(602, 507);
	    setSize(1250,675);
	    //setResizable(false);
	   setLocationRelativeTo(null);

	    
	    //��ӱ���ͼƬ
	    backgroundImage = new ImageIcon("background//sky2.jpg");
	    backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(1250,675,Image.SCALE_SMOOTH));
        label = new JLabel(backgroundImage);  
        label.setBounds(0,0, this.getWidth(), this.getHeight());   
        imagePanel = (JPanel) this.getContentPane();  
        imagePanel.setOpaque(false);  
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        
        //�˵���
        JMenuBar bar = new JMenuBar();
        bar.setBackground(Color.white);
  		setJMenuBar(bar);
  		JMenu Settings = new JMenu("����");
  		Settings.setFont(f);
  		JMenu Help = new JMenu("����");
  		Help.setFont(f);
  		JMenu About = new JMenu("����");
  		About.setFont(f);
  		bar.add(Settings);
  		bar.add(Help);
  		bar.add(About);	
      		
  		JMenuItem set_background = new JMenuItem("��������");
  		set_background.setFont(f2);
		JMenu set_head = new JMenu("������ͷ");
		set_head.setFont(f2);
		JMenu set_body = new JMenu("��������");
		set_body.setFont(f2);
		//add by jere
		JMenu set_size = new JMenu("��������");
		set_body.setFont(f2);
		JMenu set_speed= new JMenu("�����ٶ�");
		set_speed.setFont(f2);
		final JMenuItem remove_net= new JMenuItem("�Ƴ�����");
		remove_net.setFont(f2);
		Settings.add(set_background);
		Settings.add(set_head);
		Settings.add(set_body);
		Settings.add(set_speed);
		Settings.add(set_size);
		Settings.add(remove_net);
		
		JMenuItem help = new JMenuItem("��Ϸָ��");
		help.setFont(f2);
		Help.add(help);
		
		JMenuItem about = new JMenuItem("���ڴ���Ϸ");
		about.setFont(f2);
		About.add(about);
		
		
	    final SnakeDemo snake = new SnakeDemo();
		snake.Thread();
		snake.setOpaque(false);
		imagePanel.add(snake, BorderLayout.CENTER);
		
		//��Ӽ�����
		remove_net.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		if(!snake.If_remove)
        		{
        			snake.If_remove = true;

					remove_net.setText("��ʾ����");
        		}
        		else
        		{
        			snake.If_remove = false;


					remove_net.setText("�Ƴ�����");
        		}
        	}
        });
		
		String speed[] = {"����","����","����","���"};
		speedItems = new JRadioButtonMenuItem[speed.length];
		speedGroup = new ButtonGroup();
		for(int i = 0;i < speed.length;i++)
		{
			speedItems[i] = new JRadioButtonMenuItem(speed[i]);
			speedItems[i].setFont(f2);
			set_speed.add(speedItems[i]);
			speedGroup.add(speedItems[i]);
			speedItems[i].addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e){
							for(int i = 0;i < speedItems.length;i++)
							{
								if(speedItems[i].isSelected())
								{
									if(i == 0)
									{
										snake.normal_speed = 600;
									}
									else if(i == 1)
									{
										snake.normal_speed = 400;
									}
									else if(i == 2)
									{
										snake.normal_speed = 300;
									}
									else if(i == 3)
									{
										snake.normal_speed = 100;
									}
								}
							}
						}
					}
			);
		}
		speedItems[2].setSelected(true);
		
		String head[] = {"doge","����","����","��"};
		headItems = new JRadioButtonMenuItem[head.length];
		headGroup = new ButtonGroup();
		final ImageIcon headIcon[] = new ImageIcon[head.length];
		headIcon[0] = new ImageIcon("head//head.png");
		headIcon[1] = new ImageIcon("head//head2.png");
		headIcon[2] = new ImageIcon("head//head3.png");
		headIcon[3] = new ImageIcon("head//head4.png");
		headIcon[0].setImage(headIcon[0].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		headIcon[1].setImage(headIcon[1].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		headIcon[2].setImage(headIcon[2].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		headIcon[3].setImage(headIcon[3].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		for(int i = 0;i < head.length;i++)
		{
			headItems[i] = new JRadioButtonMenuItem(head[i]);
			headItems[i].setFont(f2);
			headItems[i].setIcon(headIcon[i]);
			set_head.add(headItems[i]);
			headGroup.add(headItems[i]);
			headItems[i].addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e){
							for(int i = 0;i < headItems.length;i++)
							{
								if(headItems[i].isSelected())
								{
									snake.remove(snake.head_label);
									snake.snakehead = new ImageIcon(headIcon[i].toString());
									snake.snakehead.setImage(snake.snakehead.getImage().getScaledInstance((int)( (snake.wallW/40)*(3.0/4)),(int)( (snake.wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));
									snake.head_label = new JLabel(snake.snakehead);
									snake.add(snake.head_label);
								}
							}
						}
					}
			);
		}
		headItems[3].setSelected(true);
		
		String body[] = {"����","Ц��","����","����"};
		bodyItems = new JRadioButtonMenuItem[body.length];
		bodyGroup = new ButtonGroup();
		final ImageIcon bodyIcon[] = new ImageIcon[body.length];
		bodyIcon[0] = new ImageIcon("body//body.png");
		bodyIcon[1] = new ImageIcon("body//body2.png");
		bodyIcon[2] = new ImageIcon("body//body3.png");
		bodyIcon[3] = new ImageIcon("body//body4.png");
		bodyIcon[0].setImage(bodyIcon[0].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		bodyIcon[1].setImage(bodyIcon[1].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		bodyIcon[2].setImage(bodyIcon[2].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		bodyIcon[3].setImage(bodyIcon[3].getImage().getScaledInstance((int)( (snake.wallW/40)*(2.0/3)),(int)( (snake.wallW/40)*(2.0/3)),Image.SCALE_SMOOTH));//����ͼƬ������
		for(int i = 0;i < body.length;i++)
		{
			bodyItems[i] = new JRadioButtonMenuItem(body[i]);
			bodyItems[i].setFont(f2);
			bodyItems[i].setIcon(bodyIcon[i]);
			set_body.add(bodyItems[i]);
			bodyGroup.add(bodyItems[i]);
			bodyItems[i].addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e){
							for(int i = 0;i < bodyItems.length;i++)
							{
								if(bodyItems[i].isSelected())
								{
									for(int j = 0;j < snake.body_length;j++)
									{
										snake.remove(snake.body_label[j]);
									}
									snake.snakebody = new ImageIcon(bodyIcon[i].toString());
									snake.snakebody.setImage(snake.snakebody.getImage().getScaledInstance((int)( (snake.wallW/40)*(3.0/4)),(int)( (snake.wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));
									for(int k = 0; k < snake.MAX_SIZE;k++)
									{
										snake.body_label[k] = new JLabel(snake.snakebody); 
										snake.body_label[k].setOpaque(false);
									}
									
									for(int l = 0;l < snake.body_length;l++)
									{
										snake.add(snake.body_label[l]);
									}
								}
							}
						}
					}
			);
		}
		bodyItems[0].setSelected(true);


		//add by jere
		String size[] = { "1250:675","1000:560","750:400","500:270"};
		sizeItems = new JRadioButtonMenuItem[size.length];
		sizeGroup = new ButtonGroup();

		for (int i =0;i<size.length;i++){
			sizeItems[i] = new JRadioButtonMenuItem(size[i]);
			sizeItems[i].setFont(f);
			String sizeL = size[i];
			set_size.add(sizeItems[i]);
			sizeItems[i].addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							final String[] temp = sizeL.split(":");
							setSize(Integer.parseInt  (temp[0]),Integer.parseInt(temp[1]));
							label.setBounds(0,0,Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
							backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Image.SCALE_SMOOTH));
							sizeW=Integer.parseInt(temp[0]);
							sizeH=Integer.parseInt(temp[1]);

							repaint();
							snake.label.setBounds(Integer.parseInt(temp[0])-100, 10, 80, 20);
							snake.label.setFont(f);

							snake.Length.setBounds(Integer.parseInt(temp[0])-100, 35, 80, 20);
							snake.Length.setFont(f);

							snake.label2.setBounds(Integer.parseInt(temp[0])-100, 70, 80, 20);
							snake.label2.setFont(f);

							snake.Time.setBounds(Integer.parseInt(temp[0])-100, 95, 80, 20);
							snake.Time.setFont(f);

							snake.label3.setBounds(Integer.parseInt(temp[0])-100, 130, 80, 20);
							snake.label3.setFont(f);

							snake.Score.setBounds(Integer.parseInt(temp[0])-100, 155, 80, 20);
							snake.Score.setFont(f);

							snake.p.setBounds(Integer.parseInt(temp[0])-102, 200, 93, 1);
							snake.p.setBorder(BorderFactory.createLineBorder(Color.white));

							snake.label4.setBounds(Integer.parseInt(temp[0])-100, 210, 80, 20);
							snake.label4.setFont(f);

							snake.Time2.setBounds(Integer.parseInt(temp[0])-100, 235, 80, 20);
							snake.Time2.setFont(f);

							snake.wallW=Integer.parseInt(temp[0])-130;
							snake.wallH=snake.wallW/2;

							snake.reStar();


						}
					}
			);
		}
		
		set_background.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		new Alter_Bacground();
        	}
        });
		
		about.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		new About();
        	}
        });
		
		help.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		new Help();
        	}
        });
		
	    setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	class Alter_Bacground extends JDialog{
		/**
		 * 
		 */
		private static final long serialVersionUID = -990903376750998765L;
		private final int back_kind = 6;
		private Font f = new Font("΢���ź�",Font.PLAIN,15);
		private JPanel p = new JPanel();
		
		public Alter_Bacground(){
			 setTitle("������Ϸ����");//���ô������
			 Image img=Toolkit.getDefaultToolkit().getImage("title.png");//����ͼ��
			 setIconImage(img);
		     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		     setModal(true);//����Ϊģ̬����
		     setSize(650,390);
		     setResizable(false);
		     setLocationRelativeTo(null);
		     
		     //��ӱ���ͼƬ
		     final ImageIcon background[] = new ImageIcon[back_kind];
		     background[0] = new ImageIcon("background//desert.jpg");
		     background[1] = new ImageIcon("background//grass.jpg");
		     background[2] = new ImageIcon("background//ocean.jpg");
		     background[3] = new ImageIcon("background//ocean2.jpg");
		     background[4] = new ImageIcon("background//sky.jpg");
		     background[5] = new ImageIcon("background//sky2.jpg");

		     background[0].setImage(background[0].getImage().getScaledInstance(200,110,Image.SCALE_FAST));//����
		     background[1].setImage(background[1].getImage().getScaledInstance(200,110,Image.SCALE_FAST));
		     background[2].setImage(background[2].getImage().getScaledInstance(200,110,Image.SCALE_FAST));
		     background[3].setImage(background[3].getImage().getScaledInstance(200,110,Image.SCALE_FAST));
		     background[4].setImage(background[4].getImage().getScaledInstance(200,110,Image.SCALE_FAST));
		     background[5].setImage(background[5].getImage().getScaledInstance(200,110,Image.SCALE_FAST));
		     
		     JLabel Back_label[] = new JLabel[back_kind];
		     final JButton choose[] = new JButton[back_kind];
		     for(int i = 0;i < back_kind;i++)
		     {
		    	 Back_label[i] = new JLabel(background[i],SwingConstants.LEFT);
		    	 Back_label[i].setFont(f);
		    	 Back_label[i].setHorizontalTextPosition(SwingConstants.CENTER);
		    	 Back_label[i].setVerticalTextPosition(SwingConstants.BOTTOM);
		    	 
		    	 choose[i] = new JButton("ѡ��");
		    	 choose[i].setFont(f);
		    	 p.add(choose[i]);
		    	 p.add(Back_label[i]);
		     }
		     
		     add(p,BorderLayout.CENTER);
		     p.setBackground(Color.white);
		     p.setLayout(null);
		     
		     Back_label[0].setBounds(10, 0, 200, 120);
		     choose[0].setBounds(70, 140, 80, 25);
		     Back_label[1].setBounds(220, 0, 200, 120);
		     choose[1].setBounds(280, 140, 80, 25);
		     Back_label[2].setBounds(430, 0, 200, 120);
		     choose[2].setBounds(490, 140, 80, 25);
		     Back_label[3].setBounds(10, 180, 200, 120);
		     choose[3].setBounds(70, 320, 80, 25);
		     Back_label[4].setBounds(220, 180, 200, 120);
		     choose[4].setBounds(280, 320, 80, 25);
		     Back_label[5].setBounds(430, 180, 200, 120);
		     choose[5].setBounds(490, 320, 80, 25);
		     
		     for(int i = 0;i < back_kind;i++)
		     {
		    	 choose[i].addActionListener(new ActionListener(){
		         	public void actionPerformed(ActionEvent e){
		        		if(e.getSource() == choose[0])
		        		{
		        			ImageIcon temp = new ImageIcon(background[0].toString());
		        			backgroundImage.setImage(temp.getImage().getScaledInstance(sizeW,sizeH,Image.SCALE_SMOOTH));
		        			repaint();

		        			JOptionPane.showMessageDialog(null,"���ĳɹ����ص���Ϸ������Ч");
		        		}
		        		else if(e.getSource() == choose[1])
		        		{
		        			ImageIcon temp = new ImageIcon(background[1].toString());
		        			backgroundImage.setImage(temp.getImage().getScaledInstance(sizeW,sizeH,Image.SCALE_SMOOTH));
		        			repaint();
		        			JOptionPane.showMessageDialog(null,"���ĳɹ����ص���Ϸ������Ч");
		        		}
		        		else if(e.getSource() == choose[2])
		        		{
		        			ImageIcon temp = new ImageIcon(background[2].toString());
		        			backgroundImage.setImage(temp.getImage().getScaledInstance(sizeW,sizeH,Image.SCALE_SMOOTH));
		        			repaint();
		        			JOptionPane.showMessageDialog(null,"���ĳɹ����ص���Ϸ������Ч");
		        		}
		        		else if(e.getSource() == choose[3])
		        		{
		        			ImageIcon temp = new ImageIcon(background[3].toString());
		        			backgroundImage.setImage(temp.getImage().getScaledInstance(sizeW,sizeH,Image.SCALE_SMOOTH));
		        			repaint();
		        			JOptionPane.showMessageDialog(null,"���ĳɹ����ص���Ϸ������Ч");
		        		}
		        		else if(e.getSource() == choose[4])
		        		{
		        			ImageIcon temp = new ImageIcon(background[4].toString());
		        			backgroundImage.setImage(temp.getImage().getScaledInstance(sizeW,sizeH,Image.SCALE_SMOOTH));
		        			repaint();
		        			JOptionPane.showMessageDialog(null,"���ĳɹ����ص���Ϸ������Ч");
		        		}
		        		else if(e.getSource() == choose[5])
		        		{
		        			ImageIcon temp = new ImageIcon(background[5].toString());
		        			backgroundImage.setImage(temp.getImage().getScaledInstance(sizeW,sizeH,Image.SCALE_SMOOTH));
		        			repaint();
		        			JOptionPane.showMessageDialog(null,"���ĳɹ����ص���Ϸ������Ч");
		        		}
		        	}
		        });
		     }
		     setVisible(true);
		}
	}
}
