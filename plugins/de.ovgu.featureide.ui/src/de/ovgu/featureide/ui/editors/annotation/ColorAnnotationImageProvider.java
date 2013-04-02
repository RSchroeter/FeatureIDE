/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2011  FeatureIDE Team, University of Magdeburg
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.ui.editors.annotation;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;


/**
 * Image for the annotations shown in the vertical ruler
 * 
 * @author Sebastian Krieter
 */
public class ColorAnnotationImageProvider implements IAnnotationImageProvider {

	private static final class ColorAnnotationDescriptor extends ImageDescriptor {
		private final ImageData imgdata;

		public ColorAnnotationDescriptor(int color) {
			imgdata = new ImageData(10, 15, 1, new PaletteData(
					new RGB[] { ColorPalette.getRGB(color, 0.6f) }));
		}
		
		@Override
		public ImageData getImageData() {
			return imgdata;
		}
	}

	@Override
	public Image getManagedImage(Annotation annotation) {
		return null;
	}

	@Override
	public String getImageDescriptorId(Annotation annotation) {
		if (annotation instanceof ColorAnnotation) {
			ColorAnnotation ca = (ColorAnnotation) annotation;
			if (ca.isImageAnnotation()) {
				return ca.getId();
			}
		}
		return null;
	}

	@Override
	public ImageDescriptor getImageDescriptor(String imageDescritporId) {
		return new ColorAnnotationDescriptor(Integer.parseInt(imageDescritporId));
	}

}
