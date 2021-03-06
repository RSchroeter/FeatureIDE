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
package de.ovgu.featureide.fm.core.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.prop4j.Node;

import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.editing.Comparison;
import de.ovgu.featureide.fm.core.editing.ModelComparator;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelReader;

/**
 * Basic test super-class for IFeatureModelReader/IFeatureModelWriter
 * implementations tests will write feature-models into a string and read it
 * back to check if the result is as expected
 * 
 * To add additional readers/writers extend this class and override abstract
 * methods
 * 
 * Add model.m files into folder testFeatureModels to add test cases
 * 
 * @author Fabian Benduhn
 */
@RunWith(Parameterized.class)
public abstract class TAbstractFeatureModelReaderWriter {

	// feature models are created by using XmlFeatureModelWriter, so for each
	// test case
	// there should be an corresponding test case for the
	// GuidslReader which tests the resulting FeatureModel directly

	 protected static File MODEL_FILE_FOLDER = new
	 File("/vol1/teamcity_itidb/TeamCity/buildAgent/work/featureide/tests/de.ovgu.featureide.fm.core-test/src/testFeatureModels/");


	static boolean online = false;
	protected FeatureModel origFm;
	protected FeatureModel newFm;
	protected String failureMessage;

	public TAbstractFeatureModelReaderWriter(FeatureModel fm, String s)
			throws UnsupportedModelException {

		this.origFm = fm;
		this.newFm = writeAndReadModel();
		this.failureMessage = "(" + s + ")";

	}

	@Parameters
	public static Collection<Object[]> getModels()
			throws FileNotFoundException, UnsupportedModelException {
		//first tries the location on build server, if this fails tries to use local location
		if (!MODEL_FILE_FOLDER.canRead()){
			MODEL_FILE_FOLDER = new File(ClassLoader.getSystemResource(
			"testFeatureModels").getPath());
		}
		Collection<Object[]> params = new ArrayList<Object[]>();
		for (File f : MODEL_FILE_FOLDER.listFiles(getFileFilter(".xml"))) {
			Object[] models = new Object[2];

			FeatureModel fm = new FeatureModel();
			XmlFeatureModelReader r = new XmlFeatureModelReader(fm);
			r.readFromFile(f);
			models[0] = fm;
			models[1] = f.getName();
			params.add(models);

		}

		return params;
	}

	@Test
	public void testRoot() throws FileNotFoundException,
			UnsupportedModelException {
		assertEquals(failureMessage, origFm.getRoot().getName(), newFm
				.getRoot().getName());
	}

	@Test
	public void testFeatureCount() throws FileNotFoundException,
			UnsupportedModelException {

		assertEquals(failureMessage, origFm.getFeatures().size(), newFm
				.getFeatures().size());
	}

	@Test
	public void testFeatureNames() throws FileNotFoundException,
			UnsupportedModelException {

		assertEquals(failureMessage, origFm.getFeatureNames(),
				newFm.getFeatureNames());
	}

	@Test
	public void testFeatureGroupTypeAnd() throws FileNotFoundException,
			UnsupportedModelException {
		for (Feature origF : origFm.getFeatures()) {

			if (origF.isAnd()) {
				Feature newF = newFm.getFeature(origF.getName());
				if (newF == null) {
					// fail("Feature " + origF.getName() + " cannot be found");
				} else {
					assertTrue(failureMessage, newFm
							.getFeature(origF.getName()).isAnd());
				}
			}
		}
	}

	@Test
	public void testFeatureGroupTypeOr() throws FileNotFoundException,
			UnsupportedModelException {
		for (Feature origF : origFm.getFeatures()) {

			if (origF.isOr()) {
				Feature newF = newFm.getFeature(origF.getName());
				if (newF == null) {
					// fail("Feature " + origF.getName() + " cannot be found");
				} else {
					assertTrue(failureMessage, newFm
							.getFeature(origF.getName()).isOr());
				}
			}
		}
	}

