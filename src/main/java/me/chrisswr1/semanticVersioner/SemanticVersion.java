package me.chrisswr1.semanticVersioner;

import lombok.Getter;
import me.chrisswr1.semanticVersioner.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chwe
 */
public class SemanticVersion
		implements Comparable<SemanticVersion> {
	public static final String  partDelimiter          = ".";
	public static final String  preReleaseDelimiter    = "-";
	public static final String  buildMetadataDelimiter = "+";
	public static final String  intRegEx               = "[1-9]\\d*|\\d";
	public static final String  wordRegEx              = "[0-9A-Za-z-]+";
	public static final Pattern versionPattern         = Pattern.compile("(?<complete>"
	                                                                     + "(?<main>"
	                                                                     + "("
	                                                                     + "("
	                                                                     + "(?<majorzero>0?)"
	                                                                     + "\\" + partDelimiter + "(?<minorzero>[1-9][\\d]*)"
	                                                                     + ")"
	                                                                     + "|"
	                                                                     + "("
	                                                                     + "(?<majornonzero>[1-9]\\d*)"
	                                                                     + "\\" + partDelimiter + "(?<minornonzero>" + intRegEx + ")"
	                                                                     + ")"
	                                                                     + ")"
	                                                                     + "(\\" + partDelimiter + "(?<patch>" + intRegEx + "))?"
	                                                                     + ")"
	                                                                     + "(" + preReleaseDelimiter + "(?<release>"
	                                                                     + "(?<release1>" + wordRegEx + ")"
	                                                                     + "(\\" + partDelimiter + "(?<releasen>" + wordRegEx + "))*"
	                                                                     + "))?"
	                                                                     + "((\\" + buildMetadataDelimiter + "|_)(?<build>"
	                                                                     + "(?<build1>" + wordRegEx + ")"
	                                                                     + "(\\" + partDelimiter + "(?<buildn>" + wordRegEx + "))*"
	                                                                     + "))?"
	                                                                     + ")");
	
	@Getter
	private long         major         = 0;
	@Getter
	private long         minor         = 0;
	@Getter
	private long         patch         = 0;
	private List<Object> preRelease    = new LinkedList<>();
	private List<Object> buildMetadata = new LinkedList<>();
	
	public SemanticVersion(long major, long minor, long patch) {
		if (major < 0 || minor < 0 || patch < 0) {
			throw new IllegalArgumentException("Version numbers must not negative!");
		}
		
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	public SemanticVersion(String version) {
		Matcher matcher = SemanticVersion.versionPattern.matcher(version);
		
		if (!(matcher.matches())) {
			throw new IllegalArgumentException("The given string is not a valid semantic version!");
		}
		
		String majorZero = matcher.group("majorzero");
		
		if (majorZero != null) {
			this.major = Long.valueOf(majorZero);
			this.minor = Long.valueOf(matcher.group("minorzero"));
		} else {
			this.major = Long.valueOf(matcher.group("majornonzero"));
			this.minor = Long.valueOf(matcher.group("minornonzero"));
		}
		
		this.patch = Long.valueOf(matcher.group("patch"));
		
		String release = matcher.group("release");
		if (release != null) {
			for (String releasePart : release.split("\\" + partDelimiter)) {
				if (StringUtils.isNumeric(releasePart)) {
					this.preRelease.add(Double.valueOf(releasePart));
				} else {
					this.preRelease.add(releasePart);
				}
			}
		}
		
		String build = matcher.group("build");
		if (build != null) {
			for (String buildPart : build.split("\\" + partDelimiter)) {
				if (StringUtils.isNumeric(buildPart)) {
					this.buildMetadata.add(Double.valueOf(buildPart));
				} else {
					this.buildMetadata.add(buildPart);
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return this.equals(new SemanticVersion((String) obj));
		}
		
		if (!(obj instanceof SemanticVersion)) {
			throw new IllegalArgumentException("The other object must be a SemanticVersion!");
		}
		
		SemanticVersion other = (SemanticVersion) obj;
		
		return
				this.getMajor() == other.getMajor()
				&& this.getMinor() == other.getMinor()
				&& this.getPatch() == other.getPatch()
				&& CollectionUtils.identical(this.preRelease, other.preRelease);
	}
	
	@Override
	public String toString() {
		String result = this.getMainString();
		
		if (this.preRelease.size() != 0) {
			StringBuilder builder = new StringBuilder(preReleaseDelimiter);
			
			for (Object preReleasePart : this.preRelease) {
				if (preReleasePart instanceof Number) {
					builder.append(((Number) preReleasePart).longValue());
				} else {
					builder.append(preReleasePart);
				}
				
				builder.append(partDelimiter);
			}
			
			builder.setLength(Math.max(builder.length() - partDelimiter.length(), 0));
			
			result += builder.toString();
		}
		
		if (this.buildMetadata.size() != 0) {
			StringBuilder builder = new StringBuilder(buildMetadataDelimiter);
			
			for (Object buildMetadataPart : this.buildMetadata) {
				if (buildMetadataPart instanceof Number) {
					builder.append(((Number) buildMetadataPart).longValue());
				} else {
					builder.append(buildMetadataPart);
				}
				
				builder.append(partDelimiter);
			}
			
			builder.setLength(Math.max(builder.length() - partDelimiter.length(), 0));
			
			result += builder.toString();
		}
		
		return result;
	}
	
	@Override
	public int compareTo(SemanticVersion other) {
		long tMajor = this.getMajor();
		long tMinor = this.getMinor();
		long tPatch = this.getPatch();
		
		long oMajor = other.getMajor();
		long oMinor = other.getMinor();
		long oPatch = other.getPatch();
		
		long majorDiff = tMajor - oMajor;
		long minorDiff = tMinor - oMinor;
		long patchDiff = tPatch - oPatch;
		
		int tPreReleaseParts = this.preRelease.size();
		int oPreReleaseParts = other.preRelease.size();
		int preReleaseParts  = Math.max(tPreReleaseParts, oPreReleaseParts);
		int partsCountDiff   = tPreReleaseParts - oPreReleaseParts;
		int parts            = preReleaseParts + 3;
		
		double increment = NumberUtils.max(new long[]{
				tMajor,
				oMajor,
				tMinor,
				oMinor,
				tPatch,
				oPatch,
				Math.abs(partsCountDiff),
				Character.MAX_VALUE
		});
		for (Object pr : this.preRelease) {
			if (pr instanceof Number) {
				increment = Math.max(increment, ((Number) pr).doubleValue());
			}
			// TODO lexiographic order...
		}
		
		increment = Math.pow(10, Math.ceil(Math.log10(increment)));
		if (increment < 10) {
			increment = 10;
		}
		
		double diff =
				majorDiff * Math.pow(increment, parts - 1)
				+ minorDiff * Math.pow(increment, parts - 2)
				+ patchDiff * Math.pow(increment, parts - 3);
		
		double partsDiff       = 0;
		int    comparableParts = Math.min(tPreReleaseParts, oPreReleaseParts);
		for (int i = 0; i < comparableParts; i++) {
			Object tPreReleasePart = this.preRelease.get(i);
			Object oPreReleasePart = other.preRelease.get(i);
			
			double partDiff;
			
			if (tPreReleasePart instanceof Number && oPreReleasePart instanceof Number) {
				partDiff = ((Number) tPreReleasePart).doubleValue() - ((Number) oPreReleasePart).doubleValue();
			} else {
				// TODO §11: Numeric identifiers always have lower precedence than non-numeric identifiers.
				
				String tPartString;
				String oPartString;
				
				if (tPreReleasePart instanceof Number) {
					tPartString = Long.toString(((Number) tPreReleasePart).longValue());
				} else {
					tPartString = tPreReleasePart.toString();
				}
				if (oPreReleasePart instanceof Number) {
					oPartString = Long.toString(((Number) oPreReleasePart).longValue());
				} else {
					oPartString = oPreReleasePart.toString();
				}
				
				// TODO calculate real difference
				partDiff = tPartString.toLowerCase().compareTo(oPartString.toLowerCase());
			}
			
			partsDiff += partDiff * Math.pow(increment, parts - (i + 4));
		}
		
		if (partsDiff == 0) {
			partsDiff = partsCountDiff * Math.pow(increment, parts - 4);
		}
		
		if (tPreReleaseParts == 0) {
			diff += Math.abs(partsDiff);
		} else if (oPreReleaseParts == 0) {
			diff -= Math.abs(partsDiff);
		} else {
			diff += partsDiff;
		}
		
		if (diff > 0) {
			diff = Math.ceil(diff);
		} else if (diff < 0) {
			diff = Math.floor(diff);
		}
		
		if (diff > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		if (diff < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		
		return (int) diff;
	}
	
	public static boolean isValid(String version) {
		return versionPattern.matcher(version).matches();
	}
	
	public void setMajor(long major) {
		if (major < 0) {
			throw new IllegalArgumentException("Major must not be negative!");
		}
		
		if (this.minor == 0 && major == 0) {
			throw new IllegalArgumentException("Major and minor must not be both 0!");
		}
		
		this.major = major;
	}
	
	public void setMinor(long minor) {
		if (minor < 0) {
			throw new IllegalArgumentException("Minor must not be negative!");
		}
		
		if (this.major == 0 && minor == 0) {
			throw new IllegalArgumentException("Major and minor must not be both 0!");
		}
		
		this.minor = minor;
	}
	
	public void setPatch(long patch) {
		if (patch < 0) {
			throw new IllegalArgumentException("Patch must not be negative!");
		}
		
		this.patch = patch;
	}
	
	public String getMainString() {
		return String.format("%s.%s.%s", this.getMajor(), this.getMinor(), this.getPatch());
	}
	
	public boolean isSnapshot() {
		if (this.preRelease.size() != 1) {
			return false;
		}
		
		Object preReleasePart = this.preRelease.get(0);
		
		if (preReleasePart instanceof String) {
			return ((String) preReleasePart).equalsIgnoreCase("SNAPSHOT");
		}
		
		return false;
	}
	
	public boolean isRelease() {
		return this.preRelease.isEmpty();
	}
	
	public long getReleaseCandidateNumber() {
		long result = -1;
		
		if (this.preRelease.size() < 1) {
			return result;
		}
		
		Object preReleasePart = this.preRelease.get(0);
		if (!(preReleasePart instanceof String)
		    || !(((String) preReleasePart).equalsIgnoreCase("RC"))) {
			return result;
		}
		
		result = 0;
		
		if (this.preRelease.size() >= 2) {
			preReleasePart = this.preRelease.get(1);
			if (preReleasePart instanceof Number) {
				result = ((Number) preReleasePart).longValue();
			}
		}
		
		return result;
	}
	
	public boolean isInitialDevelopment() {
		return this.major <= 0;
	}
	
	public String toUrlSaveString() {
		return this.toString().replaceAll("\\+", "_");
	}
}