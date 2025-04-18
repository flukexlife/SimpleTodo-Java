import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Todolist_java implements ActionListener {
    JFrame window = new JFrame("To Do List");
    Container c;
    JPanel p1, p2;
    JTextField textField1;
    JButton btn1;
    ArrayList<String> datas;
    JButton[] btnRemove;

    public Todolist_java() {
        datas = new ArrayList<>();
        readToArray();
        c = window.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(addMenu());
        c.add(showDisplay());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 300);
        window.setVisible(true);
    }

    public JPanel addMenu() {
        p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        textField1 = new JTextField(30);
        textField1.setPreferredSize(new Dimension(150, 40));
        btn1 = new JButton("Add");
        btn1.addActionListener(this);
        btn1.setPreferredSize(new Dimension(50, 40));
        p1.add(textField1);
        p1.add(btn1);
        return p1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textField1.getText();
        if (text.isEmpty())
            return; // กันไม่ให้ใส่ข้อความว่าง
        try {
            FileWriter writer = new FileWriter("data.txt", true);
            writer.close();
            textField1.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(window, "Error saving file: " + ex.getMessage());
        }
        datas.add(text);
        saveToFile();
        updateDisplay();

    }

    public void readToArray() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                datas.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public JPanel showDisplay() {
        p2 = new JPanel();
        p2.setLayout(new GridLayout(0, 2));
        btnRemove = new JButton[datas.size()];
        updateDisplay();
        return p2;
    }

    public void updateDisplay() {
        p2.removeAll();
        btnRemove = new JButton[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            btnRemove[i] = new JButton("Remove");
            final int index = i;
            btnRemove[i].addActionListener(e -> {
                datas.remove(index);
                updateDisplay();
                saveToFile();
            });
            JPanel taskPanel = new JPanel();
            taskPanel.add(new JLabel(datas.get(i)));
            taskPanel.add(btnRemove[i]);
            p2.add(taskPanel);
        }
        // refresh UI
        p2.revalidate();
        p2.repaint();
    }

    public void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));

            for (String task : datas) {
                writer.write(task); // เขียน task ลงในไฟล์
                writer.newLine(); // ลงบรรทัดใหม่หลังจากแต่ละ task
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Todolist_java();
    }
}
