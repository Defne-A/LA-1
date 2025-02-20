public class Playlist{
  private ArrayList<Song> list;
  private String name;

  public Playlist(String title){
    this.name = title;
  }

  public void changeName(String name){
    this.name = name;
  }

}
