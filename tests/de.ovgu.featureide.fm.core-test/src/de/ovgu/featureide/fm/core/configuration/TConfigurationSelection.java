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
package de.ovgu.featureide.fm.core.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.ovgu.featureide.fm.core.FeatureModel;

/**
 * Tests about feature selection.
 * 
 * @author Fabian Benduhn
 */
public class TConfigurationSelection extends AbstractConfigurationTest{
	
	@Override
	FeatureModel loadModel() 
	{
		return loadGUIDSL("S : [A] [B] C :: _S; %% not B;");
	}

	
	@Test
	public void testSelection1() 
	{
		Configuration c = new Configuration(fm, true);
//		Configuration c = new Configuration(fm, false);
		c.setManual("C", Selection.SELECTED);
		assertTrue(c.valid());
		assertEquals(2, c.number());
	}

	@Test
	public void testSelection2() 
	{
		Configuration c = new Configuration(fm, true);
		assertTrue(c.valid());
		assertEquals(2, c.number());
	}

	@Test
	public void testSelection3() 
	{
		Configuration c = new Configuration(fm, true);
//		Configuration c = new Configuration(fm, false);
		c.setManual("A", Selection.SELECTED);
		c.setManual("C", Selection.SELECTED);
		assertTrue(c.valid());
		assertEquals(1, c.number());
	}

	@Test
	public void testSelection4() 
	{
		Configuration c = new Configuration(fm, true);
		c.setManual("A", Selection.SELECTED);
		assertTrue(c.valid());
		assertEquals(1, c.number());
	}

	@Test
	public void testSelection5() 
	{
		Configuration c = new Configuration(fm, true);
		boolean exception = false;
		try {
			c.setManual("B", Selection.SELECTED);
		} catch (SelectionNotPossibleException e) {
			exception = true;
		}
		assertTrue(exception);
	}


}
