package io.despick.opensaml.example;

import io.despick.opensaml.saml.HTTPRedirectSender;
import io.despick.opensaml.saml.SingleLogout;
import io.despick.opensaml.saml.session.UserSession;
import io.despick.opensaml.web.AuthFilter;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.core.LogoutRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "logoutServlet", urlPatterns = "/logout")
public class LogoutButton extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(AuthFilter.AUTHENTICATED_SESSION_ATTRIBUTE) != null) {
            UserSession userSession = (UserSession) request.getSession().getAttribute(AuthFilter.AUTHENTICATED_SESSION_ATTRIBUTE);
            LogoutRequest logoutRequest = new SingleLogout().buildLogoutRequest(userSession);

            HTTPRedirectSender.sendLogoutRequestRedirectMessage(response, logoutRequest, SAMLConstants.SAML2_REDIRECT_BINDING_URI);
        } else {
            //do nothing. user is not logged in
        }
    }

}