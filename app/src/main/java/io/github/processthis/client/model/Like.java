package io.github.processthis.client.model;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

//This is a pojo.
public class Like {

  private UUID id;

  private Date created;

  private Date updated;

  private UserProfile userProfile;

  private Sketch sketch;

  private URI href;

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

  public UserProfile getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  public Sketch getSketch() {
    return sketch;
  }

  public void setSketch(Sketch sketch) {
    this.sketch = sketch;
  }

  public URI getHref() {
    return href;
  }

  public void setHref(URI href) {
    this.href = href;
  }
}
