package io.github.processthis.client.model;

import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Pojo class for setting and retrieving Userprofile information from the ProcessThis RESTful web
 * API
 */
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

  /**Gets the UUID of a UserProfile
   * @return
   */
  public UUID getId() {
    return id;
  }

  /**Sets the UUID of a UserProfile
   * @param id
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**Gets the date a UserProfile was created as a Date
   * @return
   */
  public Date getCreated() {
    return created;
  }

  /**Sets the date a UserProfile was created as a Date
   * @param created
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**Gets the date a UserProfile was updated as a Date
   * @return
   */
  public Date getUpdated() {
    return updated;
  }

  /**Sets the date a UserProfile was updated as a Date
   * @param updated
   */
  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  /**Gets the AuthId supplied by Google Sign-In for a UserProfile
   * @return
   */
  public String getAuthId() {
    return authId;
  }

  /**Sets the AuthId supplied by Google Sign-In for a UserProfile
   * @param authId
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }

  /**Gets the userName of a UserProfile
   * @return
   */
  public String getUserName() {
    return userName;
  }

  /**Sets the userName of a UserProfile
   * @param userName
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**Gets the String bio of a UserProfile
   * @return
   */
  public String getBio() {
    return bio;
  }

  /**Sets the String bio of a UserProfile
   * @param bio
   */
  public void setBio(String bio) {
    this.bio = bio;
  }

  /**Gets the Sketches created by a UserProfile
   * @return
   */
  public List getSketches() {
    return sketches;
  }

  /**Sets the Sketches created by a UserProfile
   * @param sketches
   */
  public void setSketches(List sketches) {
    this.sketches = sketches;
  }

  /**Gets the Likes created by a UserProfile
   * @return
   */
  public List getLikes() {
    return likes;
  }

  /**GSts the Likes created by a UserProfile
   * @param likes
   */
  public void setLikes(List likes) {
    this.likes = likes;
  }

  /**Gets th URI path to a UserProfile in Spring Server
   * @return
   */
  public URI getHref() {
    return href;
  }

  /**Sets th URI path to a UserProfile in Spring Server
   * @param href
   */
  public void setHref(URI href) {
    this.href = href;
  }

}
