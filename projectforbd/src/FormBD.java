import com.opencsv.CSVWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FormBD{

    private JPanel panel1;
    private JTable table1;
    private JTabbedPane tabbedPane1;

    private JPanel Update;
    private JPanel Insert;
    private JPanel Delete;

    private JButton button1;


    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField1;

    private JButton DOWNLOADButton;
    private JButton DELETEButton;
    private JTextField DeLtextField;
    private JButton UPDATEButton;
    private JTextField textField7;
    private JComboBox comboBox1;
    private JTextField textField8;

    String[] columnNames = {"ID_R", "TITLE", "ID", "TYPE", "SECTIONNAME", "SDATE"};

    public FormBD() throws SQLException {

        TableDraw();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insert();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        DOWNLOADButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classForBD bd = new classForBD();

                CSVWriter writer = null;
                try {
                    writer = new CSVWriter(new FileWriter("PAPER.csv"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ResultSet myResultSet = null;
                try {
                    myResultSet = bd.getStatement().executeQuery(
                            //"select ID_R, TITLE, ID, TYPE, SECTIONNAME, to_char(SDATE, 'dd/mm/yyyy') AS SDATE from paper"
                            "select ID_R, Title, FName, Type, Sectionname, to_char(SDATE, 'dd/mm/yyyy') AS SDATE from MIK_6307.paper, MIK_6307.participants"
                                    +" "+ "where MIK_6307.paper.id = MIK_6307.participants.id");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.writeAll(myResultSet, true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    update();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();
                    frame.setContentPane(new FormBD().panel1);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setSize(725,500);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void delete() throws SQLException { //delete from paper where id_r = textfield;
        classForBD bd = new classForBD();
        bd.getStatement().executeUpdate(
                "delete from paper " +
                "where id_r =" + DeLtextField.getText().toString());
        TableDraw();
    }

    public void update() throws SQLException {
        classForBD bd = new classForBD();
        if(comboBox1.getSelectedItem() == "TITLE"){
            bd.getStatement().executeUpdate(
                    "UPDATE PAPER SET TITLE =" + "'"+textField8.getText().toString()+"' WHERE ID_R =" + textField7.getText().toString()
            );
        }
        if(comboBox1.getSelectedItem() == "ID"){
            bd.getStatement().executeUpdate(
                    "UPDATE PAPER SET ID =" + textField8.getText().toString()+  "WHERE ID_R =" + textField7.getText().toString()
            );
        }
        if(comboBox1.getSelectedItem() == "TYPE"){
            bd.getStatement().executeUpdate(
                    "UPDATE PAPER SET TYPE =" + "'"+textField8.getText().toString()+"' WHERE ID_R =" + textField7.getText().toString()
            );
        }
        if(comboBox1.getSelectedItem() == "SECTIONNAME"){
            bd.getStatement().executeUpdate(
                    "UPDATE PAPER SET SECTIONNAME =" + "'"+textField8.getText().toString()+"' WHERE ID_R =" + textField7.getText().toString()
            );
        }
        if(comboBox1.getSelectedItem() == "SDATE"){
            //System.out.println("UPDATE PAPER SET SDATE =" + "to_date("+ "'" + textField8.getText().toString()+ "','dd/mm/yyyy')" +" WHERE ID_R =" + textField7.getText().toString());
            bd.getStatement().executeUpdate(
                    "UPDATE PAPER SET SDATE =" +
                            "TO_DATE(" +
                            "'" + textField8.getText().toString() + "'" + ",'DD/MM/YYYY')" +
                            " WHERE ID_R ="
                            + textField7.getText().toString()
            );
        }
        TableDraw();
    }

    public void insert() throws SQLException {

        classForBD bd = new classForBD();
        Statement statement = bd.getStatement();

        statement.executeUpdate("INSERT INTO PAPER VALUES (" +
                textField1.getText().toString() + ", " + //id_r
                "'" + textField6.getText().toString() + "'" + ", " + //title
                textField5.getText().toString() + ", " + //id
                "'" + textField4.getText().toString() + "'" + ", " + //type
                textField3.getText().toString() + ", " + //sectionname
                "to_date(" + "'" + textField2.getText().toString() + "', " + "'dd/mm/yyyy')" + ")");

        TableDraw();
    }

    public void TableDraw() throws SQLException {

        classForBD bdSize = new classForBD();
        classForBD bd = new classForBD();
        ResultSet size = bdSize.getRS();
        ResultSet rs1 = bd.getRS();

        int count = 0;
        while(size.next()) {
            //System.out.println(
                    //size.getInt(1) + " " + size.getString(2) + " " + size.getString(3) + " " + size.getString(4) + " " + size.getInt(5) + " " + size.getDate(6));
            count++;
        }
        Object[][] data = new Object[count][6];
        count = 0;
        while(rs1.next()) {
            data[count][0] = rs1.getInt(1);
            data[count][1] = rs1.getString(2);
            data[count][2] = rs1.getInt(3);
            data[count][3] = rs1.getString(4);
            data[count][4] = rs1.getInt(5);
            data[count][5] = rs1.getDate(6);
            count++;
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table1.setModel(model);
        panel1.repaint();
    }
}
