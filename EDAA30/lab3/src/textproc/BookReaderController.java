package textproc;

import java.awt.*;
 
import javax.swing.*; 

import java.util.Map.Entry;

public class BookReaderController {
    public BookReaderController(GeneralWordCounter counter) {
        SwingUtilities.invokeLater(() -> createWindow(counter, "BookReader", 1000, 500));
    }

    private void createWindow(GeneralWordCounter counter, String title, int width, int height) {
        // List config
    	SortedListModel listModel = new SortedListModel(counter.getWordList());
    	JList<SortedListModel> listView = new JList<SortedListModel>(listModel);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Window config
        JFrame frame = new JFrame(title);
        Container pane = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane(listView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(width, height));

        // Sorting
        JRadioButton sortAlphabetical = new JRadioButton("Alphabetically sorted");
        JRadioButton sortFrequency = new JRadioButton("Frequency sorted");
        sortAlphabetical.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sortAlphabetical);
        buttonPanel.add(sortFrequency);
        buttonGroup.add(sortAlphabetical);
        buttonGroup.add(sortFrequency);

        sortAlphabetical.addActionListener(e -> listModel.sort((x,y) -> ((Entry<String, Integer>) x).getKey().compareTo(((Entry<String, Integer>) y).getKey())));
        sortFrequency.addActionListener(e -> listModel.sort((x,y) -> -(((Entry<String, Integer>) x).getValue() - ((Entry<String, Integer>) y).getValue())));

        // Searching
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Find");
        searchField.setPreferredSize(new Dimension(500, (int) searchButton.getPreferredSize().getHeight()));
        searchPanel.add(searchButton);
        searchPanel.add(searchField);
        frame.getRootPane().setDefaultButton(searchButton);
        
        searchButton.addActionListener(e -> {
            String searchedKey = searchField.getText().toLowerCase().trim();
            boolean found = false;

            for(int i = 0; i < listModel.getSize(); i++) {
                String currentKey = ((Entry<String, Integer>) listModel.getElementAt(i)).getKey();

                if(currentKey.equals(searchedKey)) {
                    listView.setSelectedIndex(i);
                    listView.ensureIndexIsVisible(i);
                    found = true;
                    break;
                }
            }

            if(!found) {
                JOptionPane.showMessageDialog(frame, "Word not found");
            }
        });
        
        // Add panes
        pane.add(scrollPane, BorderLayout.NORTH);
        pane.add(buttonPanel, BorderLayout.CENTER);
        pane.add(searchPanel, BorderLayout.SOUTH);
        
        frame.pack();
        frame.setVisible(true);
    }
}