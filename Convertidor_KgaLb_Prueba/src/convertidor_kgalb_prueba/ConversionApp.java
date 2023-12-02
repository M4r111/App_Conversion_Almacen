package convertidor_kgalb_prueba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Marco Gonzalez
 */
public class ConversionApp extends JFrame {

    private JPanel mainPanel, conversionPanel, productPanel, resultPanel;
    private JTextField kgField, lbField, productNameField, quantityField;
    private JButton convertToLbButton, convertToKgButton, enterProductButton, saveButton, nextButton, backButton;
    private JTextArea productDetailsArea;
    private double convertedLbs;
    private boolean isKgToLb;
    private List<Product> productList;

    public ConversionApp() {
        setTitle("Conversor y Gestor de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        productList = new ArrayList<>();

        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        conversionPanel = new JPanel();
        conversionPanel.setLayout(new GridLayout(4, 2));

        JLabel kgLabel = new JLabel("Kilogramos:");
        kgField = new JTextField();
        JLabel lbLabel = new JLabel("Libras:");
        lbField = new JTextField();
        convertToLbButton = new JButton("Convertir a Libras");
        convertToLbButton.addActionListener(new ConvertToLbButtonListener());
        convertToKgButton = new JButton("Convertir a Kilogramos");
        convertToKgButton.addActionListener(new ConvertToKgButtonListener());
        nextButton = new JButton("Siguiente");
        nextButton.addActionListener(new NextButtonListener());

        conversionPanel.add(kgLabel);
        conversionPanel.add(kgField);
        conversionPanel.add(lbLabel);
        conversionPanel.add(lbField);
        conversionPanel.add(convertToKgButton);
        conversionPanel.add(convertToLbButton);
        backButton = new JButton("Volver");
        backButton.addActionListener(new BackButtonListener());

        conversionPanel.add(backButton);
        conversionPanel.add(nextButton);
        lbField.setEditable(true);

        mainPanel.add(conversionPanel, "CONVERSION");

        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(4, 2));

        JLabel productNameLabel = new JLabel("Nombre del Producto:");
        productNameField = new JTextField();
        JLabel quantityLabel = new JLabel("Cantidad de Piezas:");
        quantityField = new JTextField();
        enterProductButton = new JButton("Ingresar Producto");
        enterProductButton.addActionListener(new EnterProductButtonListener());
        backButton = new JButton("Volver");
        backButton.addActionListener(new BackButtonListener());

        productDetailsArea = new JTextArea();
        productDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productDetailsArea);

        productPanel.add(productNameLabel);
        productPanel.add(productNameField);
        productPanel.add(quantityLabel);
        productPanel.add(quantityField);
        productPanel.add(enterProductButton);
        productPanel.add(backButton);
        productPanel.add(new JLabel());
        productPanel.add(scrollPane);

        mainPanel.add(productPanel, "PRODUCT");

        resultPanel = new JPanel();
        saveButton = new JButton("Guardar");
        saveButton.addActionListener(new SaveButtonListener());

        resultPanel.add(saveButton);

        mainPanel.add(resultPanel, "RESULT");

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ConvertToLbButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double kg = Double.parseDouble(kgField.getText());
                convertedLbs = kg * 2.20462;
                lbField.setText(String.valueOf(convertedLbs));
                isKgToLb = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor válido para los kilogramos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ConvertToKgButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double lb = Double.parseDouble(lbField.getText());
                double convertedKg = lb / 2.20462;
                kgField.setText(String.valueOf(convertedKg));
                isKgToLb = false;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor válido para las libras.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class NextButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            cardLayout.show(mainPanel, "PRODUCT");
        }
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            cardLayout.show(mainPanel, "CONVERSION");
        }
    }

    private class EnterProductButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String productName = productNameField.getText();
            int quantity = 0;

            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String conversionType = isKgToLb ? "Kilogramos" : "Libras";
            double value = isKgToLb ? Double.parseDouble(kgField.getText()) : convertedLbs;

            System.out.println("Producto: " + productName + " - Cantidad: " + quantity + " - " + conversionType + ": " + value);
            productList.add(new Product(productName, quantity, conversionType, value));

            updateProductDetailsArea();

            productNameField.setText("");
            quantityField.setText("");

            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            cardLayout.show(mainPanel, "CONVERSION");
        }
    }

    private void updateProductDetailsArea() {
        StringBuilder details = new StringBuilder();
        details.append("Detalles del Producto:\n");

        for (Product product : productList) {
            details.append("Nombre: ").append(product.getName()).append(" - Cantidad: ").append(product.getQuantity())
                    .append(" - ").append(product.getConversionType()).append(": ").append(product.getValue()).append("\n");
        }

        productDetailsArea.setText(details.toString());
    }

    private class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Product product : productList) {
                System.out.println("Producto: " + product.getName() + " - Cantidad: " + product.getQuantity()
                        + " - " + product.getConversionType() + ": " + product.getValue());
            }

            JOptionPane.showMessageDialog(null, "Datos guardados temporalmente.");
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConversionApp());
    }
}
