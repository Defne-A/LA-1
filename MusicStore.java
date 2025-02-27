//make sure to add packages later
import java.io.BufferedReader;
import java.lang.String;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
public class MusicStore{
    //this will provide a dataibase of music (albums, information, etc) for the 
    //libraryModel class to pull information from for the user's music library.
    //////if albums.txt has album_name, album_artist, then go to that album, read all of the songs in and the information.
    //instance variables
    // in order to achieve maximum reliability, say i have a Hashmap. keys are artist name, and it contains an inner hashmap album as key and album info as value (maybe an albuminfo class?)
    private HashMap<String,HashMap<String,Album>> musicinfo = new HashMap<>();
    
    public void readFile() throws IOException{
         BufferedReader in = new BufferedReader(new FileReader("albums.txt"));
         //temp array for each part to generate into a string
         while(in.ready()){
             String currLine = in.readLine();
             int splitIndex = currLine.indexOf(',');
             String artistName = currLine.substring(splitIndex+1,currLine.length());
             String albumName = currLine.substring(0,splitIndex);
             String textFile = albumName + "_" + artistName + ".txt";
             readAlbumInfo(textFile);
         }
         }
    
    private void readAlbumInfo(String fname)throws IOException{
        //this function will read in the second part of the hashmap
        //as in, the Album arrayList for each artist! (Album will contain 
        //an arrayList containing Song objects.
        BufferedReader in2 = new BufferedReader(new FileReader(fname));
        //read in the FIRST LINE
        if(in2.ready()){
            String[] splitString = in2.readLine().split(",");
            String albumName = splitString[0];
            String artistName = splitString[1];
            String genre = splitString[2];
            String year = splitString[3];
            if(musicinfo.containsKey(artistName)){ //if the artist is seen again
                musicinfo.get(splitString[1]).put(albumName,new Album(artistName,genre,albumName,year));
            }
            else{
                musicinfo.put(artistName,new HashMap<String,Album>());
                musicinfo.get(artistName).put(albumName,new Album(artistName,genre,albumName,year));
            }
            //start adding songs
            while(in2.ready()){
                musicinfo.get(artistName).get(albumName).addSong(new Song(in2.readLine(),artistName));
        }
    }
}
    
public void printLibrary(){
    //given the instance variable hashmap, go through it and ensure the data is okay!
    //This method will simply print out the current library
    for(String artistName : musicinfo.keySet()){
        System.out.println("Artist Name : " + artistName);
        for(String albumName : musicinfo.get(artistName).keySet()){
            System.out.println("Album Name :" + albumName);
            System.out.print(musicinfo.get(artistName).get(albumName).toString());
                } 
}
}
}
