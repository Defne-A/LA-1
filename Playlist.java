public class Playlist{
  private ArrayList<Song> list;
  private String name;

  public Playlist(String title){
    this.name = title;
  }

  public void addSong(Song song){
    list.add(song);
  }

  public ArrayList<Song> getPlaylist(){
    ArrayList<Song> playlist = new ArrayList<Song>();
    for (Song s: list){
      playlist.add(s);
    }
    return playlist;
  }

  public String getTitle(){
    String title = name;
    return title;
  }

  public void changeName(String name){
    this.name = name;
  }

}
