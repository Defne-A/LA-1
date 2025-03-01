//make sure to add packages later
package la1;

import java.io.BufferedReader;
import java.lang.String;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicStore {
    private HashMap<String, HashMap<String, Album>> musicinfo = new HashMap<>();
    
    public void readFile() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("albums.txt"));
        //temp array for each part to generate into a string
        while (in.ready()) {
            String currLine = in.readLine();
            int splitIndex = currLine.indexOf(',');
            String artistName = currLine.substring(splitIndex + 1, currLine.length());
            String albumName = currLine.substring(0, splitIndex);
            String textFile = albumName + "_" + artistName + ".txt";
            readAlbumInfo(textFile);
        }
        in.close();
    }

    private void readAlbumInfo(String fname) throws IOException {
        //this function will read in the second part of the hashmap
        //as in, the Album arrayList for each artist! (Album will contain 
        //an arrayList containing Song objects.
        BufferedReader in2 = new BufferedReader(new FileReader(fname));
        //read in the FIRST LINE
        if (in2.ready()) {
            String[] splitString = in2.readLine().split(",");
            String albumName = splitString[0];
            String artistName = splitString[1];
            String genre = splitString[2];
            String year = splitString[3];
            if (musicinfo.containsKey(artistName)) { //if the artist is seen again
                musicinfo.get(splitString[1]).put(albumName, new Album(artistName, genre, albumName, year));
            } else {
                musicinfo.put(artistName, new HashMap<String, Album>());
                musicinfo.get(artistName).put(albumName, new Album(artistName, genre, albumName, year));
            }
            //start adding songs
            while (in2.ready()) {
                musicinfo.get(artistName).get(albumName).addSong(new Song(in2.readLine(), artistName));
            }
        }
        in2.close();
    }

    // Search methods for songs
    public ArrayList<Song> searchSongByTitle(String title) {
        ArrayList<Song> results = new ArrayList<>();
        
        for (String artist : musicinfo.keySet()) {
            HashMap<String, Album> artistAlbums = musicinfo.get(artist);
            
            for (Album album : artistAlbums.values()) {
                for (Song song : extractSongsFromAlbum(album)) {
                    if (song.getSongTitle().toLowerCase().contains(title.toLowerCase())) {
                        results.add(song);
                    }
                }
            }
        }
        
        return results;
    }
    
    public ArrayList<Song> searchSongByArtist(String artist) {
        ArrayList<Song> results = new ArrayList<>();
        
        for (String artistName : musicinfo.keySet()) {
            if (artistName.toLowerCase().contains(artist.toLowerCase())) {
                HashMap<String, Album> artistAlbums = musicinfo.get(artistName);
                
                for (Album album : artistAlbums.values()) {
                    results.addAll(extractSongsFromAlbum(album));
                }
            }
        }
        
        return results;
    }
    
    // Search methods for albums
    public ArrayList<Album> searchAlbumByTitle(String title) {
        ArrayList<Album> results = new ArrayList<>();
        
        for (String artist : musicinfo.keySet()) {
            HashMap<String, Album> artistAlbums = musicinfo.get(artist);
            
            for (String albumName : artistAlbums.keySet()) {
                if (albumName.toLowerCase().contains(title.toLowerCase())) {
                    results.add(artistAlbums.get(albumName));
                }
            }
        }
        
        return results;
    }
    
    public ArrayList<Album> searchAlbumByArtist(String artist) {
        ArrayList<Album> results = new ArrayList<>();
        
        for (String artistName : musicinfo.keySet()) {
            if (artistName.toLowerCase().contains(artist.toLowerCase())) {
                HashMap<String, Album> artistAlbums = musicinfo.get(artistName);
                results.addAll(artistAlbums.values());
            }
        }
        
        return results;
    }
    
    // Get specific song or album
    public Song getSong(String title, String artist) {
        if (musicinfo.containsKey(artist)) {
            HashMap<String, Album> artistAlbums = musicinfo.get(artist);
            
            for (Album album : artistAlbums.values()) {
                for (Song song : extractSongsFromAlbum(album)) {
                    if (song.getSongTitle().equalsIgnoreCase(title)) {
                        return song;
                    }
                }
            }
        }
        
        return null;
    }
    
    public Album getAlbum(String title, String artist) {
        if (musicinfo.containsKey(artist)) {
            HashMap<String, Album> artistAlbums = musicinfo.get(artist);
            
            if (artistAlbums.containsKey(title)) {
                return artistAlbums.get(title);
            }
        }
        
        return null;
    }
    
    public ArrayList<Song> getSongsFromAlbum(String albumTitle, String artist) {
        Album album = getAlbum(albumTitle, artist);
        
        if (album != null) {
            return extractSongsFromAlbum(album);
        }
        
        return null;
    }
    
    // Helper methods
    private ArrayList<Song> extractSongsFromAlbum(Album album) {
        ArrayList<Song> songs = new ArrayList<>();
        
        // This is a workaround since the Album class doesn't provide a method to get all songs
        // We're using the toString method which lists all songs
        String albumSongsStr = album.toString();
        String[] songLines = albumSongsStr.split("\n");
        
        for (String songLine : songLines) {
            if (songLine.startsWith("SongName : ")) {
                String songTitle = songLine.substring("SongName : ".length()).trim();
                songs.add(new Song(songTitle, album.getArtist()));
            }
        }
        
        return songs;
    }
}
