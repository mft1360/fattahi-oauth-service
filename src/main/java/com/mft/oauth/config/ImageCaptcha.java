package com.mft.oauth.config;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jj.play.ns.nl.captcha.Captcha;

public class ImageCaptcha extends HttpServlet {
	public static final String CAPTCHA_SESSION_FIELD_NAME = "imageCaptcha";
	private static final long serialVersionUID = -4766303743133276027L;

	public static boolean validateResponse(HttpServletRequest request, String answer) {
		Captcha captcha = (Captcha) request.getSession().getAttribute(CAPTCHA_SESSION_FIELD_NAME);
		return (captcha.isCorrect(answer));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "image/jpg");
		OutputStream out = resp.getOutputStream();
		Captcha captcha = new Captcha.Builder(270, 50).addText().build();
		req.getSession().setAttribute(CAPTCHA_SESSION_FIELD_NAME, captcha);
		ImageIO.write(captcha.getImage(), "jpg", out);
		out.flush();
		out.close();
	}

}