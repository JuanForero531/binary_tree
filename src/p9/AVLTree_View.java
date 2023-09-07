package p9;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class AVLTree_View extends JPanel {
    private AVLTree avlTree;
    private JTextArea outputTextArea;
    private JTextField inputField;

  public AVLTree_View() {
        avlTree = new AVLTree();  
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Ingrese un valor:");
        inputField = new JTextField(10);
        JButton insertButton = new JButton("Insertar");
       insertButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int value = Integer.parseInt(inputField.getText());
            avlTree.root = avlTree.insert(avlTree.root, value); 
            inputField.setText("");
            updateOutput();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor válido.");
        }
    }
});


        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(inputField);
        inputPanel.add(insertButton);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateOutput() {
        if (avlTree != null) {
            outputTextArea.setText("Arbol AVL:\n" + avlTree.toString());
        } else {
            outputTextArea.setText("Arbol AVL vacio.");
        }
    }
}
