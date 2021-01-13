package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialsForm {
    private String url;
    private String credentailsusername;
    private String credentialsurlpassword;
    private Integer credentialid;

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCredentailsusername() {
        return credentailsusername;
    }

    public void setCredentailsusername(String credentailsusername) {
        this.credentailsusername = credentailsusername;
    }

    public String getCredentialsurlpassword() {
        return credentialsurlpassword;
    }

    public void setCredentialsurlpassword(String credentialsurlpassword) {
        this.credentialsurlpassword = credentialsurlpassword;
    }
}

