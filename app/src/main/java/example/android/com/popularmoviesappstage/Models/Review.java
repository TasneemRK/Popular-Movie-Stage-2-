package example.android.com.popularmoviesappstage.Models;

public class Review {

    private String author ;
    private String Content;

    public Review(String author, String content) {
        this.author = author;
        Content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
