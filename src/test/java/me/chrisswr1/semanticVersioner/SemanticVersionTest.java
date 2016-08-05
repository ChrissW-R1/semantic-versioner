package me.chrisswr1.semanticVersioner;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		verFirstRelease = new SemanticVersion(1, 0, 0);
		verOneOneZero = new SemanticVersion(1, 1, 0);
		verOneOneOne = new SemanticVersion(1, 1, 1);
		verFirstIteration = new SemanticVersion(0, 1, 0);
		verFirstSnap = new SemanticVersion("1.0.0-SNAPSHOT");
		verFirstAlpha = new SemanticVersion("1.0.0-alpha.1");
		verFirstTimestampBuild = new SemanticVersion("1.0.0+20130313144700");
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
	 * Test method for {@link SemanticVersion#equals(Object)} with given {@link String}
	 */
	@Test
	public void testEqualsObjectString() {
		Assert.assertTrue(verFirstRelease.equals(verFirstRelease.toString()));
		Assert.assertFalse(verFirstRelease.equals(verOneOneZero.toString()));
		Assert.assertFalse(verFirstRelease.equals(verOneOneOne.toString()));
		Assert.assertFalse(verFirstRelease.equals(verFirstSnap.toString()));
		Assert.assertFalse(verFirstRelease.equals(verFirstAlpha.toString()));
		Assert.assertFalse(verFirstRelease.equals(verFirstTimestampBuild.toString()));
	}

	/**
	 * tests if {@link SemanticVersion#equals(Object)} fails on wrong argument type
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testEqualsObjectWrongArgumentType() {
		verFirstRelease.equals(Pattern.compile("\\w"));
	}

	/**
	 * Test method for {@link SemanticVersion#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
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
	 * Test method for {@link SemanticVersion#setMajor(int)}.
	 */
	@Test
	public void testSetMajor() {
		int rndInt = (new Random()).nextInt() + 1;
		
		verFirstRelease.setMajor(rndInt);
		Assert.assertEquals(rndInt, verFirstRelease.getMajor(), 0);
	}

	/**
	 * tests if {@link SemanticVersion#setMajor(int)} throws an {@link IllegalArgumentException} on empty main version
	 */
	@Test(expected=IllegalArgumentException.class)
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
	 * Test method for {@link SemanticVersion#setMinor(int)}.
	 */
	@Test
	public void testSetMinor() {
		int rndInt = (new Random()).nextInt() + 1;
		
		verFirstRelease.setMinor(rndInt);
		Assert.assertEquals(rndInt, verFirstRelease.getMinor(), 0);
	}

	/**
	 * tests if {@link SemanticVersion#setMinor(int))} throws an {@link IllegalArgumentException} on empty main version
	 */
	@Test(expected=IllegalArgumentException.class)
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
	 * Test method for {@link SemanticVersion#setPatch(int)}.
	 */
	@Test
	public void testSetPatch() {
		int rndInt = (new Random()).nextInt() + 1;
		
		verFirstRelease.setPatch(rndInt);
		Assert.assertEquals(rndInt, verFirstRelease.getPatch(), 0);
	}

	/**
	 * tests if {@link SemanticVersion#setPatch(int))} throws an {@link IllegalArgumentException} on empty main version
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetPatchZero() {
		verFirstIteration.setMinor(0);
	}

	/**
	 * Test method for {@link SemanticVersion#getMainString()}.
	 */
	@Test
	public void testGetMainString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link SemanticVersion#toUrlSaveString()}.
	 */
	@Test
	public void testToUrlSaveString() {
		fail("Not yet implemented");
	}
}