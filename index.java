
package random_choose;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class index {
    static Random random = new Random();
    static int random_data = 0;
    static Scanner menu = new Scanner(System.in);
    static String cate;
    static int cnt = 0;
    static int lport;
    static String rhost;
    static int rport;
    static String changed;
    static ResultSet countting;
    static ArrayList<UserBean> menu_list = new ArrayList<UserBean>();
    static String cate_name;

    public static void main_Frame(){
        JFrame f = new JFrame("오늘 뭐 먹지?");
        f.setSize(500,200);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(null);

        JButton korea = new JButton("한식");
        JButton china = new JButton("중식");
        JButton japen = new JButton("일식");
        JButton western = new JButton("양식");

        Container contentPane = f.getContentPane();
        contentPane.setBackground(Color.pink);

        contentPane.setLayout(new GridLayout(1,4));

        f.getContentPane().add(korea);
        f.getContentPane().add(china);
        f.getContentPane().add(japen);
        f.getContentPane().add(western);

        f.setVisible(true);

        korea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if(btn.getText().equals("한식")){
                    cate = "한식";
                    sub_Frame();
                    categori();
                }
            }
        });
        china.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if(btn.getText().equals("중식")){
                    cate = "중식";
                    categori();
                }
            }
        });
        japen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if(btn.getText().equals("일식")){
                    cate = "일식";
                    categori();
                }
            }
        });
        western.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if(btn.getText().equals("양식")){
                    cate = "양식";
                    categori();
                }
            }
        });
    }

    public static void sub_Frame(){
        JFrame f2 = new JFrame("오늘 뭐 먹지?");
        JPanel p2 = new JPanel();
        f2.setSize(500,200);
        f2.setLocationRelativeTo(null);
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.getContentPane().setLayout(null);

        Container contentPane = f2.getContentPane();
        contentPane.setBackground(Color.pink);

        contentPane.setLayout(new BorderLayout());

        f2.add(new JTextField());
        f2.add(p2);
        f2.setVisible(true);
    }

    public static void categori() {

        switch (cate) {
            case "한식":
                changed = "Korea";
                cate_name = "한식";
                System.out.println("한식메뉴를 추천해드리겠습니다.");
                select();
                break;
            case "중식":
                changed = "China";
                cate_name = "중식";
                System.out.println("중식메뉴를 추천해드리겠습니다.");
                select();
                break;
            case "일식":
                changed = "Japen";
                cate_name = "일식";
                System.out.println("일식메뉴를 추천해드리겠습니다.");
                select();
                break;
            case "양식":
                changed = "Western";
                cate_name = "양식";
                System.out.println("양식메뉴를 추천해드리겠습니다.");
                select();
                break;
            default:
                System.out.println("잘못 입력 하셨습니다.");
        }

        menu.close();
    }

    public static void go() {
        String user = "hojin";
        String password = "abc1234#";
        String host = "218.144.98.116";
        int port = 22;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            lport = 3927;
            rhost = "127.0.0.1";
            rport = 3927;
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
        } catch (Exception e) {
        }
    }

    public static void select() {
        try {
            go();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Connection con = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + rhost + ":" + lport + "/";
        String db = "menu";
        String dbUser = "project";
        String dbPasswd = "project!39#";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + db, dbUser, dbPasswd);
            try {
                Statement st = con.createStatement();
                ResultSet rs = null;
                String sql = "select name from " + changed;
                rs = st.executeQuery(sql);

                while (rs.next()) {
                    UserBean bean = new UserBean();
                    bean.setname(rs.getString("name"));
                    menu_list.add(bean);
                }

                random_data = random.nextInt(menu_list.size());
                System.out.println(" ===== 오늘의 "+ cate_name +" 추천 메뉴는 ?? =====");
                System.out.println("===== " + menu_list.get(random_data).getname() + " =====");

            } catch (SQLException s) {
                System.out.println("SQL statement is not executed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        main_Frame();
    }

}
