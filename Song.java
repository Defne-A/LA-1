public class Song {
  private String name;
  //private string album;
  private String artist;
  private Boolean favorite;
  public enum Rating {ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5)};
  private Rating rating;

  public Song(String name, String artist){
    this.artist = artist;
    this.name = name;
  }

  public void setFav(){
    this.favorite = true;
  }

  public String getSongTitle(){
    String title = name;
    return title;
  }

  public String getSongArtist(){
    String art = artist;
    return art;
  }

  public Boolean isFavorite() {
    Boolean truth = favorite;
    return truth;
  }

  public Rating getRating(){
    Rating rat = rating;
    return rat;
  }
  
}
