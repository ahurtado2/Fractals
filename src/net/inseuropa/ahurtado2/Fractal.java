package net.inseuropa.ahurtado2;

import ij.*;
import ij.plugin.filter.FractalBoxCounter;
import ij.process.ImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fractal{
    public final File file;
    public final ImagePlus imagen;
    public final HashMap<Integer,Integer> hm=new HashMap<>();
    public final List<String> lista=new ArrayList<String>();
    public FractalBoxCounter FBC;
    public double dimension;
    public final BufferedImage bi;
    public Fractal(File f,int color)throws Exception{
        file=f;
        imagen=new ImagePlus("Fractal",bi=escalaGrises(f,color));
    }

    public Fractal comprobar(String boxsize,boolean fondoNegro){
        ImageConverter ic=new ImageConverter(imagen);
        ic.convertToGray8();
        ic.convertToRGB();
        IJ.run(imagen,"8-bit","");
        imagen.updateAndDraw();
        FBC=new FractalBoxCounter();
        FBC.sizes=boxsize;
        if(fondoNegro)FBC.blackBackground=true;
        else FBC.blackBackground=false;
        FBC.setup("",imagen);
        FBC.run(imagen.getProcessor());
        dimension=FBC.dimension;
        return this;
    }

    public static BufferedImage escalaGrises(File f,int color)throws Exception{
        BufferedImage im=ImageIO.read(f);
        Raster r=im.getRaster();
        for(int x=0;x<im.getWidth();x++)for(int y=0;y<im.getHeight();y++){
            int[] i=r.getPixel(x,y,new int[4]);
            i[3]=0;
            int media=i[0]+i[1]+i[2];
            media/=3;
            media=media>=color?255:0;
            for(int X=0;X<3;X++)i[X]=media;
            ((WritableRaster)r).setPixel(x,y,i);
        }return im;
    }
}
