package it.polimi.ingsw.PSP13.model.player;

import it.polimi.ingsw.PSP13.model.Turn;

public class Player {

    private Color color;
    private Builder[] builders;
    private Turn god;
    private int age;
    private String username;


    public void move()
    {
        god.move();
    }

    public void build()
    {
        god.build();
    }

    public void setup()
    {
        god.setup();
    }

    public void win()
    {
        god.checkWin();
    }

    /**
     * @param color
     * @param age
     * @param username
     * initializes a new instance with the basic attributes
     */
    public Player(Color color, int age, String username)
    {
        this.color = color;
        this.age = age;
        this.username = username;

        builders = new Builder[2];
    }




    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Builder[] getBuilders() {
        return builders;
    }

    public void setBuilders(Builder[] builders) {
        this.builders = builders;
    }

    public Turn getGod() {
        return god;
    }

    public void setGod(Turn god) {
        this.god = god;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}