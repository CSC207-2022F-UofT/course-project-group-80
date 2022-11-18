package com.group80.uoftinder.entities;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class User {

    private String uid;

    private String userType; // make this final later idk

    private int score;

    // this information is displayed on profile
    private String displayName; // last name and first name displayed on profile
    private Uri photoUrl;
    private int age;
    private String gender;

    private ArrayList<HashSet<Integer>> answers;
    private List viewed;
    private List liked;
    private List matches;
    private List compatibilityList;


    public User(){
        this.liked = new ArrayList<String>();
        this.viewed = new ArrayList<String>();
        this.matches = new ArrayList<String>();
        this.compatibilityList = new ArrayList<String>();
    }

    public String getUserType(){
        return userType;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public ArrayList<HashSet<Integer>> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<HashSet<Integer>> answers) {
        this.answers = answers;
    }

    public List getViewed() {
        return viewed;
    }

    public void setViewed(List viewed) {
        this.viewed = viewed;
    }

    public List getLiked() {
        return liked;
    }

    public void setLiked(List liked) {
        this.liked = liked;
    }

    public List getMatches() {
        return matches;
    }

    public void setMatches(List matches) {
        this.matches = matches;
    }

    public List getCompatibilityList() {
        return compatibilityList;
    }

    public void setCompatibilityList(List compatibilityList) {
        this.compatibilityList = compatibilityList;
    }
}