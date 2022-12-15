package com.example.testone;

public class Review {
    private int groceryItemId;
    private String userName;
    private String Text;
    private String Date;

    public Review(int groceryItemId, String userName, String text, String date) {
        this.groceryItemId = groceryItemId;
        this.userName = userName;
        Text = text;
        Date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "groceryItemId=" + groceryItemId +
                ", userName='" + userName + '\'' +
                ", Text='" + Text + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
