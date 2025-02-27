import java.io.IOException;
public class LibraryModel{
    public LibraryModel(){
        //interacts with the view, playlist, ect.
        try{
        MusicStore m1 = new MusicStore();
        m1.readFile();
        m1.printLibrary();
    }
    catch(IOException e){
        System.out.println("TEST");
    }
    }
}
