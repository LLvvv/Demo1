package xjx;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

class Tile{
	int x;
	int y;
	
	public Tile(int x0,int y0){
		x = x0;
		y = y0;
	}
}

public class SnakeDemo extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3794762291171148906L;

	public JLabel label  = new JLabel("当前长度：");
	public JLabel label2 = new JLabel("所花时间：");
	public JLabel label3 = new JLabel("当前得分：");
	public JLabel label4 = new JLabel("剩余时间：");
	public JLabel Length = new JLabel("1");
	public JLabel Score = new JLabel("0");
	public JLabel Time = new JLabel("");
	public JLabel Time2 = new JLabel("5");
	private Font f = new Font("微软雅黑",Font.PLAIN,15);
	public JPanel p = new JPanel();
	
	public final int MAX_SIZE = 400;//蛇身体最长为400节
	private Tile temp = new Tile(0,0);
	private Tile temp2 = new Tile(0,0);
	private Tile head;
	private Tile[] body = new Tile[MAX_SIZE];
	
	private String direction = "R";//默认向右走
	private String current_direction = "R";//当前方向
	private boolean first_launch = false;
	private boolean iseaten = false;
	private boolean isrun = true;
	private int randomx,randomy;
	public int body_length = 0;//身体长度初始化为0
	private Thread run;
	
	private int hour =0;
	private int min =0;
	private int sec =0 ;
	
	private boolean pause = false;
	
	public static long normal_speed = 300;
	private long millis = normal_speed;//每隔normal_speed毫秒刷新一次
	private boolean speed = true;
	private Calendar Cld;
	private int MI,MI2,MI3;
	private int SS,SS2,SS3;
	
	private final int foodkind = 6;
	private int score = 0;
	private int foodtag;
	private int[] point_list = new int[6];
	public ImageIcon snakehead;
	public ImageIcon snakebody;
	private ImageIcon[] food = new ImageIcon[foodkind];
	public JLabel head_label;
	public JLabel[] body_label = new JLabel[MAX_SIZE];
	private JLabel food_label;//每次产生一种食物
	
	private int countsecond = 5;//每种食物产生5秒后消失或者换位置
	private boolean ifcount = false;
	
	public static boolean If_remove = false;//是否移除网格线W


	public int wallW=1122;
	public  int wallH=wallW/2;
	
	public SnakeDemo(){
		
		String lookAndFeel =UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		//布局
		add(label);
	    label.setBounds(1150, 10, 80, 20);
	    label.setFont(f);
	    add(Length);
	    Length.setBounds(1150, 35, 80, 20);
	    Length.setFont(f);
	    add(label2);
	    label2.setBounds(1150, 70, 80, 20);
	    label2.setFont(f);
	    add(Time);
	    Time.setBounds(1150, 95, 80, 20);
	    Time.setFont(f);    
	    add(label3);
	    label3.setBounds(1150, 130, 80, 20);
	    label3.setFont(f);
	    add(Score);
	    Score.setBounds(1150, 155, 80, 20);
	    Score.setFont(f);
	    add(p);
	    p.setBounds(1150, 200, 93, 1);
	    p.setBorder(BorderFactory.createLineBorder(Color.white));
	    add(label4);
	    label4.setBounds(1148, 210, 80, 20);
	    label4.setFont(f);
	    add(Time2);
	    Time2.setBounds(1150, 235, 80, 20);
	    Time2.setFont(f); 
	    
	    //字体颜色，为了便于分辨，设为白色
	    label.setForeground(Color.white);
	    label2.setForeground(Color.white);
	    label3.setForeground(Color.white);
	    label4.setForeground(Color.white);
	    Length.setForeground(Color.white);
		Score.setForeground(Color.white);
		Time.setForeground(Color.white);
		Time2.setForeground(Color.white);    
	    
	    //初始化头部坐标
	    ProduceRandom();
	    head = new Tile(randomx,randomy);
	    snakehead = new ImageIcon("head//head.png");
	    snakehead.setImage(snakehead.getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
        head_label = new JLabel(snakehead); 
        add(head_label);
        head_label.setBounds(head.x, head.y, (int)( (wallW/40)*(3.0/4)), (int)( (wallW/40)*(3.0/4)));
        head_label.setOpaque(false);
        
        //初始化身体所有节点
        snakebody = new ImageIcon("body//body4.png");
		snakebody.setImage(snakebody.getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
		for(int i = 0; i < MAX_SIZE;i++)
		{
			body[i] = new Tile(0,0);
			body_label[i] = new JLabel(snakebody); 
			body_label[i].setOpaque(false);
		}
		
		//初始化食物
		food[0] = new ImageIcon("food//hotdog.png");
	    food[0].setImage(food[0].getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
	    food[1] = new ImageIcon("food//hamburger.png");
	    food[1].setImage(food[1].getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
	    food[2] = new ImageIcon("food//drink.png");
	    food[2].setImage(food[2].getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
	    food[3] = new ImageIcon("food//green_apple.png");
	    food[3].setImage(food[3].getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
	    food[4] = new ImageIcon("food//blue_apple.png");
	    food[4].setImage(food[4].getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
	    food[5] = new ImageIcon("food//red_apple.png");
	    food[5].setImage(food[5].getImage().getScaledInstance((int)( (wallW/40)*(3.0/4)),(int)( (wallW/40)*(3.0/4)),Image.SCALE_SMOOTH));//保持图片的清晰
	    
	    //初始化各食物对应的得分
	    point_list[0] = 20;
	    point_list[1] = 40;
	    point_list[2] = 30;
	    point_list[3] = 10;
	    point_list[4] = 30;
	    point_list[5] = 50;
	    
	    ProduceFood();
        food_label.setOpaque(false);
		
		//添加监听器
		this.addKeyListener(new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	    		super.keyPressed(e);
	    		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
	    		{
	    			if(isrun && current_direction != "L")
	    			{
	    				direction = "R";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_LEFT)
	    		{
	    			if(isrun && current_direction != "R")
	    			{
	    				direction = "L";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_UP)
	    		{
	    			if(isrun && current_direction != "D")
	    			{
	    				direction = "U";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_DOWN)
	    		{
	    			if(isrun && current_direction != "U")
	    			{
	    				direction = "D";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)//重置所有变量初始值
	    		{
	    			remove(food_label);//去掉被吃掉的食物
					score = 0;
					Score.setText("0");
	    			//初始化头部坐标
	    		    ProduceRandom();
	    		    head = new Tile(randomx,randomy);
	    		    //初始化身体节点部坐标
	    		    for(int i = 0; i < MAX_SIZE;i++)
	    			{
	    				body[i].x = 0;
	    				body[i].y = 0;
	    			}
	    		    
	    			hour =0;
	    			min =0;
	    			sec =0 ;
	    			direction = "R";//默认向右走
	    			current_direction = "R";//当前方向
	    			first_launch = false;
	    			iseaten = false;
	    			isrun = true;
	    			pause = false;
	    			millis = normal_speed;//每隔normal_speed毫秒刷新一次
	    			speed = true;
	    			body_length = 0;
	    			Length.setText("1");
	    			
	    			run = new Thread();
	    			run.start();
	    			
	    			ifcount = false;
		    		countsecond = 5;
		    		Time2.setText("5");
		    		ifcount = true;
		    		
	    			System.out.println("Start again");
	    		}
	    		
	    		if(e.getKeyCode() == KeyEvent.VK_SPACE)
	    		{
	    			if(!pause)//暂停
	    			{
	    				pause = true;
	    				isrun = false;
	    				ifcount = false;
	    			}
	    			else//开始
	    			{
	    				pause = false;
	    				isrun = true;
	    				ifcount = true;
	    			}
	    		}
	    	}
		});
		
		this.addKeyListener(new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	    		int key = e.getKeyCode();
	    		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
	    		{
	    			if(speed)
	    			{
	    				Cld = Calendar.getInstance();
		    			int YY = Cld.get(Calendar.YEAR) ;
		    	        int MM = Cld.get(Calendar.MONTH)+1;
		    	        int DD = Cld.get(Calendar.DATE);
		    	        int HH = Cld.get(Calendar.HOUR_OF_DAY);
		    	        int mm = Cld.get(Calendar.MINUTE);
		    	        SS = Cld.get(Calendar.SECOND);
		    	        MI = Cld.get(Calendar.MILLISECOND); 
		    	        System.out.println("Pressed time  " + YY + "/" + MM + "/" + DD + "-" + HH
		    	        		+ ":" + mm + ":" + SS + ":" + MI);
		    	        
		    	        speed = false;
	    			}
	    			
	    			Cld = Calendar.getInstance();
	    			SS3 = Cld.get(Calendar.SECOND);
	    	        MI3 = Cld.get(Calendar.MILLISECOND); 
	    	        int x = SS3 * 1000 + MI3 - ( SS * 1000 + MI);
	    	        if(x > 400)//按一个按钮的时长大于400毫秒识别为长按
	    	        {
	    	        	millis = 50;//加速时每隔50毫秒刷新一次
	    	        	System.out.println("Long Pressed");
	    	        }
	    		}
	    	}
		});
		
		this.addKeyListener(new KeyAdapter() {
	    	public void keyReleased(KeyEvent e) {
	    		int key = e.getKeyCode();
	    		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
	    		{
	    			Cld = Calendar.getInstance();
	    			int YY2 = Cld.get(Calendar.YEAR) ;
	    	        int MM2 = Cld.get(Calendar.MONTH)+1;
	    	        int DD2 = Cld.get(Calendar.DATE);
	    	        int HH2 = Cld.get(Calendar.HOUR_OF_DAY);
	    	        int mm2 = Cld.get(Calendar.MINUTE);
	    	        SS2 = Cld.get(Calendar.SECOND);
	    	        MI2 = Cld.get(Calendar.MILLISECOND); 
	    	        System.out.println("Released time " + YY2 + "/" + MM2 + "/" + DD2 + "-" + HH2 
	    	        		+ ":" + mm2 + ":" + SS2 + ":" + MI2 + "\n" );

	    	        speed = true;
	    	        millis = normal_speed;
	    		}
	    	}
		});
		
		
		new Timer();//开始计时
		
		new Countdown();//开始倒计时
		
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g1){
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);
		
		//刷新头部坐标
		head_label.setBounds(head.x, head.y, (int)( (wallW/40)*(3.0/4))-1, (int)( (wallW/40)*(3.0/4))-1);
		
		if(!first_launch)
		{
			//初始化食物位置
			ProduceFood();
			ProduceRandom();
			add(food_label);
			food_label.setBounds(randomx, randomy, (int)( (wallW/40)*(3.0/4))-1, (int)( (wallW/40)*(3.0/4))-1);
			ifcount = true;
		}
		else
		{
			//每次刷新身体
			for(int i = 0;i < body_length;i++)
			{
				body_label[i].setBounds(body[i].x, body[i].y, (int)( (wallW/40)*(3.0/4))-1, (int)( (wallW/40)*(3.0/4))-1);
			}
			
			if(EatFood())//被吃了重新产生食物
			{
				remove(food_label);//去掉被吃掉的食物
				ProduceFood();
				ProduceRandom();
				add(food_label);
				food_label.setBounds(randomx, randomy, (int)( (wallW/40)*(3.0/4))-1, (int)( (wallW/40)*(3.0/4))-1);
				iseaten = false;
				
				ifcount = false;
				Time2.setText("5");
				countsecond = 5;
				ifcount = true;
			}
			else
			{
				food_label.setBounds(randomx, randomy, (int)( (wallW/40)*(3.0/4))-1, (int)( (wallW/40)*(3.0/4))-1);
			}
		}
		first_launch = true;
		
		//墙
		g.setPaint(new GradientPaint(115,135,Color.CYAN,230,135,Color.MAGENTA,true));
		g.setStroke( new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		g.drawRect(2, 10, wallW, wallH);//+400
		
		if(!If_remove)
		{
			//网格线
			for(int i = 1;i < 40;i++)
			{
//				g.setStroke( new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));//实线
				g.setStroke( new BasicStroke(1f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 3.5f, new float[] { 28, 20, },
                        0f));//虚线
				g.setColor(Color.black);
				g.drawLine(5+i*(int)((wallW-2)/40),9,5+i*(int)((wallW-2)/40),((wallW-2)/2));
				if(i <= 20)
				{
					g.drawLine(4,10+i*(int)((wallW-2)/40),wallW-2,10+i*(int)((wallW-2)/40));//+400
				}
			}
		}
	}
	
	public void ProduceFood(){
		Random rand = new Random();
		double x;
		x = rand.nextDouble();
		
		if(x >= 0 && x <0.1)
		{
			food_label = new JLabel(food[1]);
			foodtag = 1;
		}
		else if(x >= 0.1 && x <0.25)
		{
			food_label = new JLabel(food[2]);
			foodtag = 2;
		}
		else if(x >= 0.25 && x <0.5)
		{
			food_label = new JLabel(food[0]);
			foodtag = 0;
		}
		else if(x >= 0.5 && x <0.8)
		{
			food_label = new JLabel(food[3]);
			foodtag = 3;
		}
		else if(x >= 0.8 && x <0.95)
		{
			food_label = new JLabel(food[4]);
			foodtag = 4;
		}
		else if(x >= 0.95 && x <1)
		{
			food_label = new JLabel(food[5]);
			foodtag = 5;
		}
		
	} 
	
	public void ProduceRandom(){
		boolean flag = true;
		Random rand = new Random();
		randomx = (rand.nextInt(39) + 1) * (int)((wallW-2)/40) + 7;
		randomy = (rand.nextInt(19) + 1) *(int)((wallW-2)/40) + 12;
		while(flag)
		{
			if(body_length == 0)
			{
				flag = false;
			}
			else
			{
				for(int i = 0;i < body_length; i++)
				{
					if(body[i].x == randomx && body[i].y == randomy)
					{
						randomx = (rand.nextInt(21) + 1) * (int)((wallW-2)/40) + 7;
						randomy = (rand.nextInt(20) + 1) *(int)((wallW-2)/40) + 12;
						flag = true;
						break;
					}
					else
					{
						if(i == body_length - 1)
						{
							flag = false;
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void HitWall(){//判断是否撞墙
		if(current_direction == "L")
		{
			if(head.x < 0)
			{
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					remove(food_label);//去掉被吃掉的食物
					score = 0;
					Score.setText("0");
					//初始化头部坐标
	    		    ProduceRandom();
	    		    head = new Tile(randomx,randomy);
	    		    //初始化身体节点部坐标
	    		    for(int i = 0; i < MAX_SIZE;i++)
	    			{
	    				body[i].x = 0;
	    				body[i].y = 0;
	    			}
	    		    
	    		    for(int k = 0;k < body_length;k++)
	    		    {
	    		    	remove(body_label[k]);
	    		    }
	    		    
	    			hour =0;
	    			min =0;
	    			sec =0 ;
	    			direction = "R";//默认向右走
	    			current_direction = "R";//当前方向
	    			first_launch = false;
	    			iseaten = false;
	    			isrun = true;
	    			pause = false;
	    			millis = normal_speed;//每隔normal_speed毫秒刷新一次
	    			speed = true;
	    			body_length = 0;
	    			Length.setText("1");
	    			
	    			run = new Thread();
	    			run.start();
	    			
	    			ifcount = false;
		    		countsecond = 5;
		    		Time2.setText("5");
		    		ifcount = true;
		    		
	    			System.out.println("Start again");
				}
				else
				{
					run.stop();
				}		
			}
		}
		if(current_direction == "R")
		{
			if(head.x > wallW)
			{
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					remove(food_label);//去掉被吃掉的食物
					score = 0;
					Score.setText("0");
					//初始化头部坐标
	    		    ProduceRandom();
	    		    head = new Tile(randomx,randomy);
	    		    //初始化身体节点部坐标
	    		    for(int i = 0; i < MAX_SIZE;i++)
	    			{
	    				body[i].x = 0;
	    				body[i].y = 0;
	    			}
	    		    
	    		    for(int k = 0;k < body_length;k++)
	    		    {
	    		    	remove(body_label[k]);
	    		    }
	    		    
	    			hour =0;
	    			min =0;
	    			sec =0 ;
	    			direction = "R";//默认向右走
	    			current_direction = "R";//当前方向
	    			first_launch = false;
	    			iseaten = false;
	    			isrun = true;
	    			pause = false;
	    			millis = normal_speed;//每隔normal_speed毫秒刷新一次
	    			speed = true;
	    			body_length = 0;
	    			Length.setText("1");
	    			
	    			run = new Thread();
	    			run.start();
	    			
	    			ifcount = false;
		    		countsecond = 5;
		    		Time2.setText("5");
		    		ifcount = true;
		    		
	    			System.out.println("Start again");
				}
				else
				{
					run.stop();
				}
			}
		}
		if(current_direction == "U")
		{
			if(head.y < 12)
			{
				remove(food_label);//去掉被吃掉的食物
				score = 0;
				Score.setText("0");
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					//初始化头部坐标
	    		    ProduceRandom();
	    		    head = new Tile(randomx,randomy);
	    		    //初始化身体节点部坐标
	    		    for(int i = 0; i < MAX_SIZE;i++)
	    			{
	    				body[i].x = 0;
	    				body[i].y = 0;
	    			}
	    		    
	    		    for(int k = 0;k < body_length;k++)
	    		    {
	    		    	remove(body_label[k]);
	    		    }
	    		    
	    			hour =0;
	    			min =0;
	    			sec =0 ;
	    			direction = "R";//默认向右走
	    			current_direction = "R";//当前方向
	    			first_launch = false;
	    			iseaten = false;
	    			isrun = true;
	    			pause = false;
	    			millis = normal_speed;//每隔normal_speed毫秒刷新一次
	    			speed = true;
	    			body_length = 0;
	    			Length.setText("1");
	    			
	    			run = new Thread();
	    			run.start();
	    			
	    			ifcount = false;
		    		countsecond = 5;
		    		Time2.setText("5");
		    		ifcount = true;
		    		
	    			System.out.println("Start again");
				}
				else
				{
					run.stop();
				}
			}
		}
		if(current_direction == "D")
		{
			if(head.y > wallH)
			{
				remove(food_label);//去掉被吃掉的食物
				score = 0;
				Score.setText("0");
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					//初始化头部坐标
	    		    ProduceRandom();
	    		    head = new Tile(randomx,randomy);
	    		    //初始化身体节点部坐标
	    		    for(int i = 0; i < MAX_SIZE;i++)
	    			{
	    				body[i].x = 0;
	    				body[i].y = 0;
	    			}
	    		    
	    		    for(int k = 0;k < body_length;k++)
	    		    {
	    		    	remove(body_label[k]);
	    		    }
	    		    
	    			hour =0;
	    			min =0;
	    			sec =0 ;
	    			direction = "R";//默认向右走
	    			current_direction = "R";//当前方向
	    			first_launch = false;
	    			iseaten = false;
	    			isrun = true;
	    			pause = false;
	    			millis = normal_speed;//每隔normal_speed毫秒刷新一次
	    			speed = true;
	    			body_length = 0;
	    			Length.setText("1");
	    			
	    			run = new Thread();
	    			run.start();
	    			
	    			ifcount = false;
		    		countsecond = 5;
		    		Time2.setText("5");
		    		ifcount = true;
		    		
	    			System.out.println("Start again");
				}
				else
				{
					run.stop();
				}
			}
		}
	}


	public void reStar() {//重新开始


				new AePlayWave("over.wav").start();
				isrun = false;
				//int result = JOptionPane.showConfirmDialog(null, "size change，please restart!", "Information", JOptionPane.YES_NO_OPTION);
					remove(food_label);//去掉被吃掉的食物
					score = 0;
					Score.setText("0");
					//初始化头部坐标
					ProduceRandom();
					head = new Tile(randomx, randomy);
					//初始化身体节点部坐标
					for (int i = 0; i < MAX_SIZE; i++) {
						body[i].x = 0;
						body[i].y = 0;
					}

					for (int k = 0; k < body_length; k++) {
						remove(body_label[k]);
					}

					hour = 0;
					min = 0;
					sec = 0;
					direction = "R";//默认向右走
					current_direction = "R";//当前方向
					first_launch = false;
					iseaten = false;
					isrun = true;
					pause = false;
					millis = normal_speed;//每隔normal_speed毫秒刷新一次
					speed = true;
					body_length = 0;
					Length.setText("1");

					run = new Thread();
					run.start();

					ifcount = false;
					countsecond = 5;
					Time2.setText("5");
					ifcount = true;

					System.out.println("Start again");



	}
	
	@SuppressWarnings("deprecation")
	public void HitSelf(){//判断是否撞到自己身上
		for(int i = 0;i < body_length; i++)
		{
			if(body[i].x == head.x && body[i].y == head.y)
			{
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					remove(food_label);//去掉被吃掉的食物
					score = 0;
					Score.setText("0");
					//初始化头部坐标
	    		    ProduceRandom();
	    		    head = new Tile(randomx,randomy);
	    		    //初始化身体节点部坐标
	    		    for(int j = 0; j < MAX_SIZE;j++)
	    			{
	    				body[j].x = 0;
	    				body[j].y = 0;
	    			}
	 
	    		    for(int k = 0;k < body_length;k++)
	    		    {
	    		    	remove(body_label[k]);
	    		    }
	    		    
	    			hour =0;
	    			min =0;
	    			sec =0 ;
	    			direction = "R";//默认向右走
	    			current_direction = "R";//当前方向
	    			first_launch = false;
	    			iseaten = false;
	    			isrun = true;
	    			pause = false;
	    			millis = normal_speed;//每隔normal_speed毫秒刷新一次
	    			speed = true;
	    			body_length = 0;
	    			Length.setText("1");
	    			
	    			run = new Thread();
	    			run.start();
	    			
	    			ifcount = false;
		    		countsecond = 5;
		    		Time2.setText("5");
		    		ifcount = true;
		    		
	    			System.out.println("Start again");
				}
				else
				{
					run.stop();
				}
				break;
			}
		}
	}
	
	public boolean  EatFood(){
		if(head.x == randomx && head.y == randomy)
		{
			iseaten = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void Thread(){
//		millis = normal_speed;//默认每隔normal_speed毫秒刷新一次

		run = new Thread() {
			public void run() {
				while (true) 
				{
					try {
						Thread.sleep(millis);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					
					if(!pause)
					{	
						temp.x = head.x;
						temp.y = head.y;
						//头部移动
						if(direction == "L")
						{
							head.x -= (int)((wallW-2)/40);
						}
						if(direction == "R")
						{
							head.x += (int)((wallW-2)/40);
						}
						if(direction == "U")
						{
							head.y -= (int)((wallW-2)/40);
						}
						if(direction == "D")
						{
							head.y += (int)((wallW-2)/40);
						}
						current_direction = direction;//刷新当前前进方向
						//身体移动
						for(int i = 0;i < body_length;i++)
						{
							temp2.x = body[i].x;
							temp2.y = body[i].y;
							body[i].x = temp.x;
							body[i].y = temp.y;
							temp.x = temp2.x;
							temp.y = temp2.y;
						}
						
						if(EatFood())
						{
							body_length ++;
							body[body_length-1].x = temp.x;
							body[body_length-1].y = temp.y;
							
							add(body_label[body_length-1]);
							
							Length.setText("" + (body_length+1) );//刷新长度
							score += point_list[foodtag];
							Score.setText("" + score);//刷新得分
							new AePlayWave("eat.wav").start();
						}
						
						repaint();
						//刷新完判断是否撞墙和撞自己的身体
						HitWall();
						HitSelf();
					}
				}
			}
		};
		
		run.start();
	}
	
	//倒计时类
	class Countdown extends Thread{
	    public Countdown(){
	        this.start();
	    }
	    
	    @SuppressWarnings("deprecation")
		public void run() {
	    	while(true){
	    		if(ifcount)
		    	{
		    		if(countsecond >= 1)//2太难玩了
			    	{
			    		countsecond --;
			    		Time2.setText("" + countsecond);
			    	}
			    	else
			    	{
			    		ifcount = false;
			    		countsecond = 5;
			    		Time2.setText("5");
//			    		point_list[0] = 20;
//			    	    point_list[1] = 40;
//			    	    point_list[2] = 30;
//			    	    point_list[3] = 10;
//			    	    point_list[4] = 30;
//			    	    point_list[5] = 50;
			    		remove(food_label);
			    		if(foodtag == 1 || foodtag == 5)//分值最高的两种食物在规定时间内没被吃掉就消失
			    		{
			    			ProduceFood();
			    		}
			    		ProduceRandom();
			    		add(food_label);
			    		ifcount = true;
			    	}
		    	}
	    		
	    		try {
	    			Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	    	}
	    }
	}
	
	//计时器类
	class Timer extends Thread{  
		    public Timer(){
		        this.start();
		    }
		    @Override
		    public void run() {
		        while(true){
		            if(isrun){
		                sec +=1 ;
		                if(sec >= 60){
		                    sec = 0;
		                    min +=1 ;
		                }
		                if(min>=60){
		                    min=0;
		                    hour+=1;
		                }
		                showTime();
		            }
		 
		            try {
		                Thread.sleep(1000);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		             
		        }
		    }

		    private void showTime(){
		        String strTime ="" ;
		        if(hour < 10)
		            strTime = "0"+hour+":";
		        else
		            strTime = ""+hour+":";
		         
		        if(min < 10)
		            strTime = strTime+"0"+min+":";
		        else
		            strTime =strTime+ ""+min+":";
		         
		        if(sec < 10)
		            strTime = strTime+"0"+sec;
		        else
		            strTime = strTime+""+sec;
		         
		        //在窗体上设置显示时间
		        Time.setText(strTime);
		    }
		}	
}



class AePlayWave extends Thread { 	 
    private String filename;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 

    public AePlayWave(String wavfile) { 
        filename = wavfile;
    } 
    	    
    public void run() { 
        File soundFile = new File(filename); 
        AudioInputStream audioInputStream = null;
        try { 
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) { 
            e1.printStackTrace();
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            while (nBytesRead != -1) { 
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) 
                    auline.write(abData, 0, nBytesRead);
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally { 
            auline.drain();
            auline.close();
        } 
    } 
}