package com.intensityanalytics.keyid;

public class KeyIDSettings
{
    private String license;
    private String url = "http://invalid.invalid";
    private boolean passiveValidation = false;
    private boolean passiveEnrollment = false;
    private boolean customThreshold = false;
    private double thresholdConfidence = 70.0;
    private double thresholdFidelity = 70.0;
    private int timeout = 0;
    private boolean strictSSL = true;

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean isPassiveValidation()
    {
        return passiveValidation;
    }

    public void setPassiveValidation(boolean passiveValidation)
    {
        this.passiveValidation = passiveValidation;
    }

    public boolean isPassiveEnrollment()
    {
        return passiveEnrollment;
    }

    public void setPassiveEnrollment(boolean passiveEnrollment)
    {
        this.passiveEnrollment = passiveEnrollment;
    }

    public boolean isCustomThreshold()
    {
        return customThreshold;
    }

    public void setCustomThreshold(boolean customThreshold)
    {
        this.customThreshold = customThreshold;
    }

    public double getThresholdConfidence()
    {
        return thresholdConfidence;
    }

    public void setThresholdConfidence(double thresholdConfidence)
    {
        this.thresholdConfidence = thresholdConfidence;
    }

    public double getThresholdFidelity()
    {
        return thresholdFidelity;
    }

    public void setThresholdFidelity(double thresholdFidelity)
    {
        this.thresholdFidelity = thresholdFidelity;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    public boolean isStrictSSL()
    {
        return strictSSL;
    }

    public void setStrictSSL(boolean strictSSL)
    {
        this.strictSSL = strictSSL;
    }
}