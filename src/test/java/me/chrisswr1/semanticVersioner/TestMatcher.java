package me.chrisswr1.semanticVersioner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chwe
 */
public class TestMatcher {
	public TestMatcher() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matcher matcher = SemanticVersion.versionPattern.matcher("1.2.3-alpha.0.7.4+exp.sha.5114f85");
		
		System.out.println(matcher.matches());
		System.out.println("Complete:\t" + matcher.group("complete"));
		System.out.println("Main:\t\t" + matcher.group("main"));
		System.out.println("Major:\t\t" + matcher.group("majornonzero") + " | " + matcher.group("majorzero"));
		System.out.println("Minor:\t\t" + matcher.group("minornonzero") + " | " + matcher.group("minorzero"));
		System.out.println("Patch:\t\t" + matcher.group("patch"));
		System.out.println("Release:\t" + matcher.group("release"));
		System.out.println("Release #1:\t" + matcher.group("release1"));
		System.out.println("Release #n:\t" + matcher.group("releasen"));
		System.out.println("Build:\t\t" + matcher.group("build"));
		System.out.println("Build #1:\t" + matcher.group("build1"));
		System.out.println("Build #n:\t" + matcher.group("buildn"));
	}
}