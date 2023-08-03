package grafics.grafics1;

// $Id$
/*
 * ImageProducer
 *
 * Copyright (c) 2000 Ken McCrary, All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies.
 *
 * KEN MCCRARY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. KEN MCCRARY
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT
 * OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * Copyright (c) 2000 ������� ������ ������� (victor@uwc.ru)
 */
import java.io.OutputStream;
import java.io.IOException;

/**
 * ������, ������������ �����������, ������ ������������� ���� ���������.
 *
 * @version $Revision$ $Date$
 */
public interface ImageProducer {
	/**
	 * MIME ��� ������������ �����������.
	 *
	 * @return MIME ��� �����������.
	 */
	public String getMIMEType();
	
	/**
	 * ������� ����������� � ���������� ��� � ��������� �����.
	 *
	 * @param stream ���� ������ ��������.
	 */
	public void createImage (OutputStream stream) throws IOException;
}