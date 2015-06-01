package AppPackage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerError;
import javazoom.jl.decoder.JavaLayerException;

public class MainClass 
{

    FileInputStream FIS;
    BufferedInputStream BIS;
    
    public Player player;
    public long pauselocation;
    public String filelocation;
    public long songtotallength;
    
    public void Stop()
    {
        if(player!=null)
        {
          player.close();
          pauselocation=0;
          songtotallength=0;
                  MP3PlayerGUI.Display.setText("");
        }
    }
    
    public void Pause()
    {
        if(player!=null)
        {
            try {
                pauselocation=FIS.available();
            } catch (IOException ex) {
                
            }
          player.close();
        }
    }
    
    public void play(String path)
    {
        try {
            FIS=new FileInputStream(path);
            BIS=new BufferedInputStream(FIS);
        } catch (FileNotFoundException ex) {
            
        }
        
        try {
            player=new Player(BIS);
            songtotallength=FIS.available();
            filelocation=path + "";
        } catch (JavaLayerException | IOException ex) {
            
        }
       
        new Thread(){
            @Override
            public void run(){
                try {
                    player.play();
                    if(player.isComplete() && MP3PlayerGUI.count==1)
                    {
                        play(filelocation);
                    }
                    if(player.isComplete())
                    {
                        MP3PlayerGUI.Display.setText("");
                    }
                } catch (JavaLayerException ex) {
                    
                }
            }
        }.start();
        
        
        
    }
    
    public void Resume()
    {
        try {
            FIS=new FileInputStream(filelocation);
            BIS=new BufferedInputStream(FIS);
        } catch (FileNotFoundException ex) {
            
        }
        
        try {
            player=new Player(BIS);
            FIS.skip(songtotallength-pauselocation);
        } catch (JavaLayerException ex) {
            
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        new Thread(){
            @Override
            public void run(){
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    
                }
            }
        }.start();
        
        
        
    }
    
    
    
    
}
