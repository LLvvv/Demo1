package xjx;

import java.awt.*;
import javax.swing.*;

public class Help extends JDialog {
    private static final long serialVersionUID = 4693799019369193520L;
    private JPanel contentPane;
    private Font f = new Font("΢���ź�",Font.PLAIN,15);
	
    public Help() {
        setTitle("��Ϸ����˵��");//���ô������
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");//����ͼ��
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//����Ϊģ̬����
        setSize(400,300);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// �����������
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();// �������䱳�����
        contentPane.add(shadePanel, BorderLayout.CENTER);// �����嵽�����������
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("ע�⣬�˷���Ϸ˵������Ե����汾�ģ�������Ϸ���и��£���Ϸ˵��Ҳ����Ӧ���и���\n"
        		+ "����Ϸ����ŵ����ԭ����ƣ�"
        		+ "�����˱��˵�һЩ˼���͸��죬����һЩ���淨��\n��Ϸ˵�����£�\nͨ�������ϵķ������������ǰ���ķ��򣬳������Լ��١���Ϸ����"
        		+ "��ESC������ֱ�����¿�ʼ��Ϸ�����ո������ʵ����ͣ�Ϳ�ʼ���˵��������ò˵����Ը�����ͷ������ѡ���ٶ�"
        		+ "�������������Լ����������Ƿ�ɼ�����Ϸ�����ұ߻���ʾ��ĵ�ǰ�����Լ��÷֡���Ϸ���ж���ʳ����Ƕ�Ӧ��"
        		+ "��ֵ��ͬ�����ֵĸ���Ҳ��ͬ������ʳ�������Ӧʱ�����Զ��ƶ�������ʧ�����ԣ�����ʱ���١�");
        J1.setFocusable(false);
    	J1.setFont(f);
    	J1.setEditable(false);
    	J1.setOpaque(false);//����͸��
    	J1.setLineWrap(true);
    	shadePanel.add(J1);
    	J1.setBounds(10, 10, 380, 280);
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new Help();
	}
}