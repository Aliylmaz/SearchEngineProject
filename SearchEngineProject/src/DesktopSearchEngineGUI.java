import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;


public class DesktopSearchEngineGUI extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    private BinarySearchTree tree;


    public DesktopSearchEngineGUI() {
        setTitle("Mini Desktop Search Engine");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        tree = new BinarySearchTree();


        // Başlık paneli
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(52, 152, 219));
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titleLabel = new JLabel("Mini Masaüstü Arama Motoru");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Arama paneli
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        searchLabel = new JLabel("Arama:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton = new JButton("Ara");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.addActionListener(this);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.CENTER);

        // Sonuç paneli
        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        resultArea.setBackground(new Color(240, 240, 240));
        resultArea.setForeground(Color.BLACK);
        resultArea.setText("Arama sonuçları burada görüntülenecek.");
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        resultPanel.add(scrollPane);
        add(resultPanel, BorderLayout.SOUTH);


        // Ignore paneli

        JOptionPane.showMessageDialog(null, "İstenmeyen kelimeleri belirlemek için bir dosya seçin.");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // seçilen dosya okunacak
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            // dosya içeriği okunacak
            readIgnoreListFromTextFile(filePath);
            JOptionPane.showMessageDialog(null, "İstenmeyen kelimeler başarıyla yüklendi.");
            list.print();
        }

        // Silme butonunu ekle
        addClearButton();

        // Print butonunu ekle
        addPrintButton();

        // PreOrder butonunu ekle
        preOrderButton();

        // InOrder butonunu ekle
        inOrderButton();

        // PostOrder butonunu ekle
        postOrderButton();















        setVisible(true);
    }
    private void addClearButton() {
        JButton clearButton = new JButton("Temizle");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TextArea'yı temizle
                resultArea.setText("");
            }
        });
        // Silme butonunu arama paneline ekle
        JPanel searchPanel = (JPanel) getContentPane().getComponent(1); // Arama panelini al
        searchPanel.add(clearButton); // Silme butonunu arama paneline ekle
    }

    private void addPrintButton() {
        JButton printButton = new JButton("Print Tree");
        printButton.setFont(new Font("Arial", Font.PLAIN, 16));
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Tree print
                System.out.println("Tree print");
                tree.print();
            }
        });
        // Print butonunu arama paneline ekle
        JPanel searchPanel = (JPanel) getContentPane().getComponent(1); // Arama panelini al
        searchPanel.add(printButton); // Print butonunu arama paneline ekle
    }
    private void preOrderButton() {
        JButton preOrderButton = new JButton("PreOrder");
        preOrderButton.setFont(new Font("Arial", Font.PLAIN, 16));
        preOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // PreOrder print
                resultArea.setText("PreOrder print\n");
                String preOrder = tree.preOrderTraversal();
                resultArea.append(preOrder);
            }
        });
        // PreOrder butonunu arama paneline ekle
        JPanel searchPanel = (JPanel) getContentPane().getComponent(1); // Arama panelini al
        searchPanel.add(preOrderButton); // PreOrder butonunu arama paneline ekle
    }
    private void inOrderButton() {
        JButton inOrderButton = new JButton("InOrder");
        inOrderButton.setFont(new Font("Arial", Font.PLAIN, 16));
        inOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // InOrder print
                resultArea.setText("InOrder print\n");
                String inOrder = tree.inOrderTraversal();
                resultArea.append(inOrder);
            }
        });
        // InOrder butonunu arama paneline ekle
        JPanel searchPanel = (JPanel) getContentPane().getComponent(1); // Arama panelini al
        searchPanel.add(inOrderButton); // InOrder butonunu arama paneline ekle
    }
    private void postOrderButton() {
        JButton postOrderButton = new JButton("PostOrder");
        postOrderButton.setFont(new Font("Arial", Font.PLAIN, 16));
        postOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // PostOrder print
                resultArea.setText("PostOrder print\n");
                String postOrder = tree.postOrderTraversal();
                resultArea.append(postOrder);
            }
        });
        // PostOrder butonunu arama paneline ekle
        JPanel searchPanel = (JPanel) getContentPane().getComponent(1); // Arama panelini al
        searchPanel.add(postOrderButton); // PostOrder butonunu arama paneline ekle
    }

    SingleLinkedList list = new SingleLinkedList();






    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchButton) {
            // butona tıklandıgınd dosya seçme penceresi açılacak
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(false);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                // seçilen dosya okunacak
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                // dosya içeriği okunacak
                DosyaIcerigiOku(filePath);


                // dosya içeriği aranacak
                String searchText = searchField.getText();
                search(searchText);


                // Tree print
                resultArea.setText("Tree başarılı bir şekilde oluşturuldu.\n\n");
                tree.print();





                // sonuçlar ekrana yazdırılacak
                //String inorder = tree.inOrderTraversal();
                //String postorder = tree.postOrderTraversal();
               // String preorder = tree.preOrderTraversal();
               // resultArea.setText("Arama sonuçları İnorder :\n" + inorder + "\n\n");
               // resultArea.append("Arama sonuçları Postorder :\n" + postorder + "\n\n");
               // resultArea.append("Arama sonuçları Preorder :\n" + preorder + "\n\n");
            }
        }
    }
    public void DosyaIcerigiOku(String fileName) {
        try {
            // Dosya adını al
            Path path = Paths.get(fileName);
            String justFileName = path.getFileName().toString();

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                // Etiketleri işleme
                if (line.startsWith("<") && line.endsWith(">")) {
                    continue; // Etiket satırını atla
                }

                // Satırı kelimelere ayır
                String[] words = line.split("\\s+"); // Boşluklara göre ayır
                for (String word : words) {
                    // Küçük harfe dönüştür
                    word = word.toLowerCase();
                    // Rakamları ve noktalama işaretlerini dikkate alma
                    if (!word.matches("[0-9,.:;]+") && !list.contains(word)) {
                        // İstenmeyen kelimeleri dikkate alma ve kelimeyi ağaca ekme işlemi
                        BinarySearchTree(word, justFileName);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void readIgnoreListFromTextFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void BinarySearchTree(String word, String fileName) {
        tree.insert(word, fileName);
    }

    public void search(String word) {
        int result = tree.search(word);
        resultArea.append("Arama sonuçları:\n" + word + " kelimesi " + result + " kez bulundu.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DesktopSearchEngineGUI());
    }
}
