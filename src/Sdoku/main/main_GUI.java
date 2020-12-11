package Sdoku.main;

import Sdoku.Number.creater;
import Sdoku.Solver.solver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
class main_GUI extends JFrame implements ActionListener, KeyListener, Runnable {

    public manager m = new manager();
    public creater c = new creater();
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screen_size = tk.getScreenSize();
    boolean keyUp = false;
    boolean keyDown = false;
    boolean keyLeft = false;
    boolean keyRight = false;
    MenuBar mb = new MenuBar();
    Menu select = new Menu("선택");
    MenuItem back = new MenuItem("뒤로가기");
    MenuItem again = new MenuItem("다시하기");
    MenuItem shuffle = new MenuItem("무작위");
    Menu solve = new Menu("해결하기");
    MenuItem Rsolve = new MenuItem("해결하기");
    MenuItem Case = new MenuItem("경우의 수 보기");
    Thread th;
    int pos;
    int cnt;
    int line;
    int fill;
    int x_len, y_len;
    List<HashMap<Integer, Integer>> answerCase = new ArrayList<>();
    private final JPanel frame;
    private final JPanel frame_;
    private JPanel first_title;
    private JPanel btnPane;
    private JPanel TwoTwo;
    private JPanel TwoThree;
    private JPanel ThreeThree;
    private JPanel Pdummy;
    private JPanel scrollPane;
    private JPanel ScrollPane;
    private JPanel[] TP;
    private JScrollPane scroll;
    private JDialog casesee;
    private JLabel[] dummy;
    private JLabel Title;
    private JLabel deco;
    private JTextField[] blank;
    private JTextField input_x;
    private JTextField input_y;
    private JButton[] Select;
    private JButton[] dbtn;
    private JButton[] casebtn;
    private JButton btn;
    ;
    private JButton input;
    private String[] num;

