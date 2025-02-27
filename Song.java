public class Song {
  private String name;
  //private string album;
  private String artist;
  private Boolean favorite;
  public  enum Rating {ONE, TWO, THREE, FOUR, FIVE};
  //we can just use something like .ordinal here!
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
  @Override
  public String toString(){
      return "SongName : " + getSongTitle() + " ";
  }
  
}
