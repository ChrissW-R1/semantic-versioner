package me.chrisswr1.semanticVersioner;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author chwe
 */
public class SemanticVersionTest {
	private static final String leadingZero = "1.08.3";
	
	private static SemanticVersion verFirstRelease;
	private static SemanticVersion verOneOneZero;
	private static SemanticVersion verOneOneOne;
	private static SemanticVersion verFirstIteration;
	private static SemanticVersion verFirstSnap;
	private static SemanticVersion verFirstAlpha;
	private static SemanticVersion verFirstTimestampBuild;
	
	@Before
	public void setUp() {
		verFirstRelease = new SemanticVersion(1, 0, 0);
		verOneOneZero = new SemanticVersion(1, 1, 0);
		verOneOneOne = new SemanticVersion(1, 1, 1);
		verFirstIteration = new SemanticVersion(0, 1, 0);
		verFirstSnap = new SemanticVersion("1.0.0-SNAPSHOT");
		verFirstAlpha = new SemanticVersion("1.0.0-alpha.1");
		verFirstTimestampBuild = new SemanticVersion("1.0.0+20130313144700");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegative() {
		new SemanticVersion(-1, 0, 0);
	}
	
	/**
	 * Test method for {@link SemanticVersion#equals(Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Assert.assertTrue(verFirstRelease.equals(verFirstRelease));
		Assert.assertTrue(verFirstRelease.equals(new SemanticVersion(verFirstRelease.toString())));
		Assert.assertFalse(verFirstRelease.equals(verOneOneZero));
		Assert.assertFalse(verFirstRelease.equals(verOneOneOne));
		Assert.assertFalse(verFirstRelease.equals(verFirstSnap));
	}
	
	/**
	 * Test method for {@link SemanticVersion#equals(Object)} with given {@link String}.
	 */
	@Test
	public void testEqualsObjectString() {
		Assert.assertTrue(verFirstRelease.equals(verFirstRelease.toString()));
		Assert.assertFalse(verFirstRelease.equals(verOneOneZero.toString()));
		Assert.assertFalse(verFirstRelease.equals(verOneOneOne.toString()));
		Assert.assertFalse(verFirstRelease.equals(verFirstSnap.toString()));
		Assert.assertFalse(verFirstRelease.equals(verFirstAlpha.toString()));
		Assert.assertTrue(verFirstRelease.equals(verFirstTimestampBuild.toString()));
	}
	
	/**
	 * tests if {@link SemanticVersion#equals(Object)} fails on wrong argument type
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEqualsObjectWrongArgumentType() {
		verFirstRelease.equals(Pattern.compile("\\w"));
	}
	
	/**
	 * Test method for {@link SemanticVersion#toString()}.
	 */
	@Test
	public void testToString() {
		MatcherAssert.assertThat(verFirstRelease.toString(), Matchers.equalTo("1.0.0"));
		MatcherAssert.assertThat(verOneOneZero.toString(), Matchers.equalTo("1.1.0"));
		MatcherAssert.assertThat(verOneOneOne.toString(), Matchers.equalTo("1.1.1"));
		MatcherAssert.assertThat(verFirstIteration.toString(), Matchers.equalTo("0.1.0"));
		MatcherAssert.assertThat(verFirstSnap.toString(), Matchers.equalTo("1.0.0-SNAPSHOT"));
		MatcherAssert.assertThat(verFirstAlpha.toString(), Matchers.equalTo("1.0.0-alpha.1"));
		MatcherAssert.assertThat(verFirstTimestampBuild.toString(), Matchers.equalTo("1.0.0+20130313144700"));
	}
	
	/**
	 * Test method for {@link SemanticVersion#compareTo(SemanticVersion)}.
	 */
	@Test
	public void testCompareTo() {
		MatcherAssert.assertThat(verFirstRelease.compareTo(verFirstRelease), Matchers.equalTo(0));
		MatcherAssert.assertThat(verOneOneZero.compareTo(verOneOneZero), Matchers.equalTo(0));
		MatcherAssert.assertThat(verOneOneOne.compareTo(verOneOneOne), Matchers.equalTo(0));
		MatcherAssert.assertThat(verFirstIteration.compareTo(verFirstIteration), Matchers.equalTo(0));
		MatcherAssert.assertThat(verFirstSnap.compareTo(verFirstSnap), Matchers.equalTo(0));
		MatcherAssert.assertThat(verFirstAlpha.compareTo(verFirstAlpha), Matchers.equalTo(0));
		MatcherAssert.assertThat(verFirstTimestampBuild.compareTo(verFirstTimestampBuild), Matchers.equalTo(0));
		
		MatcherAssert.assertThat(verFirstRelease.compareTo(verOneOneZero), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstRelease.compareTo(verOneOneOne), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstRelease.compareTo(verFirstIteration), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstRelease.compareTo(verFirstSnap), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstRelease.compareTo(verFirstAlpha), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstRelease.compareTo(verFirstTimestampBuild), Matchers.equalTo(0));
		
		MatcherAssert.assertThat(verOneOneZero.compareTo(verFirstRelease), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneZero.compareTo(verOneOneOne), Matchers.lessThan(0));
		MatcherAssert.assertThat(verOneOneZero.compareTo(verFirstIteration), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneZero.compareTo(verFirstSnap), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneZero.compareTo(verFirstAlpha), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneZero.compareTo(verFirstTimestampBuild), Matchers.greaterThan(0));
		
		MatcherAssert.assertThat(verOneOneOne.compareTo(verFirstRelease), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneOne.compareTo(verOneOneZero), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneOne.compareTo(verFirstIteration), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneOne.compareTo(verFirstSnap), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneOne.compareTo(verFirstAlpha), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verOneOneOne.compareTo(verFirstTimestampBuild), Matchers.greaterThan(0));
		
		MatcherAssert.assertThat(verFirstIteration.compareTo(verFirstRelease), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstIteration.compareTo(verOneOneZero), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstIteration.compareTo(verOneOneOne), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstIteration.compareTo(verFirstSnap), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstIteration.compareTo(verFirstAlpha), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstIteration.compareTo(verFirstTimestampBuild), Matchers.lessThan(0));
		
		MatcherAssert.assertThat(verFirstSnap.compareTo(verFirstRelease), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstSnap.compareTo(verOneOneZero), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstSnap.compareTo(verOneOneOne), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstSnap.compareTo(verFirstIteration), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstSnap.compareTo(verFirstAlpha), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstSnap.compareTo(verFirstTimestampBuild), Matchers.lessThan(0));
		
		MatcherAssert.assertThat(verFirstAlpha.compareTo(verFirstRelease), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstAlpha.compareTo(verOneOneZero), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstAlpha.compareTo(verOneOneOne), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstAlpha.compareTo(verFirstIteration), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstAlpha.compareTo(verFirstSnap), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstAlpha.compareTo(verFirstTimestampBuild), Matchers.lessThan(0));
		
		MatcherAssert.assertThat(verFirstTimestampBuild.compareTo(verFirstRelease), Matchers.equalTo(0));
		MatcherAssert.assertThat(verFirstTimestampBuild.compareTo(verOneOneZero), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstTimestampBuild.compareTo(verOneOneOne), Matchers.lessThan(0));
		MatcherAssert.assertThat(verFirstTimestampBuild.compareTo(verFirstIteration), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstTimestampBuild.compareTo(verFirstSnap), Matchers.greaterThan(0));
		MatcherAssert.assertThat(verFirstTimestampBuild.compareTo(verFirstAlpha), Matchers.greaterThan(0));
	}
	
	/**
	 * Test method for {@link SemanticVersion#isValid(String)}.
	 */
	@Test
	public void testIsValid() {
		Assert.assertTrue(SemanticVersion.isValid(verFirstRelease.toString()));
		Assert.assertTrue(SemanticVersion.isValid(verOneOneZero.toString()));
		Assert.assertTrue(SemanticVersion.isValid(verOneOneOne.toString()));
		Assert.assertTrue(SemanticVersion.isValid(verFirstSnap.toString()));
		Assert.assertTrue(SemanticVersion.isValid(verFirstAlpha.toString()));
		Assert.assertTrue(SemanticVersion.isValid(verFirstTimestampBuild.toString()));
		
		Assert.assertFalse(SemanticVersion.isValid(leadingZero));
	}
	
	/**
	 * Test method for {@link SemanticVersion#getMajor()}.
	 */
	@Test
	public void testGetMajor() {
		Assert.assertEquals(1, verFirstRelease.getMajor(), 0);
		Assert.assertEquals(1, verOneOneZero.getMajor(), 0);
		Assert.assertEquals(1, verOneOneOne.getMajor(), 0);
		Assert.assertEquals(1, verFirstSnap.getMajor(), 0);
		Assert.assertEquals(1, verFirstAlpha.getMajor(), 0);
		Assert.assertEquals(1, verFirstTimestampBuild.getMajor(), 0);
	}
	
	/**
	 * Test method for {@link SemanticVersion#setMajor(long)}.
	 */
	@Test
	public void testSetMajor() {
		int rndInt = (new Random()).nextInt(Integer.MAX_VALUE) + 1;
		
		verFirstRelease.setMajor(rndInt);
		Assert.assertEquals(rndInt, verFirstRelease.getMajor(), 0);
	}
	
	/**
	 * tests if {@link SemanticVersion#setMajor(long)} throws an {@link IllegalArgumentException} on negative major
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMajorNegative() {
		int rndInt = ((new Random()).nextInt(Integer.MAX_VALUE) + 1) * (-1);
		
		verFirstRelease.setMajor(rndInt);
	}
	
	/**
	 * tests if {@link SemanticVersion#setMajor(long)} throws an {@link IllegalArgumentException} on nullable version
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMajorZero() {
		verFirstRelease.setMajor(0);
	}
	
	/**
	 * Test method for {@link SemanticVersion#getMinor()}.
	 */
	@Test
	public void testGetMinor() {
		Assert.assertEquals(0, verFirstRelease.getMinor(), 0);
		Assert.assertEquals(1, verOneOneZero.getMinor(), 0);
		Assert.assertEquals(1, verOneOneOne.getMinor(), 0);
		Assert.assertEquals(0, verFirstSnap.getMinor(), 0);
		Assert.assertEquals(0, verFirstAlpha.getMinor(), 0);
		Assert.assertEquals(0, verFirstTimestampBuild.getMinor(), 0);
	}
	
	/**
	 * Test method for {@link SemanticVersion#setMinor(long)}.
	 */
	@Test
	public void testSetMinor() {
		int rndInt = (new Random()).nextInt(Integer.MAX_VALUE) + 1;
		
		verFirstRelease.setMinor(rndInt);
		Assert.assertEquals(rndInt, verFirstRelease.getMinor(), 0);
	}
	
	/**
	 * tests if {@link SemanticVersion#setMajor(long)} throws an {@link IllegalArgumentException} on negative minor
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMinorNegative() {
		int rndInt = ((new Random()).nextInt(Integer.MAX_VALUE) + 1) * (-1);
		
		verFirstRelease.setMinor(rndInt);
	}
	
	/**
	 * tests if {@link SemanticVersion#setMinor(long)} throws an {@link IllegalArgumentException} on nullable version
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMinorZero() {
		verFirstIteration.setMinor(0);
	}
	
	/**
	 * Test method for {@link SemanticVersion#getPatch()}.
	 */
	@Test
	public void testGetPatch() {
		Assert.assertEquals(0, verFirstRelease.getPatch(), 0);
		Assert.assertEquals(0, verOneOneZero.getPatch(), 0);
		Assert.assertEquals(1, verOneOneOne.getPatch(), 0);
		Assert.assertEquals(0, verFirstSnap.getPatch(), 0);
		Assert.assertEquals(0, verFirstAlpha.getPatch(), 0);
		Assert.assertEquals(0, verFirstTimestampBuild.getPatch(), 0);
	}
	
	/**
	 * Test method for {@link SemanticVersion#setPatch(long)}.
	 */
	@Test
	public void testSetPatch() {
		int rndInt = (new Random()).nextInt(Integer.MAX_VALUE) + 1;
		
		verFirstRelease.setPatch(rndInt);
		Assert.assertEquals(rndInt, verFirstRelease.getPatch(), 0);
	}
	
	/**
	 * tests if {@link SemanticVersion#setMajor(long)} throws an {@link IllegalArgumentException} on negative patch
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPatchNegative() {
		int rndInt = ((new Random()).nextInt(Integer.MAX_VALUE) + 1) * (-1);
		
		verFirstRelease.setPatch(rndInt);
	}
	
	/**
	 * Test method for {@link SemanticVersion#getMainString()}.
	 */
	@Test
	public void testGetMainString() {
		MatcherAssert.assertThat(verFirstRelease.getMainString(), Matchers.equalTo("1.0.0"));
		MatcherAssert.assertThat(verOneOneZero.getMainString(), Matchers.equalTo("1.1.0"));
		MatcherAssert.assertThat(verOneOneOne.getMainString(), Matchers.equalTo("1.1.1"));
		MatcherAssert.assertThat(verFirstIteration.getMainString(), Matchers.equalTo("0.1.0"));
		MatcherAssert.assertThat(verFirstSnap.getMainString(), Matchers.equalTo("1.0.0"));
		MatcherAssert.assertThat(verFirstAlpha.getMainString(), Matchers.equalTo("1.0.0"));
		MatcherAssert.assertThat(verFirstTimestampBuild.getMainString(), Matchers.equalTo("1.0.0"));
	}
	
	/**
	 * Test method for {@link SemanticVersion#isSnapshot()}.
	 */
	@Test
	public void testIsSnapshot() {
		Assert.assertFalse(verFirstRelease.isSnapshot());
		Assert.assertFalse(verOneOneZero.isSnapshot());
		Assert.assertFalse(verOneOneOne.isSnapshot());
		Assert.assertFalse(verFirstIteration.isSnapshot());
		Assert.assertTrue(verFirstSnap.isSnapshot());
		Assert.assertFalse(verFirstAlpha.isSnapshot());
		Assert.assertFalse(verFirstTimestampBuild.isSnapshot());
	}
	
	/**
	 * Test method for {@link SemanticVersion#isRelease()}.
	 */
	@Test
	public void testIsRelease() {
		Assert.assertTrue(verFirstRelease.isRelease());
		Assert.assertTrue(verOneOneZero.isRelease());
		Assert.assertTrue(verOneOneOne.isRelease());
		Assert.assertTrue(verFirstIteration.isRelease());
		Assert.assertFalse(verFirstSnap.isRelease());
		Assert.assertFalse(verFirstAlpha.isRelease());
		Assert.assertTrue(verFirstTimestampBuild.isRelease());
	}
	
	/**
	 * Test method for {@link SemanticVersion#getReleaseCandidateNumber()}.
	 */
	@Test
	public void testGetReleaseCandidateNumber() {
		MatcherAssert.assertThat(verFirstRelease.getReleaseCandidateNumber(), Matchers.equalTo(-1L));
		MatcherAssert.assertThat(verOneOneZero.getReleaseCandidateNumber(), Matchers.equalTo(-1L));
		MatcherAssert.assertThat(verOneOneOne.getReleaseCandidateNumber(), Matchers.equalTo(-1L));
		MatcherAssert.assertThat(verFirstIteration.getReleaseCandidateNumber(), Matchers.equalTo(-1L));
		MatcherAssert.assertThat(verFirstSnap.getReleaseCandidateNumber(), Matchers.equalTo(-1L));
		MatcherAssert.assertThat(verFirstAlpha.getReleaseCandidateNumber(), Matchers.equalTo(-1L));
		MatcherAssert.assertThat(verFirstTimestampBuild.getReleaseCandidateNumber(), Matchers.equalTo(-1L));
	}
	
	/**
	 * Test method for {@link SemanticVersion#isInitialDevelopment()}.
	 */
	@Test
	public void testIsInitialDevelopment() {
		Assert.assertFalse(verFirstRelease.isInitialDevelopment());
		Assert.assertFalse(verOneOneZero.isInitialDevelopment());
		Assert.assertFalse(verOneOneOne.isInitialDevelopment());
		Assert.assertTrue(verFirstIteration.isInitialDevelopment());
		Assert.assertFalse(verFirstSnap.isInitialDevelopment());
		Assert.assertFalse(verFirstAlpha.isInitialDevelopment());
		Assert.assertFalse(verFirstTimestampBuild.isInitialDevelopment());
	}
	
	/**
	 * Test method for {@link SemanticVersion#toUrlSaveString()}.
	 */
	@Test
	public void testToUrlSaveString() {
		MatcherAssert.assertThat(verFirstRelease.toUrlSaveString(), Matchers.equalTo("1.0.0"));
		MatcherAssert.assertThat(verOneOneZero.toUrlSaveString(), Matchers.equalTo("1.1.0"));
		MatcherAssert.assertThat(verOneOneOne.toUrlSaveString(), Matchers.equalTo("1.1.1"));
		MatcherAssert.assertThat(verFirstIteration.toUrlSaveString(), Matchers.equalTo("0.1.0"));
		MatcherAssert.assertThat(verFirstSnap.toUrlSaveString(), Matchers.equalTo("1.0.0-SNAPSHOT"));
		MatcherAssert.assertThat(verFirstAlpha.toUrlSaveString(), Matchers.equalTo("1.0.0-alpha.1"));
		MatcherAssert.assertThat(verFirstTimestampBuild.toUrlSaveString(), Matchers.equalTo("1.0.0_20130313144700"));
	}
}