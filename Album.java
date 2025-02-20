import java.util.ArrayList;

public class Album {
  private ArrayList<Song> tracklist;
  private String artist;
  private String genre;
  private String title;

  public Album(String artist, String genre, String title) {
    this.artist = artist;
    this.genre = genre;
    this.title = title;
  }

  public void addSong(Song song){
    this.tracklist.add(song);
  }

  
}
