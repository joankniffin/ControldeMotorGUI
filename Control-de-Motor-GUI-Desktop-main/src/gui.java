import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import javax.imageio.ImageIO;

public class gui extends JFrame implements ActionListener {

    private JButton boton_izquierda, boton_derecha, boton_pausa;
    private JLabel label_estado, label_tiempo;
    Font customFont = new Font("Arial", Font.BOLD, 16);

    public gui() {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Motor controller GUI");

        label_estado = new JLabel("El motor esta detenido");
        label_estado.setFont(customFont);
        label_estado.setBounds(134, 100, 300, 100);
        add(label_estado);

        // Scale and load the images
        ImageIcon img_flecha_izquierda = new ImageIcon(scaleImage("img/izq.png", 80, 80));
        boton_izquierda = new JButton(img_flecha_izquierda);
        boton_izquierda.setBounds(134, 200, 80, 80);
        boton_izquierda.addActionListener(this);
        add(boton_izquierda);

        ImageIcon img_pausa = new ImageIcon(scaleImage("img/pausa.jpeg", 80, 80));
        boton_pausa = new JButton(img_pausa);
        boton_pausa.setBounds(218, 200, 80, 80);
        boton_pausa.addActionListener(this);
        add(boton_pausa);

        ImageIcon img_flecha_derecha = new ImageIcon(scaleImage("img/der.png", 80, 80));
        boton_derecha = new JButton(img_flecha_derecha);
        boton_derecha.setBounds(302, 200, 80, 80);
        boton_derecha.addActionListener(this);
        add(boton_derecha);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = sdf.format(now);

        label_tiempo = new JLabel(formattedTime);
        label_tiempo.setFont(customFont);
        label_tiempo.setBounds(218, 250, 100, 100);
        add(label_tiempo);

        // Create a Timer to update the time every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the current time
                Date now = new Date();
                // Format the time in the desired format (e.g., HH:mm:ss)
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String formattedTime = sdf.format(now);
                // Update the text of the JLabel with the formatted time
                label_tiempo.setText(formattedTime);
            }
        });
        timer.start(); // Start the Timer
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton_izquierda) {
            label_estado.setText("El motor esta girando a la izquierda");
        }
        if (e.getSource() == boton_pausa) {
            label_estado.setText("El motor esta detenido");
        }
        if (e.getSource() == boton_derecha) {
            label_estado.setText("El motor esta girando a la derecha");
        }
    }

    public static void main(String[] args) {
        gui gui = new gui();
        gui.setBounds(0, 0, 500, 500);
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setLocationRelativeTo(null);
    }

    public BufferedImage scaleImage(String imagePath, int width, int height) {
        try {
            // Load the original image using getResourceAsStream
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            if (inputStream == null) {
                throw new IOException("Could not find file: " + imagePath);
            }
            BufferedImage originalImage = ImageIO.read(inputStream);

            // Scale the image to the new dimensions
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Create a new image from the scaled image
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(scaledImage, 0, 0, null);
            g.dispose();

            return resizedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
