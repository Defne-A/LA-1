import java.util.ArrayList;

public class Album {
  private ArrayList<Song> tracklist = new ArrayList<Song>(); //this was causing a null exception, so i changed it
  private String artist;
  private String genre;
  private String title;
  private String year;
  public Album(String artist, String genre, String title,String year) {
    this.artist = artist;
    this.genre = genre;
    this.title = title;
    this.year = year;
  }

  public void addSong(Song song){ //make sure to avoid the escaping reference to song here
    this.tracklist.add(song);
  }

  public String getArtist(){
    String name = artist;
    return name;
  }
  public String getGenre(){
    String gen = genre;
    return gen;
  }

  public String getAlbumTitle(){
    String alb = title; //is this required? Strings are immutable so idk if this matters
    return alb;
  }
  public String getYear(){
      return this.year;
  }
  @Override
  public String toString(){
    String netStr = "";
    for(Song s : this.tracklist){
        netStr+= s.toString() + "\n";
    }
    return netStr;
  }
  
}
