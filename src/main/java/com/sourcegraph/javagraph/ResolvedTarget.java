package com.sourcegraph.javagraph;


class ResolvedTarget {
    String ToRepoCloneURL;
    String ToUnit;
    String ToUnitType;
    String ToVersionString;

    public static ResolvedTarget jdk() {
        ResolvedTarget target = new ResolvedTarget();
        target.ToRepoCloneURL = ScanCommand.JDK_REPO;
        target.ToUnitType = "Java";
        target.ToUnit = ".";
        return target;
    }

    public static ResolvedTarget androidSDK() {
        ResolvedTarget target = new ResolvedTarget();
        target.ToRepoCloneURL = ScanCommand.ANDROID_SDK_REPO;
        target.ToUnitType = "JavaArtifact";
        target.ToUnit = ".";
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResolvedTarget that = (ResolvedTarget) o;

        if (ToRepoCloneURL != null ? !ToRepoCloneURL.equals(that.ToRepoCloneURL) : that.ToRepoCloneURL != null)
            return false;
        if (ToUnit != null ? !ToUnit.equals(that.ToUnit) : that.ToUnit != null) return false;
        if (ToUnitType != null ? !ToUnitType.equals(that.ToUnitType) : that.ToUnitType != null) return false;
        if (ToVersionString != null ? !ToVersionString.equals(that.ToVersionString) : that.ToVersionString != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ToRepoCloneURL != null ? ToRepoCloneURL.hashCode() : 0;
        result = 31 * result + (ToUnit != null ? ToUnit.hashCode() : 0);
        result = 31 * result + (ToUnitType != null ? ToUnitType.hashCode() : 0);
        result = 31 * result + (ToVersionString != null ? ToVersionString.hashCode() : 0);
        return result;
    }
}
