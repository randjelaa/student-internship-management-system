package com.example.dto;

public class CompanyResponseDTO {

    private Long id;
    private Long userId;
    private String email;
    private String name;
    private String description;
    private String website;
    private Boolean active;

    public CompanyResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
