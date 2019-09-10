package View;




import DownloadUtils.DownloadWithBar;
import Parser.BossAz;
import Parser.WorkUA;

import Utils.Settings;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Window {


    public static JTextField userText;
    public static JPasswordField passwordText;
    public static JTextField keyWordsText;
    public static JTextField quantityText;
    public static JTextField cityText;
    public static JProgressBar j ;
    public static JButton loginButton = new JButton("Login");
    public static JButton installUpdate = new JButton("Install update?");
    public static JButton updateButton = new JButton("No update now");
    public static JButton exitButton = new JButton("Exit");
    public static JComboBox<String> siteMenu;
    public static JLabel help;
    public static JLabel userLabel = new JLabel("Email");
    public static JLabel passwordLabel = new JLabel("Password");
    public static JLabel keyWords = new JLabel("Keywords");
    public static JLabel quantity = new JLabel("Quantity");
    public static JLabel city = new JLabel("City");
    public static JLabel errorLabel = new JLabel();
    public static JLabel hintQ = new JLabel("max: 500");
    public static JLabel chooseSite = new JLabel("Choose site");
    public static boolean checkStart;

    //Array of Site
    public static String[] siteList = {"https://www.work.ua/",
            "https://ru.boss.az/",
    };


    public static int numberVersion = 3;

    public static JFrame frame;


    public static void main(String[] args) {
        frame = new JFrame("WorkUA parser v" + numberVersion);
        frame.setPreferredSize(new Dimension(400,380));

        // handle window close
        ImageIcon img = new ImageIcon(System.getProperty("user.dir") +"\\src\\main\\resources\\apple-touch-icon.png");


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });



        // set up panels with buttons

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(31,29,42));
        mainView(panel1);


        // display
        frame.getContentPane().setBackground(Color.red);
        frame.getContentPane().add(panel1);
        frame.setIconImage(img.getImage());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);



        //Check Update, if have - updateButton Enable. Else - Disable
        if(checkUpdateStatus()){
            updateButton.setEnabled(true);
            updateButton.setText("Update now");

        fill();
        }







    }

    private static void mainView(JPanel panel) {
//        Enter in JIRA account
        panel.setLayout(null);
//        JOptionPane.showMessageDialog(loginButton,  System.getProperty("user.dir") + "\\WorkUA_parser_installer.exe");



        JLabel h1 = new JLabel("<html> <font color='#ffffff'>Enter in</font> <font color='#ffa800'>WorkUA employer account</font></html>");
        h1.setBounds(150,10,150,30);
        panel.add(h1);

        help = new JLabel("?");
        help.setBounds(360, 10, 15, 15);
        help.setForeground(Color.WHITE);
        panel.add(help);
        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(loginButton, "*Запускать от имени админа." +
                        "\n*Отчет сохраняется в корневой папки с программой." +
                        "\n*Кнопка обновления появится если будет новое актуальное обновление.");
            }
        });

        chooseSite.setBounds(10, 50, 80, 25);
        chooseSite.setForeground(Color.WHITE);
        panel.add(chooseSite);

        siteMenu = new JComboBox<>(siteList);
        siteMenu.setBounds(180, 50, 180, 25);
        panel.add(siteMenu);
        siteMenu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(siteMenu.getSelectedIndex() == 1){
                    userText.setEditable(false);
                    userText.setText("");
                    userText.setForeground(Color.DARK_GRAY);
                    passwordText.setEditable(false);
                    passwordText.setForeground(Color.DARK_GRAY);
                }else{
                    userText.setEditable(true);
                    userText.setForeground(Color.WHITE);
                    passwordText.setEditable(true);
                    passwordText.setForeground(Color.WHITE);
                }
            }
        });




        userLabel.setBounds(10, 80, 80, 25);
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(180, 80, 180, 25);
        panel.add(userText);
        try {
            userText.setText(Settings.profile());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        passwordLabel.setBounds(10, 110, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(180, 110, 180, 25);
        panel.add(passwordText);

        keyWords.setBounds(10, 140, 180, 25);
        keyWords.setForeground(Color.WHITE);
        panel.add(keyWords);

        keyWordsText = new JTextField(20);
        keyWordsText.setBounds(180, 140, 180, 25);
        panel.add(keyWordsText);

        quantity.setBounds(10, 170, 80, 25);
        quantity.setForeground(Color.WHITE);
        panel.add(quantity);

        hintQ.setBounds(120, 170, 80, 25);
        hintQ.setForeground(Color.WHITE);
        panel.add(hintQ);

        errorLabel.setBounds(250, 170, 80, 25);
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);

        quantityText= new JTextField(20);

        quantityText.setBounds(180, 170, 180, 25);
        quantityText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char input = e.getKeyChar();

                try{
                    if ((input < '0' || input > '9') && input != '\b') {
                        e.consume();
                        errorLabel.setText("only digits");
                        checkStart = false;

                    }else{
                        errorLabel.setText("");
                        checkStart = true;
                    }


                }catch (NumberFormatException e1){
                    errorLabel.setText("only digits");
                }
            }
        });

        panel.add(quantityText);

        city.setBounds(10, 200, 80, 25);
        city.setForeground(Color.WHITE);
        panel.add(city);

        cityText = new JTextField(20);
        cityText.setBounds(180, 200, 180, 25);
        panel.add(cityText);

        j = new JProgressBar(0, DownloadWithBar.getSize());
        j.setBorder(new LineBorder(new Color(255,158,0)));
        j.setBounds(10, 240, 360, 25);
        j.setMinimum(0);
        j.setMaximum(100);
        j.setStringPainted(true);
        panel.add(j);


        loginButton.setBounds(10, 280, 80, 25);
        loginButton.setBorder(new LineBorder(new Color(255,158,0)));

        loginButton.setFocusPainted(false);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(255,158,0));
        loginButton.setFont(new Font("Roboto Mono", Font.PLAIN, 14));
        panel.add(loginButton);
        loginButton.addActionListener(new Window.LoginPressed());

        updateButton.setBounds(125, 280, 120, 25);
        updateButton.setBorder(new LineBorder(new Color(255,158,0)));
        updateButton.setFocusPainted(false);
        updateButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(255,158,0));
        updateButton.setFont(new Font("Roboto Mono", Font.PLAIN, 14));
        updateButton.setEnabled(false);
        panel.add(updateButton);
        updateButton.addActionListener(new Window.UpdateChecker());

        installUpdate.setBounds(125, 280, 120, 25);
        installUpdate.setBorder(new LineBorder(new Color(255,158,0)));
        installUpdate.setFocusPainted(false);
        installUpdate.setForeground(Color.WHITE);
        installUpdate.setBackground(new Color(255,158,0));
        installUpdate.setFont(new Font("Roboto Mono", Font.PLAIN, 14));
        installUpdate.setVisible(false);
        panel.add(installUpdate);
        installUpdate.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "\\WorkUA_parser_installer.exe"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(loginButton,  "Can't find install file: " + System.getProperty("user.dir") + "\\WorkUA_parser_installer.exe");
                ex.printStackTrace();
            }
        });



        exitButton.setBounds(280, 280, 80, 25);
        exitButton.setBorder(new LineBorder(new Color(255,158,0)));
        exitButton.setFocusPainted(false);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(255,158,0));
        exitButton.setFont(new Font("Roboto Mono", Font.PLAIN, 14));
        panel.add(exitButton);
        exitButton.addActionListener(new Window.ExitActionListener());


    }

    public static boolean checkUpdateStatus() {
        if(Settings.checkUpdate()){
            return true;
        }
        return false;
    }

    public static boolean checkSiteIsPicked(){
        if(siteMenu.getSelectedIndex() == 1){
           return true;
        }
        return false;
    }

    public static void fill() {
        int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                j.setValue(i);

                // delay the thread
                Thread.sleep(1000);
                i = (int) DownloadWithBar.getProgress();
                if(i==100){
                    updateButton.setVisible(false);
                    installUpdate.setVisible(true);
                }
            }
        } catch (Exception e) {
            System.out.println("catch v fill");
        }
    }



    public static class LoginPressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(siteMenu.getSelectedIndex());


            if(userText.getText().equals("")){
                userLabel.setForeground(Color.RED);
                return;
            }else {
                userLabel.setForeground(Color.WHITE);
            }
            if(passwordText.getPassword().length < 2){
                passwordLabel.setForeground(Color.RED);
                return;
            }
            else {
                passwordLabel.setForeground(Color.WHITE);
            }
            if(keyWordsText.getText().equals("")){
                keyWords.setForeground(Color.RED);
                return;
            }else {
                keyWords.setForeground(Color.WHITE);
            }

            if(quantityText.getText().equals("") && !checkStart){
                quantity.setForeground(Color.RED);
                return;
            }else {
                quantity.setForeground(Color.WHITE);
            }
            if(cityText.getText().equals("")){
                city.setForeground(Color.RED);
                return;
            }else {
                city.setForeground(Color.WHITE);
            }

            switch (siteMenu.getSelectedIndex()){
                case (0):
                    loginButton = (JButton) e.getSource();
                    JOptionPane.showMessageDialog(loginButton,  "СТАРТУЕМ!!!!!");
                    frame.dispose();
                    try {
                        WorkUA workUA = new WorkUA();
                        workUA.parser();
                        break;
                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                case (1):
                    loginButton = (JButton) e.getSource();
                    JOptionPane.showMessageDialog(loginButton,  "СТАРТУЕМ!!!!!");
                    frame.dispose();
                    try {
                        BossAz bossAz = new BossAz();
                        bossAz.parser();
                        break;
                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
            }




        }
    }

    public static class UpdateChecker implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (updateButton.getModel().isArmed()) {
                try {
                    DownloadWithBar downloadWithBar = new DownloadWithBar(new URL("https://github.com/or1k/WorkUA_parser/releases/download/" + (numberVersion+1) + "/WorkUA_parser_installer.exe"));
//                        updateButton.setText("Downloading");
                    if (DownloadWithBar.getStatus() == 0) {
                        updateButton.setText("Downloading");
                    }
                    updateButton.setEnabled(false);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public static class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            exitButton = (JButton)e.getSource();
            System.exit(0);

        }
    }



}