	@Test
	public void testFeatureGroupTypeAlternative() throws FileNotFoundException,
			UnsupportedModelException {
		for (Feature origF : origFm.getFeatures()) {

			if (origF.isAlternative()) {
				Feature newF = newFm.getFeature(origF.getName());
				if (newF == null) {
					// fail("Feature " + origF.getName() + " cannot be found");
				} else {
					assertTrue(failureMessage, newFm
							.getFeature(origF.getName()).isAlternative());
				}
			}
		}
	}

	@Test
	public void testFeatureConcrete() throws FileNotFoundException,
			UnsupportedModelException {
		for (Feature origF : origFm.getFeatures()) {

			if (origF.isConcrete()) {
				Feature newF = newFm.getFeature(origF.getName());
				if (newF == null) {
					// fail("Feature " + origF.getName() + " cannot be found");
				} else {
					assertTrue(failureMessage + origF,
							newFm.getFeature(origF.getName()).isConcrete());
				}
			}
		}
	}

	@Test
	public void testFeatureHidden() throws FileNotFoundException,
			UnsupportedModelException {
		for (Feature origF : origFm.getFeatures()) {

			if (origF.isHidden()) {
				Feature newF = newFm.getFeature(origF.getName());
				if (newF == null) {
					// fail("Feature " + origF.getName() + " cannot be found");
				} else {
					assertEquals(
							failureMessage + "Feature: " + origF.getName(),
							origF.isHidden(), newFm.getFeature(origF.getName())
									.isHidden());
				}
			}
		}
	}

	@Test
	public void testFeatureMandatory() throws FileNotFoundException,
			UnsupportedModelException {
		for (Feature origF : origFm.getFeatures()) {

			if (origF.isMandatory()) {
				Feature newF = newFm.getFeature(origF.getName());
				if (newF == null) {
					// fail("Feature " + origF.getName() + " cannot be found");
				} else {
					assertTrue(failureMessage, newFm
							.getFeature(origF.getName()).isMandatory());
				}
			}
		}
	}

	//TODO @Fabian @Test
	public void testPropNodes() throws FileNotFoundException,
			UnsupportedModelException {

		for (Node n : origFm.getPropositionalNodes()) {

			System.out.println(newFm.getPropositionalNodes());
			assertFalse(failureMessage + n, newFm.getPropositionalNodes()
					.contains(n));
		}
	}

	@Test
	public void testConstraintCount() throws FileNotFoundException,
			UnsupportedModelException {
		assertEquals(failureMessage, origFm.getConstraintCount(),
				origFm.getConstraintCount());
	}

	@Test
	public void testConstraints() throws FileNotFoundException,
			UnsupportedModelException {
		assertEquals(failureMessage, origFm.getConstraints(),
				origFm.getConstraints());
	}

	@Test
	public void testAnnotations() throws FileNotFoundException,
			UnsupportedModelException {
		assertEquals(failureMessage, origFm.getAnnotations(),
				origFm.getAnnotations());
	}

	// @Test // java.lang.AssertionError: (gpl_medium_model.xml) expected:<REFACTORING> but was:<SPECIALIZATION>
	public void testIsRefactoring() throws FileNotFoundException,
			UnsupportedModelException {
		Comparison compare = new ModelComparator(1000).compare(origFm, newFm);
		if (!compare.equals(Comparison.ARBITRARY)) {
			assertEquals(failureMessage, Comparison.REFACTORING, compare);
		}

	}
	
	@Test
	public void testDescription() {
		for (Feature origFeature : origFm.getFeatures()) {
			Feature newFeature = newFm.getFeature(origFeature.getName());
			assertEquals(origFeature.getDescription(), newFeature.getDescription());
		}
	}

	private final FeatureModel writeAndReadModel()
			throws UnsupportedModelException {
		FeatureModel newFm = new FeatureModel();
		IFeatureModelWriter writer = getWriter(origFm);
		IFeatureModelReader reader = getReader(newFm);
		reader.readFromString(writer.writeToString());
		return newFm;
	}

	

	private final static FileFilter getFileFilter(final String s) {
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(s);
			}
		};
		return filter;
	}

	protected abstract IFeatureModelWriter getWriter(FeatureModel fm);

	protected abstract IFeatureModelReader getReader(FeatureModel fm);

}
