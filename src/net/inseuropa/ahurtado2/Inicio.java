package net.inseuropa.ahurtado2;
import ij.gui.ImageLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.text.DecimalFormat;

public class Inicio extends JFrame{
    public static void main(String[] args)throws Exception{
        new Inicio("Calculadora de la dimensió fractal");
    }
    Inicio(String s){

        setTitle(s);
        setSize(510,155);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton seleccionar=new JButton("Seleccionar imatge");
        seleccionar.setSize(150,25);
        seleccionar.setLocation(5,5);
        add(seleccionar);

        JTextField directorio=new JTextField("Imatge no seleccionada");
        directorio.setSize(330,25);
        directorio.setLocation(160,5);
        add(directorio);

        JLabel labelbs=new JLabel("Tamany de les caixes:");
        labelbs.setSize(240,25);
        labelbs.setLocation(5,35);
        add(labelbs);

        JTextField boxsize=new JTextField("2,4,8,16,32,64");
        boxsize.setSize(240,25);
        boxsize.setLocation(5,60);
        add(boxsize);

        JLabel labelcl=new JLabel("Saturació de color");
        labelcl.setSize(240,25);
        labelcl.setLocation(250,35);
        add(labelcl);

        JTextField color=new JTextField("128");
        color.setSize(240,25);
        color.setLocation(250,60);
        add(color);

        JButton enviar=new JButton("Envia");
        enviar.setSize(150,25);
        enviar.setLocation(5,95);
        add(enviar);

        JButton ver=new JButton("Visualitza");
        ver.setSize(150,25);
        ver.setLocation(160,95);
        add(ver);

        JLabel labelfondo=new JLabel("Fons negre");
        labelfondo.setSize(100,25);
        labelfondo.setLocation(320,95);
        add(labelfondo);

        JCheckBox fondoNegro=new JCheckBox();
        fondoNegro.setSize(20,20);
        fondoNegro.setLocation(385,98);
        add(fondoNegro);


        seleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setCurrentDirectory(new File(directorio.getText()).getParentFile());
                FileNameExtensionFilter jnef=new FileNameExtensionFilter("Imatges","png","jpg","jpeg","gif");
                jfc.setFileFilter(jnef);
                if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)directorio.setText(jfc.getSelectedFile().getAbsolutePath());
            }
        });

        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Fractal f=new Fractal(new File(directorio.getText()),Integer.valueOf(color.getText())).comprobar(boxsize.getText(),fondoNegro.isSelected());
                    JOptionPane.showMessageDialog(null,f.dimension<=0?"Va haver un error al resoldre la imatge.":"La dimensió fractal és "+new DecimalFormat("#.#######").format(f.dimension)+".","Dimensió fractal trobada",JOptionPane.INFORMATION_MESSAGE);
                }catch(Exception Ex){
                    Ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Va haver un error trobant la dimensió fractal del fitxer.","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Fractal F=new Fractal(new File(directorio.getText()),Integer.valueOf(color.getText()));
                    File f=File.createTempFile("png","png");
                    ImageIO.write(F.bi,"png",f);
                    Desktop.getDesktop().open(f);
                }catch(Exception Ex){
                    Ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Va haver un error al mostrar la imatge.");
                }
            }
        });

        setVisible(true);
    }
}
