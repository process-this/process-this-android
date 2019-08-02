package io.github.processthis.client.model;

import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

//this is a pojo
public class UserProfile {

  private UUID id;

  private Date created;

  private Date updated;

  private String authId;

  private String userName;

  private String bio;

  private URI href;

  private List sketches = new LinkedList<>();

  private List likes = new LinkedList<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public String getAuthId() {
    return authId;
  }

  public void setAuthId(String authId) {
    this.authId = authId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public List getSketches() {
    return sketches;
  }

  public void setSketches(List sketches) {
    this.sketches = sketches;
  }

  public List getLikes() {
    return likes;
  }

  public void setLikes(List likes) {
    this.likes = likes;
  }

  public URI getHref() {
    return href;
  }

  public void setHref(URI href) {
    this.href = href;
  }


}
