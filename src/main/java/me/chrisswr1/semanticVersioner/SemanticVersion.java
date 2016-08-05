package me.chrisswr1.semanticVersioner;

import java.util.regex.Pattern;

/**
 * @author chwe
 */
public class SemanticVersion {
	public static final String intRegEx = "[1-9]\\d*|\\d";
	public static final String wordRegEx = "[0-9A-Za-z-]+";
	public static final Pattern versionPattern = Pattern.compile(
			"(?<complete>"
			+ 	"(?<main>"
			+ 		"("
			+ 			"("
			+ 				"(?<majorzero>0?)"
			+ 				"\\.(?<minorzero>[1-9][\\d]*)"
			+ 			")"
			+ 			"|"
			+ 			"("
			+ 				"(?<majornonzero>[1-9]\\d*)"
			+ 				"\\.(?<minornonzero>" + intRegEx + ")"
			+ 			")"
			+ 		")"
			+ 		"(\\.(?<patch>" + intRegEx + "))?"
			+ 	")"
			+	"(-(?<release>"
			+ 		"(?<release1>" + wordRegEx + ")"
			+ 		"(\\.(?<releasen>" + wordRegEx + "))*"
			+ 	"))?"
			+ 	"((\\+|_)(?<build>"
			+ 		"(?<build1>" + wordRegEx + ")"
			+ 		"(\\.(?<buildn>" + wordRegEx + "))*"
			+ 	"))?"
			+ ")"
	);
	
	private int major = 0;
	private int minor = 0;
	private int patch = 0;
	
	public SemanticVersion(int major, int minor, int patch){
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	public SemanticVersion(String version){
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String)
			return this.equals(new SemanticVersion((String)obj));
		
		if (!(obj instanceof SemanticVersion))
			throw new IllegalArgumentException("The other object must be a SemanticVersion!");
		
		SemanticVersion other = (SemanticVersion)obj;
		
		return
				this.getMajor() == other.getMajor()
				&& this.getMinor() == other.getMinor()
				&& this.getPatch() == other.getPatch();
	}

	@Override
	public String toString() {
		return getMainString();
	}
	
	public static boolean isValid(String version) {
		return versionPattern.matcher(version).matches();
	}
	
	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		if (major == 0)
			throw new IllegalArgumentException("Yo");
		
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getPatch() {
		return patch;
	}

	public void setPatch(int patch) {
		this.patch = patch;
	}

	public String getMainString() {
		return getMajor() + "." + getMinor() + "." + getPatch();
	}
	
	public String toUrlSaveString() {
		return getMainString();
	}
}