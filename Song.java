package la1;
public class Song {
    private final String name;
    //private string album;
    private final String artist;
    private Boolean favorite;
    public enum Rating {ONE, TWO, THREE, FOUR, FIVE};
    //we can just use something like .ordinal here!
    private Rating rating;

    public Song(String name, String artist) {
        this.artist = artist;
        this.name = name;
        this.favorite = false; // Initialize as not favorite
    }

    public void setFav() {
        this.favorite = true;
    }

    public String getSongTitle() {
        String title = name;
        return title;
    }

    public String getSongArtist() {
        String art = artist;
        return art;
    }

    public Boolean isFavorite() {
        Boolean truth = favorite;
        return truth;
    }

    public Rating getRating() {
        Rating rat = rating;
        return rat;
    }
    
    // New method to set the rating
    public void setRating(Rating rating) {
        this.rating = rating;
        
        // If rating is FIVE, automatically set as favorite
        if (rating == Rating.FIVE) {
            setFav();
        }
    }
    
    @Override
    public String toString() {
        return "SongName : " + getSongTitle() + " ";
    }
}
