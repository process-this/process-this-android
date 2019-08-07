package io.github.processthis.client.model;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

/**
 * This pojo Class mirrors information contained in the server so that the client understand that attributes of a Like
 */
//This is a pojo.
public class Like {

  private UUID id;

  private Date created;

  private Date updated;

  private UserProfile userProfile;

  private Sketch sketch;

  private URI href;

  /**Gets the UUID of a like object
   * @return 
   */
  public UUID getId() {
    return id;
  }

  /**Sets the UUID of a like object
   * @param id 
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**Gets the date a Like was created as a Date 
   * @return 
   */
  public Date getCreated() {
    return created;
  }

  /**sets the date a like was created as a Date
   * @param created 
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**Sets the date a Like was updated as a Date
   * @return 
   */
  public Date getUpdated() {
    return updated;
  }

  /**Gets the date a Like was updated as a Date
   * @param updated 
   */
  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  /**Gets the UserpPofile that created a Like
   * @return 
   */
  public UserProfile getUserProfile() {
    return userProfile;
  }

  /**Sets the UserpPofile that created a Like
   * @param userProfile 
   */
  public void setUserProfile(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  /**Gets the sketch that was liked by a user
   * @return 
   */
  public Sketch getSketch() {
    return sketch;
  }

  /**Sets the sketch that was liked by a user
   * @param sketch 
   */
  public void setSketch(Sketch sketch) {
    this.sketch = sketch;
  }

  /**Gets the URI path to a Like object on the server
   * @return 
   */
  public URI getHref() {
    return href;
  }

  /**
   * Sets the URI path to a Like object on the server
   * @param href 
   */
  public void setHref(URI href) {
    this.href = href;
  }
}
