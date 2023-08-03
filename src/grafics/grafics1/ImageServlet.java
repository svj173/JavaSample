package grafics.grafics1;

// $Id$
/*
 * ImageServlet
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import com.sun.image.codec.jpeg.JPEGCodec;

/**
 * �������, ����������� ������������ �����������.
 *
 * <p>� �������� ��������� �������� ����� ���������� ��� ������,
 * ������������ ��������� {@link ImageProducer ImageProducer}.
 * ���� ����� ����� ����������� ��� �������� ��������.
 *
 * <p>������:<br>
 * <code>http://www.home.ru/servlets/ImageServlet?JIMIProducer</code>
 *
 * @see ImageProducer
 */
public class ImageServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		try {
			ImageProducer imageProducer =
				(ImageProducer) Class.forName(request.getQueryString()).newInstance();
			
			response.setContentType(imageProducer.getMIMEType());
			imageProducer.createImage(response.getOutputStream());
		} catch (Exception e) {
			throw new ServletException(e.toString());
		}
	}
}