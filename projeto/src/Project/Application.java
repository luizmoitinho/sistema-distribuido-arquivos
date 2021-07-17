package Project;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import servers.Client;
import servers.ClientDownloadFile;
import utils.Server;
import utils.Servers;

import javax.swing.JLabel;

public final class Application extends JFrame {

    Client c;
    private JPanel contentPane;
    private JTextField txtSearch;
    private JTable serversTB;
    private Servers servers;
    private JButton btnDownload;
    private JScrollPane scrollPane;
    private JButton btnSearch;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Application frame = new Application();
                    frame.setLocationRelativeTo(null);
                    frame.setTitle("meuTorrent");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Application() throws IOException {
        try {
            c = new Client();
            servers = new Servers();

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 524, 520);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);

            serversTB = new JTable();
            serversTB.setRowHeight(30);
            serversTB.setBounds(30, 130, 445, 300);
            serversTB.setPreferredScrollableViewportSize(new Dimension(300, 20));
            serversTB.setFillsViewportHeight(true);
            serversTB.setVisible(false);
            scrollPane = new JScrollPane(serversTB);
            scrollPane.setVisible(false);
            scrollPane.setSize(445, 300);
            scrollPane.setLocation(30, 100);
            btnDownload = this.createBtnDownload("Baixar", 60, 200, 211, 30, false);
            btnSearch = this.createBtnSearch("Buscar");

            contentPane.add(btnDownload);
            contentPane.add(btnSearch);
            contentPane.add(scrollPane);

            JLabel lblNewLabel = new JLabel("Digite o nome do arquivo:");
            lblNewLabel.setBounds(30, 12, 160, 34);
            contentPane.add(lblNewLabel);

            txtSearch = new JTextField("teste.txt");
            txtSearch.setBounds(30, 56, 357, 31);
            txtSearch.setMargin(new Insets(0, 5, 0, 0));
            contentPane.add(txtSearch);
            txtSearch.setColumns(4);

        } catch (ConnectException e) {
            String mensagem = "Ocorreu um erro de Conexão!\n" + e.getMessage();
            JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
            Thread.currentThread().interrupt();
        }

    }

    public void populateTable(ArrayList<Server> servers) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("IP");
        model.addColumn("Porta");
        model.addColumn("Tamanho (Bytes)");

        for (int i = 0; i < servers.size(); i++) {
            model.addRow(new Object[]{servers.get(i).ip, servers.get(i).port, servers.get(i).size});
        }

        serversTB.setModel(model);

        btnDownload.setText("Baixar");

        scrollPane.setVisible(true);
        serversTB.setVisible(true);
    }

    private JButton createBtnDownload(String label, int x, int y, int width, int height, boolean active) {
        JButton newBtn = new JButton(label);
        newBtn.setVisible(active);
        newBtn.setBounds(145, 420, width, height);
        newBtn.addActionListener((ActionEvent arg) -> {
            if (serversTB.getSelectedRow() >= 0) {
                String search = txtSearch.getText();
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Selecione a pasta");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                int status = chooser.showOpenDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File file = new File(chooser.getSelectedFile(), search);
                    if (file == null) {
                        return;
                    }

                    String pathToSave = file.toString();
                    Server server = servers.get(serversTB.getSelectedRow());
                    ClientDownloadFile cdf = new ClientDownloadFile(server, search, pathToSave, btnDownload);
                    cdf.start();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um servidor!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return newBtn;

    }

    public JButton createBtnSearch(String label) {
        JButton btnSearch = new JButton(label);
        btnSearch.addActionListener((ActionEvent arg0) -> {
            try {
                if (txtSearch.getText().trim().length() == 0) {
                    return;
                }
                Client c1 = new Client();
                Server server;
                String[] data = c1.search(txtSearch.getText().trim());
                
                if (data == null){
                    JOptionPane.showMessageDialog(null, "O arquivo não existe", "Erro", JOptionPane.ERROR_MESSAGE);
                    servers.clean();
                    populateTable(servers.getAll());
                    return;
                }
                
                servers.clean();
                
                for (int count = 0; count < data.length; count++) {
                    String[] item = (String[]) data[count].split(":");
                    server = new Server(item[0], item[1], item[2]);
                    servers.add(server);
                }
                populateTable(servers.getAll());
                btnDownload.setVisible(true);
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnSearch.setBounds(385, 56, 89, 30);

        return btnSearch;

    }

}