    public main_GUI(String title) {
        this.setTitle(title);
        this.setLocation((screen_size.width / 3) - 300, (screen_size.height / 2) - 350);
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        frame = new JPanel();
        frame.setLayout(new GridLayout(2, 1));

        frame_ = new JPanel();
        frame_.setLayout(new BorderLayout());

        JPanel first = new JPanel();
        first.setBackground(Color.WHITE);
        first.setLayout(new GridLayout(3, 7));

        first_title = new JPanel();
        first_title.setBackground(Color.WHITE);
        first_title.setLayout(new GridLayout(3, 5));

        TwoTwo = new JPanel();
        TwoTwo.setBackground(Color.WHITE);
        TwoTwo.setLayout(new GridLayout(6, 6));

        TwoThree = new JPanel();
        TwoThree.setBackground(Color.WHITE);
        TwoThree.setLayout(new GridLayout(8, 8));

        ThreeThree = new JPanel();
        ThreeThree.setBackground(Color.WHITE);
        ThreeThree.setLayout(new GridLayout(11, 11));

        btn = new JButton();
        btn.addActionListener(this);

        back.addActionListener(this);
        again.addActionListener(this);
        shuffle.addActionListener(this);
        Rsolve.addActionListener(this);
        Case.addActionListener(this);

        select.add(back);
        select.add(again);
        select.add(shuffle);
        select.addActionListener(this);

        solve.add(Rsolve);
        solve.add(Case);
        solve.addActionListener(this);

        mb.add(select);
        mb.add(solve);

        setMenuBar(mb);

        Title = new JLabel("   스도쿠!!!");
        Font title_font = new Font("Serif", Font.BOLD, 40);
        Title.setFont(title_font);
        Title.setAlignmentX(CENTER_ALIGNMENT);


        dummy = new JLabel[14];
        for (int i = 0; i < 7; i++) {
            dummy[i] = new JLabel();
            dummy[i].setBackground(Color.DARK_GRAY);
            first_title.add(dummy[i]);
        }
        first_title.add(Title);
        for (int i = 0; i < 7; i++) {
            dummy[i + 7] = new JLabel();
            dummy[i].setBackground(Color.DARK_GRAY);
            first_title.add(dummy[i + 7]);
        }


        Select = new JButton[3];
        Font btn_font = new Font("Serif", Font.BOLD, 30);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Select[i + j] = new JButton((i + 2) + "X" + (j + 2));
                Select[i + j].setFont(btn_font);
                Select[i + j].addActionListener(this);
                Select[i + j].setBorder(null);
                Select[i + j].setBackground(Color.WHITE);
            }
        }

        Font startFont = new Font("Serif", Font.BOLD, 30);

        input_x = new JTextField("x");
        input_y = new JTextField("y");
        deco = new JLabel("X");
        input = new JButton();
        btnPane = new JPanel();

        input_x.setFont(startFont);
        input_x.setBackground(Color.LIGHT_GRAY);
        input_x.setBorder(null);
        input_y.setFont(startFont);
        input_y.setBackground(Color.LIGHT_GRAY);
        input_y.setBorder(null);
        deco.setFont(startFont);
        input_x.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        input_y.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        deco.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        btnPane.setLayout(new GridLayout(1, 3));
        btnPane.setBackground(Color.LIGHT_GRAY);
        btnPane.add(input_x);
        btnPane.add(deco);
        btnPane.add(input_y);

        input.setBackground(Color.LIGHT_GRAY);
        input.addActionListener(this);
        input.add(btnPane);

        dummy = new JLabel[24];
        dbtn = new JButton[18];

        for (int i = 0; i < 18; i++) {
            dbtn[i] = new JButton();
            dbtn[i].setBorder(null);
            dbtn[i].setVisible(false);
            dbtn[i].setBackground(Color.WHITE);
        }

        first.add(dbtn[0]);
        first.add(Select[0]);
        first.add(dbtn[1]);
        first.add(Select[1]);
        first.add(dbtn[2]);
        first.add(Select[2]);
        first.add(dbtn[3]);

        for (int i = 0; i < 3; i++) {
            first.add(dbtn[i + 4]);
        }
        first.add(input);
        for (int i = 3; i < 13; i++) {
            first.add(dbtn[i + 4]);
        }
        frame.add(first_title);
        frame.add(first);

        this.add(frame);
        this.setVisible(true);
    }

    public void diversion(int a, int b) {
        Pdummy = new JPanel();
        TP = new JPanel[a * b];
        blank = new JTextField[a * a * b * b];
        Font main_font = new Font("Serif", Font.BOLD, 40);

        Pdummy.setLayout(new GridLayout(a, b));

        for (int i = 0; i < a * b; i++) {
            TP[i] = new JPanel();
            TP[i].setLayout(new GridLayout(b, a));
        }
        int count = 0;
        int white = 0;
        boolean temp = true;
        for (int i = 0; i < a * b; i++) {
            if (temp) {
                white = i / b;
                temp = false;
            }
            for (int j = 0; j < a * b; j++) {
                blank[count] = new JTextField();
                blank[count].setFont(main_font);
                if (white % 2 == 0) blank[count].setBackground(Color.white);
                else blank[count].setBackground(Color.LIGHT_GRAY);
                blank[count].setHorizontalAlignment(0);
                blank[count].addActionListener(this);
                blank[count].addKeyListener(this);

                TP[i].add(blank[count]);
                count++;
            }
            Pdummy.add(TP[i]);
            white++;
            if (white == b + i / b) temp = true;
        }
    }

    public void moveZone() {
        String[] zone = new String[line * line];
        for (int lines = 0; lines < line * line; lines++) {
            zone[lines] = String.valueOf(lines);
        }
        zone = m.convertSolve(zone, x_len, y_len);
        for (int lines = 0; lines < line * line; lines++) {

            if (Integer.parseInt(zone[lines]) == pos) {
                LineBorder border = new LineBorder(Color.RED, 8);
                blank[lines].setBorder(border);
                blank[lines].requestFocus(true);
                if (fill == -1) {
                    blank[lines].setText("");
                    fill = 0;
                }
            } else {
                LineBorder lb = new LineBorder(Color.black, 1);
                blank[lines].setBorder(lb);
            }
        }
    }

    public void start() { // 기본적인 명령처리
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setFocusable(true);
        th = new Thread(this);
        th.start();
    }

    public void run() { // 스레드 메소드, 무한 루프
        while (true) {
            try {
                moveZone();
                for (int j = 0; j < num.length; j++) {
                    num[j] = blank[j].getText();
                }
                m.manage(num, blank, y_len, x_len);
                Thread.sleep(20);
                cnt++;

            } catch (Exception e) {
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        solver s = new solver(m.convertSolve(num, x_len, y_len), x_len, y_len);
        if (e.getSource() == shuffle) {
            m.clearblank(blank);
            m.setblank(c.RanNum(y_len, x_len), blank);
        }
        if (e.getSource() == Rsolve) {
            for (int j = 0; j < num.length; j++) {
                num[j] = blank[j].getText();
            }
            m.PaintBlue(num, blank);
            m.setblank(m.convertSolve(s.solve(), x_len, y_len), blank);

            answerCase = s.answers;

            casebtn = new JButton[answerCase.size()];
            scrollPane = new JPanel();
            scrollPane.setLayout(new GridLayout(answerCase.size() / 2 + 1, 2));
            casebtn = new JButton[answerCase.size()];
            for (int i = 0; i < answerCase.size(); i++) {
                casebtn[i] = new JButton(i + 1 + "");
                casebtn[i].addActionListener(this);
                Dimension btn = new Dimension(100, 100);
                casebtn[i].setPreferredSize(btn);
                casebtn[i].setBackground(Color.black);
                casebtn[i].setForeground(Color.orange);
                Font caseFont = new Font("serif", Font.BOLD, 30);
                casebtn[i].setFont(caseFont);
                scrollPane.add(casebtn[i]);
            }
        }
        if (e.getSource() == Case) {
            casesee = new JDialog(this, "경우의 수", true);
            casesee.setSize(300, 400);
            casesee.add(scrollPane);
            scroll = new JScrollPane(scrollPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            casesee.add(scroll);
            casesee.setVisible(true);
        }
        for (int i = 0; i < answerCase.size(); i++) {
            if (e.getSource() == casebtn[i]) {
                System.out.println("selected answer" + i + " : " + answerCase.get(i));
                m.setblank(m.convertSolve(answerCase.get(i), x_len, y_len), blank);
                break;
            }
        }
        if (e.getSource() == again) {
            m.clearblank(blank);
        }
        if (e.getSource() == back) {
            frame_.setVisible(false);
            frame_.removeAll();
            frame.setVisible(true);
        }
        if (e.getSource() == Select[0]) {
            setting(2, 2);
        } else if (e.getSource() == Select[1]) {
            setting(2, 3);
        } else if (e.getSource() == Select[2]) {
            setting(3, 3);
        } else if (e.getSource() == input) {
            try {
                setting(Integer.parseInt(input_x.getText()), Integer.parseInt(input_y.getText()));
            } catch (Exception e2) {
                System.out.println("잘못된 입력입니다");
            }
        }
    }

    public void setting(int x, int y) {
        frame.setVisible(false);
        diversion(x, y);
        x_len = x;
        y_len = y;
        frame_.add(Pdummy);
        frame_.setVisible(true);
        this.add(frame_, BorderLayout.CENTER);
        line = x * y;
        num = new String[line * line];
        start();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                pos--;
                fill = 0;
                break;
            case KeyEvent.VK_RIGHT:
                pos++;
                fill = 0;
                break;
            case KeyEvent.VK_UP:
                pos -= line;
                fill = 0;
                break;
            case KeyEvent.VK_DOWN:
                pos += line;
                fill = 0;
                break;
            case KeyEvent.VK_1:
                fill = 1;
                break;
            case KeyEvent.VK_2:
                fill = 2;
                break;
            case KeyEvent.VK_3:
                fill = 3;
                break;
            case KeyEvent.VK_4:
                fill = 4;
                break;
            case KeyEvent.VK_5:
                fill = 5;
                break;
            case KeyEvent.VK_6:
                fill = 6;
                break;
            case KeyEvent.VK_7:
                fill = 7;
                break;
            case KeyEvent.VK_8:
                fill = 8;
                break;
            case KeyEvent.VK_9:
                fill = 9;
                break;
            case KeyEvent.VK_BACK_SPACE:
                fill = -1;
                break;
        }
        if (pos >= line * line) {
            pos = pos % (line * line);
        } else if (pos < 0) {
            pos = line * line + pos;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
