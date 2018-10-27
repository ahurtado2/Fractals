package net.inseuropa.ahurtado2;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;

public class Inicio extends JFrame{
    public static void main(String[] args)throws Exception{
        new Inicio("Calculadora de la dimensió fractal");
    }
    Inicio(String s){
        setTitle(s);
        setSize(510,110);
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

        JButton enviar=new JButton("Envia");
        enviar.setSize(150,25);
        enviar.setLocation(5,40);
        add(enviar);


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
                    Fractal f=new Fractal(new File(directorio.getText()));
                    JOptionPane.showMessageDialog(null,f.dimension<=0?"Va haver un error al resoldre la imatge.":"La dimensió fractal és "+new DecimalFormat("#.#######").format(f.dimension)+".","Dimensió fractal trobada",JOptionPane.INFORMATION_MESSAGE);
                }catch(Exception Ex){
                    Ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Va haver un error trobant la dimensió fractal del fitxer.","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}
