import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


import java.io.IOException;

public class DesktopSearchEngineGUI extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    private BinarySearchTree tree;

    public DesktopSearchEngineGUI() {
        //this.tree = tree;
        initializeGUI();

    }

    private void initializeGUI() {
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
        searchButton = new JButton("Dosya Yükle");
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
        ignoreCodes();


        // Ignore paneli


        // Silme butonunu ekle
        addClearButton();

        // Print butonunu ekle
        // addPrintButton();

        // PreOrder butonunu ekle
        preOrderButton();

        // InOrder butonunu ekle
        inOrderButton();

        // PostOrder butonunu ekle
        postOrderButton();
        // Kelime arama butonunu ekle
        SearchWordButton();


        setVisible(true);
    }

    private void ignoreCodes() {
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
        } else {
            JOptionPane.showMessageDialog(null, "İstenmeyen kelimeler yüklenemedi.");
        }
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
            // Open a file chooser dialog to select multiple files
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);  // Allow only files to be selected
            fileChooser.setMultiSelectionEnabled(true);  // Enable multiple file selection
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected files
                File[] selectedFiles = fileChooser.getSelectedFiles();

                // Process each selected file
                for (File file : selectedFiles) {
                    // Read and process HTML file
                    readFileContent(file.getAbsolutePath());
                }


                // Assuming `tree.print()` prints to a console or similar. For GUI, you might want to update this
                // Example: resultArea.setText("Contents of the tree:\n" + tree.toString());
                // You need to implement the tree's toString method or similar to make this work.
                resultArea.setText("Tree başarılı bir şekilde oluşturuldu.\n\n");
                tree.print();  // Ensure this method's output is appropriate for your context (console or GUI).
            }
        }
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

    private void SearchWordButton() {
        JButton searchWordButton = new JButton("Kelime Ara");
        searchWordButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchWordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Kelime arama
                String word = searchField.getText();
                SearchWholeTree(word);
            }
        });
        // Kelime arama butonunu arama paneline ekle
        JPanel searchPanel = (JPanel) getContentPane().getComponent(1); // Arama panelini al
        searchPanel.add(searchWordButton); // Kelime arama butonunu arama paneline ekle
    }


   /* private void addPrintButton() {
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
*/


    private void SearchWholeTree(String word) {
        searchAndAppend(word);
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


    private void readFileContent(String filePath) {
        int count = 0;
        Path path = Paths.get(filePath);
        boolean isTextContent = false;
        String justFileName = path.getFileName().toString();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Check if entering the <TEXT> tag
                if (line.startsWith("<") && line.endsWith(">")) {
                    isTextContent = true;
                    continue;
                }

                // Check if exiting the <TEXT> tag
                if (line.startsWith("</") && line.endsWith(">")) {
                    isTextContent = false;
                    continue;
                }

                // Process the content only if within the <TEXT> tag
                if (isTextContent) {
                    // Further process the line to extract words
                    String[] words = line.toLowerCase().replaceAll("[^a-z\\s]", "").split("\\s+");
                    for (String word : words) {
                        if (!word.isEmpty() && !list.contains(word)) {
                            tree.insert(word, justFileName);  // Store just the file name instead of the full path
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }


    public void BinarySearchTree(String word, String fileName) {
        tree.insert(word, fileName);
    }

    public void searchAndAppend(String word) {
        SingleLinkedList list = tree.search(word);
        if (list == null) {
            resultArea.append("Kelime bulunamadı.\n");
        } else {
            resultArea.append("Kelime: " + word + "\n");
            resultArea.append("Şu dosyalarda bu kadar geçiyor: " + list.toString() + "\n");

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DesktopSearchEngineGUI());
    }
}