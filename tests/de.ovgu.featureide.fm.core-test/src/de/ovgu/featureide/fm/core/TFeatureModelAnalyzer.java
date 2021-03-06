/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2013  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.fm.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Test;

import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelReader;

/**
 * Tests for {@link FeatureModelAnalyzer} 
 * 
 * @author Jens Meinicke
 * @author Stefan Krueger
 * @author Florian Proksch
 */
public class TFeatureModelAnalyzer {
	
	protected static File MODEL_FILE_FOLDER = getFolder();
	
	private static final FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".xml");
		}
	};
	
	private FeatureModel FM_test_1 = init("test_1.xml");
	private Feature FM1_F1 = FM_test_1.getFeature("F1");
	private Feature FM1_F2 = FM_test_1.getFeature("F2");
	private Constraint FM1_C1 = FM_test_1.getConstraints().get(0);
	private HashMap<Object, Object> FM1_DATA = FM_test_1.getAnalyser().analyzeFeatureModel(null);
	
	private FeatureModel FM_test_2 = init("test_2.xml");
	private Feature FM2_F1 = FM_test_2.getFeature("F1");
	private Feature FM2_F2 = FM_test_2.getFeature("F2");
	private Feature FM2_F3 = FM_test_2.getFeature("F3");
	private Constraint FM2_C1 = FM_test_2.getConstraints().get(0);
	private Constraint FM2_C2 = FM_test_2.getConstraints().get(1);
	private Constraint FM2_C3 = FM_test_2.getConstraints().get(2);
	private HashMap<Object, Object> FM2_DATA = FM_test_2.getAnalyser().analyzeFeatureModel(null);
	
	private FeatureModel FM_test_3 = init("test_3.xml");
	private Feature FM3_F2 = FM_test_3.getFeature("F2");
	private Feature FM3_F3 = FM_test_3.getFeature("F3");
	private Constraint FM3_C1 = FM_test_3.getConstraints().get(0); 
	private HashMap<Object, Object> FM3_DATA = FM_test_3.getAnalyser().analyzeFeatureModel(null);
	
	private FeatureModel FM_test_4 = init("test_4.xml");
	private Feature FM4_F1 = FM_test_4.getFeature("I");
	private Feature FM4_F2 = FM_test_4.getFeature("D");
	private Feature FM4_F3 = FM_test_4.getFeature("E");
	private Feature FM4_F4 = FM_test_4.getFeature("K");
	private Feature FM4_F5 = FM_test_4.getFeature("L");
	private Feature FM4_F6 = FM_test_4.getFeature("N");
	private Feature FM4_F7 = FM_test_4.getFeature("P");
	private Feature FM4_F8 = FM_test_4.getFeature("M");
	private Feature FM4_F9 = FM_test_4.getFeature("C");
	private Feature FM4_F10 = FM_test_4.getFeature("J");
	private HashMap<Object, Object> FM4_DATA = FM_test_4.getAnalyser().analyzeFeatureModel(null);
	
	/** 
     * @return 
	 */ 
	private static File getFolder() { 
		File folder =  new File("/vol1/teamcity_itidb/TeamCity/buildAgent/work/featureide/tests/de.ovgu.featureide.fm.core-test/src/analyzefeaturemodels/"); 
		if (!folder.canRead()) { 
			folder =  new File(ClassLoader.getSystemResource("analyzefeaturemodels").getPath()); 
		} 
		return folder; 
	}
	
	private final FeatureModel init(String name) {
		FeatureModel fm = new FeatureModel();
		for (File f : MODEL_FILE_FOLDER.listFiles(filter)) {
			if (f.getName().equals(name)) {
				try {
					new XmlFeatureModelReader(fm).readFromFile(f);
					break;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedModelException e) {
					e.printStackTrace();
				}
			}
		}
		return fm;
	}
	
	@Test
	public void TFalseOptional_FM1_F1() {
		assertEquals(FM1_DATA.get(FM1_F1), FeatureStatus.FALSE_OPTIONAL);
	}
	
	@Test
	public void TFalseOptional_FM1_F2() {
		assertEquals(FM1_DATA.get(FM1_F2), FeatureStatus.FALSE_OPTIONAL);
	}
	
	@Test
	public void TFalseOptional_FM1_C1() {
		assertEquals(FM1_DATA.get(FM1_C1), ConstraintAttribute.FALSE_OPTIONAL);
	}
	
	@Test
	public void TFalseOptional_FM1_C1_F1() {
		assertTrue(FM1_C1.getFalseOptional().contains(FM1_F1));
	}
	
	@Test
	public void TFalseOptional_FM1_C1_F2() {
		assertTrue(FM1_C1.getFalseOptional().contains(FM1_F2));
	}
	
	@Test
	public void TFalseOptional_FM2_F1() {
		assertEquals(FM2_DATA.get(FM2_F1), FeatureStatus.FALSE_OPTIONAL);
	}
	
	@Test
	public void TFalseOptional_FM2_F2() {
		assertEquals(FM2_DATA.get(FM2_F2), FeatureStatus.FALSE_OPTIONAL);
	}
	
	@Test
	public void TFalseOptional_FM2_F3() {
		assertEquals(FM2_DATA.get(FM2_F3), null);
	}
	
	@Test
	public void TFalseOptional_FM2_C1() {
		assertEquals(FM2_DATA.get(FM2_C1), ConstraintAttribute.FALSE_OPTIONAL);
	}
	
	@Test
	public void TFalseOptional_FM2_C1_F1() {
		assertTrue(FM2_C1.getFalseOptional().contains(FM2_F1));
	}
	
	@Test
	public void TFalseOptional_FM2_C1_F2() {
		assertTrue(FM2_C1.getFalseOptional().contains(FM2_F2));
	}
	
	@Test
	public void TFalseOptional_FM2_C1_F3() {
		assertTrue(!FM2_C1.getFalseOptional().contains(FM2_F3));
	}
	
	@Test
	public void TFalseOptional_FM2_C2() {
		assertEquals(FM2_DATA.get(FM2_C2), ConstraintAttribute.REDUNDANT);
	}
	
	@Test
	public void TFalseOptional_FM2_C2_F() {
		assertTrue(FM2_C2.getFalseOptional().isEmpty());
	}
	
	@Test
	public void TFalseOptional_FM2_C3() {
		assertEquals(FM2_DATA.get(FM2_C3), ConstraintAttribute.REDUNDANT);
	}
	
	@Test
	public void TFalseOptional_FM2_C3_F() {
		assertTrue(FM2_C3.getFalseOptional().isEmpty());
	}
	
	@Test
	public void TFalseOptional_FM3_F2() {
		assertEquals(FM3_DATA.get(FM3_F2), FeatureStatus.FALSE_OPTIONAL);
	}
	
	@Test
	public void TDead_FM3_F2() {
		assertEquals(FM3_DATA.get(FM3_F3), FeatureStatus.DEAD);
	}
	
	@Test
	public void TFalseOptional_FM3_C1() {
		assertEquals(FM3_DATA.get(FM3_C1), ConstraintAttribute.DEAD);
	}
	
	@Test
	public void TFalseOptional_FM3_C1_contains() {
		assertTrue(FM3_C1.getFalseOptional().contains(FM3_F2));
	}
	
	@Test
	public void TDead_FM3_C1_contains() {
		assertTrue(FM3_C1.getDeadFeatures().contains(FM3_F3));
	}
	
	@Test
	public void TFalseOptional_FM4_F1() {
		assertEquals(FM4_DATA.get(FM4_F10), FeatureStatus.FALSE_OPTIONAL);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F0() {
		assertFalse(FM4_F2.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F1() {
		assertTrue(FM4_F3.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F2() {
		assertFalse(FM4_F4.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F3() {
		assertFalse(FM4_F5.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F4() {
		assertTrue(FM4_F6.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F5() {
		assertTrue(FM4_F7.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F6() {
		assertFalse(FM4_F8.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F7() {
		assertFalse(FM4_F9.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
	@Test
	public void TIndeterminate_Hidden_FM1_F8() {
		assertTrue(FM4_F1.getFeatureStatus() == FeatureStatus.INDETERMINATE_HIDDEN);
	}
	
}
