import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DesktopSearchEngineGUI extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    private BinarySearchTree tree;

    public DesktopSearchEngineGUI() {
        setTitle("Mini Desktop Search Engine");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        setVisible(true);
    }
    private static final Set<String> unwantedWords = new HashSet<>(Arrays.asList(
            "a", "ain't", "am", "an", "and", "are", "aren't", "as", "at", "be", "been", "by",
            "can", "cannot", "cant", "can't", "co", "co.", "com", "could", "couldn't", "did",
            "didn't", "do", "does", "doesn't", "don't", "eg", "eg", "else", "et", "etc", "ex",
            "for", "from", "going", "got", "had", "hadn't", "has", "hasn't", "have", "haven't",
            "he", "he'd", "he'll", "her", "hers", "he's", "hi", "him", "his", "how", "i", "i'd",
            "ie", "if", "i'll", "i'm", "in", "inc", "instead", "into", "is", "isn't", "it", "it'd",
            "it'll", "its", "it's", "i've", "let", "let's", "ltd", "may", "mayn't", "me", "might",
            "mightn't", "mine", "mr", "mrs", "ms", "must", "mustn't", "my", "nd", "needn't", "no",
            "non", "none", "nor", "not", "of", "off", "oh", "ok", "okay", "on", "one's", "onto",
            "or", "ought", "oughtn't", "our", "ours", "out", "over", "per", "que", "qv", "rd", "re",
            "shall", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "sub",
            "such", "sup", "th", "than", "that", "that'll", "thats", "that's", "that've", "the",
            "their", "theirs", "them", "then", "thence", "there", "there'd", "there'll", "there're",
            "theres", "there's", "there've", "these", "they", "they'd", "they'll", "they're", "they've",
            "thing", "things", "this", "those", "thus", "to", "too", "un", "up", "us", "via", "viz",
            "vs", "was", "wasn't", "we", "we'd", "we'll", "were", "we're", "weren't", "we've", "what'll",
            "what's", "what've", "where's", "who'd", "who'll", "who's", "will", "with", "won't", "would",
            "wouldn't", "yet", "you", "you'd", "you'll", "your", "you're", "yours", "you've", "1", "2",
            "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",":",";"
    ));


    public void actionPerformed(ActionEvent e) {
        tree = new BinarySearchTree();
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

                // sonuçlar ekrana yazdırılacak
                String inorder = tree.inOrderTraversal();
                String postorder = tree.postOrderTraversal();
                String preorder = tree.preOrderTraversal();
                resultArea.setText("Arama sonuçları İnorder :\n" + inorder + "\n\n");
                resultArea.append("Arama sonuçları Postorder :\n" + postorder + "\n\n");
                resultArea.append("Arama sonuçları Preorder :\n" + preorder + "\n\n");
            }
        }
    }

    public void DosyaIcerigiOku(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
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
                    if (!word.matches("[0-9,.:;]+") && !unwantedWords.contains(word)) {
                        //  istenmeyen kelimeleri dikkate alma ve kelimeyi ağaca ekleme işlemi
                        BinarySearchTree(word);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void BinarySearchTree(String word) {
        tree.insert(word);
    }

    public void search(String word) {
        int result = tree.search(word);
        resultArea.append("Arama sonuçları:\n" + word + " kelimesi " + result + " kez bulundu.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DesktopSearchEngineGUI());
    }
}
