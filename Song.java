public class Song {
  private String name;
  //private string album;
  private String artist;
  private boolean favorite;
  public enum Rating {ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5)};
  private Rating rating;

  public Song(String name, String artist){

  }

  public void setFav(){
    this.favorite = true;
  }


  
}
