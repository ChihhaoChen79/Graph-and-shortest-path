package hao.graph.practice;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class TaiwanNavigation implements ActionListener {
    private final ArrayList<String> cities;
    private final Graph taiwanMap;
    private JComboBox<String> sourceBox;
    private JComboBox<String> destinationBox;
    private JButton buttonGo;
    private JLabel resultLabelCost;
    private JLabel resultLabelTime;
    private String selectSource;
    private String selectDestination;

    public TaiwanNavigation(){

        this.cities = new ArrayList<>();
        Collections.addAll(cities,"台北", "新北", "基隆", "桃園", "新竹",
                "苗栗", "台中", "彰化", "雲林", "嘉義",
                "台南", "高雄", "屏東", "宜蘭", "花蓮",
                "台東", "南投", "澎湖", "金門", "馬祖");
        this.taiwanMap = new Graph(cities);
        // 設定公路交通
        this.taiwanMap.setEdge("台北","新北","開車",0.5, 60);
        this.taiwanMap.setEdge("新北","基隆","開車",0.5, 60);
        this.taiwanMap.setEdge("新北","桃園","開車",0.5, 60);
        this.taiwanMap.setEdge("新北","宜蘭","開車",1, 100);
        this.taiwanMap.setEdge("桃園","新竹","開車",0.5, 60);
        this.taiwanMap.setEdge("桃園","宜蘭","開車",2, 250);
        this.taiwanMap.setEdge("新竹","苗栗","開車",0.5, 60);
        this.taiwanMap.setEdge("苗栗","台中","開車",0.5, 60);
        this.taiwanMap.setEdge("台中","宜蘭","開車",8, 350); // 台7甲
        this.taiwanMap.setEdge("台中","南投","開車",0.5, 90);
        this.taiwanMap.setEdge("台中","彰化","開車",0.5, 60);
        this.taiwanMap.setEdge("彰化","南投","開車",0.5, 120);
        this.taiwanMap.setEdge("彰化","雲林","開車",0.5, 60);
        this.taiwanMap.setEdge("雲林","南投","開車",0.5, 60);
        this.taiwanMap.setEdge("雲林","嘉義","開車",0.5, 60);
        this.taiwanMap.setEdge("嘉義","南投","開車",1, 140);
        this.taiwanMap.setEdge("嘉義","台南","開車",0.5, 60);
        this.taiwanMap.setEdge("台南","高雄","開車",0.5, 60);
        this.taiwanMap.setEdge("高雄","屏東","開車",0.5, 60);
        this.taiwanMap.setEdge("高雄","台東","開車",6, 400); // 南橫
        this.taiwanMap.setEdge("屏東","台東","開車",1.5, 90);
        this.taiwanMap.setEdge("宜蘭","花蓮","開車",2, 140); // 蘇花
        this.taiwanMap.setEdge("花蓮","南投","開車",2, 120);
        this.taiwanMap.setEdge("花蓮","台東","開車",1, 90);
        // 設定搭高鐵
        this.taiwanMap.setEdge("台北","桃園","搭高鐵",0.3, 160);
        this.taiwanMap.setEdge("台北","台中","搭高鐵",0.75, 700);
        this.taiwanMap.setEdge("台北","台南","搭高鐵",1.4, 1350);
        this.taiwanMap.setEdge("台北","高雄","搭高鐵",1.5, 1490);
        this.taiwanMap.setEdge("桃園","台中","搭高鐵",0.5, 540);
        this.taiwanMap.setEdge("桃園","台南","搭高鐵",1.15, 1190);
        this.taiwanMap.setEdge("桃園","高雄","搭高鐵",1.3, 1330);
        this.taiwanMap.setEdge("台中","台南","搭高鐵",0.7, 650);
        this.taiwanMap.setEdge("台中","高雄","搭高鐵",0.85, 790);
        this.taiwanMap.setEdge("台南","高雄","搭高鐵",0.2, 140);
        // 設定國內航班
        this.taiwanMap.setEdge("澎湖","台北","搭飛機",1.67,2000);
        this.taiwanMap.setEdge("澎湖","台中","搭飛機",1.5,1600);
        this.taiwanMap.setEdge("澎湖","高雄","搭飛機",1.5,1700);
        this.taiwanMap.setEdge("金門","台北","搭飛機",2,2500);
        this.taiwanMap.setEdge("金門","台中","搭飛機",1.67,2100);
        this.taiwanMap.setEdge("金門","高雄","搭飛機",1.67,2200);
        this.taiwanMap.setEdge("馬祖","台北","搭飛機",2.5,2700);
        this.taiwanMap.setEdge("馬祖","台中","搭飛機",2.75,2300);
        this.taiwanMap.setEdge("花蓮","台北","搭飛機",1.5,2300);
        this.taiwanMap.setEdge("花蓮","台中","搭飛機",1.5,2300);
        this.taiwanMap.setEdge("花蓮","高雄","搭飛機",1.5,2300);
        // 設定渡輪
        this.taiwanMap.setEdge("基隆","馬祖","坐船",9,1050);

        this.showWindow();
    }

    private void showWindow(){
        Color textColor = Color.BLACK;

        JPanel panelLeft = new JPanel();
        panelLeft.setBounds(0,0,240,320);
        panelLeft.setBackground(Color.GRAY);
        panelLeft.setLayout(null);

        JPanel panelRight = new JPanel();
        panelRight.setBounds(240,0,560,320);
        panelRight.setLayout(null);
        panelRight.setBackground(Color.LIGHT_GRAY);

        int stringsSize = cities.size();
        String[] cityNames = new String[stringsSize+1];
        cityNames[0] = "";
        for(int i=0;i<stringsSize;i++){
            cityNames[i+1]=cities.get(i);
        }

        sourceBox = new JComboBox<>(cityNames);
        sourceBox.addActionListener(this);
        sourceBox.setBounds(20,20,200,60);
        sourceBox.setFont(new Font("微軟正黑體",Font.PLAIN,20));
        sourceBox.setBorder(BorderFactory.createTitledBorder("Start from:"));

        destinationBox = new JComboBox<>(cityNames);
        destinationBox.addActionListener(this);
        destinationBox.setBounds(20,100,200,60);
        destinationBox.setFont(new Font("微軟正黑體",Font.PLAIN,20));
        destinationBox.setBorder(BorderFactory.createTitledBorder("Go to:"));

        buttonGo = new JButton();
        buttonGo.addActionListener(this);
        buttonGo.setFocusable(false);
        buttonGo.setLayout(null);
        buttonGo.setBounds(20,180,200,50);
        buttonGo.setText("出發");
        buttonGo.setFont(new Font("微軟正黑體",Font.PLAIN,16));
        buttonGo.setHorizontalTextPosition(JButton.CENTER);
        buttonGo.setVerticalTextPosition(JButton.CENTER);
        buttonGo.setForeground(textColor);

        resultLabelCost = new JLabel("請選擇起點、終點");
        resultLabelCost.setLayout(null);
        resultLabelCost.setBounds(10,10,520,140);
        resultLabelCost.setBorder(new TitledBorder("Least Cost"));
        resultLabelCost.setFont(new Font("微軟正黑體",Font.PLAIN,16));
        resultLabelCost.setForeground(textColor);
        resultLabelCost.setBackground(Color.WHITE);
        resultLabelCost.setVerticalTextPosition(JLabel.TOP);
        resultLabelCost.setHorizontalTextPosition(JLabel.LEFT);

        resultLabelTime = new JLabel("請選擇起點、終點");
        resultLabelTime.setLayout(null);
        resultLabelTime.setBounds(10,150,520,140);
        resultLabelTime.setBorder(new TitledBorder("Least Time"));
        resultLabelTime.setFont(new Font("微軟正黑體",Font.PLAIN,16));
        resultLabelTime.setForeground(textColor);
        resultLabelTime.setBackground(Color.WHITE);
        resultLabelTime.setVerticalTextPosition(JLabel.TOP);
        resultLabelTime.setHorizontalTextPosition(JLabel.LEFT);

        JLabel signature = new JLabel("<html><body><p align=\"left\">Chihhao<br/>2021<body><html>");
        signature.setLayout(null);
        signature.setBounds(20,250,100,28);
        signature.setFont(new Font("微軟正黑體",Font.BOLD,12));

        panelLeft.add(sourceBox);
        panelLeft.add(destinationBox);
        panelLeft.add(buttonGo);
        panelLeft.add(signature);
        panelRight.add(resultLabelCost);
        panelRight.add(resultLabelTime);

        JFrame frame = new JFrame();
        frame.setSize(800,340);
        frame.setTitle("Taiwan Cities Navigation");
        ImageIcon frameIcon = new ImageIcon("src/CAR.png");
        frame.setIconImage(frameIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(30,30,30));
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.add(panelLeft);
        frame.add(panelRight);

    }

    public String showLeastCostPath(String source,String destination){
        if(source==null || destination==null || source.equals("") || destination.equals("")){
            return "請選擇起點、終點";
        }
        int cost = this.taiwanMap.leastCost(source,destination);
        ArrayList<String> path = this.taiwanMap.getResultPath();
        ArrayList<String> vehicle = this.taiwanMap.getResultVehicle();

        StringBuilder string = new StringBuilder();
        for(int i=0;i<vehicle.size();i++){
            string.append(path.get(i)).append(" ").append(vehicle.get(i)).append(" 到 ");
        }
        string.append(path.get(path.size() - 1));
        return "<html><body><p align=\"left\">最省錢路徑：<br/>"+string+"<br/>總花費："+cost+" 元<body><html>";

    }
    public String showLeastTimePath(String source,String destination){
        if(source==null || destination==null || source.equals("") || destination.equals("")){
            return "請選擇起點、終點";
        }
        double time = this.taiwanMap.leastTime(source,destination);
        ArrayList<String> path = this.taiwanMap.getResultPath();
        ArrayList<String> vehicle = this.taiwanMap.getResultVehicle();

        StringBuilder string = new StringBuilder();
        for(int i=0;i<vehicle.size();i++){
            string.append(path.get(i)).append(" ").append(vehicle.get(i)).append(" 到 ");
        }
        string.append(path.get(path.size() - 1)).append("\n");
        return "<html><body><p align=\"left\">最省時路徑：<br/>"+string+"<br/>總花費："+time+" 小時<body><html>";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sourceBox){
            selectSource = Objects.requireNonNull(sourceBox.getSelectedItem()).toString();
        }
        if(e.getSource()==destinationBox){
            selectDestination = Objects.requireNonNull(destinationBox.getSelectedItem()).toString();
        }
        if(e.getSource()==buttonGo){
            String stringCost = showLeastCostPath(selectSource,selectDestination);
            String stringTime = showLeastTimePath(selectSource,selectDestination);
            resultLabelCost.setText(stringCost);
            resultLabelTime.setText(stringTime);
        }
    }
}
